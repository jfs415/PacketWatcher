package com.jfs415.packetwatcher_api.model.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.repositories.LockedUserHistoryRepository;
import com.jfs415.packetwatcher_api.model.user.LockedUserHistory;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserActivationState;
import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import com.jfs415.packetwatcher_api.views.collections.LockedUserHistoryCollectionView;

@Service
public class UserActivationStateService {

	@Autowired
	private LockedUserHistoryRepository lockedUserHistoryRepo;
	
	@Autowired
	private UserService userService;

	private static final int LOGIN_ATTEMPT_THRESHOLD = 3;

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

	private void updateLockedUserHistory(User user, long time) {
		
	}
	
	public LockedUserHistoryView getLockedUserHistoryRecordsByUsername(String username) {
		return lockedUserHistoryRepo.getReferenceById(username).toLockedUserHistoryView();
	}

	public LockedUserHistoryCollectionView getAllLockedUserHistoryRecords() {
		return new LockedUserHistoryCollectionView(lockedUserHistoryRepo.findAll().stream().map(LockedUserHistory::toLockedUserHistoryView).collect(Collectors.toList()));
	}
	
}
