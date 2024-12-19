package com.example.cloud.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.service.DetailUtilisateurService;
import com.example.cloud.service.SessionService;
import com.example.cloud.service.UtilisateurService;

@Controller
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
    @ResponseBody
    public String validationSession(@RequestParam String email){
        try{
            //verifier si l'utilisateur est connecter
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return "Aucun utilisateur trouvé pour cet email.";
            }
            Session session = sessionService.getByIdUtilisateur(utilisateur);
            if(session == null){
                return"vous n'etes pas encore connecter";
            }
            else{
                return"vous etes connecter";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return"vous n'estes pas connecter";
        }
    }
    @PostMapping("/detail-utilisateur")
    @ResponseBody
    public String modificationDU(
        @RequestParam String email,
        @RequestParam String nom,
        @RequestParam String prenom,
        @RequestParam LocalDate dateNaissance,
        @RequestParam String telephone
     ){
         
            if(nom == null){
                return "le nom est null";
            }
            if(prenom == null){
                return "le prenom est null";
            }
            if(dateNaissance == null){
                return "le dateNaissance est null";
            }
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return "Aucun utilisateur trouvé pour cet email.";
            }
            DetailUtilisateur updateDU = detailUtilisateurService.updateDetailUtilisateur(utilisateur.getDetailUtilisateur().getIdDetailUtilisateur(), nom, prenom, telephone, dateNaissance);
            return "modification reusi!" + updateDU.getIdDetailUtilisateur();
    }

    @PostMapping("/reinitialiser-password")
    @ResponseBody
    public String reinitialiserPassword(
        @RequestParam String email,
        @RequestParam String mdp1,
        @RequestParam String mdp2
    ){
        if(email == null){
            return"votre email est vide";
        }
        if(mdp1 == null || mdp2 ==null){
            return"l' un de votre email est vide";
        }
        if(mdp1 == mdp2){
            return "l'email ne correspond pas";
        }
        else{
            Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return "Aucun utilisateur trouvé pour cet email.";
            }
            else{
                Utilisateur newUtilisateur = utilisateurService.reinitialiserMdp(utilisateur.getIdUtilisateur(),email,mdp1);
                return"votre mot de passe est reinitialiser " + newUtilisateur.getIdUtilisateur();
            }
        }
    }

    @PostMapping("/loug-out")
    @ResponseBody
    public String lougOut(
        @RequestParam String email
    ){
        Utilisateur utilisateur = utilisateurService.getByEmail(email);
            if (utilisateur == null) {
                return "Aucun utilisateur trouvé pour cet email.";
            }
            else{
                
            Session session = sessionService.getByIdUtilisateur(utilisateur);
            sessionService.deleteSession(session);
                return "vous etes deconnecter!";
            }
    }
}
