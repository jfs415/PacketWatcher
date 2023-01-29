package com.jfs415.packetwatcher_api.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
	
	USER("USER"),
	ADMIN("ADMIN"),
	ROOT("ROOT"),
	TEST_USER("TEST_USER");

	private final String authority;

	private Authority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
