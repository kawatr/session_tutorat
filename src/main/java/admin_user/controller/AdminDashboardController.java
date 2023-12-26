package admin_user.controller;

import admin_user.model.SessionTutorat;
import admin_user.model.User;
import admin_user.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
