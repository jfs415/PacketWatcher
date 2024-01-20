package com.jfs415.packetwatcher_api.auth;

public record UserPasswordResetRequest(String username, String password, String token) {}
