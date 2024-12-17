# Utiliser l'image JDK 17
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR dans l'image
COPY target/*.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
