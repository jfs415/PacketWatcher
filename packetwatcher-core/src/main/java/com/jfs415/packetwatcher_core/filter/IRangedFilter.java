package com.jfs415.packetwatcher_core.filter;

import java.util.List;

public interface IRangedFilter<T extends Comparable<T>> extends IFilter<T> {

	T getRangeStart();
	
	T getRangeEnd();

	boolean isInRangeExclusive(T value);

	boolean isInRangeLowerInclusiveUpperExclusive(T value);

	boolean isInRangeLowerExclusiveUpperInclusive(T value);

	boolean anyInRange(List<T> values);

	boolean hasRangedComparator();

}
