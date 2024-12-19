CREATE TABLE Detail_Utilisateur(
   id_D_Utilisateur SERIAL,
   nom VARCHAR(50)  NOT NULL,
   prenom VARCHAR(50)  NOT NULL,
   telephone VARCHAR(50) ,
   date_naissance DATE NOT NULL,
   PRIMARY KEY(id_D_Utilisateur)
);

CREATE TABLE Parametre(
   id_Parametre SERIAL,
   durreSession VARCHAR(50)  NOT NULL,
   durrePin VARCHAR(50)  NOT NULL,
   tentation INTEGER NOT NULL,
   PRIMARY KEY(id_Parametre)
);

CREATE TABLE Utilisateur(
   id_Utilisateur SERIAL,
   email VARCHAR(50)  NOT NULL,
   password VARCHAR(50)  NOT NULL,
   id_D_Utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_Utilisateur),
   UNIQUE(id_D_Utilisateur),
   UNIQUE(email),
   FOREIGN KEY(id_D_Utilisateur) REFERENCES Detail_Utilisateur(id_D_Utilisateur)
);

CREATE TABLE Session(
   id_session SERIAL,
   token VARCHAR(100) ,
   date_create TIMESTAMP,
   date_exp TIMESTAMP,
   id_Utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_session),
   UNIQUE(token),
   FOREIGN KEY(id_Utilisateur) REFERENCES Utilisateur(id_Utilisateur)
);
