package com.jfs415.packetwatcher_api.util;

import java.sql.Timestamp;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Immutable
public class SearchTimeframe {

    private final Timeframe timeframe;
    private final Timestamp timestamp;

    protected SearchTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
        this.timestamp = null;
    }

    private SearchTimeframe(Timeframe timeframe, Timestamp timestamp) {
        this.timeframe = timeframe;
        this.timestamp = timestamp;
    }

    public static SearchTimeframe before(Timestamp timestamp) {
        return new SearchTimeframe(Timeframe.BEFORE, timestamp);
    }

    public static SearchTimeframe before(long time) {
        return new SearchTimeframe(Timeframe.BEFORE, new Timestamp(time));
    }

    public static SearchTimeframe after(Timestamp timestamp) {
        return new SearchTimeframe(Timeframe.AFTER, timestamp);
    }

    public static SearchTimeframe after(long time) {
        return new SearchTimeframe(Timeframe.AFTER, new Timestamp(time));
    }

    public enum Timeframe {
        BEFORE,
        AFTER,
        BETWEEN;
    }
}
