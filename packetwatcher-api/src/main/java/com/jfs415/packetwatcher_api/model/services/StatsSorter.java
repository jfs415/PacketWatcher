package com.jfs415.packetwatcher_api.model.services;

import java.util.Comparator;
import java.util.List;

import com.jfs415.packetwatcher_api.model.analytics.StatsRecord;

public class StatsSorter {

	public List<? extends StatsRecord> sortList(List<? extends StatsRecord> records, SortType sortType) {
		records.sort(sortType.comparator);
		return records;
	}

	enum SortType {

		ALPHABETICAL_ASC(Comparator.comparing(StatsRecord::getName)),
		ALPHABETICAL_DESC(Comparator.comparing(StatsRecord::getName).reversed()),
		AMOUNT_ASC(Comparator.comparing(StatsRecord::getRecordsCaught)),
		AMOUNT_DESC(Comparator.comparing(StatsRecord::getRecordsCaught).reversed()),
		FIRST_CAUGHT_ASC(Comparator.comparing(StatsRecord::getFirstCaught)),
		FIRST_CAUGHT_DESC(Comparator.comparing(StatsRecord::getFirstCaught).reversed()),
		LAST_CAUGHT_ASC(Comparator.comparing(StatsRecord::getLastCaught)),
		LAST_CAUGHT_DESC(Comparator.comparing(StatsRecord::getLastCaught).reversed());

		private final Comparator<StatsRecord> comparator;

		SortType(Comparator<StatsRecord> comparator) {
			this.comparator = comparator;
		}
	}

}
