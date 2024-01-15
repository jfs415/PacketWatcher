package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.analytics.StatOrderBy;
import com.jfs415.packetwatcher_api.model.analytics.StatsDashboardTopic;
import com.jfs415.packetwatcher_api.model.services.StatsServiceImpl;
import com.jfs415.packetwatcher_api.model.services.inf.StatsService;
import com.jfs415.packetwatcher_api.util.SearchFilter;
import com.jfs415.packetwatcher_api.views.collections.StatTimelineCollectionView;
import com.jfs415.packetwatcher_api.views.collections.StatsTopNDashboardCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/stats")
public class StatsController {

    private final StatsService statsServiceImpl;

    @Autowired
    public StatsController(StatsServiceImpl statsServiceImpl) {
        this.statsServiceImpl = statsServiceImpl;
    }

    @GetMapping(value = "/dashboard")
    public ResponseEntity<StatsTopNDashboardCollectionView> getDashboardStats(
            @RequestParam("count") int count,
            @RequestParam("topic") StatsDashboardTopic topic,
            @RequestParam("orderBy") StatOrderBy orderBy,
            @RequestParam(value = "filter", required = false) Optional<SearchFilter> filter) {
        return ResponseEntity.ok(statsServiceImpl.getDashboardViews(count, topic, orderBy, filter.orElse(null)));
    }
    
    @GetMapping(value = "/dashboard/timeline")
    public ResponseEntity<StatTimelineCollectionView> getTimelineStats(@RequestParam("startMillis") long startMillis, @RequestParam("endMillis") long endMillis, @RequestParam("days") int days) {
        return ResponseEntity.ok(statsServiceImpl.getTimelineStats(startMillis, endMillis, days));
    }
}
