package com.lop.controller;

import java.io.*;
import java.net.InetAddress;


public class ConfigController {
    private ConfigController() {

    }
    public static boolean firstTime() throws IOException, ClassNotFoundException {

        File f = new File("machine.dot");
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


    public static void pipMySQL() throws IOException {
        ProcessBuilder pip = new ProcessBuilder("pip", "install", "-r",
                "src/main/java/com/lop/model/dao/initPython/requirements.txt");
        errorProcess(pip);
    }

    public static void baseInit(String name, String password) throws IOException, ClassNotFoundException {
        if (firstTime()) {
            pipMySQL();
            ProcessBuilder pb = new ProcessBuilder("python",
                    "src/main/java/com/lop/model/dao/initPython/main.py", "localhost", name, password);
            errorProcess(pb);
        }
    }
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
