package com.jfs415.packetwatcher_api.views;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;

@Immutable
public class FlaggedPacketView implements Serializable {

	private final String timestamp;
	private final String destinationHost;
	private final String destinationPort;
	private final String flaggedCountry;
	
	public FlaggedPacketView(FlaggedPacketRecord record) {
		this.timestamp = record.getKey().getTimestamp().toString();
		this.destinationHost = record.getDestinationHost();
		this.destinationPort = record.getKey().getDestinationPort();
		this.flaggedCountry = record.getFlaggedCountry();
	}

	public FlaggedPacketView(String timestamp, String destinationHost, String destinationPort, String flaggedCountry) {
		this.timestamp = timestamp;
		this.destinationHost = destinationHost;
		this.destinationPort = destinationPort;
		this.flaggedCountry = flaggedCountry;
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

}
