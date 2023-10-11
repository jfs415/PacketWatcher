package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;

public interface AuthorizationEventRepository extends PacketWatcherEventRepository<AuthorizationEvent, String> {
    //Intentionally empty, all used methods are inherited currently. Type specific ones can be added here.
}
