package com.jfs415.packetwatcher_api.test.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.controllers.DashboardController;
import com.jfs415.packetwatcher_api.views.collections.FlaggedPacketChoroplethCollectionView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = PacketWatcherApi.class)
class DashboardControllerTests {

    private final DashboardController dashboardController;

    @Autowired
    public DashboardControllerTests(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @Test
    void fetchDashboardChoroplethData() {
        ResponseEntity<FlaggedPacketChoroplethCollectionView> response = dashboardController.getChoroplethMapData();

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().views());
        assertFalse(response.getBody().views().isEmpty());
    }
}
