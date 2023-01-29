package com.jfs415.packetwatcher_api;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Tasks {
	
	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshDashboard() {
		//TODO: implement
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshConfig() {
		PacketWatcherApi.getPropertiesManager().refresh();
		PacketWatcherApi.debug("PacketWatcher config has been refreshed");
	}
	
}
