package com.jfs415.packetwatcher_api.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;

public interface AuthorizationEventRepository extends JpaRepository<AuthorizationEvent, String> {
	
}
