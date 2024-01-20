package com.jfs415.packetwatcher_api.events.authentication;

import com.jfs415.packetwatcher_api.events.IAuthEventType;

public enum AuthenticationEventType implements IAuthEventType {
    BAD_CREDENTIALS_ATTEMPT,
    DISABLED_LOGIN_ATTEMPT,
    LOCKED_LOGIN_ATTEMPT,
    PROXY_UNTRUSTED_ATTEMPT,
    EXPIRED_CREDENTIALS_ATTEMPT
}
