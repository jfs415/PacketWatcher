package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface HostnameStatsRepository extends PacketWatcherStatsRepository<HostnameStatsRecord, String> {}
