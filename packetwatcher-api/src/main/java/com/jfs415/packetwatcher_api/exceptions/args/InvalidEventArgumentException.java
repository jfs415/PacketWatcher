package com.jfs415.packetwatcher_api.exceptions.args;

import com.jfs415.packetwatcher_api.events.authorization.AuthorizationDeniedEvent;
import com.jfs415.packetwatcher_api.events.authorization.AuthorizationGrantedEvent;

public class InvalidEventArgumentException extends InvalidArgumentException {

    private static final String DEFAULT_MESSAGE = "Only " + AuthorizationDeniedEvent.class.getSimpleName() + " and "
            + AuthorizationGrantedEvent.class.getSimpleName() + " are accepted!";

    public InvalidEventArgumentException(String message) {
        super(message);
    }

    public InvalidEventArgumentException() {
        super(DEFAULT_MESSAGE);
    }
}
