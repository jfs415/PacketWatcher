package com.jfs415.packetwatcher_api.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Username not found");
    }

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
