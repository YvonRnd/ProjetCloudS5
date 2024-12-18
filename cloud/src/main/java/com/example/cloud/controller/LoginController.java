package com.example.cloud.controller;

import com.example.cloud.model.Pin;
import com.example.cloud.service.DetailUtilisateurService;
import com.example.cloud.service.SessionService;
import com.example.cloud.service.UtilisateurService;
import com.example.cloud.service.EmailService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/login")
public class LoginController {
    private final UtilisateurService utilisateurService;
    private final DetailUtilisateurService detailUtilisateurService;
    private final SessionService sessionService;
    private final EmailService emailService;

    // Stock temporaire pour le PIN généré
    private Pin currentPin;

    public LoginController(UtilisateurService us, DetailUtilisateurService dus, SessionService ss, EmailService es) {
        this.utilisateurService = us;
        this.detailUtilisateurService = dus;
        this.sessionService = ss;
        this.emailService = es;
    }

    @PostMapping("/send-pin")
    @ResponseBody
    public String sendPin(@RequestParam String email) {
        try {
            // Générer un PIN
            currentPin = new Pin().generatePin();
            System.out.println(currentPin.getPin());

            // Envoyer l'email
            emailService.sendPinEmail(email, currentPin.getPin());

            return "PIN envoyé avec succès à " + email + ".";
        } catch (Exception e) {
            e.printStackTrace();
            return "Échec de l'envoi du PIN à " + email + ". Erreur : " + e.getMessage();
        }
    }

    @PostMapping("/verify-pin")
    @ResponseBody
    public String verifyPin(@RequestParam String enteredPin) {
        if (currentPin == null) {
            return "Aucun PIN n'a été généré.";
        }

        LocalDateTime now = LocalDateTime.now();

        // Vérification si le PIN correspond
        if (!currentPin.getPin().equals(enteredPin)) {
            return "Le PIN saisi est incorrect.";
        }

        // Vérification de la validité (expiration)
        if (now.isAfter(currentPin.getDateExpiration())) {
            return "Le PIN a expiré.";
        }

        return "Le PIN est valide.";
    }
}
