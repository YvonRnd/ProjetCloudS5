package com.example.cloud.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloud.dto.ApiResponse;
import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.service.DetailUtilisateurService;
import com.example.cloud.service.SessionService;
import com.example.cloud.service.UtilisateurService;

@RestController
@RequestMapping("/api/accueil")
public class AccueilControlleur {
    private final UtilisateurService utilisateurService;
    private final DetailUtilisateurService detailUtilisateurService;
    private final SessionService sessionService;


    public AccueilControlleur(UtilisateurService us, DetailUtilisateurService dus, SessionService ss){
        this.utilisateurService = us;
        this.detailUtilisateurService = dus;
        this.sessionService = ss;
    }

    @PostMapping("/validation-Session")
    public ResponseEntity<ApiResponse<String>> validationSession(@RequestParam String email){
        try{
             // Vérifier si l'email est valide
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Email invalide.", null, false));
        }

        // Vérifier si l'utilisateur existe dans la base de données
        Utilisateur utilisateur = utilisateurService.getByEmail(email);
        if (utilisateur == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Aucun utilisateur trouvé pour cet email.", null, false));
        }
            Session session = sessionService.getByIdUtilisateur(utilisateur);
            if(session == null){
                return ResponseEntity.badRequest().body(new ApiResponse<>("vous n'etes pas encore connecter! ", null, false));
            }
            else{
                return ResponseEntity.badRequest().body(new ApiResponse<>("vous n'etes connecter! ", "idUtilisateur :" + utilisateur.getIdUtilisateur(), true));
            }
        
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>("Erreur dans les paramètres du système. Veuillez vérifier les valeurs.", null, false));
        }
    }
    @PostMapping("/detail-utilisateur")
    public ResponseEntity<ApiResponse<String>> modificationDU(
       @RequestBody Map<String, Object> payload
     ){
            // Récupération des données
            String email = (String) payload.get("email");
            String nom = (String) payload.get("nom");
            String prenom = (String) payload.get("prenom");
            String telephone = (String) payload.get("telephone");
            LocalDate dateNaissance = LocalDate.parse((String) payload.get("dateNaissance"));

            if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Email invalide.", null, false));
            }
            if (nom == null || prenom == null || dateNaissance == null || telephone == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Tous les champs sont requis.", null, false));
            }
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Aucun Utilisateur qui corespond au email.", null, false));
            }
            DetailUtilisateur updateDU = detailUtilisateurService.updateDetailUtilisateur(utilisateur.getDetailUtilisateur().getIdDetailUtilisateur(), nom, prenom, telephone, dateNaissance);
            return ResponseEntity.badRequest().body(new ApiResponse<>("Mise à jour effectuer.", "utilisateur : " + updateDU.getNom(), true));
    }

    @PostMapping("/reinitialiser-password")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> reinitialiserPassword(
        @RequestBody Map<String, String> payload
    ){
        String email = payload.get("email");
            String mdp1 = payload.get("mdp1");
            String mdp2 = payload.get("mdp2");
         // Validation des entrées
         if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Email invalide.", null, false));
        }
        if (mdp1 == null || mdp1.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Le mot de passe est requis.", null, false));
        }
        if(mdp1 != mdp2){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Le mot de passe ne correspond pas.", null, false));
        }
        else{
            // Vérification de l'utilisateur
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Utilisateur non trouvé.", null, false));
            }
            else{
                Utilisateur newUtilisateur = utilisateurService.reinitialiserMdp(utilisateur.getIdUtilisateur(),email,mdp1);
                return ResponseEntity.badRequest().body(new ApiResponse<>("votre mot de passe et reinitialiser.", "utiliasteur : " + newUtilisateur.getIdUtilisateur(), true));
            }
        }
    }

    @PostMapping("/loug-out")
    public ResponseEntity<ApiResponse<String>> lougOut(
        @RequestParam String email
    ){
        Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Utilisateur non trouvé.", null, false));
            }

            else{
                
            Session session = sessionService.getByIdUtilisateur(utilisateur);
            sessionService.deleteSession(session);
            return ResponseEntity.badRequest().body(new ApiResponse<>("Utilisateur deconnecter avec succes.", null, true));
            }
    }
}
