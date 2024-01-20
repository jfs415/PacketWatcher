package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.auth.AuthenticationRequest;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import com.jfs415.packetwatcher_api.model.services.AuthServiceImpl;
import com.jfs415.packetwatcher_api.model.services.inf.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final AuthService authServiceImpl;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthServiceImpl authServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> processLogin(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            String token = authServiceImpl.logUserIn(authentication);

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(true);
        } catch (BadCredentialsException | InvalidArgumentException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/disconnect") // Do not override default spring /logout endpoint
    public ResponseEntity<Boolean> processLogout(@RequestBody String token) {
        try {
            return ResponseEntity.ok(authServiceImpl.logUserOut(token));
        } catch (InvalidConfigurationPropertyValueException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
