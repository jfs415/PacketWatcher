package com.jfs415.packetwatcher_api.model.analytics;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherStats;
import java.io.Serializable;
import javax.persistence.Column;
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
@PacketWatcherStats
@Table(name = "hostname_stats_records", schema = "packetwatcher")
public class HostnameStatsRecord extends StatsRecord implements Serializable {

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "port")
    private String port;

    @Override
    public boolean equals(Object other) {
        if (other instanceof HostnameStatsRecord statsRecord) {
            return super.equals(statsRecord)
                    && this.ipAddress.equals(statsRecord.ipAddress)
                    && this.port.equals(statsRecord.port);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * (super.hashCode() + ipAddress.hashCode() + port.hashCode());
    }
}
