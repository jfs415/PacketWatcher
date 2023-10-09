package com.jfs415.packetwatcher_api.model.analytics;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
        if (other instanceof HostnameStatsRecord) {
            HostnameStatsRecord obj = (HostnameStatsRecord) other;

            return super.equals(obj) && this.ipAddress.equals(obj.ipAddress) && this.port.equals(obj.port);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * (super.hashCode() + ipAddress.hashCode() + port.hashCode());
    }

}
