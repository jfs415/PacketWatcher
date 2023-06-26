package com.jfs415.packetwatcher_api;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.model.services.UserService;

@Component
public class PacketWatcherApiTasks {

	private final Logger logger = LoggerFactory.getLogger(PacketWatcherApiTasks.class);
	private final UserService userService;

	@Autowired
	public PacketWatcherApiTasks(UserService userService) {
		this.userService = userService;
	}

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void refreshDashboard() {
		//TODO: implement
	}

	@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
	public void purgeExpiredPasswordResetRequests() {
		userService.purgeExpiredPasswordResetRequests();
		logger.debug("Expired password reset requests have been purged");
	}

}
