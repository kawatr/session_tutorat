package admin_user.service;

import admin_user.model.SessionTutorat;
import admin_user.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardService {

    @PersistenceContext
    private EntityManager entityManager;

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
        return entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class)
            .getSingleResult();
    }
  
}
