package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryStatsRepository extends PacketWatcherStatsRepository<CountryStatsRecord, String> {}
