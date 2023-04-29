package com.jfs415.packetwatcher_core.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.ArchivedFlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.PacketRecord;
import com.jfs415.packetwatcher_core.model.repositories.ArchivedFlaggedPacketRepository;
import com.jfs415.packetwatcher_core.model.repositories.FlaggedPacketRepository;

@Service
public class PacketService {

	@Autowired
	private FlaggedPacketRepository flaggedPacketRepo;

	@Autowired
	private ArchivedFlaggedPacketRepository archivedPacketRepo;

	private final ArrayList<FlaggedPacketRecord> flaggedPacketSaveQueue = new ArrayList<>();
	private final ArrayList<ArchivedFlaggedPacketRecord> archivedPacketSaveQueue = new ArrayList<>();
	
	public void cleanupAllRecords() {
		archiveAllRecordsOverThreshold();
		purgeAllArchivesOverThreshold();
	}
	
	@Transactional
	public void archiveAllRecordsOverThreshold() {
		LocalDateTime now = LocalDateTime.now();
		Timestamp flaggedThreshold = new Timestamp(convertLocalDateTimeToEpochSecond(now, PacketWatcherCore.getCoreConfigProperties().getFlaggedRetentionDays()));
		Timestamp archiveThreshold = new Timestamp(convertLocalDateTimeToEpochSecond(now, PacketWatcherCore.getCoreConfigProperties().getArchiveRetentionDays()));
		
		List<FlaggedPacketRecord> flaggedRecords = flaggedPacketRepo.getAllByKey_TimestampLessThan(flaggedThreshold);
		List<ArchivedFlaggedPacketRecord> archivedRecords = ArchivedFlaggedPacketRecord.batchConvert(flaggedRecords);
		addBatchToArchivedPacketSaveQueue(archivedRecords);
		
		flaggedPacketRepo.deleteAllByKey_TimestampLessThan(flaggedThreshold);
		archivedPacketRepo.deleteAllByKey_TimestampLessThan(archiveThreshold);
	}
	
	public void purgeAllArchivesOverThreshold() {
		archivedPacketRepo.purgeExpiredRecords();
	}

	@Transactional
	public void savePacketRecord(PacketRecord record) {
		validateRecord(record);

		if (record instanceof FlaggedPacketRecord) {
			flaggedPacketRepo.saveAndFlush((FlaggedPacketRecord) record);
		} else if (record instanceof ArchivedFlaggedPacketRecord) {
			archivedPacketRepo.saveAndFlush((ArchivedFlaggedPacketRecord) record);
		} else {
			throw new IllegalArgumentException("Record must be a FlaggedPacketRecord or ArchivedFlaggedPacketRecord!");
		}
	}

	private void processFlaggedPacketSaveQueue() {
		ArrayList<FlaggedPacketRecord> flaggedSaveQueueCopy;

		synchronized (flaggedPacketSaveQueue) {
			flaggedSaveQueueCopy = new ArrayList<>(flaggedPacketSaveQueue);
			flaggedPacketSaveQueue.clear();
		}

		int size = flaggedSaveQueueCopy.size();
		flaggedPacketRepo.saveAllAndFlush(flaggedSaveQueueCopy);
		PacketWatcherCore.debug("Saved " + size + " flagged packets from the queue");
	}

	private void processArchivedPacketSaveQueue() {
		ArrayList<ArchivedFlaggedPacketRecord> archivedSaveQueueCopy;

		synchronized (archivedPacketSaveQueue) {
			archivedSaveQueueCopy = new ArrayList<>(archivedPacketSaveQueue);
			archivedPacketSaveQueue.clear();
		}

		int size = archivedSaveQueueCopy.size();
		archivedPacketRepo.saveAllAndFlush(archivedSaveQueueCopy);
		PacketWatcherCore.debug("Saved " + size + " archived packets from the queue");
	}

	@Transactional
	public void flushSaveQueues() {
		processFlaggedPacketSaveQueue();
		processArchivedPacketSaveQueue();
	}

	public void addToSaveQueue(PacketRecord record) {
		if (record instanceof FlaggedPacketRecord) {
			synchronized (flaggedPacketSaveQueue) {
				flaggedPacketSaveQueue.add((FlaggedPacketRecord) record);
			}
		} else if (record instanceof ArchivedFlaggedPacketRecord) {
			synchronized (archivedPacketSaveQueue) {
				archivedPacketSaveQueue.add((ArchivedFlaggedPacketRecord) record);
			}
		} else {
			throw new IllegalArgumentException("Record must be a FlaggedPacketRecord or PacketRecordRepository!");
		}
	}

	public void addBatchToFlaggedPacketSaveQueue(List<FlaggedPacketRecord> records) {
		validateNotNullOrEmpty(records, true);

		synchronized (flaggedPacketSaveQueue) {
			flaggedPacketSaveQueue.addAll(records);
		}
	}

	public void addBatchToArchivedPacketSaveQueue(List<ArchivedFlaggedPacketRecord> records) {
		validateNotNullOrEmpty(records, false);

		synchronized (archivedPacketSaveQueue) {
			archivedPacketSaveQueue.addAll(records);
		}
	}

	public List<FlaggedPacketRecord> getAllFlaggedPacketRecords() {
		return flaggedPacketRepo.findAll();
	}
	
	public List<FlaggedPacketRecord> getLast30FlaggedPacketRecords() {
		return flaggedPacketRepo.findTop30ByOrderByKey_TimestampDesc();
	}
	
	private long convertLocalDateTimeToEpochSecond(LocalDateTime localDateTime, int offset) {
		return localDateTime.minusDays(offset).atZone(ZoneId.systemDefault()).toEpochSecond();
	}

	private void validateNotNullOrEmpty(List<? extends PacketRecord> records, boolean throwIllegalArgException) {
		if (records == null || records.isEmpty()) {
			if (throwIllegalArgException) {
				throw new IllegalArgumentException("PacketRecord Lists cannot be null or empty!");
			}
		}
	}

	public void validateRecord(PacketRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("The PacketRecord cannot be null!");
		}
	}

}
