package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.events.IAuthEventType;
import java.sql.Timestamp;

public record EventView(Timestamp timestamp, String username, String ipAddress, IAuthEventType eventType) {}
