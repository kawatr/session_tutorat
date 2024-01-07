package admin_user.controller;

import admin_user.model.SessionTutorat;
import admin_user.model.User;
import admin_user.service.AdminDashboardService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
    	 System.out.println("Inside adminDashboard method");
        // Retrieve sessions and users from the service
        List<SessionTutorat> sessions = adminDashboardService.getAllSessions();
        List<User> users = adminDashboardService.getAllUsers();

        // Add the data to the model
        model.addAttribute("sessions", sessions);
        model.addAttribute("users", users);
        
        long totalSessions = adminDashboardService.getTotalSessions();
        long totalUsers = adminDashboardService.getTotalUsers();

        model.addAttribute("totalSessions", totalSessions);
        model.addAttribute("totalUsers", totalUsers);

        // Return the admin dashboard view
        return "admin_dashboard";
    }
    
    @GetMapping("/admin/sessions/add")
    public String showAddSessionForm(Model model) {
        SessionTutorat session = new SessionTutorat();
        List<User> tutors = adminDashboardService.getTutors();

        model.addAttribute("session", session);
        model.addAttribute("tutors", tutors);

        return "add_form";
    }


    @PostMapping("/admin/sessions/add")
    public String addSession(@ModelAttribute("session") @Valid SessionTutorat session, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Gérer les erreurs de validation
            model.addAttribute("tutors", adminDashboardService.getTutors());
            return "add_form";
        }

        
        
        // Récupérer le tuteur depuis la base de données
        User tuteur = adminDashboardService.getUserById(session.getTuteur().getId());
        
        if (tuteur == null) {
            // Gérer le cas où le tuteur n'est pas trouvé
            result.rejectValue("tuteur.id", "session.tuteur.id", "Invalid tuteur");
            model.addAttribute("tutors", adminDashboardService.getTutors());
            return "add_form";
        }

        session.setTuteur(tuteur);

        // Ajouter l'objet "session" au modèle
        model.addAttribute("session", session);

        adminDashboardService.addSession(session);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/admin/sessions/delete/{sessionId}")
    public String deleteSession(@PathVariable Long sessionId) {
        adminDashboardService.deleteSession(sessionId);
        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/admin/sessions/update/{sessionId}")
    public String showUpdateSessionForm(@PathVariable Long sessionId, Model model) {
        // Retrieve the existing session by ID
        SessionTutorat existingSession = adminDashboardService.getSessionById(sessionId);

        if (existingSession == null) {
            // Handle the case where the session is not found
            // You might redirect to an error page or handle it based on your requirements
            return "redirect:/admin/dashboard";
        }

        List<User> tutors = adminDashboardService.getTutors();

        // Add the existing session and tutors to the model
        model.addAttribute("existingSession", existingSession);
        model.addAttribute("tutors", tutors);

        // Return the update form view
        return "update_form";
    }

    @PostMapping("/admin/sessions/update/{sessionId}")
    public String updateSession(@PathVariable Long sessionId,
                                @ModelAttribute("existingSession") @Valid SessionTutorat updatedSession,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            model.addAttribute("tutors", adminDashboardService.getTutors());
            return "update_form";
        }

        // Retrieve the existing session by ID
        SessionTutorat existingSession = adminDashboardService.getSessionById(sessionId);

        if (existingSession == null) {
            // Handle the case where the session is not found
            // You might redirect to an error page or handle it based on your requirements
            return "redirect:/admin/dashboard";
        }

        // Set the tuteur property in the existing session with the ID from the updated session
        existingSession.setTuteur(updatedSession.getTuteur());

        // Update the existing session with the new data
        existingSession.setDate(updatedSession.getDate());
        existingSession.setDescription(updatedSession.getDescription());
        
        // Update other fields as needed

        // Save the updated session
        adminDashboardService.updateSession(existingSession);

        // Redirect to the admin dashboard after updating
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/users/add")
    public String showAddUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "add_user_form";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        String errorMessage = adminDashboardService.addUser(user);

        if (errorMessage != null) {
            // Il y a eu une erreur, renvoyer le message à la vue
            model.addAttribute("errorMessage", errorMessage);
            return "add_user_form";
        }

        // Rediriger vers une autre page ou faire d'autres traitements en cas de succès
        return "redirect:/admin/dashboard";
    }


    






    
    
}
