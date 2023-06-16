package com.jfs415.packetwatcher_core.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.services.PacketService;

@SpringBootTest(classes = PacketWatcherCore.class)
public class PacketTests {

	@Autowired
	private PacketService packetService;

	@Test
	public void addTestPacket() {
		packetService.savePacketRecord(FlaggedPacketRecord.createTestPacket());
	}

}
