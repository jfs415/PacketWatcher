package integration;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.repositories.ArchivedFlaggedPacketRepository;
import com.jfs415.packetwatcher_core.model.repositories.FlaggedPacketRepository;

@DataJpaTest
@ContextConfiguration(classes = PacketWatcherCore.class)
public class PacketTests {

	@Autowired
	private FlaggedPacketRepository flaggedPacketRepo;

	@Autowired
	private ArchivedFlaggedPacketRepository archivedFlaggedPacketRepo;

	private final int deleteOffsetDays = 180; //Delete number of packets older than this many days ago

	@Test
	public void getAllFlaggedPackets() {
		FlaggedPacketRecord record = flaggedPacketRepo.findTopByOrderByKey_TimestampDesc();

		if (record == null) {
			System.out.println("No records returned!");
		} else {
			System.out.println(record);
		}
	}

	@Test
	public void addFlaggedPackets() {
		int daysCounted = 0;
		int totalAdded = 0;

		//Add packets starting from this many days ago
		final int addPacketsOffsetStartDay = 180;
		LocalDateTime start = LocalDate.now().atStartOfDay().minusDays(addPacketsOffsetStartDay);

		//Number of packets added each day
		final int addPacketsPerDays = 10;
		while (daysCounted < addPacketsOffsetStartDay) {
			for (int packetsAddedToday = 0; packetsAddedToday < addPacketsPerDays; packetsAddedToday++) {

				totalAdded++;
			}

			start = start.plusDays(1);
			daysCounted++;
		}

		Assert.assertEquals(totalAdded, addPacketsPerDays * addPacketsOffsetStartDay);
	}

	@Test
	public void getPacketsFromLast30Days() {

	}

	@Test
	public void getPacketsFromLast60Days() {

	}

	@Test
	public void getPacketsFromLast90Days() {

	}

	@Test
	public void deletePacketsFromOffset() {

	}

}
