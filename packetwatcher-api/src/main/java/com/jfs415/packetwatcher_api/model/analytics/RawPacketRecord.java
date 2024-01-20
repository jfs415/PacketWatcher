package com.jfs415.packetwatcher_api.model.analytics;

import com.jfs415.packetwatcher_api.views.RawPacketView;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flagged_packet_records", schema = "packetwatcher")
public class RawPacketRecord {

    @EmbeddedId
    private RawPacketRecordKey key;

    @Column(name = "destination_host")
    private String destinationHost;

    @Column(name = "flagged_country")
    private String flaggedCountry;

    public RawPacketView toRawPacketRecordView() {
        return new RawPacketView(
                key.getTimestamp().toString(),
                destinationHost,
                key.getDestinationPort(),
                key.getSourceHost(),
                key.getSourcePort(),
                flaggedCountry);
    }
}
