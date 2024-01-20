package com.jfs415.packetwatcher_core.test.integration;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.services.PacketServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
@Order(5)
class PacketTests {

    private final PacketServiceImpl packetService;

    @Autowired
    public PacketTests(PacketServiceImpl packetService) {
        this.packetService = packetService;
    }

    @Test
    void addTestPacket() {
        packetService.savePacketRecord(FlaggedPacketRecord.createTestPacket());
    }
}
