package com.jfs415.packetwatcher_api.exceptions;

public class InvalidEventTypeArgumentException extends UnsupportedOperationException {

	public InvalidEventTypeArgumentException() {
		super("Only AuthorizationEventType and AuthenticationEventTypes are accepted!");
	}

	public InvalidEventTypeArgumentException(String message) {
		super(message);
	}
	
}
