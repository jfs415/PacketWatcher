package com.jfs415.packetwatcher_api;

import com.jfs415.packetwatcher_api.model.user.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.EnumSet;
import java.util.Set;

@SpringBootApplication()
@EnableJpaRepositories()
@ComponentScan(basePackages = { "com.jfs415.packetwatcher_core", "com.jfs415.packetwatcher_api" })
@EnableScheduling
public class PacketWatcherApi extends SpringBootServletInitializer {

    private final Logger logger = LoggerFactory.getLogger(PacketWatcherApi.class);
    private static final EnumSet<Authority> authoritySet = EnumSet.allOf(Authority.class);

    public static Set<Authority> getAuthoritySet() {
        return authoritySet;
    }

    public void fail(String message) {
        logger.error(message);
        System.exit(1);
    }

    public static void main(String... args) {
        SpringApplication.run(PacketWatcherApi.class);
    }

}
