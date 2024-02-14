package com.jfs415.packetwatcher_api.test.integration;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.controllers.StatsController;
import com.jfs415.packetwatcher_api.model.analytics.StatOrderBy;
import com.jfs415.packetwatcher_api.model.analytics.StatsDashboardTopic;
import com.jfs415.packetwatcher_api.views.collections.StatTimelineCollectionView;
import com.jfs415.packetwatcher_api.views.collections.StatsTopNDashboardCollectionView;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = PacketWatcherApi.class)
class StatsControllerTest {

    private final StatsController statsController;

    @Autowired
    public StatsControllerTest(StatsController statsController) {
        this.statsController = statsController;
    }

    @Test
    void testInvalidTopNDashboardStatsEndpoint() {
        int count = 0;
        StatsDashboardTopic topic = StatsDashboardTopic.COUNTRY;
        StatOrderBy orderBy = StatOrderBy.OLDEST_SEEN;

        ResponseEntity<StatsTopNDashboardCollectionView> response =
                statsController.getDashboardStats(count, topic, orderBy, Optional.empty());
        assertSame(HttpStatus.BAD_REQUEST, response.getStatusCode());

        count = 5;
        topic = null;
        response = statsController.getDashboardStats(count, topic, orderBy, Optional.empty());
        assertSame(HttpStatus.BAD_REQUEST, response.getStatusCode());

        topic = StatsDashboardTopic.COUNTRY;
        orderBy = null;
        response = statsController.getDashboardStats(count, topic, orderBy, Optional.empty());
        assertSame(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testInvalidDashboardTimelineEndpoint() {
        long start = -10000;
        long end = -1000;
        int days = 90;

        ResponseEntity<StatTimelineCollectionView> view = statsController.getTimelineStats(start, end, days);
        assertSame(HttpStatus.BAD_REQUEST, view.getStatusCode());

        start = 100000;
        end = 1000;

        view = statsController.getTimelineStats(start, end, days);
        assertSame(HttpStatus.BAD_REQUEST, view.getStatusCode());

        end = System.currentTimeMillis();
        start = start - 10000;
        days = 0;
        view = statsController.getTimelineStats(start, end, days);
        assertSame(HttpStatus.BAD_REQUEST, view.getStatusCode());
    }

    @Test
    void testValidDashboardTimelineEndpoint() {
        long end = System.currentTimeMillis();
        long start = end - 10000;
        int days = 2;

        ResponseEntity<StatTimelineCollectionView> view = statsController.getTimelineStats(start, end, days);
        assertSame(HttpStatus.OK, view.getStatusCode());
    }

    public static Stream<Map<StatsDashboardTopic, StatOrderBy>> validDashboardComponentsMap() {
        Map<StatsDashboardTopic, StatOrderBy> componentMap = new HashMap<>();

        for (StatsDashboardTopic topic : StatsDashboardTopic.values()) {
            for (StatOrderBy orderBy : StatOrderBy.values()) {
                componentMap.put(topic, orderBy);
            }
        }

        return Stream.of(componentMap);
    }

    @ParameterizedTest
    @MethodSource("validDashboardComponentsMap")
    void testValidTopNDashboardStatsEndpoint(Map<StatsDashboardTopic, StatOrderBy> validComponentMap) {
        int count = 5;
        validComponentMap.forEach(((topic, statOrderBy) -> {
            ResponseEntity<StatsTopNDashboardCollectionView> response =
                    statsController.getDashboardStats(count, topic, statOrderBy, Optional.empty());
            assertSame(HttpStatus.OK, response.getStatusCode());
        }));
    }
}
