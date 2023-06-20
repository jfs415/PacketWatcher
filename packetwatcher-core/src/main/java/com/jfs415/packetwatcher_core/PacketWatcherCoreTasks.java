package com.jfs415.packetwatcher_core;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.services.PacketService;

@Component
public class PacketWatcherCoreTasks {

	private static final Logger logger = LoggerFactory.getLogger(PacketWatcherCoreTasks.class);

	private final PacketService packetService;

	@Autowired
	public PacketWatcherCoreTasks(PacketService packetService) {
		this.packetService = packetService;
	}

	@Scheduled(fixedDelay = 33, timeUnit = TimeUnit.SECONDS)
	public void processSaveQueues() {
		packetService.flushSaveQueues();
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
	public void createTestPacket() {
		packetService.savePacketRecord(FlaggedPacketRecord.createTestPacket());
		logger.debug("Created test packet");
	}

}
