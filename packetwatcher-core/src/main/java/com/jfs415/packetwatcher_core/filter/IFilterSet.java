package com.jfs415.packetwatcher_core.filter;

import java.util.List;
import java.util.Set;

public interface IFilterSet<T> extends IFilter<T> {

    boolean containsAll(List<T> values);

    boolean containsAny(List<T> values);

    Set<T> getDataSet();

}