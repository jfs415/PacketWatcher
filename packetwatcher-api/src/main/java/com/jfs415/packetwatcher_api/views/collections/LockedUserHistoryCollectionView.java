package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class LockedUserHistoryCollectionView {

    private final List<LockedUserHistoryView> lockedUserHistoryViews;

    public LockedUserHistoryCollectionView() {
        this.lockedUserHistoryViews = new ArrayList<>();
    }
}
