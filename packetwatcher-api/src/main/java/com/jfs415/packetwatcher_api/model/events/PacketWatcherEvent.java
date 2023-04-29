package com.jfs415.packetwatcher_api.model.events;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.jfs415.packetwatcher_api.views.EventView;

@MappedSuperclass
public class PacketWatcherEvent implements Serializable {

	@Id
	@Column(name = "timestamp", unique = true)
	private Timestamp timestamp;

	@Column(name = "attempted_username")
	private String attemptedUsername;

	@Column(name = "ip_address")
	private String ipAddress;

	public PacketWatcherEvent() { }

	public PacketWatcherEvent(long time) {
		this.timestamp = new Timestamp(time);
		this.attemptedUsername = null;
		this.ipAddress = null;
	}

	public PacketWatcherEvent(long time, String attemptedUsername, String ipAddress) {
		this.timestamp = new Timestamp(time);
		this.attemptedUsername = attemptedUsername;
		this.ipAddress = ipAddress;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAttemptedUsername() {
		return attemptedUsername;
	}

	public void setAttemptedUsername(String username) {
		this.attemptedUsername = username;
	}

	public EventView toEventView() {
		return new EventView(this);
	}

}
