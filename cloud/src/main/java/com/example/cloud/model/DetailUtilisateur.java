package com.example.cloud.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "detail_utilisateur")
public class DetailUtilisateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_D_Utilisateur")
    private Integer idDetailUtilisateur;

    @Column(name= "nom" ,length = 50, nullable = false)
    private String nom;

    @Column(name= "prenom" ,length = 50, nullable = false)
    private String prenom;

    @Column(name= "telephone", length = 50)
    private String telephone;

    @Column(name= "date_naissance" , nullable = false)
    private LocalDate dateNAissance;

    public LocalDate getDateNAissance() {
        return dateNAissance;
    }

    public void setDateNAissance(LocalDate dateNAissance) {
        this.dateNAissance = dateNAissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public Integer getIdDetailUtilisateur() {
        return idDetailUtilisateur;
    }

    public void setIdDetailUtilisateur(Integer idDetailUtilisateur) {
        this.idDetailUtilisateur = idDetailUtilisateur;
    }
}
