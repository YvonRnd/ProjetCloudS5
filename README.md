## Instructions pour lancer le projet
## 1. Cloner le projet

Clonez ce dépôt Git sur votre machine locale :

git clone https://github.com/YvonRnd/ProjetCloudS5
cd votre-repo

## 2. Configuration des variables d’environnement

Si nécessaire, modifiez les variables dans le fichier application.propreties .

Les paramètres principaux incluent :
- votre basses de données :

spring.datasource.url=jdbc:postgresql://localhost:5433/nom_du_bd
spring.datasource.username=
spring.datasource.password=

puis configurer l' email haute :
en autorisant le double facteur de votre compte
puis faire un mot de passe d'application google et metre a jour le parametre:
# Configuration SMTP pour Gmail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=email.haute@gmail.com
spring.mail.password=XXXX XXXX XXXX le mot de passe donner lors du creation du ot de passe d'application google

dans EmailService.java met a jour aussi :
message.setFrom("email.haute@gmail.com");

## 4. Construire et lancer les conteneurs

Exécutez la commande suivante pour construire et démarrer les conteneurs :

docker-compose up --build

## 5. Accéder à l’application

Une fois les conteneurs en cours d’exécution, accédez à votre navigateur et ouvrez : http://localhost:8080