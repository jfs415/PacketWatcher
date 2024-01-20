package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.auth.JwtUtilImpl;
import com.jfs415.packetwatcher_api.auth.UserPasswordResetRequest;
import com.jfs415.packetwatcher_api.auth.inf.JwtUtil;
import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.views.UserProfileView;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RecoverAccountController {

    private final UserService userService;
    private final JwtUtil jwtUtilImpl;

    @Autowired
    public RecoverAccountController(UserService userService, JwtUtilImpl jwtUtilImpl) {
        this.userService = userService;
        this.jwtUtilImpl = jwtUtilImpl;
    }

    @PostMapping(value = "/account/reset", produces = "application/json")
    public ResponseEntity<?> processAuthenticatedUserPasswordReset(
            @CookieValue(name = "jwt") String token, @RequestBody UserProfileView userProfileView) {
        try {
            Boolean isValidToken = jwtUtilImpl.validateToken(token, userProfileView.username());
            return ResponseEntity.ok(isValidToken);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/accounts/forgot", produces = "application/json")
    public ResponseEntity<?> processAccountRecoveryInitiation(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");

            if (!Objects.requireNonNull(email).isEmpty()) {
                long timestamp = System.currentTimeMillis();
                userService.handleAccountRecoveryInitiation(timestamp, email);

                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/accounts/reset", produces = "application/json")
    public ResponseEntity<?> processAccountRecoveryRequest(@RequestBody UserPasswordResetRequest request) {
        try {
            User user = userService.getUserByUsername(request.username());

            if (!userService.isCorrectPasswordResetToken(request.token(), user.getPasswordResetToken())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Username or Password");
            }

            userService.updatePassword(user, request.password());

            return ResponseEntity.ok(true);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
