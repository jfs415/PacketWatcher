package com.jfs415.packetwatcher_api.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.user.LockedUserHistory;

@Repository
public interface LockedUserHistoryRepository extends JpaRepository<LockedUserHistory, String> { }
