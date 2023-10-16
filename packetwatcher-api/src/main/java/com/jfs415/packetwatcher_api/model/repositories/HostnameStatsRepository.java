package com.jfs415.packetwatcher_api.model.repositories;

import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;

@Repository
public interface HostnameStatsRepository extends PacketWatcherStatsRepository<HostnameStatsRecord, String> {

}
