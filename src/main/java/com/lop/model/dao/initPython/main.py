
import platform

import mysql.connector
import random as rd
import csv
import shelve
import sys
import os
from mysql.connector import IntegrityError

os.chdir('src/main/java/com/lop/model/dao/initPython/')
with open("log.txt", "w") as logFile:
    cur = None
    conn = None


    def connection() -> None:
        """
        Effectue la connection à la base de donnée
        si on est sur une nouvelle machine le nom de la machine seras différentes et on va donc devoir recueillir des
        informations sur la base de données de la machine
        """
        global cur, conn
        # Fichier de configuration
        with shelve.open('config/config') as config:
            if "machineName" not in config or config["machineName"] != platform.node():
                print("****Configuration de la base****", file=logFile)
                config['host'] = sys.argv[1]
                config['userName'] = sys.argv[2]
                config['password'] = sys.argv[3]
            print(f"host: {config['host']}, userName: {config['userName']}, password: {config['password']}",
                  file=logFile)
            conn = mysql.connector.connect(
                host=config['host'],
                user=config['userName'],
                password=config['password']
            )
        cur = conn.cursor()
        init()


    def init() -> None:
        """
        Crée la base nécessaire au fonctionnement du programme puis crée les tables nécessaires
        """
        cur.execute("create database if not exists `manager`;")
        try:
            with open('sql/LDD.sql') as f:
                full_sql = f.read()
                sql_commands = full_sql.split(';')
                for sql_command in sql_commands:
                    cur.execute(sql_command)
        except UnicodeDecodeError as e:
            print(f"Erreur line 41: {e}", file=logFile)


    def add_bloc() -> None:
        """
        Initialisation de la table Bloc
        """
        global conn, cur
        for index in ['A', 'B', 'C', 'D']:
            cur.execute("INSERT INTO manager.BLOC VALUES(%s)", [index])


    def add_salle():
        """
        Initialisation de la table Salle:
        - La capacité est (compris) entre 40 et 70
        - Le nombre d'équipements est compris entre 0 et 10
        """
        global conn, cur
        for salle_index in range(1, 5):
            for bloc_index in ['A', 'B', 'C', 'D']:
                cur.execute('INSERT INTO manager.SALLE VALUES(%s, %s, %s, %s)',
                            (salle_index, bloc_index, rd.randint(40, 70), rd.randint(0, 10)))


    def add_reservataire():
        """
        Initialisation de la table des Réservataires: Un étudiant peut réserver maximum 4 fois tandis qu'un proffesseur
        autant de fois
        """
        global conn, cur

        # Initialisation de la table fonction
        cur.execute("INSERT INTO manager.FONCTION VALUES(1, 'Proffesseur')")
        cur.execute("INSERT INTO manager.FONCTION VALUES(2, 'Etudiant')")

        # Ajout des réservataires
        with open('reservataire.csv') as csvfile:
            # Fichier csv comportant ma base de données de personne
            u = csv.DictReader(csvfile)
            for id_reservataire, reservataire in enumerate(u, start=1):
                fonction = rd.randint(1, 2)
                data = [id_reservataire, fonction, reservataire['NOM:'], reservataire['PRENOM:'],
                        4 if fonction == 2 else 100000]
                cur.execute('INSERT INTO manager.RESERVATAIRE VALUES(%s, %s, %s, %s, %s)',
                            data)
        conn.commit()


    if __name__ == '__main__':
        connection()
        print('Connection Réussi', file=logFile)
        for i in [add_bloc, add_salle, add_reservataire]:
            try:
                i()
            except IntegrityError as e:
                print(f"Erreur line 109: {e}", file=logFile)
            else:
                print(f'{i.__name__} est effect', file=logFile)
