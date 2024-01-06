package admin_user.controller;

import admin_user.model.User;
import admin_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("etudiants")
public class EtudiantController {

    @Autowired
    UserService userService;

    @GetMapping
    public String gestionEtudiantPage(Model model) {
        List<User> etudiants = userService.getEtudiants();
        model.addAttribute("etudiants", etudiants);
        return "gestion_etudiant";
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateEtudiant(@RequestBody User etudiant) {
        try {
            userService.updateUserEmailAndName(etudiant);
            return ResponseEntity.ok("Etudiant updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating etudiant: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEtudiant(@RequestBody Map<String, Long> requestBody) {
        Long etudiantId = requestBody.get("id");

        try {
            userService.deleteUser(etudiantId);
            return ResponseEntity.ok("Etudiant deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting etudiant: " + e.getMessage());
        }
    }
}
