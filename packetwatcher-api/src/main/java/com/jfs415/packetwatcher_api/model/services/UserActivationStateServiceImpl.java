package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.repositories.LockedUserHistoryRepository;
import com.jfs415.packetwatcher_api.model.services.inf.UserActivationStateService;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import com.jfs415.packetwatcher_api.model.user.LockedUserHistory;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserActivationState;
import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import com.jfs415.packetwatcher_api.views.collections.LockedUserHistoryCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserActivationStateServiceImpl implements UserActivationStateService {

    private final LockedUserHistoryRepository lockedUserHistoryRepo;

    private final UserService userService;

    private static final int LOGIN_ATTEMPT_THRESHOLD = 3;

    @Autowired
    public UserActivationStateServiceImpl(LockedUserHistoryRepository lockedUserHistoryRepo, UserServiceImpl userService) {
        this.lockedUserHistoryRepo = lockedUserHistoryRepo;
        this.userService = userService;
    }

    @Transactional
    public void handleFailedUserLogin(String username, long time) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        int loginAttempts = user.getFailedLoginAttempts();

        if (loginAttempts < LOGIN_ATTEMPT_THRESHOLD) {
            loginAttempts += 1;
            user.setFailedLoginAttempts(loginAttempts);
            user.setUserActivationState(UserActivationState.LOCKED);
            updateLockedUserHistory(user, time);

            userService.saveUser(user);
        }
    }
    
    public void updateLockedUserHistory(User user, long time) {
        throw new UnsupportedOperationException();
    }

    @Transactional()
    public LockedUserHistoryView getLockedUserHistoryRecordsByUsername(String username) {
        return lockedUserHistoryRepo.getReferenceById(username).toLockedUserHistoryView();
    }

    @Transactional
    public LockedUserHistoryCollectionView getAllLockedUserHistoryRecords() {
        return new LockedUserHistoryCollectionView(lockedUserHistoryRepo.findAll().stream().map(LockedUserHistory::toLockedUserHistoryView).toList());
    }

}
