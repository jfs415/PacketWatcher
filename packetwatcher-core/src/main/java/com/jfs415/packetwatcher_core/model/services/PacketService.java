package com.jfs415.packetwatcher_core.model.services;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import java.util.List;

public interface PacketService {

    void savePacketRecord(FlaggedPacketRecord packetRecord);

    void addToSaveQueue(FlaggedPacketRecord packetRecord);

    void flushSaveQueues();

    void addBatchToFlaggedPacketSaveQueue(List<FlaggedPacketRecord> packetRecords);

    void validateRecord(FlaggedPacketRecord packetRecord);

    List<FlaggedPacketRecord> getAllFlaggedPacketRecords();

    List<FlaggedPacketRecord> getLast30FlaggedPacketRecords();
}
