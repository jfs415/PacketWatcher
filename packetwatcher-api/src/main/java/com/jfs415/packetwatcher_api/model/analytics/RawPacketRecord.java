package com.jfs415.packetwatcher_api.model.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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

}
