package com.jfs415.packetwatcher_api.model.user;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
