package com.jfs415.packetwatcher_core;

import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.TcpPacket.TcpHeader;

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

			if (!PacketWatcherCore.getCoreConfigProperties().getDoNotFailOnRIRDownloadException()) {
				PacketWatcherCore.fail("Encountered a fatal exception when creating ipLookupUtility");
			}
		}
	}

	public String getFlaggedCountry(String destinationIp) {
		return ipLookupUtility.getRIRCountryCode(destinationIp);
	}

	@Override
	public void gotPacket(PcapPacket packet) {
		if (packet.getPacket() instanceof EthernetPacket && isTcpPacket(packet)) {
			EthernetPacket eth = (EthernetPacket) packet.getPacket();
			IpV4Header ipv4Header = ((IpV4Header) eth.getPayload().getHeader());
			String destIpString = ipv4Header.getDstAddr().getHostAddress();

			if (!destIpString.startsWith(PacketWatcherCore.getHostAddr())) {
				String destHostName = ipv4Header.getDstAddr().getHostName();
				String sourceHostName = ipv4Header.getSrcAddr().getHostName();
				String countryName = getFlaggedCountry(destIpString);

				if (countryName != null && !countryName.equalsIgnoreCase("Unknown")) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					TcpHeader tcpHeader = ((TcpPacket) packet.getPacket().getPayload().getPayload()).getHeader();

					String destPort = tcpHeader.getDstPort().toString();
					String sourcePort = tcpHeader.getSrcPort().toString();

					PacketRecordKey key = new PacketRecordKey(timestamp, destIpString, destPort, sourceHostName, sourcePort);
					PacketWatcherCore.getPacketService().addToSaveQueue(new FlaggedPacketRecord(key, destHostName, countryName.toUpperCase()));
				}
			}
		}
	}

	private boolean isTcpPacket(PcapPacket packet) {
		return packet.getPacket().getPayload() != null && packet.getPacket().getPayload().getPayload() != null && (packet.getPacket().getPayload().getPayload() instanceof TcpPacket);
	}

}
