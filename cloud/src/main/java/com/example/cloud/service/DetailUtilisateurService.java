package com.example.cloud.service;

import org.springframework.stereotype.Service;

import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.repository.DetailUtilisateurRepository;

@Service
public class DetailUtilisateurService {
    private final DetailUtilisateurRepository detailUtilisateurRepository;

    public DetailUtilisateurService(DetailUtilisateurRepository dur){
        this.detailUtilisateurRepository = dur;
    }

    public void enregistrerDetailUtilisateur(DetailUtilisateur detailUtilisateur) {
       this.detailUtilisateurRepository.save(detailUtilisateur);
    }
}
