package com.lop.exception;

/**
 * Username de connexion de l'application incorrecte
 * @author Machkour Oke
 */
public class UnknownUserNameException extends Exception {
    public UnknownUserNameException(String message) {
        super(message);
    }
}
