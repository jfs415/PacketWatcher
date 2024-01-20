package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.util.SearchAmount;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.StatsCollectionView;

public interface StatsService {

    StatsCollectionView getStatsByType(Class<?> statsType);

    StatsCollectionView getStatsByTypeAndLastCaughtWithTimeframe(Class<?> statsType, SearchTimeframe timeframe);

    StatsCollectionView getStatsByTypeAndFirstCaughtWithTimeframe(Class<?> statsType, SearchTimeframe timeframe);

    StatsCollectionView getStatsByTypeWithRecordsCaughtAmount(Class<?> statsType, SearchAmount amount);
}
