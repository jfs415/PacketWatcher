package com.jfs415.packetwatcher_api;

import com.jfs415.packetwatcher_api.model.user.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.EnumSet;
import java.util.Set;

@SpringBootApplication()
@EnableJpaRepositories()
@EnableScheduling
public class PacketWatcherApi extends SpringBootServletInitializer {

    private final Logger logger = LoggerFactory.getLogger(PacketWatcherApi.class);
    private static final EnumSet<Authority> authoritySet = EnumSet.allOf(Authority.class);
    private final ApplicationContext applicationContext;

    @Autowired
    public PacketWatcherApi(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        ((ConfigurableApplicationContext) this.applicationContext).registerShutdownHook();
    }

    public static Set<Authority> getAuthoritySet() {
        return authoritySet;
    }

    public synchronized void fail(String message) {
        logger.error(message);
        ((ConfigurableApplicationContext) applicationContext).close();
    }

    public static void main(String... args) {
        SpringApplication.run(PacketWatcherApi.class);
    }

}
