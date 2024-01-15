package com.jfs415.packetwatcher_api.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Getter
@Immutable
public final class RangedSearchTimeframe extends SearchTimeframe {

    private final LocalDateTime start;
    private final LocalDateTime end;

    private static final Timeframe TIMEFRAME = Timeframe.BETWEEN;

    private RangedSearchTimeframe(Timeframe timeframe, Timestamp start, Timestamp end) {
        super(timeframe);
        this.start = LocalDateTime.parse(start.toString());
        this.end = LocalDateTime.parse(end.toString());
    }

    public static RangedSearchTimeframe between(Timestamp start, Timestamp end) {
        return new RangedSearchTimeframe(TIMEFRAME, start, end);
    }

    public static RangedSearchTimeframe between(long start, long end) {
        return new RangedSearchTimeframe(TIMEFRAME, new Timestamp(start), new Timestamp(end));
    }

    public boolean isBetween(Timestamp timestamp) {
        LocalDateTime localDateTimestamp = LocalDateTime.parse(timestamp.toString());

        return localDateTimestamp.isAfter(start) && localDateTimestamp.isBefore(end);
    }

    @Override
    public String getFilterString() {
        return TIMEFRAME.getOperatorString() + start.toString() + " and " + end.toString();
    }
}
