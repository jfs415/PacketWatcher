package com.jfs415.packetwatcher_api.model.events;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.jfs415.packetwatcher_api.events.authorization.AuthorizationEventType;

@Entity
@Table(name = "authoritization_events", schema = "packetwatcher")
public class AuthorizationEvent extends EventMappedSuperclass implements Serializable, IPacketWatcherEvent {

	@Enumerated(EnumType.STRING)
	@Column(name = "event_type")
	private AuthorizationEventType eventType;
	
	public AuthorizationEvent(long time, String attemptedUsername, String ipAddress, AuthorizationEventType eventType) {
		super(time, attemptedUsername, ipAddress);
		this.eventType = eventType;
	}

	public AuthorizationEvent(long time, String ipAddress, AuthorizationEventType eventType) {
		super(time, null, ipAddress);
		this.eventType = eventType;
	}

	public AuthorizationEventType getEventType() {
		return eventType;
	}

	public void setEventType(AuthorizationEventType eventType) {
		this.eventType = eventType;
	}

}
