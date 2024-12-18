package com.example.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cloud.model.DetailUtilisateur;

public interface DetailUtilisateurRepository extends JpaRepository <DetailUtilisateur, Integer> {
    
    
}
