package com.jfs415.packetwatcher_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

import org.springframework.lang.Nullable;

public class PropertiesManager {

	private final Properties properties = new Properties();
	private final static String PATH = "PacketWatcherAPI.properties";
	
	public void load() throws IOException {
		File configFile = new File("packetwatcher-API" + File.separator + PATH);
		InputStream inputStream = new FileInputStream(configFile);

		properties.load(inputStream);
		inputStream.close();

		PacketWatcherApi.debug("PacketWatcherAPI config file loaded");
	}

	/**
	 * This method is called if the previous load method throws an exception.
	 * This method will attempt to assign values to non-nullable attributes
	 * in an attempt to keep the previous failed load from becoming a critical
	 * failure. If all default values can be assigned the application will
	 * continue initializing, otherwise it will shutdown.
	 *
	 * @return Whether all properties match the assigned default values
	 */
	public boolean canLoadDefaults() {
		long now = System.currentTimeMillis();

		setLastAppStart(new Timestamp(now));
		setLastRecordPurge(null);
		setLastAppShutdown(null);

		return getLastAppStart().equals(new Timestamp(now)) && getRecordLastPurge() == null && getLastAppShutdown() == null;
	}

	public boolean refresh() {
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
			PacketWatcherApi.warn("Unable to save properties file during refresh");
			return false;
		}

		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
			PacketWatcherApi.warn("Unable to load properties file during refresh");
			return false;
		}

		return true;
	}

	public void save() throws IOException {
		File configFile = new File(PATH);
		FileWriter writer = new FileWriter(configFile);
		properties.store(writer, "host settings");
		writer.close();

		PacketWatcherApi.debug("Config file saved");
	}

	public @Nullable Timestamp getRecordLastPurge() {
		String lastPurge = properties.getProperty("last_record_purge");
		return isNullOrEmpty(lastPurge) ? null : new Timestamp(Long.parseLong(lastPurge));
	}

	public void setLastRecordPurge(@Nullable Timestamp lastPurge) {
		properties.setProperty("last_record_purge", lastPurge == null ? "" : String.valueOf(lastPurge.getTime()));
	}

	public Timestamp getLastAppStart() {
		return new Timestamp(Long.parseLong(properties.getProperty("last_start")));
	}

	public void setLastAppStart(Timestamp lastAppStart) {
		properties.setProperty("last_start", String.valueOf(lastAppStart.getTime()));
	}

	public @Nullable Timestamp getLastAppShutdown() {
		String lastShutdown = properties.getProperty("last_shutdown");
		return isNullOrEmpty(lastShutdown) ? null : new Timestamp(Long.parseLong(lastShutdown));
	}

	public void setLastAppShutdown(@Nullable Timestamp lastAppShutdown) {
		properties.setProperty("last_shutdown", lastAppShutdown == null ? "" : String.valueOf(lastAppShutdown.getTime()));
	}
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

}
