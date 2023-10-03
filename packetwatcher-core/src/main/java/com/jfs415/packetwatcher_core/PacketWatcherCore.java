package com.jfs415.packetwatcher_core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class PacketWatcherCore {

    private final Logger logger = LoggerFactory.getLogger(PacketWatcherCore.class);

    public void fail(String message) {
        logger.error(message);
        System.exit(1);
    }

    public static void main(String... args) {
        SpringApplication.run(PacketWatcherCore.class);
    }

}
