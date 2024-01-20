package com.jfs415.packetwatcher_api.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPasswordResetRequest {

    private String username;
    private String password;
    private String token;
}
