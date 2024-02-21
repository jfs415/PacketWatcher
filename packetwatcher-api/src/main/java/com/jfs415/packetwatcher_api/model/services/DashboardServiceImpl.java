package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.model.repositories.FlaggedPacketProjectionRepository;
import com.jfs415.packetwatcher_api.model.services.inf.DashboardService;
import com.jfs415.packetwatcher_api.views.FlaggedPacketChoroplethView;
import com.jfs415.packetwatcher_api.views.collections.FlaggedPacketChoroplethCollectionView;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final FlaggedPacketProjectionRepository flaggedPacketProjectionRepository;

    @Autowired
    public DashboardServiceImpl(FlaggedPacketProjectionRepository flaggedPacketProjectionRepository) {
        this.flaggedPacketProjectionRepository = flaggedPacketProjectionRepository;
    }

    @Override
    public Optional<FlaggedPacketChoroplethCollectionView> getDashboardChoroplethData() {
        return Optional.of(new FlaggedPacketChoroplethCollectionView(
                flaggedPacketProjectionRepository.getChoroplethMapData().stream()
                        .map(p -> new FlaggedPacketChoroplethView(p.country(), p.count()))
                        .toList()));
    }
}
