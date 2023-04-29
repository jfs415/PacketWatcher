package com.jfs415.packetwatcher_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfs415.packetwatcher_api.model.services.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.views.UserProfileView;

@RestController
public class UsersController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(userService.getAllUserProfilesWithLevelLessThanEqual(user.getAuthority()));
	}

	@GetMapping("/users/locked/history")
	public ResponseEntity<?> getAllLockedUserHistory() {
		return ResponseEntity.ok(userService.getAllProfiles());
	}

	@PutMapping("/profile/update")
	public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileView updatedUserProfile) {
		return ResponseEntity.ok(userService.updateUser(user, updatedUserProfile));
	}

}
