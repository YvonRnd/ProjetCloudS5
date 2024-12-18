package com.example.cloud.service;

import org.springframework.stereotype.Service;

import com.example.cloud.repository.SessionRepository;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sr){
        this.sessionRepository = sr;
    }
    
}
