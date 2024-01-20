package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import com.jfs415.packetwatcher_api.views.collections.LockedUserHistoryCollectionView;

public interface UserActivationStateService {

    void handleFailedUserLogin(String username, long time) throws UserNotFoundException;

    LockedUserHistoryView getLockedUserHistoryRecordsByUsername(String username);

    void updateLockedUserHistory(User user, long time);

    LockedUserHistoryCollectionView getAllLockedUserHistoryRecords();
}
