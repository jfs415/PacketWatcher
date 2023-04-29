package com.jfs415.packetwatcher_api.model.events;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;

@Entity
@Table(name = "authentication_events", schema = "packetwatcher")
public class AuthenticationEvent extends PacketWatcherEvent implements Serializable {

	@Enumerated(EnumType.STRING)
	@Column(name = "event_type")
	private AuthenticationEventType eventType;

	public AuthenticationEvent() { }
	
	public AuthenticationEvent(long time, String ipAddress, AuthenticationEventType eventType) {
		super(time, null, ipAddress);
		this.eventType = eventType;
	}

	public AuthenticationEvent(long time, String ipAddress, String attemptedUsername, AuthenticationEventType eventType) {
		super(time, attemptedUsername, ipAddress);
		this.eventType = eventType;
	}

	public AuthenticationEventType getEventType() {
		return eventType;
	}

	public void setEventType(AuthenticationEventType eventType) {
		this.eventType = eventType;
	}

}
