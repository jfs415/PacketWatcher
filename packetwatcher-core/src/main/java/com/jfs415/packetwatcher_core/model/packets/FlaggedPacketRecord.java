package com.jfs415.packetwatcher_core.model.packets;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flagged_packet_records", schema = "packetwatcher")
public class FlaggedPacketRecord implements Serializable {

    @EmbeddedId
    public PacketRecordKey key;

    @Column(name = "destination_host")
    public String destinationHost;

    @Column(name = "flagged_country")
    public String flaggedCountry;

    public static FlaggedPacketRecord createTestPacket() {
        PacketRecordKey key = new PacketRecordKey(
                new Timestamp(System.currentTimeMillis()), "127.0.0.1", "00000", "127.0.0.1", "00000");
        return new FlaggedPacketRecord(key, "LOCALHOST", null);
    }

    @Override
    public String toString() {
        return key.toString() + " - " + destinationHost + " " + flaggedCountry + "\n";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FlaggedPacketRecord otherRecord) {
            return this.key.equals(otherRecord.key) && this.flaggedCountry.equals(otherRecord.flaggedCountry);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * (key.hashCode() + flaggedCountry.hashCode());
    }
}
