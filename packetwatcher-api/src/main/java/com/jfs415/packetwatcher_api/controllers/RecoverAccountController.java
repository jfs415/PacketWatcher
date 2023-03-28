package com.jfs415.packetwatcher_api.controllers;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfs415.packetwatcher_api.auth.JwtUtil;
import com.jfs415.packetwatcher_api.auth.UserPasswordResetRequest;
import com.jfs415.packetwatcher_api.model.services.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserNotFoundException;

import net.bytebuddy.utility.RandomString;

@RestController
public class RecoverAccountController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping(value = "/account/reset", produces = "application/json")
	public ResponseEntity<?> processAuthenticatedUserPasswordReset(@CookieValue(name = "jwt") String token, @AuthenticationPrincipal User user) {
		try {
			Boolean isValidToken = jwtUtil.validateToken(token, user);
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
				
				String token = RandomString.make(30);
				userService.setPasswordResetToken(email, token);
				userService.sendPasswordResetEmail(email, token);
				userService.addPasswordResetTimestamp(email, timestamp);

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
				return ResponseEntity.notFound().build();
			}
			
			userService.updatePassword(user, request.getPassword());
			
			return ResponseEntity.ok(true);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}