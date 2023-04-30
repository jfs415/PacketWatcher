package com.jfs415.packetwatcher_api.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;

@Repository
public interface AuthenticationEventRepository extends JpaRepository<AuthenticationEvent, String> {

}
