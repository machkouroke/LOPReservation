package com.lop.model.dao;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class create the connection with the database
 */
public class Factory {

    private String url;
    private String name;
    private String password;

    public Factory(String url, String name, String password) {
        try {
            pipMySQL();
            ProcessBuilder pb = new ProcessBuilder("python",
                    "src/main/java/com/lop/model/dao/initPython/main.py", url, name, password);
            errorProcess(pb);
            this.url = url;
            this.name = name;
            this.password = password;
        } catch (Exception e) {
            System.out.println("Exception Raised: " + e);
        }
    }


    private static void errorProcess(ProcessBuilder pip) throws IOException {
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

    public boolean baseExist() {
        try {
            String dbName = "manager";
            ResultSet rs = getConnection().getMetaData().getCatalogs();

            while (rs.next()) {
                String catalogs = rs.getString(1);
                if (dbName.equals(catalogs)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;

    }


    /**
     * Installe les librairies MySql par le biais d'un script python si le programme est
     * lancé pour une première fois sur une
     */
    public void pipMySQL() throws IOException, ClassNotFoundException {
        File f = new File("machine.dot");
        InetAddress actualAddress = InetAddress.getLocalHost();
        if (f.exists()) {
            try (FileInputStream fichier = new FileInputStream(f)) {
                InetAddress adresse = (InetAddress) new ObjectInputStream(fichier).readObject();
                /*Comparaison du nom de la machine avec le nom enregistré dans le fichier*/
                if (!adresse.getHostName().equals(actualAddress.getHostName())) {
                    pipProcess();
                }
            }
        } else {
            try (FileOutputStream fichier = new FileOutputStream(f)) {
                new ObjectOutputStream(fichier).writeObject(actualAddress);
                pipProcess();
            }

        }
    }
    public void pipProcess() throws IOException {
        ProcessBuilder pip = new ProcessBuilder("pip", "install", "-r",
                "src/main/java/com/lop/model/dao/initPython/requirements.txt");
        errorProcess(pip);
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }
}
