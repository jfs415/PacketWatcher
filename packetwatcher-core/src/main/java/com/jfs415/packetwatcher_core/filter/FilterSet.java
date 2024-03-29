package com.jfs415.packetwatcher_core.filter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.annotation.Immutable;

@Immutable
public final class FilterSet<T> implements IFilterSet<T> {

    private final Set<T> filteredValues = ConcurrentHashMap.newKeySet();

    public FilterSet(List<T> values) {
        this.filteredValues.addAll(values);
    }

    @Override
    public boolean isFilterValue(T value) {
        return filteredValues.contains(value);
    }

    @Override
    public boolean isType(Class<?> type) {
        return !filteredValues.isEmpty()
                && filteredValues.iterator().next().getClass().isAssignableFrom(type);
    }

    @Override
    public boolean containsAll(List<T> values) {
        return filteredValues.containsAll(values);
    }

    @Override
    public boolean containsAny(List<T> values) {
        return filteredValues.stream().anyMatch(values::contains);
    }

    @Override
    public Set<T> getDataSet() {
        return filteredValues;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FilterSet<?> otherFilter) {
            if (otherFilter.filteredValues.isEmpty() && this.filteredValues.isEmpty()) {
                return true;
            }

            for (T value : filteredValues) {
                if (otherFilter.filteredValues.contains(value)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 1;

        for (T value : filteredValues) {
            hash += value.hashCode();
        }

        return 31 * hash;
    }
}
