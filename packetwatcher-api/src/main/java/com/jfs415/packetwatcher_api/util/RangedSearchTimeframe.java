package com.jfs415.packetwatcher_api.util;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Getter
@Immutable
public final class RangedSearchTimeframe extends SearchTimeframe {

    private final LocalDateTime start;
    private final LocalDateTime end;

    private static final Timeframe TIMEFRAME = Timeframe.BETWEEN;
    private static final String INVALID_EVENT_ARG_MESSAGE = "Invalid start and end times provided";

    private RangedSearchTimeframe(Timeframe timeframe, Timestamp start, Timestamp end) {
        super(timeframe);
        this.start = LocalDateTime.parse(start.toString());
        this.end = LocalDateTime.parse(end.toString());
    }

    private RangedSearchTimeframe(Timeframe timeframe, long start, long end) {
        super(timeframe);
        this.start = LocalDateTime.ofInstant(Instant.ofEpochMilli(start), TimeZone.getDefault().toZoneId());
        this.end = LocalDateTime.ofInstant(Instant.ofEpochMilli(end), TimeZone.getDefault().toZoneId());
    }

    public static RangedSearchTimeframe between(Timestamp start, Timestamp end) throws InvalidEventArgumentException {
        if (isNullInput(start, end) || isInValidBetween(start, start)) {
            throw new InvalidEventArgumentException(INVALID_EVENT_ARG_MESSAGE);
        }

        return new RangedSearchTimeframe(TIMEFRAME, start, end);
    }

    public static RangedSearchTimeframe between(long start, long end) throws InvalidEventArgumentException {
        if (isInValidLongValueInput(start, end)) {
            throw new InvalidEventArgumentException(INVALID_EVENT_ARG_MESSAGE);
        }
        
        return new RangedSearchTimeframe(TIMEFRAME, start, end);
    }

    private static boolean isNullInput(Object start, Object end) {
        return start == null || end == null;
    }

    private static boolean isInValidLongValueInput(long start, long end) {
        return start <= 0 || end <= 0 || start >= end;
    }

    private static boolean isInValidBetween(Timestamp start, Timestamp end) {
        return start.after(end);
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
