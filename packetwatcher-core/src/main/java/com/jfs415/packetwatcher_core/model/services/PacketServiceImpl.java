package com.jfs415.packetwatcher_core.model.services;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.repositories.FlaggedPacketRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacketServiceImpl implements PacketService {

    private final FlaggedPacketRepository flaggedPacketRepo;

    private static final Logger logger = LoggerFactory.getLogger(PacketServiceImpl.class);

    private final ArrayList<FlaggedPacketRecord> flaggedPacketSaveQueue = new ArrayList<>();

    @Value("${packetwatcher-core.flagged-packet-retention-days}")
    private int flaggedPacketRetentionDays; // TODO implement retention period removal query

    @Autowired
    public PacketServiceImpl(FlaggedPacketRepository flaggedPacketRepo) {
        this.flaggedPacketRepo = flaggedPacketRepo;
    }

    @Override
    @Transactional
    public void savePacketRecord(FlaggedPacketRecord packetRecord) {
        validateRecord(packetRecord);
        flaggedPacketRepo.saveAndFlush(packetRecord);
    }

    @Override
    @Transactional
    public void flushSaveQueues() {
        processFlaggedPacketSaveQueue();
    }

    @Override
    public void addToSaveQueue(FlaggedPacketRecord packetRecord) {
        synchronized (flaggedPacketSaveQueue) {
            flaggedPacketSaveQueue.add(packetRecord);
        }
    }

    @Override
    public void addBatchToFlaggedPacketSaveQueue(List<FlaggedPacketRecord> packetRecords) {
        validateNotNullOrEmpty(packetRecords);

        synchronized (flaggedPacketSaveQueue) {
            flaggedPacketSaveQueue.addAll(packetRecords);
        }
    }

    @Override
    public List<FlaggedPacketRecord> getAllFlaggedPacketRecords() {
        return flaggedPacketRepo.findAll();
    }

    @Override
    public List<FlaggedPacketRecord> getLast30FlaggedPacketRecords() {
        return flaggedPacketRepo.findTop30ByOrderByKey_TimestampDesc();
    }

    @Override
    public void validateRecord(FlaggedPacketRecord packetRecord) {
        if (packetRecord == null) {
            throw new IllegalArgumentException("The PacketRecord cannot be null!");
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
        logger.debug("Saved {} flagged packets from the queue", size);
    }

    private long convertLocalDateTimeToEpochSecond(LocalDateTime localDateTime, int offset) {
        return localDateTime.minusDays(offset).atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    private void validateNotNullOrEmpty(List<FlaggedPacketRecord> records) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("PacketRecord Lists cannot be null or empty!");
        }
    }
}
