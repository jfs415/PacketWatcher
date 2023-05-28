package com.jfs415.packetwatcher_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.views.SystemSettingView;
import com.jfs415.packetwatcher_api.views.collections.SystemSettingsCollectionView;

@Component
public class PropertiesManager implements InitializingBean {

	private final static String PATH = "PacketWatcherAPI.properties";
	private final Properties properties = new Properties();
	private final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);
	
	@Autowired
	private PacketWatcherApi packetWatcherApi;
	
	public void load() throws IOException {
		File configFile = new File("packetwatcher-API" + File.separator + PATH);
		InputStream inputStream = new FileInputStream(configFile);

		properties.load(inputStream);
		inputStream.close();

		logger.debug("PacketWatcherAPI config file loaded");
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

	public void save() throws IOException {
		File configFile = new File(PATH);
		FileWriter writer = new FileWriter(configFile);
		properties.store(writer, "host settings");
		writer.close();

		logger.debug("Config file saved");
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
	
	public int getPasswordStrength() {
		return Integer.parseInt(properties.getProperty("password_strength"));
	}
	
	public void setPasswordStrength(int passwordStrength) {
		properties.setProperty("password_Strength", String.valueOf(passwordStrength));
	}
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	public SystemSettingsCollectionView toCollectionView() {
		return new SystemSettingsCollectionView(properties.entrySet().stream().map(entry -> {
			return new SystemSettingView(entry.getKey().toString(), entry.getValue().toString());
		}).collect(Collectors.toList()));
	}
	
	public void updateSystemSettingsFromViews(List<SystemSettingView> updates) {
		for (SystemSettingView view : updates) {
			properties.setProperty(view.getSettingKey(), view.getSettingValue());
		}
	}

	@Override
	public void afterPropertiesSet() {
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();

			if (!canLoadDefaults()) {
				packetWatcherApi.fail("Encountered an exception when trying to load properties, default values could not be assigned!");
			}
		}
	}

}
