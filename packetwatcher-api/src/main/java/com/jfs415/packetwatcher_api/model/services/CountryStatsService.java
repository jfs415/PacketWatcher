package com.jfs415.packetwatcher_api.model.services;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;
import com.jfs415.packetwatcher_api.model.repositories.CountryStatsRepository;

@Service
public class CountryStatsService {

	@Autowired
	private CountryStatsRepository countryStatsRepo;

	private final LinkedList<CountryStatsRecord> countryStatsCache = new LinkedList<>();

	public Optional<CountryStatsRecord> getRecordByCountryName(String country) {
		return countryStatsRepo.findById(country);
	}

	public List<CountryStatsRecord> getAllFirstCaughtBefore(Timestamp before) {
		return List.copyOf(countryStatsRepo.findAllByFirstCaughtBefore(before));
	}

	public List<CountryStatsRecord> getAllFirstCaughtAfter(Timestamp after) {
		return List.copyOf(countryStatsRepo.findAllByFirstCaughtAfter(after));
	}

	public List<CountryStatsRecord> getAllFirstCaughtBetween(Timestamp start, Timestamp end) {
		return List.copyOf(countryStatsRepo.findAllByFirstCaughtBetween(start, end));
	}

	public List<CountryStatsRecord> getAllLastCaughtBefore(Timestamp before) {
		return List.copyOf(countryStatsRepo.findAllByLastCaughtBefore(before));
	}

	public List<CountryStatsRecord> getAllLastCaughtAfter(Timestamp after) {
		return List.copyOf(countryStatsRepo.findAllByLastCaughtAfter(after));
	}

	public List<CountryStatsRecord> getAllLastCaughtBetween(Timestamp start, Timestamp end) {
		return List.copyOf(countryStatsRepo.findAllByLastCaughtBetween(start, end));
	}

}
