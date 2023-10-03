package com.jfs415.packetwatcher_core.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Immutable;
import org.springframework.lang.Nullable;

import java.util.List;

@Immutable
public final class RangedFilter<T extends Comparable<T>> implements IRangedFilter<T> {

    @JsonProperty("start")
    private final T rangeStart;

    @JsonProperty("end")
    private final T rangeEnd;

    @Nullable
    private final RangedComparator<T, T, T> rangedComparator;

    public RangedFilter(T rangeStart, T rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangedComparator = null;
    }

    public RangedFilter(T rangeStart, T rangeEnd, RangedComparator<T, T, T> rangedComparator) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangedComparator = rangedComparator;
    }

    @Override
    public T getRangeStart() {
        return rangeStart;
    }

    @Override
    public T getRangeEnd() {
        return rangeEnd;
    }

    @Override
    public boolean isFilterValue(T value) {
        return rangedComparator == null ? value.compareTo(rangeStart) >= 0 && value.compareTo(rangeEnd) <= 0
                : rangedComparator.isInRangeInclusive(value, rangeStart, rangeEnd);
    }

    @Override
    public boolean isType(Class<?> type) {
        return rangeStart != null && rangeEnd != null && rangeStart.getClass().isAssignableFrom(type) && rangeEnd.getClass().isAssignableFrom(type);
    }

    @Override
    public boolean isInRangeExclusive(T value) {
        return rangedComparator == null ? value.compareTo(rangeStart) > 0 && value.compareTo(rangeEnd) < 0
                : rangedComparator.isInRangeExclusive(value, rangeStart, rangeEnd);
    }

    @Override
    public boolean isInRangeLowerInclusiveUpperExclusive(T value) {
        return rangedComparator == null ? value.compareTo(rangeStart) >= 0 && value.compareTo(rangeEnd) < 0
                : rangedComparator.isInRangeLowerInclusiveUpperExclusive(value, rangeStart, rangeEnd);
    }

    @Override
    public boolean isInRangeLowerExclusiveUpperInclusive(T value) {
        return rangedComparator == null ? value.compareTo(rangeStart) > 0 && value.compareTo(rangeEnd) <= 0
                : rangedComparator.isInRangeLowerExclusiveUpperInclusive(value, rangeStart, rangeEnd);
    }

    @Override
    public boolean anyInRange(List<T> values) {
        return values.stream().anyMatch(v -> {
            return rangedComparator == null ? v.compareTo(rangeStart) >= 0 && v.compareTo(rangeEnd) <= 0
                    : rangedComparator.isInRangeInclusive(v, rangeStart, rangeEnd);
        });
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RangedFilter<?>) {
            RangedFilter<?> otherFilter = (RangedFilter<?>) other;
            return this.rangeStart.equals(otherFilter.rangeStart) && this.rangeEnd.equals(otherFilter.rangeEnd);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * (rangeStart.hashCode() * rangeEnd.hashCode());
    }

    @Override
    public boolean hasRangedComparator() {
        return rangedComparator != null;
    }

}
