package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.services.inf.UserActivationStateService;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import com.jfs415.packetwatcher_api.views.UserProfileView;
import com.jfs415.packetwatcher_api.views.collections.LockedUserHistoryCollectionView;
import com.jfs415.packetwatcher_api.views.collections.UserProfilesCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    private final UserService userService;
    private final UserActivationStateService userActivationStateService;

    @Autowired
    public UsersController(UserService userService, UserActivationStateService userActivationStateService) {
        this.userService = userService;
        this.userActivationStateService = userActivationStateService;
    }

    @GetMapping(value = "/users", params = "token")
    public ResponseEntity<UserProfilesCollectionView> getAllUsers(@RequestParam String token) {
        return ResponseEntity.ok(userService.getAllUserProfilesWithLevelLessThanEqual(token));
    }

    @GetMapping("/users/locked/history")
    public ResponseEntity<LockedUserHistoryCollectionView> getAllLockedUserHistory() {
        return ResponseEntity.ok(userActivationStateService.getAllLockedUserHistoryRecords());
    }

    @GetMapping("/users/locked")
    public ResponseEntity<UserProfilesCollectionView> getAllLockedAccounts() {
        return ResponseEntity.ok(userService.getLockedUserProfiles());
    }

    @GetMapping(value = "/profile", params = "token")
    public ResponseEntity<UserProfileView> getUserProfile(@RequestParam String token) {
        try {
            UserProfileView returnProfile = userService.getUserProfile(token);
            return ResponseEntity.ok(returnProfile);
        } catch (UserNotFoundException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/profile/update")
    public ResponseEntity<UserProfileView> updateUserProfile(@RequestBody UserProfileView updatedUserProfile) {
        try {
            UserProfileView returnProfile = userService.updateUser(updatedUserProfile);
            return ResponseEntity.ok(returnProfile);
        } catch (UserNotFoundException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
