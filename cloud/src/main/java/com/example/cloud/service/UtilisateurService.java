package com.example.cloud.service;

import org.springframework.stereotype.Service;

import com.example.cloud.model.Utilisateur;
import com.example.cloud.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository ur){
        this.utilisateurRepository = ur;
    }

    public Utilisateur getById(int id){
        return this.utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur enregistrerUtilisateur(Utilisateur utilisateur){
        return this.utilisateurRepository.save(utilisateur);
    }

    public Utilisateur getByEmail(String email){
        return this.utilisateurRepository.findByEmail(email).orElse(null);
    }
}
