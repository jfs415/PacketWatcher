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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorePropertiesManager {

	private static final String PROPERTIES_FILENAME = "PacketWatcherCore.properties";
	
	private final PacketWatcherCore packetWatcherCore;
	
	private final Properties properties = new Properties();
	private final Logger logger = LoggerFactory.getLogger(CorePropertiesManager.class);
	
	@Autowired
	public CorePropertiesManager(PacketWatcherCore packetWatcherCore) {
		this.packetWatcherCore = packetWatcherCore;
		
		startup();
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
		final int archiveRetentionDays = 180;

		setLastAppStart(now);
		setLastAppShutdown(now);
		setLastFlaggedRecordPurge(now);
		setLastArchiveRecordPurge(now);
		setFlaggedRetentionDays(flaggedRetentionDays);
		setArchiveRetentionDays(archiveRetentionDays);
		setDoNotFailOnRIRDownloadException(true);

		return getLastAppStart().equals(now) && getLastFlaggedRecordPurge().equals(now) && getLastAppShutdown().equals(now) 
		       && getFlaggedRetentionDays() == flaggedRetentionDays && getArchiveRetentionDays() == archiveRetentionDays;
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
		return new Timestamp(Long.parseLong(properties.getProperty("last_start")));
	}

	public void setLastAppStart(Timestamp lastAppStart) {
		properties.setProperty("last_start", String.valueOf(lastAppStart.getTime()));
	}

	public Timestamp getLastAppShutdown() {
		String lastShutdown = properties.getProperty("last_shutdown");
		return lastShutdown.isBlank() ? null : new Timestamp(Long.parseLong(lastShutdown));
	}

	public void setLastAppShutdown(Timestamp lastAppShutdown) {
		properties.setProperty("last_shutdown", lastAppShutdown == null ? "" : String.valueOf(lastAppShutdown.getTime()));
	}

	public int getFlaggedRetentionDays() {
		return Integer.parseInt(properties.getProperty("flagged_retention_days"));
	}

	public void setFlaggedRetentionDays(int flaggedRetentionDays) {
		properties.setProperty("flagged_retention_days", String.valueOf(flaggedRetentionDays));
	}

	public int getArchiveRetentionDays() {
		return Integer.parseInt(properties.getProperty("archive_retention_days"));
	}

	public void setArchiveRetentionDays(int archiveRetentionDays) {
		properties.setProperty("archive_retention_days", String.valueOf(archiveRetentionDays));
	}

	public Timestamp getLastFlaggedRecordPurge() {
		String lastFlaggedRecordPurge = properties.getProperty("last_flagged_record_purge");
		return lastFlaggedRecordPurge.isBlank() ? null : new Timestamp(Long.parseLong(lastFlaggedRecordPurge));
	}

	public void setLastFlaggedRecordPurge(Timestamp lastFlaggedRecordPurge) {
		properties.setProperty("last_flagged_record_purge", lastFlaggedRecordPurge == null ? null : String.valueOf(lastFlaggedRecordPurge.getTime()));
	}

	public Timestamp getLastArchiveRecordPurge() {
		String lastArchivePurge = properties.getProperty("last_record_Archive_purge");
		return lastArchivePurge.isBlank() ? null : new Timestamp(Long.parseLong(lastArchivePurge));
	}

	public void setLastArchiveRecordPurge(Timestamp lastRecordArchivePurge) {
		properties.setProperty("last_record_Archive_purge", lastRecordArchivePurge == null ? null : String.valueOf(lastRecordArchivePurge.getTime()));
	}

	public boolean getDoNotFailOnRIRDownloadException() {
		return Boolean.parseBoolean(properties.getProperty("do_not_fail_on_rir_download_exception"));
	}

	public void setDoNotFailOnRIRDownloadException(boolean doNotFailOnRIRDownloadException) {
		properties.setProperty("do_not_fail_on_rir_download_exception", String.valueOf(doNotFailOnRIRDownloadException));
	}
	
	public void startup() {
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
	
	private void attemptToLoadDefaults() {
		if (!canLoadDefaults()) {
			packetWatcherCore.fail("Could not load config defaults, application is exiting");
		}
	}

}
