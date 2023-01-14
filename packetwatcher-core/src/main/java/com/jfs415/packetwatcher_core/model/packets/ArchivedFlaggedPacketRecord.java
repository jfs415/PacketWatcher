package com.jfs415.packetwatcher_core.model.packets;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jfs415.packetwatcher_core.PacketWatcherCore;

@Entity
@Table(name = "archived_flagged_packet_records", schema = "packetwatcher")
public class ArchivedFlaggedPacketRecord implements Serializable, PacketRecord {

	@EmbeddedId
	private PacketRecordKey key;

	@Column(name = "destination_host")
	public String destinationHost;

	@Column(name = "flagged_country")
	private String flaggedCountry;

	@Column(name = "expiration_timestamp")
	private Timestamp expirationTimestamp;

	public ArchivedFlaggedPacketRecord() { }

	public ArchivedFlaggedPacketRecord(PacketRecordKey key, String destinationHost, String flaggedCountry) {
		this.key = key;
		this.destinationHost = destinationHost;
		this.flaggedCountry = flaggedCountry;

		LocalDateTime now = LocalDateTime.now();
		this.expirationTimestamp = new Timestamp(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}
	
	public static List<ArchivedFlaggedPacketRecord> batchConvert(List<FlaggedPacketRecord> flaggedRecords) {
		ArrayList<ArchivedFlaggedPacketRecord> archivedRecords = new ArrayList<>();
		
		for (FlaggedPacketRecord flaggedRecord : flaggedRecords) {
			archivedRecords.add(new ArchivedFlaggedPacketRecord(flaggedRecord));
		}
		
		return archivedRecords;
	}

	public ArchivedFlaggedPacketRecord(FlaggedPacketRecord record) {
		this(record.getKey(), record.destinationHost, record.getFlaggedCountry());
	}

	public String getFlaggedCountry() {
		return flaggedCountry;
	}

	public void setFlaggedCountry(String flaggedCountry) {
		this.flaggedCountry = flaggedCountry;
	}

	public Timestamp getExpirationTimestamp() {
		return expirationTimestamp;
	}

	public void setExpirationTimestamp(Timestamp expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
	}

	public void save() {
		PacketWatcherCore.getPacketService().savePacketRecord(this);
	}

	@Override
	public String toString() {
		return key.timestamp.toString() + " - " + destinationHost + " - " + flaggedCountry + "\n";
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ArchivedFlaggedPacketRecord) {
			ArchivedFlaggedPacketRecord otherRecord = (ArchivedFlaggedPacketRecord) other;
			return this.key.equals(otherRecord.key) && this.flaggedCountry.equals(otherRecord.flaggedCountry) 
					&& this.expirationTimestamp.equals(otherRecord.expirationTimestamp);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (key.hashCode() + flaggedCountry.hashCode() + expirationTimestamp.hashCode());
	}

}
