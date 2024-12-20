package com.example.cloud.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloud.dto.ApiResponse;
import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.model.Parametre;
import com.example.cloud.model.Pin;
import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.service.DetailUtilisateurService;
import com.example.cloud.service.EmailService;
import com.example.cloud.service.ParametreService;
import com.example.cloud.service.RedisService;
import com.example.cloud.service.SessionService;
import com.example.cloud.service.UtilisateurService;
import com.example.cloud.util.BCryptUtils;


@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UtilisateurService utilisateurService;
    private final EmailService emailService;
    private final ParametreService parametreService;
    private final RedisService redisService;
    private final SessionService sessionService;
    private final DetailUtilisateurService detailUtilisateurService;

    public LoginController(UtilisateurService utilisateurService,
                           EmailService emailService,
                           ParametreService parametreService,
                           RedisService redisService,
                           SessionService sessionService,
                           DetailUtilisateurService detailUtilisateurService) {
        this.utilisateurService = utilisateurService;
        this.emailService = emailService;
        this.parametreService = parametreService;
        this.redisService = redisService;
        this.sessionService = sessionService;
        this.detailUtilisateurService = detailUtilisateurService;
    }

    // int idAko = 0;
    // Parametre parametre = parametreService.getById(idAko);
    // int tentative = parametre.getTentation();
    // String tentativeString = Integer.toString(tentative);

    @PostMapping("/send-pin")
public ResponseEntity<ApiResponse<String>> sendPin(@RequestParam String email) {
    try {
        // Vérifier si l'email est valide
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Email invalide.", null, false));
        }

        // Vérifier si l'utilisateur existe dans la base de données
        Utilisateur utilisateur = utilisateurService.getByEmail(email);
        if (utilisateur == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Aucun utilisateur trouvé pour cet email.", null, false));
        }

        // Récupérer les paramètres système, y compris la durée d'expiration du PIN
        Parametre parametre = parametreService.getById(0);
        int dPin = Integer.parseInt(parametre.getDPin()); // Longueur du PIN
        int pinExpiration = Integer.parseInt(parametre.getDPin()); // Durée d'expiration en secondes

        // Générer un PIN sécurisé
        Pin currentPin = new Pin().generatePin(dPin);

        // Stocker le PIN dans Redis avec une expiration
        redisService.saveDataWithExpiration(email, currentPin.getPin(), pinExpiration);
        redisService.resetAttempts(email);

        // Envoyer le PIN par email
        emailService.sendPinEmail(email, currentPin.getPin());

        return ResponseEntity.ok(new ApiResponse<>("PIN envoyé avec succès.", "PIN envoyé à " + email, true));
    } catch (NumberFormatException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ApiResponse<>("Erreur dans les paramètres du système. Veuillez vérifier les valeurs.", null, false));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ApiResponse<>("Échec de l'envoi du PIN.", e.getMessage(), false));
    }
}


    @PostMapping("/verify-pin")
    public ResponseEntity<ApiResponse<String>> verifyPin(@RequestParam String email, @RequestParam String enteredPin) {
        try {
            String currentPin = redisService.getData(email); // Utilisation de Redis pour obtenir le PIN
            if (currentPin == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Aucun PIN n'a été généré.", null, false));
            }
    
            // Vérifier si le PIN est correct
            if (!currentPin.equals(enteredPin)) {
                int attemptsLeft = redisService.getAttempts(email);
                if (attemptsLeft <= 0) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>("Tentatives épuisées.", null, false));
                }
                redisService.decrementAttempts(email); // Réduire les tentatives restantes
                return ResponseEntity.badRequest().body(new ApiResponse<>("PIN incorrect.", null, false));
            }
    
            redisService.resetAttempts(email); // Réinitialiser les tentatives après succès
            return ResponseEntity.ok(new ApiResponse<>("PIN validé avec succès.", null, true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponse<>("Erreur lors de la vérification du PIN.", e.getMessage(), false));
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody Map<String, Object> payload) {
        try {
            // Récupération des données
            String email = (String) payload.get("email");
            String mdp = (String) payload.get("mdp");
            String nom = (String) payload.get("nom");
            String prenom = (String) payload.get("prenom");
            String telephone = (String) payload.get("telephone");
            LocalDate dateNaissance = LocalDate.parse((String) payload.get("dateNaissance"));

            // Validation des champs
            if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Email invalide.", null, false));
            }
            if (mdp == null || mdp.length() < 6) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Le mot de passe doit contenir au moins 6 caractères.", null, false));
            }
            if (nom == null || prenom == null || dateNaissance == null || telephone == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Tous les champs sont requis.", null, false));
            }

            // Vérification si l'utilisateur existe déjà
            if (utilisateurService.getByEmail(email) != null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Cet email est déjà utilisé.", null, false));
            }

            // Création de l'utilisateur et des détails associés
            DetailUtilisateur detailUtilisateur = new DetailUtilisateur(nom, prenom, telephone, dateNaissance);
            detailUtilisateurService.enregistrerDetailUtilisateur(detailUtilisateur);

            String hashedPassword = BCryptUtils.hashPassword(mdp);
            Utilisateur utilisateur = new Utilisateur(email, hashedPassword, detailUtilisateur);
            utilisateurService.enregistrerUtilisateur(utilisateur);

            return ResponseEntity.ok(new ApiResponse<>("Utilisateur enregistré avec succès.", null, true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponse<>("Erreur lors de l'enregistrement de l'utilisateur.", e.getMessage(), false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody Map<String, String> payload) {
        try {
            // Récupération des données
            String email = payload.get("email");
            String mdp = payload.get("mdp");

            // Validation des entrées
            if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Email invalide.", null, false));
            }
            if (mdp == null || mdp.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Le mot de passe est requis.", null, false));
            }

            // Vérification de l'utilisateur
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Utilisateur non trouvé.", null, false));
            }

            // Vérification du mot de passe
            if (!BCryptUtils.verifyPassword(mdp, utilisateur.getPassword())) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Mot de passe incorrect.", null, false));
            }

            else{
                // Création de la session
            Parametre parametre = parametreService.getById(0);
            int dSession = Integer.parseInt(parametre.getDSession());
            Session session = new Session(utilisateur, dSession);
            sessionService.enregistrerSession(session);
            return ResponseEntity.ok(new ApiResponse<>("Connexion réussie.", "Session créée pour l'utilisateur.", true));
            }

           
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponse<>("Erreur lors de la connexion.", e.getMessage(), false));
        }
    }
}
