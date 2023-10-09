package com.jfs415.packetwatcher_core;

import com.jfs415.packetwatcher_core.filter.FilterYamlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(FilterYamlConfiguration.class)
@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class PacketWatcherCore {

    private final Logger logger = LoggerFactory.getLogger(PacketWatcherCore.class);

    private final ApplicationContext applicationContext;

    @Autowired
    public PacketWatcherCore(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public synchronized void fail(String message) {
        logger.error(message);
        ((ConfigurableApplicationContext) applicationContext).close();
    }

    public static void main(String... args) {
        SpringApplication.run(PacketWatcherCore.class);
    }

}
