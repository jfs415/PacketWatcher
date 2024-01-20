package com.jfs415.packetwatcher_api.auth;

import java.io.Serializable;

public record JwtResponse(String token) implements Serializable {}
