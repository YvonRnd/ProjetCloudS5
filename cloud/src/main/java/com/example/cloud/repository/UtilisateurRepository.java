package com.example.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cloud.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository <Utilisateur, Integer> {
    
    
}