package com.jfs415.packetwatcher_api.model.events;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherEvent;
import com.jfs415.packetwatcher_api.events.authorization.AuthorizationEventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PacketWatcherEvent
@Table(name = "authoritization_events", schema = "packetwatcher")
public class AuthorizationEvent extends EventMappedSuperclass implements Serializable, IPacketWatcherEvent {

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private AuthorizationEventType eventType;

    public AuthorizationEvent(long time, String ipAddress, AuthorizationEventType eventType) {
        super(new Timestamp(time), null, ipAddress);
        this.eventType = eventType;
    }

    public AuthorizationEvent(long time, String ipAddress, String username, AuthorizationEventType eventType) {
        super(new Timestamp(time), username, ipAddress);
        this.eventType = eventType;
    }

}
