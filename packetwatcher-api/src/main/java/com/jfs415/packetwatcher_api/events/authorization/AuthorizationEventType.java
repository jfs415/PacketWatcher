package com.jfs415.packetwatcher_api.events.authorization;

import com.jfs415.packetwatcher_api.events.IAuthEventType;

public enum AuthorizationEventType implements IAuthEventType {
    GRANTED,
    DENIED
}
