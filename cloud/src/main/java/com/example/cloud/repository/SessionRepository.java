package com.example.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cloud.model.Session;

public interface SessionRepository extends JpaRepository <Session, Integer> {
    
}
