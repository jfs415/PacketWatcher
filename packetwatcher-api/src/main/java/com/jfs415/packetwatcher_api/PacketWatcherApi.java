package com.jfs415.packetwatcher_api;

import java.io.IOException;
import java.util.EnumSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jfs415.packetwatcher_api.model.user.Authority;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class PacketWatcherApi extends SpringBootServletInitializer {

	private static PacketWatcherApi instance;

	private static final Logger LOG = LoggerFactory.getLogger(PacketWatcherApi.class);
	private static final EnumSet<Authority> authoritySet = EnumSet.allOf(Authority.class);

	private EntityManager entityManager;
	private final PropertiesManager propertiesManager = new PropertiesManager();
	private final EmailValidator emailValidator = new EmailValidator();

	public PacketWatcherApi() {
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Encountered an exception creating the PacketWatcherAPI Instance");
		}
	}

	public static PropertiesManager getPropertiesManager() {
		return instance.propertiesManager;
	}

	public static EntityManager getEntityManager() {
		return instance.entityManager;
	}

	public static PacketWatcherApi getInstance() {
		return instance;
	}

	public static EmailValidator getEmailValidator() {
		return instance.emailValidator;
	}

	public static EnumSet<Authority> getAuthoritySet() {
		return authoritySet;
	}

	private void onInit() {
		instance = this;

		try {
			propertiesManager.load();
		} catch (IOException e) {
			e.printStackTrace();

			if (!propertiesManager.canLoadDefaults()) {
				fail("Encountered an exception when trying to load properties, default values could not be assigned!");
			}
		}

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("packetwatcher-api");
		instance.entityManager = emf.createEntityManager();
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

	public static void main(String... args) {
		SpringApplication.run(PacketWatcherApi.class);
	}

}
