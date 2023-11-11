package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserParams;
import com.jfs415.packetwatcher_api.views.UserProfileView;
import com.jfs415.packetwatcher_api.views.collections.UserProfilesCollectionView;

import javax.mail.MessagingException;

public interface UserService {

    User createUser(UserParams userParams);

    void saveUser(User user);

    void deleteUser(User user);

    UserProfileView updateUser(UserProfileView existingProfile, UserProfileView updatedProfile);

    User getUserByUsername(String username) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    UserProfilesCollectionView getAllUserProfilesWithLevelLessThanEqual(String token);

    UserProfilesCollectionView getLockedUserProfiles();

    boolean isEmailInUse(String email);

    boolean isUsernameInUse(String username);

    boolean isCorrectPasswordResetToken(String requestToken, String storedToken);

    void setPasswordResetToken(String email, String token) throws UserNotFoundException;

    void updatePassword(User user, String password);

    void sendPasswordResetEmail(String email, String token) throws MessagingException;

    void addPasswordResetTimestamp(String email, long timestamp);

    void purgeExpiredPasswordResetRequests();

    void handleAccountRecoveryInitiation(long timestamp, String email) throws MessagingException;

}
