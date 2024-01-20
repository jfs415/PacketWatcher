package com.jfs415.packetwatcher_api.views;

import java.sql.Timestamp;

public record StatsView(
        String name, int recordsCaught, Timestamp firstCaught, Timestamp lastCaught, Timestamp lastCollectionTime) {}
