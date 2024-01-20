package com.jfs415.packetwatcher_core.test.integration;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PacketWatcherCore.class)
@Order(7)
class CoreTests {

    private final PacketWatcherCore packetWatcherCore;

    @Autowired
    public CoreTests(PacketWatcherCore packetWatcherCore) {
        this.packetWatcherCore = packetWatcherCore;
    }

    @Test
    void shutdownErrorTest() {
        /*
         * JUnit doesnt like the application context being shutdown before it's own shutdown hooks finish.
         * Using @DirtiesContext will also not resolve this.
         * For now having this run last will prevent it from failing the other integration tests and
         * will have to be good enough.
         *
         * TODO: Find workaround for this or potentially remove the test altogether.
         */
        packetWatcherCore.fail("This is a test fail");
    }
}
