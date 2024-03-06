package com.jfs415.packetwatcher_api.util;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Immutable
public class SearchTimeframe implements SearchFilter {

    private final Timeframe timeframe;
    private final LocalDateTime timestamp;

    private static final String INVALID_TIME_ARG_MESSAGE = "Invalid time provided";

    protected SearchTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
        this.timestamp = null;
    }

    private SearchTimeframe(Timeframe timeframe, long timestamp) {
        this.timeframe = timeframe;
        this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }

    public static SearchTimeframe before(Timestamp timestamp) throws InvalidEventArgumentException {
        if (isNullInput(timestamp)) {
            throw new InvalidEventArgumentException(INVALID_TIME_ARG_MESSAGE);
        }
        return new SearchTimeframe(Timeframe.BEFORE, timestamp.getTime());
    }

    public static SearchTimeframe before(long time) throws InvalidEventArgumentException {
        if (isInvalidTime(time)) {
            throw new InvalidEventArgumentException(INVALID_TIME_ARG_MESSAGE);
        }
        return new SearchTimeframe(Timeframe.BEFORE, time);
    }

    public static SearchTimeframe after(Timestamp timestamp) throws InvalidEventArgumentException {
        if (isNullInput(timestamp)) {
            throw new InvalidEventArgumentException(INVALID_TIME_ARG_MESSAGE);
        }
        return new SearchTimeframe(Timeframe.AFTER, timestamp.getTime());
    }

    public static SearchTimeframe after(long time) throws InvalidEventArgumentException {
        if (isInvalidTime(time)) {
            throw new InvalidEventArgumentException(INVALID_TIME_ARG_MESSAGE);
        }
        return new SearchTimeframe(Timeframe.AFTER, time);
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

    private static boolean isInvalidTime(long time) {
        return time <= 0;
    }

    private static boolean isNullInput(Timestamp localDateTime) {
        return localDateTime == null;
    }

    @Override
    public String getFilterString() {
        return timeframe.getOperatorString() + timestamp;
    }
}
