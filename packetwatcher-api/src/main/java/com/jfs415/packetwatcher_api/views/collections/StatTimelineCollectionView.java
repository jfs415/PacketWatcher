package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.model.core.FlaggedPacketTimelineProjection;

import java.util.List;

public record StatTimelineCollectionView(int durationDays, List<FlaggedPacketTimelineProjection> timelineData) {

}
