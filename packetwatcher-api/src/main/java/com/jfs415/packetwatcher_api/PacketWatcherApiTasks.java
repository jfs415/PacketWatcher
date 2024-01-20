package com.jfs415.packetwatcher_api;

import com.jfs415.packetwatcher_api.model.services.UserAuthCacheService;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PacketWatcherApiTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketWatcherApiTasks.class);
    private final UserService userService;
    private final UserAuthCacheService userAuthCacheService;

    @Autowired
    public PacketWatcherApiTasks(UserService userService, UserAuthCacheService userAuthCacheService) {
        this.userService = userService;
        this.userAuthCacheService = userAuthCacheService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void refreshDashboard() {
        // TODO: implement
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void purgeExpiredPasswordResetRequests() {
        userService.purgeExpiredPasswordResetRequests();
        LOGGER.debug("Expired password reset requests have been purged");
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void purgeExpiredLoginCacheTokens() {
        userAuthCacheService.purgeAllExpiredTokens();
        LOGGER.debug("Expired login tokens have been purged");
    }
}
