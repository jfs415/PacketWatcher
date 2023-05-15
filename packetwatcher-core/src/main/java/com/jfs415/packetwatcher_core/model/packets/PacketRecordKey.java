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
	
	@Column(name = "source_host")
	public String sourceHost;
	
	@Column(name = "source_port")
	public String sourcePort;
	
	public PacketRecordKey() {

	}

	public PacketRecordKey(Timestamp timestamp, String destinationIp, String destinationPort, String sourceHost, String sourcePort) {
		this.timestamp = timestamp;
		this.destinationIp = destinationIp;
		this.destinationPort = destinationPort;
		this.sourceHost = sourceHost;
		this.sourcePort = sourcePort;
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
	
	public void setSourceHost(String sourceHost) {
		this.sourceHost = sourceHost;
	}
	
	public String getSourceHost() {
		return this.sourceHost;
	}

	public void setSourcePort(String sourcePort) {
		this.sourceHost = sourceHost;
	}

	public String getSourcePort() {
		return this.sourcePort;
	}

	@Override
	public String toString() {
		return timestamp.toString() + " " + destinationIp + ":" + destinationPort + " " + sourceHost + ":" +sourcePort;
	} 

	@Override
	public boolean equals(Object other) {
		if (other instanceof PacketRecordKey) {
			PacketRecordKey otherKey = (PacketRecordKey) other;
			return this.timestamp.equals(otherKey.timestamp) && this.destinationIp.equals(otherKey.destinationIp) 
					&& this.destinationPort.equals(otherKey.destinationPort) 
	                && this.sourceHost.equals(otherKey.sourceHost) && this.sourcePort.equals(otherKey.sourcePort);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (destinationIp.hashCode() + destinationPort.hashCode() + timestamp.hashCode() + sourceHost.hashCode() + sourcePort.hashCode());
	}

}
