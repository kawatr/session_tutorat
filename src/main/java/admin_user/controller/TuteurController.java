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
@RequestMapping("tuteurs")
public class TuteurController {

    @Autowired
    UserService userService;

    @GetMapping
    public String gestionTuteurPage(Model model) {
        List<User> tuteurs = userService.getTuteurs();
        model.addAttribute("tuteurs", tuteurs);
        return "gestion_tuteur";
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateTuteur(@RequestBody User tuteur) {
        try {
            userService.updateUserEmailAndName(tuteur);
            return ResponseEntity.ok("Tuteur updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating tuteur: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEtudiant(@RequestBody Map<String, Long> requestBody) {
        Long tuteurId = requestBody.get("id");

        try {
            userService.deleteUser(tuteurId);
            return ResponseEntity.ok("Tuteur deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting tuteur: " + e.getMessage());
        }
    }
}
