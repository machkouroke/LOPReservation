package com.lop.error_event;

public interface ErrorListener {
    void errorOccurred(String message);
    void noError(String message);
}
