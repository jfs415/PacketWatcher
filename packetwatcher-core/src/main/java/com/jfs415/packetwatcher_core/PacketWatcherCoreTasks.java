package com.jfs415.packetwatcher_core;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.services.PacketService;

@Component
public class PacketWatcherCoreTasks {
	
	private final Logger logger = LoggerFactory.getLogger(PacketWatcherCoreTasks.class);

	@Autowired
	private PacketService packetService;

	@Autowired
	private CorePropertiesManager corePropertiesManager;

	@Scheduled(fixedDelay = 33, timeUnit = TimeUnit.SECONDS)
	public void processSaveQueues() {
		packetService.flushSaveQueues();
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
	public void createTestPacket() {
		packetService.savePacketRecord(FlaggedPacketRecord.createTestPacket());
		logger.debug("Created test packet");
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshConfig() {
		corePropertiesManager.refresh();
		logger.debug("PacketWatcher config has been refreshed");
	}

}
