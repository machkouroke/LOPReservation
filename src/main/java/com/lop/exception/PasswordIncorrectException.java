package com.lop.exception;

/**
 * Mot de passe de connexion de l'application incorrect
 * @author Machkour Oke
 */
public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
