package com.example.cloud.service;

import org.springframework.stereotype.Service;

import com.example.cloud.model.Parametre;
import com.example.cloud.repository.ParametreRepository;

@Service
public class ParametreService {
    private final ParametreRepository parametreRepository;

    public ParametreService(ParametreRepository pr){
        this.parametreRepository = pr;
    }

    public Parametre getById(int id){
        return this.parametreRepository.findById(id).orElse(null);
    }
}
