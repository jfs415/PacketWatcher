package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.StatsView;
import java.util.ArrayList;
import java.util.List;

public record StatsCollectionView(List<StatsView> statsViews) {
    public StatsCollectionView {
        statsViews = statsViews == null ? new ArrayList<>() : statsViews;
    }
}
