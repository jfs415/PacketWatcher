package com.jfs415.packetwatcher_api.views;

import java.sql.Timestamp;

public record LockedUserHistoryView(
        String username, Timestamp firstLocked, Timestamp lastLocked, int numberOfTimesLocked) {}
