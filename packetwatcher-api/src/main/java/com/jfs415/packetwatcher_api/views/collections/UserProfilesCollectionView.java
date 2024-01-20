package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.UserProfileView;
import java.util.ArrayList;
import java.util.List;

public record UserProfilesCollectionView(List<UserProfileView> profiles) {
    public UserProfilesCollectionView {
        profiles = profiles == null ? new ArrayList<>() : profiles;
    }
}
