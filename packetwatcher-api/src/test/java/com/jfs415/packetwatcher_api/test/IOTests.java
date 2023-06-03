package com.jfs415.packetwatcher_api.test;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.PropertiesManager;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class IOTests {
	
	private PropertiesManager propertiesManager;

	@BeforeEach
	void init() {
		PacketWatcherApi packetWatcherApi = mock(PacketWatcherApi.class);
		propertiesManager = new PropertiesManager(packetWatcherApi);
	}

	@Test
	public void testConfig() {
		Assert.notNull(propertiesManager, "PropertiesManager is null");

		try {
			testSave();
			testLoad();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() throws IOException {
		Assert.notNull(propertiesManager, "PropertiesManager is null");

		propertiesManager.setLastAppStart(new Timestamp(System.currentTimeMillis()));
		propertiesManager.setLastAppShutdown(new Timestamp(System.currentTimeMillis() - 1000));
		propertiesManager.setLastRecordPurge(new Timestamp(System.currentTimeMillis() - 10000));

		propertiesManager.save();
	}

	@Test
	public void testLoad() throws IOException {
		Assert.notNull(propertiesManager, "PropertiesManager is null");

		propertiesManager.load();
		System.out.println(propertiesManager.getLastAppShutdown());
		System.out.println(propertiesManager.getLastAppStart());
	}

	@Test
	public void testDefaultLoad() {
		Assert.notNull(propertiesManager, "PropertiesManager is null");

		assert propertiesManager.canLoadDefaults();
	}

}
