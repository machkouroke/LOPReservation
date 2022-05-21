package com.lop.model.dao;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * This class create the connection with the database
 */
public class  Factory{

    private  String url;
    private  String name;
    private  String password;

    public Factory(String url, String name, String password) {
        try {
            ProcessBuilder pip = new ProcessBuilder("pip", "install", "-r",
                    "src/main/java/com/lop/model/dao/initPython/requirements.txt");
            errorProcess(pip);
            ProcessBuilder pb = new ProcessBuilder("python",
                    "src/main/java/com/lop/model/dao/initPython/main.py", "localhost", "root", "claudine");
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
            while(true) {
                String line = pipProcess.errorReader().readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }
}
