package com.jfs415.packetwatcher_core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.DO_NOT_FAIL_ON_RIR_DOWNLOAD_EXCEPTION;
import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.FLAGGED_RETENTION_DAYS;
import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.LAST_FLAGGED_RECORD_PURGE;
import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.LAST_SHUTDOWN;
import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.LAST_START;
import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.LOCAL_IP_ADDRESS_END;
import static com.jfs415.packetwatcher_core.CorePropertiesManager.CoreProperties.LOCAL_IP_ADDRESS_START;

@Component
public class CorePropertiesManager implements InitializingBean {

	private static final String PROPERTIES_FILENAME = "packetwatcher-core/PacketWatcherCore.properties";

	private final PacketWatcherCore packetWatcherCore;

	private final Properties properties = new Properties();
	private static final Logger logger = LoggerFactory.getLogger(CorePropertiesManager.class);

	@Autowired
	public CorePropertiesManager(PacketWatcherCore packetWatcherCore) {
		this.packetWatcherCore = packetWatcherCore;
	}

	@PreDestroy
	public void onShutdown() {
		logger.debug("Saving PacketWatcherCore config properties");

		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("Encountered an exception saving PacketWatcherCore config properties");
		}
	}

	public void load() throws IOException {
		File configFile = new File(PROPERTIES_FILENAME);
		InputStream inputStream = new FileInputStream(configFile);

		properties.load(inputStream);
		inputStream.close();
	}

	public void save() throws IOException {
		File configFile = new File(PROPERTIES_FILENAME);
		FileWriter writer = new FileWriter(configFile);
		properties.store(writer, "Core service config settings");
		writer.close();
	}

	/**
	 * This method is called if the previous load method throws an exception.
	 * This method will attempt to assign values to non-nullable attributes
	 * in an attempt to keep the previous failed load from becoming a critical
	 * failure. If all default values can be assigned the application will
	 * continue initializing, otherwise it will shut down.
	 *
	 * @return Whether all properties match the assigned default values
	 */
	public boolean canLoadDefaults() {
		logger.info("Attempting to load default configurations");

		Timestamp now = new Timestamp(System.currentTimeMillis());

		final int flaggedRetentionDays = 180;

		setLastAppStart(now);
		setLastAppShutdown(now);
		setLastFlaggedRecordPurge(now);
		setFlaggedRetentionDays(flaggedRetentionDays);
		setDoNotFailOnRIRDownloadException(true);
		setLocalIpAddressStart("192.168.1.1");
		setLocalIpAddressEnd("192.168.1.255");

		return getLastAppStart().equals(now) && getLastFlaggedRecordPurge().equals(now) && getLastAppShutdown().equals(now) && getFlaggedRetentionDays() == flaggedRetentionDays;
	}

	public boolean refresh() {
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("Unable to write properties file during refresh");
			return false;
		}

		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("Unable to load properties file during refresh");
			return false;
		}

		return true;
	}

	public Timestamp getLastAppStart() {
		return new Timestamp(Long.parseLong(properties.getProperty(LAST_START.getName())));
	}

	public void setLastAppStart(Timestamp lastAppStart) {
		properties.setProperty(LAST_START.getName(), String.valueOf(lastAppStart.getTime()));
	}

	public Timestamp getLastAppShutdown() {
		String lastShutdown = properties.getProperty(LAST_SHUTDOWN.getName());
		return lastShutdown.isBlank() ? null : new Timestamp(Long.parseLong(lastShutdown));
	}

	public void setLastAppShutdown(Timestamp lastAppShutdown) {
		properties.setProperty(LAST_SHUTDOWN.getName(), lastAppShutdown == null ? "" : String.valueOf(lastAppShutdown.getTime()));
	}

	public int getFlaggedRetentionDays() {
		return Integer.parseInt(properties.getProperty(FLAGGED_RETENTION_DAYS.getName()));
	}

	public void setFlaggedRetentionDays(int flaggedRetentionDays) {
		properties.setProperty(FLAGGED_RETENTION_DAYS.getName(), String.valueOf(flaggedRetentionDays));
	}

	public Timestamp getLastFlaggedRecordPurge() {
		String lastFlaggedRecordPurge = properties.getProperty(LAST_FLAGGED_RECORD_PURGE.getName());
		return lastFlaggedRecordPurge.isBlank() ? null : new Timestamp(Long.parseLong(lastFlaggedRecordPurge));
	}

	public void setLastFlaggedRecordPurge(Timestamp lastFlaggedRecordPurge) {
		properties.setProperty(LAST_FLAGGED_RECORD_PURGE.getName(), lastFlaggedRecordPurge == null ? null : String.valueOf(lastFlaggedRecordPurge.getTime()));
	}

	public boolean getDoNotFailOnRIRDownloadException() {
		return Boolean.parseBoolean(properties.getProperty(DO_NOT_FAIL_ON_RIR_DOWNLOAD_EXCEPTION.getName()));
	}

	public void setDoNotFailOnRIRDownloadException(boolean doNotFailOnRIRDownloadException) {
		properties.setProperty(DO_NOT_FAIL_ON_RIR_DOWNLOAD_EXCEPTION.getName(), String.valueOf(doNotFailOnRIRDownloadException));
	}

	public String getLocalIpAddressStart() {
		return properties.getProperty(LOCAL_IP_ADDRESS_START.getName());
	}

	public void setLocalIpAddressStart(String localIpAddressStart) {
		properties.setProperty(LOCAL_IP_ADDRESS_START.getName(), localIpAddressStart);
	}

	public String getLocalIpAddressEnd() {
		return properties.getProperty(LOCAL_IP_ADDRESS_END.getName());
	}

	public void setLocalIpAddressEnd(String localIpAddressEnd) {
		properties.setProperty(LOCAL_IP_ADDRESS_END.getName(), localIpAddressEnd);
	}
	
	private void attemptToLoadDefaults() {
		if (!canLoadDefaults()) {
			packetWatcherCore.fail("Could not load config defaults, application is exiting");
		}
	}

	@Override
	public void afterPropertiesSet() {
		try {
			load();

			if (properties.isEmpty()) {
				attemptToLoadDefaults();
			}

			logger.debug("PacketWatcherCore config file loaded");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Encountered an exception when trying to load properties, attempting to load defaults");

			if (!canLoadDefaults()) {
				packetWatcherCore.fail("Could not load config defaults, application is exiting");
			}
		}
	}

	protected enum CoreProperties {

		LAST_START("last_start"),
		DO_NOT_FAIL_ON_RIR_DOWNLOAD_EXCEPTION("do_not_fail_on_rir_exception"),
		FLAGGED_RETENTION_DAYS("flagged_retention_days"),
		LAST_SHUTDOWN("last_shutdown"),
		LAST_FLAGGED_RECORD_PURGE("last_flagged_record_purge"),
		LOCAL_IP_ADDRESS_START("ip_address_start"),
		LOCAL_IP_ADDRESS_END("ip_address_end");

		private final String name;

		CoreProperties(String name) {
			this.name = name;
		}

		private String getName() {
			return name;
		}
	}

}
