package com.jfs415.packetwatcher_core;

import java.io.IOException;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jfs415.packetwatcher_core.services.PacketService;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class PacketWatcherCore {

	private static PacketWatcherCore instance;
	private static final Logger LOG = LoggerFactory.getLogger(PacketWatcherCore.class);
	private static final String HOST_ADDR = "192.168";

	@Autowired
	private PacketService packetService;
	
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

	public static CorePropertiesManager getCoreConfigProperties() {
		return instance.configProperties;
	}

	public static PacketWatcherCore getInstance() {
		return instance;
	}

	public static String getHostAddr() {
		return HOST_ADDR;
	}

	private void onInit() {
		instance = this;

		try {
			configProperties.load();
			debug("PacketWatcherCore config file loaded");
		} catch (IOException e) {
			e.printStackTrace();
			error("Encountered an exception when trying to load properties, attempting to load defaults");
			
			if (!configProperties.canLoadDefaults()) {
				fail("Could not load config defaults, application is exiting");
			}
		}

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
		LOG.error(message);
		System.exit(1);
	}

	public static void error(String errorMessage) {
		LOG.error(errorMessage);
	}

	public static void warn(String warnMessage) {
		LOG.warn(warnMessage);
	}

	public static void debug(String debugMessage) {
		LOG.debug(debugMessage);
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	public static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static void main(String... args) {
		SpringApplication.run(PacketWatcherCore.class);
	}

}
