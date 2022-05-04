package com.lop.Exception;

public class DataBaseException extends Exception{
    public DataBaseException(String message) {
        super(message);
    }
    public DataBaseException(Exception e) {
        super(e);
    }
}
