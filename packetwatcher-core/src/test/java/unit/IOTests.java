package unit;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.jfs415.packetwatcher_core.CorePropertiesManager;

public class IOTests {

	CorePropertiesManager pm = new CorePropertiesManager();

	private final int flaggedRetentionDays = 1000;
	private final int archiveRetentionDays = 180;
	private final long lastAppStart = System.currentTimeMillis();
	private final long lastAppShutdown = lastAppStart - 1000;
	private final long lastFlaggedRecordPurge = lastAppStart - 10000;
	private final long lastArchivedRecordPurge = lastAppStart - 100000;

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
		pm.setFlaggedRetentionDays(flaggedRetentionDays);
		pm.setLastAppStart(new Timestamp(lastAppStart));
		pm.setLastAppShutdown(new Timestamp(lastAppShutdown));
		pm.setLastFlaggedRecordPurge(new Timestamp(lastFlaggedRecordPurge));
		pm.setLastArchiveRecordPurge(new Timestamp(lastArchivedRecordPurge));
		pm.setArchiveRetentionDays(archiveRetentionDays);

		pm.save();
	}

	@Test
	public void testLoad() throws IOException {
		pm.load();
		System.out.println(pm.getLastAppShutdown());
		System.out.println(pm.getLastAppStart());
		System.out.println(pm.getLastFlaggedRecordPurge());
		System.out.println(pm.getLastArchiveRecordPurge());
		System.out.println(pm.getArchiveRetentionDays());
		System.out.println(pm.getFlaggedRetentionDays());
	}

	@Test
	public void testSystemDefaults() {
		File configFile = new File("PacketWatcherCore.properties");
		configFile.delete();

		try {
			testLoad();
		} catch (IOException ignored) {
			pm.canLoadDefaults();
		}
	}

	@Test
	public void testRefresh() throws IOException {
		testSave();
		testLoad();

		int newFlaggedRetentionDays = flaggedRetentionDays + 500;
		int newArchivedRetentionDays = archiveRetentionDays * 2;

		Timestamp newLastAppStart = new Timestamp(lastAppStart);
		Timestamp newLastAppShutdown = new Timestamp(lastAppStart);
		Timestamp newLastFlaggedRecordPurge = new Timestamp(lastFlaggedRecordPurge);
		Timestamp newLastArchivedRecordPurge = new Timestamp(lastArchivedRecordPurge);

		pm.setFlaggedRetentionDays(newFlaggedRetentionDays);
		pm.setLastAppStart(newLastAppStart);
		pm.setLastAppShutdown(newLastAppShutdown);
		pm.setLastFlaggedRecordPurge(newLastFlaggedRecordPurge);
		pm.setLastArchiveRecordPurge(newLastArchivedRecordPurge);
		pm.setArchiveRetentionDays(newArchivedRetentionDays);
		
		assert pm.refresh();

		assert pm.getArchiveRetentionDays()  == newArchivedRetentionDays;
		assert pm.getFlaggedRetentionDays() == newFlaggedRetentionDays;
		assert pm.getLastAppStart().equals(newLastAppStart);
		assert pm.getLastAppShutdown().equals(newLastAppShutdown);
		assert pm.getLastFlaggedRecordPurge().equals(newLastFlaggedRecordPurge);
		assert pm.getLastArchiveRecordPurge().equals(newLastArchivedRecordPurge);
	}

}
