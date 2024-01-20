package com.jfs415.packetwatcher_api.exceptions;

public class LockedUserHistoryNotFoundException extends RuntimeException {

    public LockedUserHistoryNotFoundException() {
        super("User does not have lockout history");
    }

    public LockedUserHistoryNotFoundException(String message) {
        super(message);
    }
}
