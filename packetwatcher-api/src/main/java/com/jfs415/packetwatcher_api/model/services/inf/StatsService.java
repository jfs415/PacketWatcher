package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.model.analytics.StatOrderBy;
import com.jfs415.packetwatcher_api.model.analytics.StatsDashboardTopic;
import com.jfs415.packetwatcher_api.util.SearchFilter;
import com.jfs415.packetwatcher_api.views.collections.StatTimelineCollectionView;
import com.jfs415.packetwatcher_api.views.collections.StatsTopNDashboardCollectionView;
import java.util.Optional;

public interface StatsService {

    Optional<StatsTopNDashboardCollectionView> getDashboardViews(
            int count, StatsDashboardTopic topic, StatOrderBy breakdown, SearchFilter searchFilter);

    Optional<StatTimelineCollectionView> getTimelineStats(long startMillis, long endMillis, int days);
}
