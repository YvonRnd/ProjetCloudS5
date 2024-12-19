package com.example.cloud.controller;

import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.model.Pin;
import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.service.DetailUtilisateurService;
import com.example.cloud.service.SessionService;
import com.example.cloud.service.UtilisateurService;
import com.example.cloud.util.BCryptUtils;
import com.example.cloud.service.EmailService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PostMapping("/signup")
    @ResponseBody
    public String signUp(
        @RequestBody String email, 
        @RequestBody String mdp,    
        @RequestBody String nom,
        @RequestBody String prenom,
        @RequestBody LocalDate dateNaissance,
        @RequestBody String telephone
        ) {
            if (email == null) {
                return "Votre email est incrorecte";
            }
            if (mdp == null) {
                return "Votre mdp est vide";
            }
            Utilisateur utilisateurAko = utilisateurService.getByEmail(email);
            if (utilisateurAko == null){
                return "votre email est incorrecte";
            }
            if(nom == null){
                return "le nom est null";
            }
            if(prenom == null){
                return "le prenom est null";
            }
            if(dateNaissance == null){
                return "le dateNaissance est null";
            }
            DetailUtilisateur detailUtilisateur = new DetailUtilisateur(nom,prenom,telephone,dateNaissance);
            
            detailUtilisateurService.enregistrerDetailUtilisateur(detailUtilisateur);
            System.out.println("le detail est enregistrer");
        Utilisateur utilisateur = new Utilisateur(email,mdp,detailUtilisateur);
        System.out.println("Votre mdp crypter :" + utilisateur.getPassword());

        utilisateurService.enregistrerUtilisateur(utilisateur);

        return "Utilisateur enregistrer correctement";
    }

    // @PostMapping("/detail-Utilisateur")
    // @ResponseBody
    // public String detailUtilisateur(
    //     @RequestBody String nom,
    //     @RequestBody String prenom,
    //     @RequestBody LocalDate dateNaissance,
    //     @RequestBody String telephone
    // ){
    //     if(nom == null){
    //         return "le nom est null";
    //     }
    //     if(prenom == null){
    //         return "le prenom est null";
    //     }
    //     if(dateNaissance == null){
    //         return "le dateNaissance est null";
    //     }

    //     DetailUtilisateur detailUtilisateur = new DetailUtilisateur(nom,prenom,telephone,dateNaissance);
        
    //     detailUtilisateurService.enregistrerDetailUtilisateur(detailUtilisateur);
    //     return"detail Utilisateur Enregistrer";
    // }

    @PostMapping("/login")
    @ResponseBody
    public String login(
    @RequestBody String email, 
    @RequestBody String mdp) {
        if (email == null) {
            return "Votre email est incrorecte";
        }
        if (mdp == null) {
            return "Votre mdp est vide";
        }
        Utilisateur utilisateurAko = utilisateurService.getByEmail(email);
        if (utilisateurAko == null){
            return "votre email est incorrecte";
        }
        String passwordCrypt = BCryptUtils.hashPassword(mdp);
        if(passwordCrypt == utilisateurAko.getPassword()){
            Session sessionCreate = new Session(utilisateurAko);
            sessionService.enregistrerSession(sessionCreate);
            return "validation correcte";
        }

        return "Validation incorrecte";
    }
}
