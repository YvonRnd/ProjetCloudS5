package com.example.cloud.service;

import org.springframework.stereotype.Service;

import com.example.cloud.model.Session;
import com.example.cloud.model.Utilisateur;
import com.example.cloud.repository.SessionRepository;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sr){
        this.sessionRepository = sr;
    }

    public Session enregistrerSession(Session sessionCreate) {
      return this.sessionRepository.save(sessionCreate);
    }

    public Session getByIdUtilisateur(Utilisateur utilisateur) {
        return this.sessionRepository.findByUtilisateur(utilisateur);
    }

     // Méthode pour supprimer une session par son ID
     public boolean deleteSessionById(Integer idSession) {
        if (sessionRepository.existsById(idSession)) {
            sessionRepository.deleteById(idSession);
            return true; // Indique que la suppression a réussi
        }
        return false; // Indique que la session n'existe pas
    }

    // Méthode pour supprimer une session en passant l'objet Session
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }
    
}
