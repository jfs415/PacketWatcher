package com.jfs415.packetwatcher_api.exceptions.args;

import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;
import com.jfs415.packetwatcher_api.events.authorization.AuthorizationEventType;

public class InvalidEventTypeArgumentException extends InvalidArgumentException {

    private static final String DEFAULT_MESSAGE = "Only " + AuthorizationEventType.class.getSimpleName() + " and " 
            + AuthenticationEventType.class.getSimpleName() + "'s are accepted!";
    
    public InvalidEventTypeArgumentException() {
        super(DEFAULT_MESSAGE);
    }

}
