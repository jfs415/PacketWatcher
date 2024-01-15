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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    private final FlaggedPacketProjectionRepository flaggedPacketProjectionRepository;

    @Autowired
    public StatsServiceImpl(FlaggedPacketProjectionRepository flaggedPacketProjectionRepository) {
        this.flaggedPacketProjectionRepository = flaggedPacketProjectionRepository;
    }

    @Override
    public Optional<StatsTopNDashboardCollectionView> getDashboardViews(
            int count, StatsDashboardTopic topic, StatOrderBy orderBy, @Nullable SearchFilter searchFilter) {
        if (count < 1 || topic == null || orderBy == null) {
            return Optional.empty();
        }

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

        return Optional.of(new StatsTopNDashboardCollectionView(dataViews));
    }

    @Override
    public Optional<StatTimelineCollectionView> getTimelineStats(long startMillis, long endMillis, int days) {
        if (startMillis < 0 || endMillis < 0 || days < 1 || startMillis >= endMillis) {
            return Optional.empty();
        }

        return Optional.of(new StatTimelineCollectionView(
                days, flaggedPacketProjectionRepository.getTimelineData(startMillis, endMillis)));
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
