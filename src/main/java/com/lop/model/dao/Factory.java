package com.lop.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class create the connection with the database
 */
public class Factory {
    private final String url;
    private final String name;
    private final String password;
    private static Factory instance;

    /**
     * @param url Link to database
     * @param name name of database's user
     * @param password password
     */
    public Factory(String url, String name, String password) {

        this.url = url;
        this.name = name;
        this.password = password;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur:" + e.getMessage());
        }
    }

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
