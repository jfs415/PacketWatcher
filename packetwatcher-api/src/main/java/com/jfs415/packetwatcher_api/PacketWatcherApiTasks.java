package com.jfs415.packetwatcher_api;

import com.jfs415.packetwatcher_api.model.services.UserAuthCacheServiceImpl;
import com.jfs415.packetwatcher_api.model.services.inf.UserAuthCacheService;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PacketWatcherApiTasks {

    private static final Logger logger = LoggerFactory.getLogger(PacketWatcherApiTasks.class);
    private final UserService userService;
    private final UserAuthCacheService userAuthCacheServiceImpl;

    @Autowired
    public PacketWatcherApiTasks(UserService userService, UserAuthCacheServiceImpl userAuthCacheServiceImpl) {
        this.userService = userService;
        this.userAuthCacheServiceImpl = userAuthCacheServiceImpl;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void purgeExpiredPasswordResetRequests() {
        userService.purgeExpiredPasswordResetRequests();
        logger.debug("Expired password reset requests have been purged");
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void purgeExpiredLoginCacheTokens() {
        userAuthCacheServiceImpl.purgeAllExpiredTokens();
        logger.debug("Expired login tokens have been purged");
    }
}
