package com.jfs415.packetwatcher_api.model.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;

@Repository
public interface HostnameStatsRepository extends LimitedAccessRepository<HostnameStatsRecord, String> {
	
	List<HostnameStatsRecord> findAllByFirstCaughtBefore(Timestamp before);

	List<HostnameStatsRecord> findAllByFirstCaughtAfter(Timestamp timestamp);

	List<HostnameStatsRecord> findAllByFirstCaughtBetween(Timestamp start, Timestamp end);

	List<HostnameStatsRecord> findAllByLastCaughtBefore(Timestamp before);

	List<HostnameStatsRecord> findAllByLastCaughtAfter(Timestamp after);

	List<HostnameStatsRecord> findAllByLastCaughtBetween(Timestamp start, Timestamp end);

	List<HostnameStatsRecord> findAllByOrderByRecordsCaughtDesc();

}
