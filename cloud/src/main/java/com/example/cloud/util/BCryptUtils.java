package com.example.cloud.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Hash un mot de passe en utilisant BCrypt
     *
     * @param rawPassword Le mot de passe en clair
     * @return Le mot de passe haché
     */
    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * Vérifie si un mot de passe correspond à son hachage
     *
     * @param rawPassword    Le mot de passe en clair
     * @param hashedPassword Le mot de passe haché
     * @return true si les mots de passe correspondent, false sinon
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        String motDePasse = "MonMotDePasseSecurise123!";
        
        // Hachage du mot de passe
        String hashedPassword = hashPassword(motDePasse);
        System.out.println("Mot de passe haché : " + hashedPassword);

        // Vérification du mot de passe
        boolean isCorrect = verifyPassword(motDePasse, hashedPassword);
        System.out.println("Le mot de passe est-il correct ? " + isCorrect);

        // Test avec un mauvais mot de passe
        boolean isWrong = verifyPassword("MotDePasseIncorrect", hashedPassword);
        System.out.println("Mot de passe incorrect ? " + isWrong);
    }
}
