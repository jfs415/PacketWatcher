package com.jfs415.packetwatcher_api.util;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Immutable
public class SearchTimeframe implements SearchFilter {

    private final Timeframe timeframe;
    private final LocalDateTime timestamp;

    protected SearchTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
        this.timestamp = null;
    }

    private SearchTimeframe(Timeframe timeframe, Timestamp timestamp) {
        this.timeframe = timeframe;
        this.timestamp = LocalDateTime.parse(timestamp.toString());
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

    public boolean passesFilterTest(Timestamp timestampToCheck) {
        LocalDateTime localTimestamp = LocalDateTime.parse(timestampToCheck.toString());

        switch (timeframe) {
            case BEFORE -> {
                return localTimestamp.isBefore(timestamp);
            }
            case AFTER -> {
                return localTimestamp.isAfter(timestamp);
            }
            default -> throw new InvalidArgumentException("Unknown timeframe " + timeframe);
        }
    }

    @Override
    public String getFilterString() {
        return timeframe.getOperatorString() + timestamp;
    }
}
