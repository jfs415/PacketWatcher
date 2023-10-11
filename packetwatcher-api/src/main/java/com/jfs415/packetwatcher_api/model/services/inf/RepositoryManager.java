package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.model.analytics.StatsRecord;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherStatsRepository;

import java.io.Serializable;

public interface RepositoryManager {

    <T extends EventMappedSuperclass, E extends Serializable> PacketWatcherEventRepository<T, E> getEventRepository(Class<?> entity);

    <T extends StatsRecord, E extends Serializable> PacketWatcherStatsRepository<T, E> getStatsRepository(Class<?> entity);

}
