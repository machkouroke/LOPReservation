package com.lop.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class create the connection with the database
 */
public record Factory(String url, String name, String password) {
    private static Factory instance;


    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory("jdbc:oracle:thin:@localhost:1521:xe", "system",
                    "claudine");
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }
}