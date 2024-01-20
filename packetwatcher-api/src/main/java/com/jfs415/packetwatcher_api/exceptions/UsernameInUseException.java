package com.jfs415.packetwatcher_api.exceptions;

public class UsernameInUseException extends RuntimeException {

    public UsernameInUseException() {
        super("That username is already in use.");
    }

    public UsernameInUseException(String message) {
        super(message);
    }
}
