package com.example.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cloud.model.Parametre;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
    @Autowired
    private RedisService redisService;
    private final UtilisateurRepository utilisateurRepository;
    private  ParametreService parametreService;

    public UtilisateurService(UtilisateurRepository ur){
        this.utilisateurRepository = ur;
    }
    int idAko = 0;
    Parametre parametre = parametreService.getById(idAko);
    int tentative = parametre.getTentation();
    String tentativeString = Integer.toString(tentative);



    public Utilisateur getById(int id){
        return this.utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur enregistrerUtilisateur(Utilisateur utilisateur){
        redisService.saveData("attempts:" + utilisateur.getEmail(), tentativeString);
        return this.utilisateurRepository.save(utilisateur);

    }

    public Utilisateur getByEmail(String email){
        return this.utilisateurRepository.findByEmail(email).orElse(null);
    }

    public Utilisateur reinitialiserMdp(Integer id, String email, String mdp){
        Utilisateur utilisateurAncien = utilisateurRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("votre Utilisateur not found with id:" + id));
        utilisateurAncien.setPassword(mdp);
        return utilisateurRepository.save(utilisateurAncien);
    }
}
