package com.jfs415.packetwatcher_api.exceptions.args;

public class InvalidLoginTimeArgumentException extends InvalidArgumentException {

    private static final String DEFAULT_MESSAGE = "Invalid login time attempted to be passed to the cache";

    public InvalidLoginTimeArgumentException() {
        super(DEFAULT_MESSAGE);
    }

}
