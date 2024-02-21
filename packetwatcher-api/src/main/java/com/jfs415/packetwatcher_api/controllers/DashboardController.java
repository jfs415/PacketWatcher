package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.services.inf.DashboardService;
import com.jfs415.packetwatcher_api.views.collections.FlaggedPacketChoroplethCollectionView;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<FlaggedPacketChoroplethCollectionView> getChoroplethMapData() {
        Optional<FlaggedPacketChoroplethCollectionView> view = dashboardService.getDashboardChoroplethData();

        return view.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
