package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationEventRepository extends PacketWatcherEventRepository<AuthenticationEvent, String> {
    // Intentionally empty, all used methods are inherited currently. Type specific ones can be added here.
}
