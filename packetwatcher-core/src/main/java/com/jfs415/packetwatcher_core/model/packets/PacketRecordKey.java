package com.jfs415.packetwatcher_core.model.packets;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacketRecordKey implements Serializable {

    @Column(name = "timestamp")
    public Timestamp timestamp;

    @Column(name = "destination_ip")
    public String destinationIp;

    @Column(name = "destination_port")
    public String destinationPort;

    @Column(name = "source_host")
    public String sourceHost;

    @Column(name = "source_port")
    public String sourcePort;

    @Override
    public String toString() {
        return timestamp.toString() + " " + destinationIp + ":" + destinationPort + " " + sourceHost + ":" + sourcePort;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PacketRecordKey) {
            PacketRecordKey otherKey = (PacketRecordKey) other;
            return this.timestamp.equals(otherKey.timestamp)
                    && this.destinationIp.equals(otherKey.destinationIp)
                    && this.destinationPort.equals(otherKey.destinationPort)
                    && this.sourceHost.equals(otherKey.sourceHost)
                    && this.sourcePort.equals(otherKey.sourcePort);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31
                * (destinationIp.hashCode()
                        + destinationPort.hashCode()
                        + timestamp.hashCode()
                        + sourceHost.hashCode()
                        + sourcePort.hashCode());
    }
}
