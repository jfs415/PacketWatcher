package com.jfs415.packetwatcher_api.views;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Immutable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.jfs415.packetwatcher_api.events.IAuthEventType;
import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;
import com.jfs415.packetwatcher_api.exceptions.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;

@Immutable
public class EventView implements Serializable {

	private final Timestamp timestamp;

	private final String username;

	private final String ipAddress;

	private final IAuthEventType eventType;

	public EventView(EventMappedSuperclass event) {
		this.timestamp = event.getTimestamp();
		this.username = event.getUsername();
		this.ipAddress = event.getIpAddress();
		this.eventType = getEventType(event);
	}

	public EventView(@NonNull Timestamp timestamp, @Nullable String username, @Nullable String ipAddress, @NonNull AuthenticationEventType eventType) {
		this.timestamp = timestamp;
		this.username = username;
		this.ipAddress = ipAddress;
		this.eventType = eventType;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public IAuthEventType getEventType() {
		return eventType;
	}

	public String getAttemptedUsername() {
		return username;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	private IAuthEventType getEventType(EventMappedSuperclass event) {
		if (event instanceof AuthorizationEvent) {
			return ((AuthorizationEvent) event).getEventType();
		} else if (event instanceof AuthenticationEvent) {
			return ((AuthenticationEvent) event).getEventType();
		} else {
			throw new InvalidEventArgumentException();
		}
	}

}
