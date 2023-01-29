package com.jfs415.packetwatcher_api.model.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;

@Repository
public interface CountryStatsRepository extends LimitedAccessRepository<CountryStatsRecord, String> {

	List<CountryStatsRecord> findAllByFirstCaughtBefore(Timestamp before);

	List<CountryStatsRecord> findAllByFirstCaughtAfter(Timestamp timestamp);

	List<CountryStatsRecord> findAllByFirstCaughtBetween(Timestamp start, Timestamp end);

	List<CountryStatsRecord> findAllByLastCaughtBefore(Timestamp before);

	List<CountryStatsRecord> findAllByLastCaughtAfter(Timestamp after);

	List<CountryStatsRecord> findAllByLastCaughtBetween(Timestamp start, Timestamp end);

	List<CountryStatsRecord> findAllByOrderByRecordsCaughtDesc();

	List<CountryStatsRecord> findAllByOrderByMostCaughtHostnameCountDesc();

	List<CountryStatsRecord> findAllByOrderByNameAsc();

	List<CountryStatsRecord> findAllByOrderByMostCaughtHostnameAsc();

}
