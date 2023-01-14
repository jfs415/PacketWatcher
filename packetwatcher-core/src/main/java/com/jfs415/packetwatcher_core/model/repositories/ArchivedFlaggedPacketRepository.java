package com.jfs415.packetwatcher_core.model.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jfs415.packetwatcher_core.model.packets.ArchivedFlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.PacketRecordKey;

@Repository
public interface ArchivedFlaggedPacketRepository extends JpaRepository<ArchivedFlaggedPacketRecord, PacketRecordKey> {

	List<ArchivedFlaggedPacketRecord> getAllByKey_TimestampLessThan(Timestamp timestamp);

	void deleteAllByKey_TimestampLessThan(Timestamp timestamp);

	@Modifying
	@Transactional
	@Query(value = "DELETE from archived_flagged_packet_records where expiration_timestamp <= NOW()", nativeQuery = true)
	void purgeExpiredRecords();

}
