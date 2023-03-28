package com.jfs415.packetwatcher_api.model.user;

import java.util.stream.Stream;

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

	public static Authority of(String authority) {
		return Stream.of(Authority.values()).filter(c -> c.getAuthority().equals(authority)).findFirst().orElseThrow(IllegalArgumentException::new);
	}
}
