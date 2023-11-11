package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.auth.AuthenticationRequest;
import com.jfs415.packetwatcher_api.auth.JwtUtil;
import com.jfs415.packetwatcher_api.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<?> processLogin(@RequestBody AuthenticationRequest request) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String token = jwtUtil.generateToken(user);
            
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(true);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
