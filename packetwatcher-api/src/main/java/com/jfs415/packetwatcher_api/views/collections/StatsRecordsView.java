package com.jfs415.packetwatcher_api.views.collections;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;
import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;

@Immutable
public class StatsRecordsView {

	private final List<CountryStatsRecord> countryStatsRecords;
	private final List<HostnameStatsRecord> hostnameStatsRecords;

	public StatsRecordsView(ArrayList<CountryStatsRecord> countryStatsRecords, ArrayList<HostnameStatsRecord> hostnameStatsRecords) {
		this.countryStatsRecords = countryStatsRecords;
		this.hostnameStatsRecords = hostnameStatsRecords;
	}

	public List<CountryStatsRecord> getCountryStatsRecords() {
		return countryStatsRecords;
	}

	public List<HostnameStatsRecord> getHostnameStatsRecords() {
		return hostnameStatsRecords;
	}

}
