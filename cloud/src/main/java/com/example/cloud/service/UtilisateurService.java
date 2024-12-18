package com.example.cloud.service;

import org.springframework.stereotype.Service;

import com.example.cloud.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository ur){
        this.utilisateurRepository = ur;
    }
}
