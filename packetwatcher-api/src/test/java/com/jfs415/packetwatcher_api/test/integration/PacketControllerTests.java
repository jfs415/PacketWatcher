package com.jfs415.packetwatcher_api.test.integration;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import com.jfs415.packetwatcher_api.model.services.ApiPacketServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PacketWatcherApi.class)
class PacketControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(PacketControllerTests.class);
    private final ApiPacketServiceImpl packetService;

    @Autowired
    public PacketControllerTests(ApiPacketServiceImpl packetService) {
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
