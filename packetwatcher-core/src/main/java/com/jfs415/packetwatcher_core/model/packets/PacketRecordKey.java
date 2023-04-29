package com.jfs415.packetwatcher_core.model.packets;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

public class PacketRecordKey implements Serializable {

	@Column(name = "timestamp")
	public Timestamp timestamp;

	@Column(name = "destination_ip")
	public String destinationIp;

	@Column(name = "destination_port")
	public String destinationPort;
	
	public PacketRecordKey() {

	}

	public PacketRecordKey(Timestamp timestamp, String destinationIp, String destinationPort) {
		this.timestamp = timestamp;
		this.destinationIp = destinationIp;
		this.destinationPort = destinationPort;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	public String getDestinationIp() {
		return destinationIp;
	}

	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}
	
	@Override
	public String toString() {
		return timestamp.toString() + " " + destinationIp + ":" + destinationPort;
	} 

	@Override
	public boolean equals(Object other) {
		if (other instanceof PacketRecordKey) {
			PacketRecordKey otherKey = (PacketRecordKey) other;
			return this.timestamp.equals(otherKey.timestamp) && this.destinationIp.equals(otherKey.destinationIp) 
					&& this.destinationPort.equals(otherKey.destinationPort);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (destinationIp.hashCode() + destinationPort.hashCode() + timestamp.hashCode());
	}

}
