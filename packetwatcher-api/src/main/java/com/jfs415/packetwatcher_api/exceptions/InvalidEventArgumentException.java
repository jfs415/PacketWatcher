package com.jfs415.packetwatcher_api.exceptions;

public class InvalidEventArgumentException extends RuntimeException {

    public InvalidEventArgumentException() {
        super("Only AuthorizationDeniedEvent and AuthorizationGrantedEvents are accepted!");
    }

    public InvalidEventArgumentException(String message) {
        super(message);
    }

}
