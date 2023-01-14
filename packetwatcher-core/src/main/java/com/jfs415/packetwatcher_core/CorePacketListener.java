package com.jfs415.packetwatcher_core;

import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.TcpPacket;

import com.axlabs.ip2asn2cc.Ip2Asn2Cc;
import com.axlabs.ip2asn2cc.exception.RIRNotDownloadedException;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.PacketRecordKey;

public class CorePacketListener implements PacketListener {

	private Ip2Asn2Cc ipLookupUtility = null;

	public CorePacketListener() {
		try {
			ipLookupUtility = new Ip2Asn2Cc(EnumSet.allOf(FlaggedCountries.class).stream().map(FlaggedCountries::getCountryCode).collect(Collectors.toList()));
		} catch (RIRNotDownloadedException e) {
			e.printStackTrace();
			PacketWatcherCore.fail("Encountered a fatal exception when creating ipLookupUtility");
		}
	}

	public Optional<String> getFlaggedCountry(String destinationIp) {
		return ipLookupUtility.getRIRCountryCode(destinationIp);
	}

	@Override
	public void gotPacket(PcapPacket packet) {
		if (packet.getPacket() instanceof EthernetPacket && isTcpPacket(packet)) {
			EthernetPacket eth = (EthernetPacket) packet.getPacket();
			String destIpString = ((IpV4Header) eth.getPayload().getHeader()).getDstAddr().getHostAddress();

			if (!destIpString.startsWith(PacketWatcherCore.getHostAddr())) {
				String destHostName = ((IpV4Header) eth.getPayload().getHeader()).getDstAddr().getHostName();
				Optional<String> countryName = getFlaggedCountry(destIpString);
				
				if (countryName.isPresent()) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String destPort = ((TcpPacket) packet.getPacket().getPayload().getPayload()).getHeader().getDstPort().toString();

					PacketRecordKey key = new PacketRecordKey(timestamp, destIpString, destPort);
					PacketWatcherCore.getPacketService().addToSaveQueue(new FlaggedPacketRecord(key, destHostName, countryName.get().toUpperCase()));
				}
			}
		}
	}

	private boolean isTcpPacket(PcapPacket packet) {
		return packet.getPacket().getPayload() != null && packet.getPacket().getPayload().getPayload() != null && (packet.getPacket().getPayload().getPayload() instanceof TcpPacket);
	}

}
