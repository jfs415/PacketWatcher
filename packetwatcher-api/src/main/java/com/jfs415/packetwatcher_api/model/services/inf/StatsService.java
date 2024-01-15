package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.model.analytics.StatOrderBy;
import com.jfs415.packetwatcher_api.model.analytics.StatsDashboardTopic;
import com.jfs415.packetwatcher_api.util.SearchFilter;
import com.jfs415.packetwatcher_api.views.collections.StatTimelineCollectionView;
import com.jfs415.packetwatcher_api.views.collections.StatsTopNDashboardCollectionView;

public interface StatsService {

    StatsTopNDashboardCollectionView getDashboardViews(
            int count, StatsDashboardTopic topic, StatOrderBy breakdown, SearchFilter searchFilter);
    
    StatTimelineCollectionView getTimelineStats(long startMillis, long endMillis, int days);
}
