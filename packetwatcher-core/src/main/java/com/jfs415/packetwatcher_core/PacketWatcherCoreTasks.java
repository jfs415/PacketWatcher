package com.jfs415.packetwatcher_core;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.services.PacketService;

@Component
public class PacketWatcherCoreTasks {

	@Autowired
	private PacketService packetService;

	@Scheduled(fixedDelay = 33, timeUnit = TimeUnit.SECONDS)
	public void processSaveQueues() {
		packetService.flushSaveQueues();
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
	public void createTestPacket() {
		packetService.savePacketRecord(FlaggedPacketRecord.createTestPacket());
		PacketWatcherCore.debug("CREATED TEST PACKET");
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshConfig() {
		PacketWatcherCore.getCoreConfigProperties().refresh();
		PacketWatcherCore.debug("PacketWatcher config has been refreshed");
	}

}
