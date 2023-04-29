package com.jfs415.packetwatcher_api.model.analytics;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hostname_stats_records", schema = "packetwatcher")
public class HostnameStatsRecord extends StatsRecord implements Serializable {

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "port")
	private String port;
	
	public HostnameStatsRecord() { }

	public HostnameStatsRecord(int recordsCaught, Timestamp firstCaught, Timestamp lastCaught, String hostname, String ipAddress, String port) {
		super(hostname, recordsCaught, firstCaught, lastCaught);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getPort() {
		return port;
	}

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
