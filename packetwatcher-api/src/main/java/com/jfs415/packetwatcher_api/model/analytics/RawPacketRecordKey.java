package com.jfs415.packetwatcher_api.model.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RawPacketRecordKey implements Serializable {

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "destination_ip")
    private String destinationIp;

    @Column(name = "destination_port")
    private String destinationPort;

    @Column(name = "source_host")
    private String sourceHost;

    @Column(name = "source_port")
    private String sourcePort;
}
