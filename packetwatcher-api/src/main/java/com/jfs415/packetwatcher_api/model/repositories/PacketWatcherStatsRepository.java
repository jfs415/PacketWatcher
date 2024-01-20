package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.analytics.StatsRecord;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PacketWatcherStatsRepository<T extends StatsRecord, ID extends Serializable>
        extends LimitedAccessRepository<T, ID> {

    List<T> findAllByFirstCaughtBefore(Timestamp before);

    List<T> findAllByFirstCaughtAfter(Timestamp timestamp);

    List<T> findAllByFirstCaughtBetween(Timestamp start, Timestamp end);

    List<T> findAllByLastCaughtBefore(Timestamp before);

    List<T> findAllByLastCaughtAfter(Timestamp after);

    List<T> findAllByLastCaughtBetween(Timestamp start, Timestamp end);

    List<T> findAllByLastCollectionTimeBefore(Timestamp before);

    List<T> findAllByLastCollectionTimeAfter(Timestamp after);

    List<T> findAllByLastCollectionTimeBetween(Timestamp start, Timestamp end);

    List<T> findAllByOrderByNameAsc();

    List<T> findAllByRecordsCaughtLessThan(int recordsCaught);

    List<T> findAllByRecordsCaughtLessThanEqual(int recordsCaught);

    List<T> findAllByRecordsCaughtGreaterThan(int recordsCaught);

    List<T> findAlByRecordsCaughtGreaterThanEqual(int recordsCaught);

    List<T> findAllByRecordsCaughtBetween(int start, int end);

    List<T> findAll();
}
