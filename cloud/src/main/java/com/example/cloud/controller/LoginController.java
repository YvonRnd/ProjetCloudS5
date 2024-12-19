package com.example.cloud.controller;

import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.model.Parametre;
import com.example.cloud.model.Pin;
import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.service.DetailUtilisateurService;
import com.example.cloud.service.SessionService;
import com.example.cloud.service.UtilisateurService;
import com.example.cloud.util.BCryptUtils;
import com.example.cloud.service.EmailService;
import com.example.cloud.service.ParametreService;
import com.example.cloud.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RedisService redisService;
    private ParametreService parametreService;

    

    // Stock temporaire pour le PIN généré
    private Pin currentPin;

    public LoginController(UtilisateurService us, DetailUtilisateurService dus, SessionService ss, EmailService es) {
        this.utilisateurService = us;
        this.detailUtilisateurService = dus;
        this.sessionService = ss;
        this.emailService = es;
    }
    int idAko = 0;
    Parametre parametre = parametreService.getById(idAko);
    int tentative = parametre.getTentation();
    String tentativeString = Integer.toString(tentative);

    @PostMapping("/send-pin")
    @ResponseBody
    public String sendPin(@RequestParam String email) {
        try {
            // Vérifier si l'utilisateur existe
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return "Aucun utilisateur trouvé pour cet email.";
            }
            int idAko = 0;
            Parametre parametre = parametreService.getById(idAko);
            String dPinString = parametre.getDPin();
            int dPin = Integer.parseInt(dPinString);
            // Générer un PIN
            currentPin = new Pin().generatePin(dPin);
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
    public String verifyPin(
        @RequestParam String email,
        @RequestParam String enteredPin) {
        if (currentPin == null) {
            return "Aucun PIN n'a été généré.";
        }
        Utilisateur utilisateur = utilisateurService.getByEmail(email);
        if (utilisateur == null) {
            return "Utilisateur non trouvé.";
        }
        String tentativeRestString = (String) redisService.getData("attempts" + email);
        int tentative = Integer.parseInt(tentativeRestString);
        LocalDateTime now = LocalDateTime.now();

        if(tentative == 0){
            return "vous avez essayer tout votre tentative.";
        }
        // Vérification si le PIN correspond
        if (!currentPin.getPin().equals(enteredPin)) {
            tentative = tentative -1;
            String tentativeString = Integer.toString(tentative);
            redisService.updateData("attempts" + email, tentativeString);
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
        int idAko = 0;
            Parametre parametre = parametreService.getById(idAko);
            String dSessionString = parametre.getDSession();
            int dSession = Integer.parseInt(dSessionString);
        if (utilisateurAko == null){
            return "votre email est incorrecte";
        }
        String passwordCrypt = BCryptUtils.hashPassword(mdp);
        if(passwordCrypt == utilisateurAko.getPassword()){
            Session sessionCreate = new Session(utilisateurAko, dSession);
            sessionService.enregistrerSession(sessionCreate);
            redisService.updateData("attempts" + email, tentativeString);
            return "validation correcte";
        }

        return "Validation incorrecte";
    }
}
