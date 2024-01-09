package admin_user.service;

import admin_user.model.SessionTutorat;
import admin_user.model.User;
import admin_user.repositories.SessionTutoratRepository;
import admin_user.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired 
    private SessionTutoratRepository sessionTutoratRepository;

    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public List<SessionTutorat> getAllSessions() {
        // Retrieve all sessions from the database
        return entityManager.createQuery("SELECT s FROM SessionTutorat s", SessionTutorat.class).getResultList();
    }

    @Transactional
    public List<User> getAllUsers() {
        // Retrieve all users from the database
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    @Transactional
    public long getTotalSessions() {
        return entityManager.createQuery("SELECT COUNT(s) FROM SessionTutorat s", Long.class)
            .getSingleResult();
    }

    @Transactional
    public long getTotalUsers() {
        return entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.role != 'ADMIN'", Long.class)
                .getSingleResult();
    }


    @Transactional
    public void addSession(SessionTutorat session) {
        // Ajoutez la nouvelle session à la base de données
        entityManager.persist(session);
    }
    @Transactional
    public List<User> getTutors() {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.role = 'TUTEUR'", User.class).getResultList();
    }
    @Transactional

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void deleteSession(Long sessionId) {
        sessionTutoratRepository.deleteById(sessionId);
    }

    @Transactional
    public void updateSession(SessionTutorat updatedSession) {
        // Assuming the ID is set in the updatedSession object
        sessionTutoratRepository.save(updatedSession);
    }

    @Transactional
    public SessionTutorat getSessionById(Long sessionId) {
        // Use the repository to find the session by ID
        return sessionTutoratRepository.findById(sessionId).orElse(null);
    }

    @Transactional
    public String addUser(User user) {
        // Vérifier si un utilisateur avec la même adresse e-mail existe déjà
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "Une erreur s'est produite. L'adresse e-mail est déjà utilisée par un autre utilisateur.";
        }

        // Hacher le mot de passe avant de l'enregistrer
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        try {
            // ... le reste de votre logique d'ajout d'utilisateur
            userRepository.save(user); // Assuming you have a userRepository

            return null; // Aucune erreur, l'ajout s'est déroulé avec succès
        } catch (Exception e) {
            return "Une erreur s'est produite lors de l'ajout de l'utilisateur.";
        }
    }
    
    
    @Transactional
	public void deleteUser(Long userId) {
    	   userRepository.deleteById(userId);
	}

    @Transactional
    public void updateUser(User existingUser) {
        
        userRepository.save(existingUser);
    }




}
