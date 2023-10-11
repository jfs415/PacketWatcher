package com.jfs415.packetwatcher_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfs415.packetwatcher_api.model.services.UserActivationStateService;
import com.jfs415.packetwatcher_api.model.services.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.views.UserProfileView;

@RestController
public class UsersController {

    private final UserService userService;
    private final UserActivationStateService userActivationStateService;

    @Autowired
    public UsersController(UserService userService, UserActivationStateService userActivationStateService) {
        this.userService = userService;
        this.userActivationStateService = userActivationStateService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getAllUserProfilesWithLevelLessThanEqual(user.getLevel()));
    }

    @GetMapping("/users/locked/history")
    public ResponseEntity<?> getAllLockedUserHistory() {
        return ResponseEntity.ok(userActivationStateService.getAllLockedUserHistoryRecords());
    }

    @GetMapping("/users/locked")
    public ResponseEntity<?> getAllLockedAccounts() {
        return ResponseEntity.ok(userService.getLockedUserProfiles());
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileView updatedUserProfile) {
        return ResponseEntity.ok(userService.updateUser(user, updatedUserProfile));
    }

}
