package com.jfs415.packetwatcher_api.test.integration;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import com.jfs415.packetwatcher_api.model.services.PacketServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PacketWatcherApi.class)
class PacketControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(PacketControllerTests.class);
    private final PacketServiceImpl packetService;
    
    @Autowired
    public PacketControllerTests(PacketServiceImpl packetService) {
        this.packetService = packetService;
    }
    
    @Test
    void getAllPackets() {
        List<RawPacketRecord> rawPackets = packetService.getAllFlaggedPacketRecords();
        assertNotNull(rawPackets);
        assertNotEmpty(rawPackets, "No records returned!");
        logger.info("Packets Returned: " + rawPackets.size());
    }
    
}
