package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.model.analytics.StatDashboardData;
import com.jfs415.packetwatcher_api.model.analytics.StatOrderBy;
import com.jfs415.packetwatcher_api.model.analytics.StatsDashboardTopic;
import com.jfs415.packetwatcher_api.model.core.FlaggedPacketRecordProjection;
import com.jfs415.packetwatcher_api.model.repositories.FlaggedPacketProjectionRepository;
import com.jfs415.packetwatcher_api.model.services.inf.StatsService;
import com.jfs415.packetwatcher_api.util.SearchFilter;
import com.jfs415.packetwatcher_api.views.StatsTopNDashboardView;
import com.jfs415.packetwatcher_api.views.collections.StatTimelineCollectionView;
import com.jfs415.packetwatcher_api.views.collections.StatsTopNDashboardCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    private final FlaggedPacketProjectionRepository flaggedPacketProjectionRepository;

    @Autowired
    public StatsServiceImpl(FlaggedPacketProjectionRepository flaggedPacketProjectionRepository) {
        this.flaggedPacketProjectionRepository = flaggedPacketProjectionRepository;
    }

    @Override
    public StatsTopNDashboardCollectionView getDashboardViews(
            int count, StatsDashboardTopic topic, StatOrderBy orderBy, @Nullable SearchFilter searchFilter) {
        List<FlaggedPacketRecordProjection> records = getProjectionRecordsFromTopic(count, topic);

        StringBuilder titleBuilder = new StringBuilder("Top ");
        titleBuilder.append(count).append(" ").append(topic.getTopicString());

        if (orderBy != StatOrderBy.NONE) {
            titleBuilder.append(" ordered by ").append(orderBy.getTopicString());
        }

        if (searchFilter != null) {
            titleBuilder.append(" ").append(searchFilter.getFilterString());
        }

        List<StatsTopNDashboardView> dataViews = new ArrayList<>();
        List<StatDashboardData> data = new ArrayList<>();

        for (FlaggedPacketRecordProjection projection : records) {
            data.add(new StatDashboardData(projection.projectionDataString(), projection.count()));
        }

        dataViews.add(new StatsTopNDashboardView(count, topic, orderBy, titleBuilder.toString(), data));

        return new StatsTopNDashboardCollectionView(dataViews);
    }

    @Override
    public StatTimelineCollectionView getTimelineStats(long startMillis, long endMillis, int days) {
        return new StatTimelineCollectionView(days, flaggedPacketProjectionRepository.getTimelineData(startMillis, endMillis));
    }

    private List<FlaggedPacketRecordProjection> getProjectionRecordsFromTopic(int count, StatsDashboardTopic topic) {
        switch (topic) {
            case COUNTRY -> {
                return flaggedPacketProjectionRepository.getTopByCountry(count);
            }
            case HOSTNAME -> {
                return flaggedPacketProjectionRepository.getTopByHostname(count);
            }
            case TIME_OF_DAY -> {
                return flaggedPacketProjectionRepository.getTopByTimeOfDay(count);
            }
            case DAY_OF_WEEK -> {
                return flaggedPacketProjectionRepository.getTopByDayOfWeek(count);
            }
            case DAY_OF_MONTH -> {
                return flaggedPacketProjectionRepository.getTopByDayOfMonth(count);
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}
