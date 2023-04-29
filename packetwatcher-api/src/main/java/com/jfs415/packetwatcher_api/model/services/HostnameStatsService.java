package com.jfs415.packetwatcher_api.model.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;
import com.jfs415.packetwatcher_api.model.repositories.HostnameStatsRepository;

@Service
public class HostnameStatsService {

	@Autowired
	private HostnameStatsRepository hostnameStatsRepo;

	public Optional<HostnameStatsRecord> getRecordByCountryName(String country) {
		return hostnameStatsRepo.findById(country);
	}

	public List<HostnameStatsRecord> getAllFirstCaughtBefore(Timestamp before) {
		return List.copyOf(hostnameStatsRepo.findAllByFirstCaughtBefore(before));
	}

	public List<HostnameStatsRecord> getAllFirstCaughtAfter(Timestamp after) {
		return List.copyOf(hostnameStatsRepo.findAllByFirstCaughtAfter(after));
	}

	public List<HostnameStatsRecord> getAllFirstCaughtBetween(Timestamp start, Timestamp end) {
		return List.copyOf(hostnameStatsRepo.findAllByFirstCaughtBetween(start, end));
	}

	public List<HostnameStatsRecord> getAllLastCaughtBefore(Timestamp before) {
		return List.copyOf(hostnameStatsRepo.findAllByLastCaughtBefore(before));
	}

	public List<HostnameStatsRecord> getAllLastCaughtAfter(Timestamp after) {
		return List.copyOf(hostnameStatsRepo.findAllByLastCaughtAfter(after));
	}

	public List<HostnameStatsRecord> getAllLastCaughtBetween(Timestamp start, Timestamp end) {
		return List.copyOf(hostnameStatsRepo.findAllByLastCaughtBetween(start, end));
	}

}
