package com.jfs415.packetwatcher_api.views;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;

@Immutable
public class FlaggedPacketView implements Serializable {

	private final String timestamp;
	private final String destinationHost;
	private final String destinationPort;
	private final String sourceHost;
	private final String sourcePort;
	private final String flaggedCountry;
	
	public FlaggedPacketView(FlaggedPacketRecord record) {
		this.timestamp = record.getKey().getTimestamp().toString();
		this.destinationHost = record.getDestinationHost();
		this.destinationPort = record.getKey().getDestinationPort();
		this.sourceHost = record.getKey().getSourceHost();
		this.sourcePort = record.getKey().getSourcePort();
		this.flaggedCountry = record.getFlaggedCountry();
	}

	public FlaggedPacketView(String timestamp, String destinationHost, String destinationPort, String flaggedCountry, String sourceHost, String sourcePort) {
		this.timestamp = timestamp;
		this.destinationHost = destinationHost;
		this.destinationPort = destinationPort;
		this.flaggedCountry = flaggedCountry;
		this.sourceHost = sourceHost;
		this.sourcePort = sourcePort;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getDestinationHost() {
		return destinationHost;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public String getFlaggedCountry() {
		return flaggedCountry;
	}

	public String getSourceHost() {
		return sourceHost;
	}

	public String getSourcePort() {
		return sourcePort;
	}

}
