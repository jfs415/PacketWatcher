package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.events.IAuthEventType;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Immutable
public class EventView {

    private final Timestamp timestamp;

    private final String username;

    private final String ipAddress;

    private final IAuthEventType eventType;

    public EventView(EventMappedSuperclass event) {
        this.timestamp = event.getTimestamp();
        this.username = event.getUsername();
        this.ipAddress = event.getIpAddress();
        this.eventType = getEventTypeFromSuperEvent(event);
    }

    private IAuthEventType getEventTypeFromSuperEvent(EventMappedSuperclass event) {
        if (event instanceof AuthorizationEvent authorizationEvent) {
            return authorizationEvent.getEventType();
        } else if (event instanceof AuthenticationEvent authenticationEvent) {
            return authenticationEvent.getEventType();
        } else {
            throw new InvalidEventArgumentException();
        }
    }

}
