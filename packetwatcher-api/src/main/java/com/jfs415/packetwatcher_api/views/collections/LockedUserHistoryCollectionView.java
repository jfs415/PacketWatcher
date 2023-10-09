package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Immutable
public class LockedUserHistoryCollectionView {

    private final List<LockedUserHistoryView> lockedUserHistoryViews;

    public LockedUserHistoryCollectionView() {
        this.lockedUserHistoryViews = new ArrayList<>();
    }

}
