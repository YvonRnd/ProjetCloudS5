package com.example.cloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "Parametre")
public class Parametre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Parametre")
    private Integer idParametre;

    @Column(name = "durreSession")
    private String DSession;

    @Column(name = "durrePin")
    private String DPin;

    @Column(name = "tentation")
    private Integer Tentation;

    
    public Integer getTentation() {
        return Tentation;
    }

    public void setTentation(Integer tentation) {
        Tentation = tentation;
    }

    public Integer getIdParametre() {
        return idParametre;
    }

    public void setIdParametre(Integer idParametre) {
        this.idParametre = idParametre;
    }

    
    public String getDSession() {
        return DSession;
    }

    public void setDSession(String dSession) {
        DSession = dSession;
    }
    
    public String getDPin() {
        return DPin;
    }

    public void setDPin(String dPin) {
        DPin = dPin;
    }

    public Parametre(){}
    public Parametre (String DSession,String DPin,Integer tentation){
        this.setDSession(DSession);
        this.setDPin(DPin);
        this.setTentation(tentation);
    }
}
