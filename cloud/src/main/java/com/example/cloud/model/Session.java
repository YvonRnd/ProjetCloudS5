package com.example.cloud.model;

import java.time.LocalDateTime;

import com.example.cloud.util.TokenGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session")
    private Integer idSession;

    @Column(name = "token")
    private String token;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_exp")
    private LocalDateTime dateExp;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur",nullable = false )
    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDateTime getDateExp() {
        return dateExp;
    }

    public void setDateExp(LocalDateTime dateExp) {
        this.dateExp = dateExp;
    }

    public LocalDateTime getDateCreate() {
        return getDateCreate();
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    public Session(){}

    

    public Session(Utilisateur utilisateur, int dSession){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusSeconds(dSession);
        String tokenCreate = TokenGenerator.generateToken();
        this.setToken(tokenCreate);
        this.setDateCreate(now);
        this.setDateExp(expirationTime);
        this.setUtilisateur(utilisateur);
    }

}
