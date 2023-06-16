package com.jfs415.packetwatcher_core.test.unit;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfs415.packetwatcher_core.CorePropertiesManager;
import com.jfs415.packetwatcher_core.PacketWatcherCore;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class IOTests {

	private CorePropertiesManager corePropertiesManager;
	private final Logger logger = LoggerFactory.getLogger(IOTests.class);
	private final int flaggedRetentionDays = 1000;
	private final long lastAppStart = System.currentTimeMillis();
	private final long lastAppShutdown = lastAppStart - 1000;
	private final long lastFlaggedRecordPurge = lastAppStart - 10000;
	private final String localIpStart = "1.1.1.1";
	private final String localIpEnd = "255.255.255.255";

	@BeforeEach
	public void init() {
		PacketWatcherCore packetWatcherCore = mock(PacketWatcherCore.class);
		corePropertiesManager = new CorePropertiesManager(packetWatcherCore);
	}
	
	@Test
	public void testConfig() {
		try {
			testSave();
			testLoad();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() throws IOException {
		corePropertiesManager.setFlaggedRetentionDays(flaggedRetentionDays);
		corePropertiesManager.setLastAppStart(new Timestamp(lastAppStart));
		corePropertiesManager.setLastAppShutdown(new Timestamp(lastAppShutdown));
		corePropertiesManager.setLastFlaggedRecordPurge(new Timestamp(lastFlaggedRecordPurge));

		corePropertiesManager.save();
	}

	@Test
	public void testLoad() throws IOException {
		corePropertiesManager.load();
		logger.info("Last app shutdown: " + corePropertiesManager.getLastAppShutdown());
		logger.info("Last app start: " + corePropertiesManager.getLastAppStart());
		logger.info("Last flagged record purge: " + corePropertiesManager.getLastFlaggedRecordPurge());
		logger.info("Flagged retention days: " + corePropertiesManager.getFlaggedRetentionDays());
		logger.info("Local ip address start: " + corePropertiesManager.getLocalIpAddressStart());
		logger.info("Local ip address end: " + corePropertiesManager.getLocalIpAddressEnd());
	}

	@Test
	public void testSystemDefaults() {
		File configFile = new File("PacketWatcherCore.properties");
		configFile.delete();

		try {
			testLoad();
		} catch (IOException ignored) {
			corePropertiesManager.canLoadDefaults();
		}
	}

	@Test
	public void testRefresh() throws IOException {
		testSave();
		testLoad();

		int newFlaggedRetentionDays = flaggedRetentionDays + 500;

		Timestamp newLastAppStart = new Timestamp(lastAppStart);
		Timestamp newLastAppShutdown = new Timestamp(lastAppStart);
		Timestamp newLastFlaggedRecordPurge = new Timestamp(lastFlaggedRecordPurge);

		corePropertiesManager.setFlaggedRetentionDays(newFlaggedRetentionDays);
		corePropertiesManager.setLastAppStart(newLastAppStart);
		corePropertiesManager.setLastAppShutdown(newLastAppShutdown);
		corePropertiesManager.setLastFlaggedRecordPurge(newLastFlaggedRecordPurge);

		assert corePropertiesManager.refresh();
		
		assert corePropertiesManager.getFlaggedRetentionDays() == newFlaggedRetentionDays;
		assert corePropertiesManager.getLastAppStart().equals(newLastAppStart);
		assert corePropertiesManager.getLastAppShutdown().equals(newLastAppShutdown);
		assert corePropertiesManager.getLastFlaggedRecordPurge().equals(newLastFlaggedRecordPurge);
		assert corePropertiesManager.getLocalIpAddressStart().equals(localIpStart);
		assert corePropertiesManager.getLocalIpAddressEnd().equals(localIpEnd);
	}

}
