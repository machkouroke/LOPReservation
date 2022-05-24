package com.lop.model;

import java.io.Serializable;

public record BaseLogin(String localhost, String username, String password) implements Serializable {

}
