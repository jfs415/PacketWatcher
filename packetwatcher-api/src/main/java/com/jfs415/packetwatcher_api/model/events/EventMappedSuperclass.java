package com.jfs415.packetwatcher_api.model.events;

import com.jfs415.packetwatcher_api.views.EventView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class EventMappedSuperclass implements Serializable, IPacketWatcherEvent {

    @Id
    @Column(name = "timestamp", unique = true)
    private Timestamp timestamp;

    @Column(name = "username")
    private String username;

    @Column(name = "ip_address")
    private String ipAddress;

    public EventView toEventView() {
        return new EventView(this);
    }
}
