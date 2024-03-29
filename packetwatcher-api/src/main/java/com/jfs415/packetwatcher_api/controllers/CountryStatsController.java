package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.analytics.CountryStatsRecord;
import com.jfs415.packetwatcher_api.model.services.StatsServiceImpl;
import com.jfs415.packetwatcher_api.model.services.inf.StatsService;
import com.jfs415.packetwatcher_api.util.RangedSearchAmount;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchAmount;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.StatsCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stats")
public class CountryStatsController {

    private final StatsService statsServiceImpl;
    private static final Class<?> IMPL = CountryStatsRecord.class;

    @Autowired
    public CountryStatsController(StatsServiceImpl statsServiceImpl) {
        this.statsServiceImpl = statsServiceImpl;
    }

    @GetMapping("/country")
    public ResponseEntity<StatsCollectionView> getCountryStats() {
        return ResponseEntity.ok(statsServiceImpl.getStatsByType(IMPL));
    }

    @GetMapping(value = "/country/records-caught", params = "GT")
    public ResponseEntity<StatsCollectionView> getCountryStatsWithRecordsCaughtGreaterThan(
            @RequestParam int greaterThan) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(IMPL, SearchAmount.greaterThan(greaterThan)));
    }

    @GetMapping(value = "/country/records-caught", params = "LT")
    public ResponseEntity<StatsCollectionView> getCountryStatsWithRecordsCaughtLessThan(@RequestParam int lessThan) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(IMPL, SearchAmount.lessThan(lessThan)));
    }

    @GetMapping(
            value = "/country/records-caught",
            params = {"start", "stop"})
    public ResponseEntity<StatsCollectionView> getCountryStatsWithRecordsCaughtBetweenThan(
            @RequestParam int start, @RequestParam int end) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeWithRecordsCaughtAmount(IMPL, RangedSearchAmount.between(start, end)));
    }

    @GetMapping(value = "/country/first-caught", params = "before")
    public ResponseEntity<StatsCollectionView> getCountryStatsWithFirstCaughtBefore(@RequestParam long before) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(IMPL, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/country/first-caught", params = "after")
    public ResponseEntity<StatsCollectionView> getCountryStatsWithFirstCaughtAfter(@RequestParam long after) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(IMPL, SearchTimeframe.after(after)));
    }

    @GetMapping(
            value = "/country/first-caught",
            params = {"start", "stop"})
    public ResponseEntity<StatsCollectionView> getCountryStatsWithFirstCaughtBetween(
            @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndFirstCaughtWithTimeframe(
                IMPL, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/country/last-caught", params = "before")
    public ResponseEntity<StatsCollectionView> getCountryStatsWithLastCaughtBefore(@RequestParam long before) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(IMPL, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/country/last-caught", params = "after")
    public ResponseEntity<StatsCollectionView> getCountryStatsWithCaughtAfter(@RequestParam long after) {
        return ResponseEntity.ok(
                statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(IMPL, SearchTimeframe.after(after)));
    }

    @GetMapping(
            value = "/country/last-caught",
            params = {"start", "stop"})
    public ResponseEntity<StatsCollectionView> getCountryStatsWithLastCaughtBetween(
            @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(statsServiceImpl.getStatsByTypeAndLastCaughtWithTimeframe(
                IMPL, RangedSearchTimeframe.between(start, stop)));
    }
}
