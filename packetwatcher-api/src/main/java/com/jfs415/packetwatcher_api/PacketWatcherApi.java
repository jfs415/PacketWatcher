package com.jfs415.packetwatcher_api;

import java.util.EnumSet;

import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jfs415.packetwatcher_api.model.user.Authority;

@SpringBootApplication()
@EnableJpaRepositories()
@ComponentScan(basePackages = { "com.jfs415.packetwatcher_core", "com.jfs415.packetwatcher_api" })
@EnableScheduling
public class PacketWatcherApi extends SpringBootServletInitializer {

	private final Logger logger = LoggerFactory.getLogger(PacketWatcherApi.class);
	private static final EnumSet<Authority> authoritySet = EnumSet.allOf(Authority.class);

	public PacketWatcherApi() {
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Encountered an exception creating the PacketWatcherAPI Instance");
		}
	}

	public static EnumSet<Authority> getAuthoritySet() {
		return authoritySet;
	}

	private void onInit() {
		Persistence.createEntityManagerFactory("packetwatcher-api").createEntityManager();
	}

	public void fail(String message) {
		logger.error(message);
		System.exit(1);
	}

	public static void main(String... args) {
		SpringApplication.run(PacketWatcherApi.class);
	}

}
