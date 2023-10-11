package com.jfs415.packetwatcher_api.model.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Stream;

public enum Authority implements GrantedAuthority {

    USER("USER"),
    ADMIN("ADMIN"),
    ROOT("ROOT"),
    TEST_USER("TEST_USER");

    private final String authority;

    Authority(String authority) {
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
