package com.jfs415.packetwatcher_core.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.services.PacketService;

@SpringBootTest()
public class PacketTests {

	private final PacketService packetService;

	@Autowired
	public PacketTests(PacketService packetService) {
		this.packetService = packetService;
	}

	@Test
	public void addTestPacket() {
		packetService.savePacketRecord(FlaggedPacketRecord.createTestPacket());
	}

}
