package admin_user.service;

import admin_user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import admin_user.dto.UserDto;
import admin_user.model.User;
import admin_user.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(UserDto userDto) {
		User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getFullname());
		return userRepository.save(user);
	}

	@Override
	public List<User> getEtudiants() {
		return userRepository.findUsersByRole(UserRole.ETUDIANT.getValue());
	}

	@Override
	public List<User> getTuteurs() {
		return userRepository.findUsersByRole(UserRole.TUTEUR.getValue());
	}

	@Override
	public void updateUserEmailAndName(User user) throws Exception {
		User userFound = userRepository.findById(user.getId()).orElseThrow(() -> new Exception("user not found"));
		userFound.setEmail(user.getEmail());
		userFound.setFullname(user.getFullname());
		userRepository.save(userFound);
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		userRepository.findById(id).orElseThrow(() -> new Exception("user not found"));
		userRepository.deleteById(id);
	}
}
