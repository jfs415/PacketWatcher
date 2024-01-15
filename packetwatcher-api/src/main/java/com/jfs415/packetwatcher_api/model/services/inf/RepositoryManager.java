package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;
import java.io.Serializable;

public interface RepositoryManager {

    <T extends EventMappedSuperclass, E extends Serializable> PacketWatcherEventRepository<T, E> getEventRepository(
            Class<?> entity);
}
