package admin_user.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import admin_user.dto.UserDto;

import admin_user.repositories.UserRepository;
import admin_user.service.UserService;


import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;
    

    @Autowired
    private UserService userService;
    

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            // Si un utilisateur est déjà connecté, redirigez-le vers la page appropriée
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);

            // Ajoutez une logique supplémentaire si nécessaire pour rediriger vers la page spécifique
            if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin-page";
            } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTEUR"))) {
                return "redirect:/tuteur-page";
            } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ETUDIANT"))) {
                return "redirect:/etudiant-page";
            } else {
                return "redirect:/user-page";
            }
        }

        // Si aucun utilisateur n'est connecté, affichez directement la page d'accueil
        return "home";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfully!");
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user";
    }

    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }

    @GetMapping("/tuteur-page")
    public String tuteurPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "tuteur";
    }

    @GetMapping("/etudiant-page")
    public String etudiantPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "etudiant";
    }
    
    
}
