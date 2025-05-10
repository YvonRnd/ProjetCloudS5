package com.example.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;

public interface SessionRepository extends JpaRepository <Session, Integer> {

	Session findByUtilisateur(Utilisateur utilisateur);
    
}
