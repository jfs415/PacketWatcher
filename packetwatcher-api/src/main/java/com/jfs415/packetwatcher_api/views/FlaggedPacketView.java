package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class FlaggedPacketView {

    private final String timestamp;
    private final String destinationHost;
    private final String destinationPort;
    private final String sourceHost;
    private final String sourcePort;
    private final String flaggedCountry;

    public FlaggedPacketView(FlaggedPacketRecord flaggedPacketRecord) {
        this.timestamp = flaggedPacketRecord.getKey().getTimestamp().toString();
        this.destinationHost = flaggedPacketRecord.getDestinationHost();
        this.destinationPort = flaggedPacketRecord.getKey().getDestinationPort();
        this.sourceHost = flaggedPacketRecord.getKey().getSourceHost();
        this.sourcePort = flaggedPacketRecord.getKey().getSourcePort();
        this.flaggedCountry = flaggedPacketRecord.getFlaggedCountry();
    }

}
