## TO DO

## Projet de fournisseur d' identité :
## 1er partie-A connaitre:
- Inscription: 
* Comment on fait un validation par email en ligne?

- Connexion :
* On fait un authentification multifacteur c'est a dire on envoie un pin generer a un email donner et on verifie si le pin donner correspond avec le pin envoyer

<!-- - Metre un rôle pour les utilisateur :
* admin
* membre simple  -->

- Qu'est ce qu'on peut faire pour le Gestion du compte pour l'utiliateur?

- fonctionalités minimales:
* durrée de vie des sessions (parametrable).
* Hashing sécurisé des mots de passes.
* Limite des nombres de tentatives de connexion avec reinitialisation via email.

* c'est quoi une documentation API via Swagger 


## 2eme partie-Conception:
# BD:
- Utilisateur(email,password,idD_Utilisateur).
- DetailUtilisateur(nom,prenom,Date_naissance,telephone).
- Session(token,date_create,date_exp).
- Pin(pin,date_create,date_exp).

# interface Web Api postman :
- login: 
1- Inscription:
- envoyer pin to email.
- enregistrer un Utilisateur.
- enregistrer Detail Utilisateur.
2- Connection:
- envoyer pin to email.
- validation pin.
3- Accueil:
- modification.
- deconnection.


# fonctionalités:
- validation via email
- validation email and mdp
- generer pin avec delais
- validation pin avec limite de tentation 
- reinitialisation tentative 0
- generer token
- Metre un session pour l'utilisteur
- durabilité de session 
- modification details utilisateurs
- reinitialiser mdp


<!-- 
## A faire:
- Inscription:
-- ajouter  email et mdp. 
-- envoyer une validation via email.
-- generer pin to email donner (6 chiffre aleatoire 0à9).
-- delais du confirmation pin (90 s);
-- validation pin
-- metre les details utilisteur.
>> rediriger vers Connection 
- Connection:
-- email + mot de passe
-- valider email et mdp
-- generer pin to email donner (6 chiffre aleatoire 0à9).
-- delais du confirmation pin (90 s);
-- validation pin
>> rediriger vers Accueil
* Connection >> Page d'accueil
- accueil:
-- getsion du compte utilisateur (modification details utiliasteurs) -->