package com.jfs415.packetwatcher_api.model.user;

public enum UserActivationState {

	ENABLED,
	DISABLED,
	LOCKED,
	EXPIRED,
	CREDENTIALS_EXPIRED,
	UNCONFIRMED;

	private UserActivationState() {

	}

}
