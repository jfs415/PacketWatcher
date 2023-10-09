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
@AllArgsConstructor
@NoArgsConstructor
@PacketWatcherStats
@Table(name = "country_stats_records", schema = "packetwatcher")
public class CountryStatsRecord extends StatsRecord implements Serializable {

    @Column(name = "most_caught_hostname")
    private String mostCaughtHostname;

    @Column(name = "most_caught_hostname_count")
    private int mostCaughtHostnameCount;
	
    @Override
    public boolean equals(Object other) {
        if (other instanceof CountryStatsRecord) {
            CountryStatsRecord obj = (CountryStatsRecord) other;
            return super.equals(obj) && this.mostCaughtHostname.equals(obj.mostCaughtHostname) && this.mostCaughtHostnameCount == obj.mostCaughtHostnameCount;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * (super.hashCode() + mostCaughtHostname.hashCode() + mostCaughtHostnameCount);
    }

}
