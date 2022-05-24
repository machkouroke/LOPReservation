package com.lop.model.dao;

import com.lop.controller.ConfigController;
import com.lop.model.BaseLogin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public Factory(String url, String name, String password) throws IOException, ClassNotFoundException, SQLException {

            ConfigController.baseInit(name, password);
            this.url = "jdbc:mysql://"+url+":3306/manager";
            this.name = name;
            this.password = password;

            getConnection();

            File machineName = new File("src/main/java/com/lop/controller/machine.dot");
            File dataInfo = new File("src/main/java/com/lop/controller/dataBase.dot");
            try(FileOutputStream machineNameOutput = new FileOutputStream(machineName);
                FileOutputStream dataInfoOutput = new FileOutputStream(dataInfo);
            ) {
                new ObjectOutputStream(machineNameOutput).writeObject(InetAddress.getLocalHost());
                new ObjectOutputStream(dataInfoOutput).writeObject(new BaseLogin(url, name, password));
            }

    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, name, password);
    }
}
