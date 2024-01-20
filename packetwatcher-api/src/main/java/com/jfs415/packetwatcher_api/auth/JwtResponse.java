package com.jfs415.packetwatcher_api.auth;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class JwtResponse implements Serializable {

    private final String token;
}
