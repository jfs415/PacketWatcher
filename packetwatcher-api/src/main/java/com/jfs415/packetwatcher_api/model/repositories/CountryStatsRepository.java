package com.jfs415.packetwatcher_api.model.repositories;

import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;

@Repository
public interface CountryStatsRepository extends PacketWatcherStatsRepository<CountryStatsRecord, String> {

}
