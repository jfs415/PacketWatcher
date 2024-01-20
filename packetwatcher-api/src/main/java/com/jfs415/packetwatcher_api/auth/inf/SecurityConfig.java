package com.jfs415.packetwatcher_api.auth.inf;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public interface SecurityConfig {

    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception;

    PasswordEncoder passwordEncoder();

    SecurityFilterChain configure(HttpSecurity http) throws Exception;
}
