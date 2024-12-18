package com.example.cloud.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPinEmail(String recipientEmail, String pin) {
        // Création du message simple
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tantely.yvon03@gmail.com"); // Adresse de l'expéditeur
        message.setTo(recipientEmail); // Adresse du destinataire
        message.setSubject("Votre code PIN"); // Sujet de l'email
        message.setText("Bonjour,\n\n" +
                        "Votre code PIN est : " + pin + "\n" +
                        "Ce code expirera dans 90 secondes.\n\n" +
                        "Cordialement,\nL'équipe.");

        // Envoi du message
        mailSender.send(message);
        System.out.println("Email envoyé avec succès à : " + recipientEmail);
    }
}
