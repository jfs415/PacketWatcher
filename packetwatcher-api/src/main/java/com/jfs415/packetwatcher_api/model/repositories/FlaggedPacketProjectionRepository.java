package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.core.FlaggedPacketRecordProjection;
import com.jfs415.packetwatcher_api.model.core.FlaggedPacketTimelineProjection;

import java.util.List;

public interface FlaggedPacketProjectionRepository {
    List<FlaggedPacketRecordProjection> getTopByHostname(int limit);

    List<FlaggedPacketRecordProjection> getTopByCountry(int limit);

    List<FlaggedPacketRecordProjection> getTopByTimeOfDay(int limit);

    List<FlaggedPacketRecordProjection> getTopByDayOfWeek(int limit);

    List<FlaggedPacketRecordProjection> getTopByDayOfMonth(int limit);
    
    List<FlaggedPacketTimelineProjection> getTimelineData(long startMillis, long endMillis);
}
