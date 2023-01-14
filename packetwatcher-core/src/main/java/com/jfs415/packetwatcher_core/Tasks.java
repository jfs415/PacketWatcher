package com.jfs415.packetwatcher_core;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;

@Component
public class Tasks {
	
	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void processSaveQueues() {
		PacketWatcherCore.getPacketService().flushSaveQueues();
	}
	
	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
	public void cleanupRecords() {
		PacketWatcherCore.getPacketService().cleanupAllRecords();
		FlaggedPacketRecord.createTestPacket().save(); //Create test packets every day to confirm things are still working
	}
	
	@Scheduled(fixedDelay = 45, timeUnit = TimeUnit.SECONDS)
	public void createTestPacket() {
		FlaggedPacketRecord.createTestPacket().save();
		PacketWatcherCore.debug("CREATED TEST PACKET");
	}
	
	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshConfig() {
		PacketWatcherCore.getCoreConfigProperties().refresh();
		PacketWatcherCore.debug("PacketWatcher config has been refreshed");
	}

}