package com.jfs415.packetwatcher_core.model.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.PacketRecordKey;

@Repository
public interface FlaggedPacketRepository extends JpaRepository<FlaggedPacketRecord, PacketRecordKey> {

	FlaggedPacketRecord findTopByOrderByKey_TimestampDesc();

	List<FlaggedPacketRecord> getAllByKey_TimestampBetween(Timestamp start, Timestamp end);

	List<FlaggedPacketRecord> getAllByKey_TimestampLessThan(Timestamp timestamp);

	List<FlaggedPacketRecord> getAllByFlaggedCountry(String flaggedCountry);
	
	List<FlaggedPacketRecord> findTop30ByOrderByKey_TimestampDesc();

	void deleteAllByKey_TimestampLessThan(Timestamp timestamp);

}
