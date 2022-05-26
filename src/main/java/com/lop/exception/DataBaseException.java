package com.lop.exception;

/**
 * Exception concernant la base de données
 * @author Machkour Oke
 */
public class DataBaseException extends Exception{
    public DataBaseException(String message) {
        super(message);
    }
    public DataBaseException(Exception e) {
        super(e);
    }
}
