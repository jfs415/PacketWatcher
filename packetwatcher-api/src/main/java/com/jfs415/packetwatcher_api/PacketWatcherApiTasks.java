package com.jfs415.packetwatcher_api;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.model.services.UserService;

@Component
public class PacketWatcherApiTasks {
	
	@Autowired
	private UserService userService;

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshDashboard() {
		//TODO: implement
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshConfig() {
		PacketWatcherApi.getPropertiesManager().refresh();
		PacketWatcherApi.debug("PacketWatcher config has been refreshed");
	}

	@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
	public void purgeExpiredPasswordResetRequests() {
		userService.purgeExpiredPasswordResetRequests();
		PacketWatcherApi.debug("Expired password reset requests have been purged");
	}

}
