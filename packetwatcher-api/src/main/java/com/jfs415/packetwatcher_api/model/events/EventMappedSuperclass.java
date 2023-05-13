package com.jfs415.packetwatcher_api.model.events;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.jfs415.packetwatcher_api.views.EventView;

@MappedSuperclass
public class EventMappedSuperclass implements Serializable, IPacketWatcherEvent {

	@Id
	@Column(name = "timestamp", unique = true)
	private Timestamp timestamp;

	@Column(name = "username")
	private String username;

	@Column(name = "ip_address")
	private String ipAddress;

	public EventMappedSuperclass() { }

	public EventMappedSuperclass(long time) {
		this.timestamp = new Timestamp(time);
		this.username = null;
		this.ipAddress = null;
	}

	public EventMappedSuperclass(long time, String username, String ipAddress) {
		this.timestamp = new Timestamp(time);
		this.username = username;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EventView toEventView() {
		return new EventView(this);
	}

}
