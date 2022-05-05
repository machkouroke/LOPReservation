package com.lop.exception;

public class UnknownUserNameException extends Exception {
    public UnknownUserNameException(String message) {
        super(message);
    }
}
