package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.auth.JwtUtil;
import com.jfs415.packetwatcher_api.auth.UserPasswordResetRequest;
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
    private final JwtUtil jwtUtil;

    @Autowired
    public RecoverAccountController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/account/reset", produces = "application/json")
    public ResponseEntity<?> processAuthenticatedUserPasswordReset(
            @CookieValue(name = "jwt") String token, @RequestBody UserProfileView userProfileView) {
        try {
            Boolean isValidToken = jwtUtil.validateToken(token, userProfileView.getUsername());
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
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/accounts/reset", produces = "application/json")
    public ResponseEntity<?> processAccountRecoveryRequest(@RequestBody UserPasswordResetRequest request) {
        try {
            User user = userService.getUserByUsername(request.getUsername());

            if (!userService.isCorrectPasswordResetToken(request.getToken(), user.getPasswordResetToken())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Username or Password");
            }

            userService.updatePassword(user, request.getPassword());

            return ResponseEntity.ok(true);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
