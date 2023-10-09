package com.jfs415.packetwatcher_core.filter;

public interface RangedComparator<V, F, C> {

    boolean isInRangeInclusive(V value, F floor, C ceiling);

    boolean isInRangeExclusive(V value, F floor, C ceiling);

    boolean isInRangeLowerExclusiveUpperInclusive(V value, F floor, C ceiling);

    boolean isInRangeLowerInclusiveUpperExclusive(V value, F floor, C ceiling);

}
