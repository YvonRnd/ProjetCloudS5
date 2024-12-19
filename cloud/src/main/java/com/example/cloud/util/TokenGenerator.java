package com.example.cloud.util;

import java.util.UUID; // Import nécessaire pour utiliser UUID

public class TokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
