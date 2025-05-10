package com.example.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cloud.model.Parametre;

public interface ParametreRepository extends JpaRepository <Parametre, Integer> {
    
}
