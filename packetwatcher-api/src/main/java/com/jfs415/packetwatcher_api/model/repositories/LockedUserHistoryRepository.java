package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.user.LockedUserHistory;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockedUserHistoryRepository extends JpaRepository<LockedUserHistory, String> {

    List<LockedUserHistory> findAllByFirstLockedBefore(Timestamp before);

    List<LockedUserHistory> findAllByFirstLockedAfter(Timestamp after);

    List<LockedUserHistory> findAllByFirstLockedBetween(Timestamp stop, Timestamp start);

    List<LockedUserHistory> findAllByLastLockedBefore(Timestamp before);

    List<LockedUserHistory> findAllByLastLockedAfter(Timestamp after);

    List<LockedUserHistory> findAllByLastLockedBetween(Timestamp start, Timestamp stop);

    List<LockedUserHistory> findAllByNumberOfTimesLockedGreaterThan(int numberOfTimesLocked);

    List<LockedUserHistory> findAllByNumberOfTimesLockedGreaterThanEqual(int numberOfTimesLocked);

    List<LockedUserHistory> findAllByNumberOfTimesLockedLessThan(int numberOfTimesLocked);

    List<LockedUserHistory> findAllByNumberOfTimesLockedLessThanEqual(int numberOfTimesLocked);

    List<LockedUserHistory> findAllByNumberOfTimesLockedBetween(int start, int stop);
}
