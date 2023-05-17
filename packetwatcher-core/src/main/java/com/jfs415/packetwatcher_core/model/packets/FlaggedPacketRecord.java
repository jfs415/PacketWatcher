package com.jfs415.packetwatcher_core.model.packets;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flagged_packet_records", schema = "packetwatcher")
public class FlaggedPacketRecord implements Serializable {

	@EmbeddedId
	private PacketRecordKey key;

	@Column(name = "destination_host")
	public String destinationHost;
	
	@Column(name = "flagged_country")
	private String flaggedCountry;

	public FlaggedPacketRecord() {

	}

	public static FlaggedPacketRecord createTestPacket() {
		PacketRecordKey key = new PacketRecordKey(new Timestamp(System.currentTimeMillis()), "127.0.0.1", "00000", "127.0.0.1" , "00000");
		return new FlaggedPacketRecord(key, "LOCALHOST", null);
	}

	public FlaggedPacketRecord(PacketRecordKey key, String destinationHost, String flaggedCountry) {
		this.destinationHost = destinationHost;
		this.key = key;
		this.flaggedCountry = flaggedCountry;
	}
	
	public String getDestinationHost() {
		return destinationHost;
	}
	
	public void setDestinationHost(String destinationHost) {
		this.destinationHost = destinationHost;
	}

	public String getFlaggedCountry() {
		return flaggedCountry;
	}

	public void setFlaggedCountry(String flaggedCountry) {
		this.flaggedCountry = flaggedCountry;
	}

	public PacketRecordKey getKey() {
		return key;
	}

	@Override
	public String toString() {
		return key.toString() + " - " + destinationHost + " " + flaggedCountry + "\n";
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof FlaggedPacketRecord) {
			FlaggedPacketRecord otherRecord = (FlaggedPacketRecord) other;
			return this.key.equals(otherRecord.key) && this.flaggedCountry.equals(otherRecord.flaggedCountry);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (key.hashCode() + flaggedCountry.hashCode());
	}

}
