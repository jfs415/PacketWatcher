package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.model.user.LockedUserHistory;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public final class LockedUserHistoryView {

    private final String username;
    private final Timestamp firstLocked;
    private final Timestamp lastLocked;
    private final int numberOfTimesLocked;

    public LockedUserHistoryView(LockedUserHistory lockedUserHistory) {
        this.username = lockedUserHistory.getUsername();
        this.firstLocked = lockedUserHistory.getFirstLocked();
        this.lastLocked = lockedUserHistory.getLastLocked();
        this.numberOfTimesLocked = lockedUserHistory.getNumberOfTimesLocked();
    }
}
