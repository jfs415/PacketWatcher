package com.jfs415.packetwatcher_core.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.repositories.FlaggedPacketRepository;

@Service
public class PacketService {

	@Autowired
	private FlaggedPacketRepository flaggedPacketRepo;

	private final ArrayList<FlaggedPacketRecord> flaggedPacketSaveQueue = new ArrayList<>();

	@Transactional
	public void savePacketRecord(FlaggedPacketRecord record) {
		validateRecord(record);
		flaggedPacketRepo.saveAndFlush(record);
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

	@Transactional
	public void flushSaveQueues() {
		processFlaggedPacketSaveQueue();
	}

	public void addToSaveQueue(FlaggedPacketRecord record) {
		synchronized (flaggedPacketSaveQueue) {
			flaggedPacketSaveQueue.add(record);
		}
	}

	public void addBatchToFlaggedPacketSaveQueue(List<FlaggedPacketRecord> records) {
		validateNotNullOrEmpty(records);

		synchronized (flaggedPacketSaveQueue) {
			flaggedPacketSaveQueue.addAll(records);
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

	private void validateNotNullOrEmpty(List<FlaggedPacketRecord> records) {
		if (records == null || records.isEmpty()) {
			throw new IllegalArgumentException("PacketRecord Lists cannot be null or empty!");
		}
	}

	public void validateRecord(FlaggedPacketRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("The PacketRecord cannot be null!");
		}
	}

}
