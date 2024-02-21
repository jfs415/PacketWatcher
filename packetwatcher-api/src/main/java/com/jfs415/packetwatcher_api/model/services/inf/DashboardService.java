package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.views.collections.FlaggedPacketChoroplethCollectionView;
import java.util.Optional;

public interface DashboardService {

    Optional<FlaggedPacketChoroplethCollectionView> getDashboardChoroplethData();
}
