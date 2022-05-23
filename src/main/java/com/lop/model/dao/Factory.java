package com.lop.model.dao;

import com.lop.controller.ConfigController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;

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

            ConfigController.baseInit(name, password);
            this.url = url;
            this.name = name;
            this.password = password;
            File f = new File("src/main/java/com/lop/controller/machine.dot");
            try(FileOutputStream fichier = new FileOutputStream(f)) {
                new ObjectOutputStream(fichier).writeObject(InetAddress.getLocalHost());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, name, password);
    }
}
