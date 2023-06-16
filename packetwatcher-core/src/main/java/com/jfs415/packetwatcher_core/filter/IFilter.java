package com.jfs415.packetwatcher_core.filter;

public interface IFilter<T> {
	
	boolean isFilterValue(T value);
	
	boolean isType(Class<?> type);

}
