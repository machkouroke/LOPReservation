USE `manager`;
CREATE TABLE IF NOT EXISTS FONCTION
(
    id_fonction  int PRIMARY KEY,
    Nom_fonction VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS Reservataire
(
    #Peut être un CNE ou un Numéro de professeur en fonction de la nature
    #du réservataire
    id_reservataire int PRIMARY KEY,
    id_fonction     int,
    nom             VARCHAR(50),
    prenom          VARCHAR(50),
    #Contient le quota de réservation du réservataire
    #Pour un étudiant il est de 4 tandis que pour un professeur il est quasi infini
    quota           int DEFAULT NULL,
    CONSTRAINT fk_reservataire FOREIGN KEY (id_fonction) references FONCTION (id_fonction)
);
CREATE TABLE IF NOT EXISTS BLOC
(
    id_bloc CHAR PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS Salle
(
    #Les numéro de salle seront compris entre 1 et 5 inclus
    #tandis que l'id_reservataire bloc est un caractère entre A et d inclus
    #Ainsi une salle unique est détecté par son numéro de bloc et de salle exemple: Salle A2,B3,...
    Num_Salle  int,
    id_bloc    CHAR NOT NULL,
    Capacite   int  NOT NULL,
    Nombre_eqt int,
    CONSTRAINT FK_SALLE FOREIGN KEY (id_bloc) references BLOC (id_bloc),
    CONSTRAINT PK_SALLE PRIMARY KEY (Num_Salle, id_bloc)
);
CREATE TABLE IF NOT EXISTS Evenements
(
    #Ici on s'assure que l'id_reservataire d'évènement est incrémenté à chaque ajout tout en commencant
    #par 1
    id_event        int auto_increment unique ,
    id_reservataire int,
    Num_Salle       int,
    Id_Bloc         CHAR,
    Nom             VARCHAR(100),
    date_evt        DATE,
    #Pour éviter qu'une salle ne soit occupé au même moment on vas prendre comme clé primaire
    #la clé de la salle et le jour de l'évènement
    CONSTRAINT PK_Event PRIMARY KEY (Num_Salle, Id_Bloc, date_evt),
    CONSTRAINT FK_Event1 FOREIGN KEY (id_reservataire) references Reservataire (id_reservataire),
    CONSTRAINT FK_Event2 FOREIGN KEY (Num_Salle, Id_Bloc) references Salle (Num_Salle, Id_Bloc)
);

CREATE TABLE IF NOT EXISTS Equipement
(
    Num_eqt   int PRIMARY KEY,
    Num_salle int,
    id_bloc   CHAR        NOT NULL,
    Type      VARCHAR(50) NOT NULL,
    CONSTRAINT FK_EQUIPEMENT FOREIGN KEY (Num_salle, id_bloc) references Salle (Num_Salle, id_bloc)
);
create table IF NOT EXISTS User
(
ID int auto_increment unique primary key ,
USERNAME VARCHAR(50) unique not null,
PASSWORD VARCHAR(50)
);
insert into User (username, password) value ('machkour', 'bonjour2000');

