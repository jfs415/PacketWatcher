package com.jfs415.packetwatcher_api.model.repositories;

import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;

@Repository
public interface AuthenticationEventRepository extends PacketWatcherEventRepository<AuthenticationEvent, String> {
	//Intentionally empty, all used methods are inherited currently. Type specific ones can be added here.
}
