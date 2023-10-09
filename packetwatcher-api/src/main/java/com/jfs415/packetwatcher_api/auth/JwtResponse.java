package com.jfs415.packetwatcher_api.auth;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter
public class JwtResponse implements Serializable {

    private final String token;

}
