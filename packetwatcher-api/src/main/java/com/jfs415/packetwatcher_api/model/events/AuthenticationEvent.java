package com.jfs415.packetwatcher_api.model.events;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherEvent;
import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "authentication_events", schema = "packetwatcher")
@PacketWatcherEvent
public class AuthenticationEvent extends EventMappedSuperclass implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private AuthenticationEventType eventType;

    public AuthenticationEvent(long time, String ipAddress, AuthenticationEventType eventType) {
        super(new Timestamp(time), null, ipAddress);
        this.eventType = eventType;
    }

    public AuthenticationEvent(
            long time, String ipAddress, String attemptedUsername, AuthenticationEventType eventType) {
        super(new Timestamp(time), attemptedUsername, ipAddress);
        this.eventType = eventType;
    }
}
