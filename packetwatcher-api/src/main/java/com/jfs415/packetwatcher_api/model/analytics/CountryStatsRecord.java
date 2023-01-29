package com.jfs415.packetwatcher_api.model.analytics;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "country_stats_records", schema = "packetwatcher")
public class CountryStatsRecord extends StatsRecord implements Serializable {

	@Column(name = "most_caught_hostname")
	private String mostCaughtHostname;

	@Column(name = "most_caught_hostname_count")
	private int mostCaughtHostnameCount;
	
	public CountryStatsRecord() { }

	public CountryStatsRecord(String countryName, int recordsCaught, String mostCaughtHostname, int mostCaughtHostnameCount, Timestamp firstCaught, Timestamp lastCaught) {
		super(countryName, recordsCaught, firstCaught, lastCaught);
		this.mostCaughtHostname = mostCaughtHostname;
		this.mostCaughtHostnameCount = mostCaughtHostnameCount;
	}

	public String getMostCaughtHostname() {
		return mostCaughtHostname;
	}

	public void setMostCaughtHostname(String mostCaughtHostname) {
		this.mostCaughtHostname = mostCaughtHostname;
	}

	public int getMostCaughtHostnameCount() {
		return mostCaughtHostnameCount;
	}

	public void setMostCaughtHostnameCount(int mostCaughtHostnameCount) {
		this.mostCaughtHostnameCount = mostCaughtHostnameCount;
	}

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
