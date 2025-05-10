package com.example.cloud.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Pin {
    
    private String pin;
    
    private LocalDateTime dateCreate;

    private LocalDateTime dateExpiration;

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Pin () {
    }

    public Pin generatePin(int dPin) {
        Random random = new Random();
        StringBuilder generatedPin = new StringBuilder();

        
        // Génération de 6 chiffres aléatoires
        for (int i = 0; i < 6; i++) {
            generatedPin.append(random.nextInt(10)); // Nombre entre 0 et 9
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusSeconds(dPin);

        // Initialiser les champs
        this.pin = generatedPin.toString();
        this.dateCreate = now;
        this.dateExpiration = expirationTime;

        // Retourner l'instance mise à jour
        return this;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Pin{" +
                "pin='" + pin + '\'' +
                ", dateCreate=" + dateCreate.format(formatter) +
                ", dateExpiration=" + dateExpiration.format(formatter) +
                '}';
    }

}
