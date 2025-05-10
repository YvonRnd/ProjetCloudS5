package com.example.cloud.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.cloud.model.DetailUtilisateur;
import com.example.cloud.repository.DetailUtilisateurRepository;

@Service
public class DetailUtilisateurService {
    private final DetailUtilisateurRepository detailUtilisateurRepository;

    public DetailUtilisateurService(DetailUtilisateurRepository dur){
        this.detailUtilisateurRepository = dur;
    }

    public DetailUtilisateur enregistrerDetailUtilisateur(DetailUtilisateur detailUtilisateur) {
       return this.detailUtilisateurRepository.save(detailUtilisateur);
    }

    public DetailUtilisateur updateDetailUtilisateur(Integer id,String nom,String prenom,String telephone,LocalDate dateNaissance){
        DetailUtilisateur detailUtilisateurAncien = detailUtilisateurRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("detail Utilisateur not found with id:" + id));
        detailUtilisateurAncien.setNom(nom);
        detailUtilisateurAncien.setPrenom(prenom);
        detailUtilisateurAncien.setTelephone(telephone);
        detailUtilisateurAncien.setDateNAissance(dateNaissance);
        return detailUtilisateurRepository.save(detailUtilisateurAncien);
    }   
}
