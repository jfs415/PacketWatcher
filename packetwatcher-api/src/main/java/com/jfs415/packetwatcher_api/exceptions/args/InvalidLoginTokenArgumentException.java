package com.jfs415.packetwatcher_api.exceptions.args;

public class InvalidLoginTokenArgumentException extends InvalidArgumentException {

    private static final String DEFAULT_MESSAGE = "Invalid login token attempted to be passed to the cache";

    public InvalidLoginTokenArgumentException() {
        super(DEFAULT_MESSAGE);
    }
}
