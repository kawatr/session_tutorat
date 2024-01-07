package admin_user.service;

import java.util.Map;

import admin_user.dto.UserDto;
import admin_user.model.User;

public interface UserService {
	
	User save (UserDto userDto);
	 User findByEmail(String email);

	

}
