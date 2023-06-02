package com.jfs415.packetwatcher_core;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

	public PacketWatcherCore() {
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Encountered an exception while creating the PacketWatcherCore Instance");
		}
	}

	private void onInit() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("packetwatcher-core");
		emf.createEntityManager();
	}

	public void fail(String message) {
		logger.error(message);
		System.exit(1);
	}

	public static void main(String... args) {
		SpringApplication.run(PacketWatcherCore.class);
	}

}
