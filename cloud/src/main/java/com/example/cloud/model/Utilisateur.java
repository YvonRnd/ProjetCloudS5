package com.example.cloud.model;

import com.example.cloud.util.BCryptUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Integer idUtilisateur;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 60) // BCrypt nécessite au moins 60 caractères
    private String password;

    @ManyToOne
    @JoinColumn(name =  "id_D_Utilisateur", nullable = false)
    private DetailUtilisateur detailUtilisateur;

    public DetailUtilisateur getDetailUtilisateur() {
        return detailUtilisateur;
    }

    public void setDetailUtilisateur(DetailUtilisateur detailUtilisateur) {
        this.detailUtilisateur = detailUtilisateur;
    }

    // Getters et setters
    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCryptUtils.hashPassword(password);
    }

    // Constructeur par défaut
    public Utilisateur() {
    }

    // Constructeur avec paramètres
    public Utilisateur(String email, String password, DetailUtilisateur dU) {
        this.setEmail(email);
        String passwordCrypt = BCryptUtils.hashPassword(password);
        this.setPassword(passwordCrypt);
        this.setDetailUtilisateur(dU);
    }

    // Hook pour hachage du mot de passe avant persistance
    @PrePersist
    private void hashPasswordBeforeSave() {
        this.password = BCryptUtils.hashPassword(this.password);
    }
}
