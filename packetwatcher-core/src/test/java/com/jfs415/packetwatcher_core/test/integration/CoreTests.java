package com.jfs415.packetwatcher_core.test.integration;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PacketWatcherCore.class)
class CoreTests {

    private final PacketWatcherCore packetWatcherCore;

    @Autowired
    public CoreTests(PacketWatcherCore packetWatcherCore) {
        this.packetWatcherCore = packetWatcherCore;
    }

    @Test
    void shutdownErrorTest() {
        packetWatcherCore.fail("This is a test fail"); //TODO: Make this more graceful with listeners to prevent total failure so test can finish 
    }

}
