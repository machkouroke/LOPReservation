package com.lop.model.dao;

import com.lop.exception.DataBaseException;
import com.lop.exception.PasswordIncorrectException;
import com.lop.exception.UnknownUserNameException;
import com.lop.model.beans.Users;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class check user information and allow or deny access to the website
 */
public class UserConnection {
    private final Factory factory;

    public UserConnection(Factory factory) {
        this.factory = factory;
    }

    public boolean connexionValidate(String username, String password)
            throws PasswordIncorrectException, UnknownUserNameException, DataBaseException {
        try (PreparedStatement requete = this.factory.getConnection()
                .prepareStatement("select * from \"User\" where username = ?")) {
            requete.setString(1, username);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()) {
                Users user = new Users(resultat.getString("username"),
                        resultat.getString("password"));
                if (user.getPassword().equals(password)) {
                    return true;
                } else {
                    throw new PasswordIncorrectException("Mot de passe incorrect");
                }
            } else {
                throw new UnknownUserNameException("Utilisateurs non enrégistré");
            }
        } catch (SQLException e) {
            throw new DataBaseException("Une erreur est subvenu lors de la connection à la base " +
                    "de données:" + e.getMessage());
        }
    }
}
