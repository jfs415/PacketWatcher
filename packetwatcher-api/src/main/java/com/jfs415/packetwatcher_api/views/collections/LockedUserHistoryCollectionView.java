package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import java.util.ArrayList;
import java.util.List;

public record LockedUserHistoryCollectionView(List<LockedUserHistoryView> lockedUserHistoryViews) {
    public LockedUserHistoryCollectionView {
        lockedUserHistoryViews = lockedUserHistoryViews == null ? new ArrayList<>() : lockedUserHistoryViews;
    }
}
