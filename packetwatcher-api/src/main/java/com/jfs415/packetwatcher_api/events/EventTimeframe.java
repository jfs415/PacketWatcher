package com.jfs415.packetwatcher_api.events;

import java.sql.Timestamp;

public class EventTimeframe {

	private final Timeframe timeframe;
	private final Timestamp timestamp;

	protected EventTimeframe(Timeframe timeframe) {
		this.timeframe = timeframe;
		this.timestamp = null;
	}

	private EventTimeframe(Timeframe timeframe, Timestamp timestamp) {
		this.timeframe = timeframe;
		this.timestamp = timestamp;
	}

	public static EventTimeframe before(Timestamp timestamp) {
		return new EventTimeframe(Timeframe.BEFORE, timestamp);
	}

	public static EventTimeframe before(long time) {
		return new EventTimeframe(Timeframe.BEFORE, new Timestamp(time));
	}

	public static EventTimeframe after(Timestamp timestamp) {
		return new EventTimeframe(Timeframe.AFTER, timestamp);
	}

	public static EventTimeframe after(long time) {
		return new EventTimeframe(Timeframe.AFTER, new Timestamp(time));
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public enum Timeframe {

		BEFORE,
		AFTER,
		BETWEEN;

	}

}

