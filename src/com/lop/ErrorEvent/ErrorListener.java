package com.lop.ErrorEvent;

public interface ErrorListener {
    void errorOccurred(String message);
    void noError(String message);
}
