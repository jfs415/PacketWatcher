package com.jfs415.packetwatcher_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;
import com.jfs415.packetwatcher_api.model.services.StatsServiceImpl;
import com.jfs415.packetwatcher_api.util.RangedSearchAmount;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchAmount;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;

@Controller
public class HostnameStatsController {

	private final StatsServiceImpl statsServiceImpl;
	private final Class<?> impl = HostnameStatsRecord.class;

	@Autowired
	public HostnameStatsController(StatsServiceImpl statsServiceImpl) {
		this.statsServiceImpl = statsServiceImpl;
	}

	@GetMapping("stats/hostname")
	public ResponseEntity<?> getHostnameStats() {
		return ResponseEntity.ok(statsServiceImpl.getStatsByType(impl));
	}

	@GetMapping(value = "stats/hostname/records-caught", params = "GT")
	public ResponseEntity<?> getHostnameStatsWithRecordsCaughtGreaterThan(@RequestParam int greaterThan) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(impl, SearchAmount.greaterThan(greaterThan)));
	}

	@GetMapping(value = "stats/hostname/records-caught", params = "LT")
	public ResponseEntity<?> getHostnameStatsWithRecordsCaughtLessThan(@RequestParam int lessThan) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(impl, SearchAmount.lessThan(lessThan)));
	}

	@GetMapping(value = "stats/hostname/records-caught", params = { "start", "stop" })
	public ResponseEntity<?> getHostnameStatsWithRecordsCaughtBetweenThan(@RequestParam int start, @RequestParam int end) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(impl, RangedSearchAmount.between(start, end)));
	}

	@GetMapping(value = "stats/hostname/first-caught", params = "before")
	public ResponseEntity<?> getHostnameStatsWithFirstCaughtBefore(@RequestParam long before) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(impl, SearchTimeframe.before(before)));

	}

	@GetMapping(value = "stats/hostname/first-caught", params = "after")
	public ResponseEntity<?> getHostnameStatsWithFirstCaughtAfter(@RequestParam long after) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(impl, SearchTimeframe.after(after)));
	}

	@GetMapping(value = "stats/hostname/first-caught", params = { "start", "stop" })
	public ResponseEntity<?> getHostnameStatsWithFirstCaughtBetween(@RequestParam long start, @RequestParam long stop) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(impl, RangedSearchTimeframe.between(start, stop)));
	}

	@GetMapping(value = "stats/hostname/last-caught", params = "before")
	public ResponseEntity<?> getHostnameStatsWithLastCaughtBefore(@RequestParam long before) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(impl, SearchTimeframe.before(before)));
	}

	@GetMapping(value = "stats/hostname/last-caught", params = "after")
	public ResponseEntity<?> getHostnameStatsWithCaughtAfter(@RequestParam long after) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(impl, SearchTimeframe.after(after)));
	}

	@GetMapping(value = "stats/hostname/last-caught", params = { "start", "stop" })
	public ResponseEntity<?> getHostnameStatsWithLastCaughtBetween(@RequestParam long start, @RequestParam long stop) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(impl, RangedSearchTimeframe.between(start, stop)));
	}

}
