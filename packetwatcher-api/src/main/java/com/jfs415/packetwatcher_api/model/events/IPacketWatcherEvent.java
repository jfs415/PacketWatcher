package com.jfs415.packetwatcher_api.model.events;

import java.sql.Timestamp;

public interface IPacketWatcherEvent {

    Timestamp getTimestamp();

    String getUsername();

    String getIpAddress();
}
