package com.jfs415.packetwatcher_api.exceptions.args;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
