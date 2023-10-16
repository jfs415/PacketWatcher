package com.jfs415.packetwatcher_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;
import com.jfs415.packetwatcher_api.model.services.StatsServiceImpl;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.RangedSearchAmount;
import com.jfs415.packetwatcher_api.util.SearchAmount;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;

@Controller
public class CountryStatsController {

	private final StatsServiceImpl statsServiceImpl;
	private final Class<?> impl = CountryStatsRecord.class;

	@Autowired
	public CountryStatsController(StatsServiceImpl statsServiceImpl) {
		this.statsServiceImpl = statsServiceImpl;
	}

	@GetMapping("stats/country")
	public ResponseEntity<?> getCountryStats() {
		return ResponseEntity.ok(statsServiceImpl.getStatsByType(impl));
	}

	@GetMapping(value = "stats/country/records-caught", params = "GT")
	public ResponseEntity<?> getCountryStatsWithRecordsCaughtGreaterThan(@RequestParam int greaterThan) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(impl, SearchAmount.greaterThan(greaterThan)));
	}

	@GetMapping(value = "stats/country/records-caught", params = "LT")
	public ResponseEntity<?> getCountryStatsWithRecordsCaughtLessThan(@RequestParam int lessThan) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(impl, SearchAmount.lessThan(lessThan)));
	}

	@GetMapping(value = "stats/country/records-caught", params = { "start", "stop" })
	public ResponseEntity<?> getCountryStatsWithRecordsCaughtBetweenThan(@RequestParam int start, @RequestParam int end) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(impl, RangedSearchAmount.between(start, end)));
	}

	@GetMapping(value = "stats/country/first-caught", params = "before")
	public ResponseEntity<?> getCountryStatsWithFirstCaughtBefore(@RequestParam long before) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(impl, SearchTimeframe.before(before)));

	}

	@GetMapping(value = "stats/country/first-caught", params = "after")
	public ResponseEntity<?> getCountryStatsWithFirstCaughtAfter(@RequestParam long after) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(impl, SearchTimeframe.after(after)));
	}

	@GetMapping(value = "stats/country/first-caught", params = { "start", "stop" })
	public ResponseEntity<?> getCountryStatsWithFirstCaughtBetween(@RequestParam long start, @RequestParam long stop) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(impl, RangedSearchTimeframe.between(start, stop)));
	}

	@GetMapping(value = "stats/country/last-caught", params = "before")
	public ResponseEntity<?> getCountryStatsWithLastCaughtBefore(@RequestParam long before) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(impl, SearchTimeframe.before(before)));
	}

	@GetMapping(value = "stats/country/last-caught", params = "after")
	public ResponseEntity<?> getCountryStatsWithCaughtAfter(@RequestParam long after) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(impl, SearchTimeframe.after(after)));
	}

	@GetMapping(value = "stats/country/last-caught", params = { "start", "stop" })
	public ResponseEntity<?> getCountryStatsWithLastCaughtBetween(@RequestParam long start, @RequestParam long stop) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(impl, RangedSearchTimeframe.between(start, stop)));
	}

}
