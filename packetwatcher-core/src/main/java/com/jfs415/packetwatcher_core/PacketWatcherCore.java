package com.jfs415.packetwatcher_core;

import java.io.IOException;

import javax.annotation.PreDestroy;
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
	
	private static final Logger logger = LoggerFactory.getLogger(PacketWatcherCore.class); //TODO: Logging refactor, add to all classes
	private static final String HOST_ADDR = "192.168"; //TODO: Refactor this, should be configurable

	private PacketCaptureThread handle;
	private final CorePropertiesManager configProperties = new CorePropertiesManager();

	public PacketWatcherCore() {
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Encountered an exception while creating the PacketWatcherCore Instance");
		}
	}

	public static String getHostAddr() {
		return HOST_ADDR;
	}

	private void onInit() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("packetwatcher-core");
		emf.createEntityManager();

		handle = new PacketCaptureThread();
		handle.start();
	}

	@PreDestroy
	public void onShutdown() {
		debug("PacketWatcher-Core shutting down");

		try {
			configProperties.save();
		} catch (IOException e) {
			debug("Encountered an exception saving PacketWatcherCore config on shutdown");
		}
		handle.shutdown();
	}

	public static void fail(String message) {
		logger.error(message);
		System.exit(1);
	}

	public static void debug(String debugMessage) {
		logger.debug(debugMessage);
	}

	public static void main(String... args) {
		SpringApplication.run(PacketWatcherCore.class);
	}

}
