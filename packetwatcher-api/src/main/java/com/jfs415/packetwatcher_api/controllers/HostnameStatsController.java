package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.analytics.HostnameStatsRecord;
import com.jfs415.packetwatcher_api.model.services.StatsServiceImpl;
import com.jfs415.packetwatcher_api.util.RangedSearchAmount;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchAmount;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.StatsCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HostnameStatsController {

	private final StatsServiceImpl statsServiceImpl;
	private final Class<?> IMPL = HostnameStatsRecord.class;

	@Autowired
	public HostnameStatsController(StatsServiceImpl statsServiceImpl) {
		this.statsServiceImpl = statsServiceImpl;
	}

	@GetMapping("stats/hostname")
	public ResponseEntity<StatsCollectionView> getHostnameStats() {
		return ResponseEntity.ok(statsServiceImpl.getStatsByType(IMPL));
	}

	@GetMapping(value = "stats/hostname/records-caught", params = "GT")
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithRecordsCaughtGreaterThan(@RequestParam int greaterThan) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(IMPL, SearchAmount.greaterThan(greaterThan)));
	}

	@GetMapping(value = "stats/hostname/records-caught", params = "LT")
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithRecordsCaughtLessThan(@RequestParam int lessThan) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(IMPL, SearchAmount.lessThan(lessThan)));
	}

	@GetMapping(value = "stats/hostname/records-caught", params = { "start", "stop" })
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithRecordsCaughtBetweenThan(@RequestParam int start, @RequestParam int end) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(IMPL, RangedSearchAmount.between(start, end)));
	}

	@GetMapping(value = "stats/hostname/first-caught", params = "before")
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithFirstCaughtBefore(@RequestParam long before) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(IMPL, SearchTimeframe.before(before)));

	}

	@GetMapping(value = "stats/hostname/first-caught", params = "after")
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithFirstCaughtAfter(@RequestParam long after) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(IMPL, SearchTimeframe.after(after)));
	}

	@GetMapping(value = "stats/hostname/first-caught", params = { "start", "stop" })
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithFirstCaughtBetween(@RequestParam long start, @RequestParam long stop) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(IMPL, RangedSearchTimeframe.between(start, stop)));
	}

	@GetMapping(value = "stats/hostname/last-caught", params = "before")
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithLastCaughtBefore(@RequestParam long before) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(IMPL, SearchTimeframe.before(before)));
	}

	@GetMapping(value = "stats/hostname/last-caught", params = "after")
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithCaughtAfter(@RequestParam long after) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(IMPL, SearchTimeframe.after(after)));
	}

	@GetMapping(value = "stats/hostname/last-caught", params = { "start", "stop" })
	public ResponseEntity<StatsCollectionView> getHostnameStatsWithLastCaughtBetween(@RequestParam long start, @RequestParam long stop) {
		return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(IMPL, RangedSearchTimeframe.between(start, stop)));
	}

}
