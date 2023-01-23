package integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;

@SpringBootTest(classes = PacketWatcherCore.class)
public class PacketTests {

	@Test
	public void addTestPacket() {
		FlaggedPacketRecord.createTestPacket().save();
	}

}
