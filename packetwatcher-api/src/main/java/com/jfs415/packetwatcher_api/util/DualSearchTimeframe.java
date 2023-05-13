package com.jfs415.packetwatcher_api.util;

import java.sql.Timestamp;

import org.springframework.data.annotation.Immutable;

@Immutable
public final class DualSearchTimeframe extends SearchTimeframe {

	private final Timestamp start;
	private final Timestamp end;
	

	private DualSearchTimeframe(Timeframe timeframe, Timestamp start, Timestamp end) {
		super(timeframe);
		this.start = start;
		this.end = end;
	}

	public static DualSearchTimeframe between(Timestamp start, Timestamp end) {
		return new DualSearchTimeframe(Timeframe.BETWEEN, start, end);
	}

	public static DualSearchTimeframe between(long start, long end) {
		return new DualSearchTimeframe(Timeframe.BETWEEN, new Timestamp(start), new Timestamp(end));
	}

	public Timestamp getStart() {
		return start;
	}

	public Timestamp getEnd() {
		return end;
	}

}
