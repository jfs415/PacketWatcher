package com.jfs415.packetwatcher_api.util;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.sql.Timestamp;

@Getter
@Immutable
public final class RangedSearchTimeframe extends SearchTimeframe {

    private final Timestamp start;
    private final Timestamp end;

    private RangedSearchTimeframe(Timeframe timeframe, Timestamp start, Timestamp end) {
        super(timeframe);
        this.start = start;
        this.end = end;
    }

    public static RangedSearchTimeframe between(Timestamp start, Timestamp end) {
        return new RangedSearchTimeframe(Timeframe.BETWEEN, start, end);
    }

    public static RangedSearchTimeframe between(long start, long end) {
        return new RangedSearchTimeframe(Timeframe.BETWEEN, new Timestamp(start), new Timestamp(end));
    }

}
