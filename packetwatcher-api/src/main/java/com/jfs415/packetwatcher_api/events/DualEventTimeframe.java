package com.jfs415.packetwatcher_api.events;

import java.sql.Timestamp;

import org.springframework.data.annotation.Immutable;

@Immutable
public final class DualEventTimeframe extends EventTimeframe {

	private final Timestamp start;
	private final Timestamp end;
	

	private DualEventTimeframe(Timeframe timeframe, Timestamp start, Timestamp end) {
		super(timeframe);
		this.start = start;
		this.end = end;
	}

	public static DualEventTimeframe between(Timestamp start, Timestamp end) {
		return new DualEventTimeframe(Timeframe.BETWEEN, start, end);
	}

	public static DualEventTimeframe between(long start, long end) {
		return new DualEventTimeframe(Timeframe.BETWEEN, new Timestamp(start), new Timestamp(end));
	}

	public Timestamp getStart() {
		return start;
	}

	public Timestamp getEnd() {
		return end;
	}

}
