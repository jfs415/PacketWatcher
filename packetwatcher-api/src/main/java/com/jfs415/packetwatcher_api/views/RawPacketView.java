package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class RawPacketView {

    private final String timestamp;
    private final String destinationHost;
    private final String destinationPort;
    private final String sourceHost;
    private final String sourcePort;
    private final String flaggedCountry;

    public RawPacketView(RawPacketRecord rawPacketRecord) {
        this.timestamp = rawPacketRecord.getKey().getTimestamp().toString();
        this.destinationHost = rawPacketRecord.getDestinationHost();
        this.destinationPort = rawPacketRecord.getKey().getDestinationPort();
        this.sourceHost = rawPacketRecord.getKey().getSourceHost();
        this.sourcePort = rawPacketRecord.getKey().getSourcePort();
        this.flaggedCountry = rawPacketRecord.getFlaggedCountry();
    }

}
