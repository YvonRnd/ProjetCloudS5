## TO DO

## Projet de fournisseur d' identité :
## 1er partie-A connaitre:
- Inscription: 
* Comment on fait un validation par email en ligne?

- Connexion :
* On fait un authentification multifacteur c'est a dire on envoie un pin generer vers  un email donner et on verifie si le pin donner correspond avec le pin envoyer

<!-- - Metre un rôle pour les utilisateur :
* admin
* membre simple  -->

- Qu'est ce qu'on peut faire pour le Gestion du compte pour l'utiliateur ?

- fonctionalités minimales:
* durrée de vie des sessions (parametrable).
* Hashing sécurisé des mots de passes.
* Limite des nombres de tentatives de connexion avec reinitialisation via email.

* c'est quoi une documentation API via Swagger 


## 2eme partie-Conception:
# BD:
- Utilisateur.(vita)
- DetailUtilisateur.(vita)
- Session.(vita)
- Parametre.(vita)

# Model
- Utilisateur.(vita)
- DetailUtilisateur.(vita)
- Session.(vita)
- Pin.(vita)
- Parametre.(vita)

# Repository
- DetailUtilisateurRepository.(vita)
- SessionRepository.(vita)
- UtilisatuerRepository.(vita)
- ParametreRepository.(vita)

# Service
- DetailUtilisateurService.(vita)
- EmailService.(vita)
- SessionService.(vita)
- UtilisateurService.(vita)
- ParametreService.(vita)
- RedisService.(vita)

# Util
- BCryotUtils.(vita)
- TokenGenerator.(vita)

# Controlleur:
- loginControlleur: 
1- Inscription(signUp):
- envoyer pin to email(sendPin).(vita)
- verification de pin (verifyPin).(vita)
- enregistrer un Utilisateur(email et mdp).(vita)
- enregistrer Detail Utilisateur.(vita)
2- Connection(Login):
- envoyer pin to email(sendPin).(vita)
- validation pin(verifyPin).(vita)
- validation de l'utiliasteur.(vita)

- AccueilControlleur:
3- Accueil:
- validation Session.(vita)
- modification detail Utilisateur.(vita)
- deconnection.(vita)
- reinitialiter mdp. (vita)
- parametrage.(viens data bd)

# fonctionalités:
- validation via email (vita)
- validation email and mdp (vita)
- generer pin avec delais (a modifier)
- validation pin avec limite de tentation (a verifier) 
- reinitialisation tentative 0 (vita)
- generer token (vita)
- Metre un session pour l'utilisteur (vita)
- durabilité de session (a modifier)
- modification details utilisateurs (a verifier)
- reinitialiser mdp(vita)
<!-- - modifiacation parametre() -->
 


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