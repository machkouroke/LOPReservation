package com.lop.controller;

import java.io.*;
import java.net.InetAddress;

/**
 * Controller permettant la configuration du programme
 * @author Machkour Oke
 */
public class ConfigController {
    private ConfigController() {

    }

    /**
     * Script qui vérifie si le programme est lancé pour la première fois sur une machine
     * @return Le programme est lancé pour la première fois ou non
     * @throws IOException Erreur lors de la lecture du fichier contenant la configuration de la machine
     * @throws ClassNotFoundException erreur lors de la deserialization
     */
    public static boolean firstTime() throws  IOException, ClassNotFoundException {

        File f = new File("src/main/java/com/lop/controller/machine.dot");
        if (f.exists()) {
            InetAddress actualAddress = InetAddress.getLocalHost();
            try (FileInputStream fichier = new FileInputStream(f)) {
                InetAddress adresse = (InetAddress) new ObjectInputStream(fichier).readObject();
                /*Comparaison du nom de la machine avec le nom enregistré dans le fichier*/
                return !adresse.getHostName().equals(actualAddress.getHostName());
            }
        }
        return true;
    }


    /**
     * Installe les dépendances requises à execution du script python de configuration
     * @throws IOException Erreur lors de la lecture du script python
     */
    public static void pipMySQL() throws IOException {
        ProcessBuilder pip = new ProcessBuilder("pip", "install", "-r",
                "src/main/java/com/lop/model/dao/initPython/requirements.txt");
        errorProcess(pip);
    }


    /**
     * Execute le script python si le programme est lancé pour la 1ere fois
     * @param name Nom de l'utilisateur de la base de données
     * @param password Mots de passe de l'utilisateur de la base de données
     * @throws IOException Erreur lors de la lecture du script
     * @throws ClassNotFoundException lancé lors de la lecture du fichier de configurations de la machine
     */
    public static void baseInit(String name, String password) throws IOException, ClassNotFoundException {
        if (firstTime()) {
            pipMySQL();
            ProcessBuilder pb = new ProcessBuilder("python",
                    "src/main/java/com/lop/model/dao/initPython/main.py", "localhost", name, password);
            errorProcess(pb);
        }
    }

    /**
     * Permet d'afficher les erreurs lors de execution des processus annexe
     * @param pip Processus à afficher les erreurs
     * @throws IOException Erreur lors du lancement du processus
     */
    static void errorProcess(ProcessBuilder pip) throws IOException {
        Process pipProcess = pip.start();
        if (pipProcess.errorReader() != null) {
            while (true) {
                String line = pipProcess.errorReader().readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        }
    }
}
