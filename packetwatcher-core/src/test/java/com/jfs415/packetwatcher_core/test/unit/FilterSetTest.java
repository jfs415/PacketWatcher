package com.jfs415.packetwatcher_core.test.unit;

import com.jfs415.packetwatcher_core.filter.FilterSet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

class FilterSetTest {

    private final List<String> stringList = List.of("CN", "RU", "BY", "IR", "CU");
    private final List<Integer> intList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private final List<Double> doubleList = List.of(11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0);
    private final List<Float> floatList = List.of(100.0000f, 200.0000f, 300.0000f, 400.0000f, 500.0000f);
    private final FilterSet<String> stringFilterSet = new FilterSet<>(stringList);
    private final FilterSet<Integer> intFilterSet = new FilterSet<>(intList);
    private final FilterSet<Double> doubleFilterSet = new FilterSet<>(doubleList);
    private final FilterSet<Float> floatFilterSet = new FilterSet<>(floatList);

    @Test
    void validateStringFilterSets() {
        assert !stringList.isEmpty();
        assert !intList.isEmpty();

        assert stringFilterSet.getDataSet() != null;
        assert !stringFilterSet.getDataSet().isEmpty();

        assert stringFilterSet.containsAll(stringList);
        assert stringFilterSet.containsAny(stringList);
        assert stringFilterSet.isFilterValue(stringList.get(0));

        assert !stringFilterSet.containsAll(intList.stream().map(String::valueOf).collect(Collectors.toList()));
        assert !stringFilterSet.containsAny(intList.stream().map(String::valueOf).collect(Collectors.toList()));
        assert !stringFilterSet.isFilterValue(intList.get(0).toString());
    }

    @Test
    void validateIntTests() {
        assert !intList.isEmpty();
        assert !doubleList.isEmpty();

        assert intFilterSet.getDataSet() != null;
        assert !intFilterSet.getDataSet().isEmpty();

        assert intFilterSet.containsAll(intList);
        assert intFilterSet.containsAny(intList);
        assert intFilterSet.isFilterValue(intList.get(0));

        assert !intFilterSet.containsAll(doubleList.stream().map(Double::intValue).collect(Collectors.toList()));
        assert !intFilterSet.containsAny(doubleList.stream().map(Double::intValue).collect(Collectors.toList()));
        assert !intFilterSet.isFilterValue(doubleList.get(0).intValue());
    }

    @Test
    void validateDoubleFilterSets() {
        assert !doubleList.isEmpty();
        assert !floatList.isEmpty();

        assert doubleFilterSet.getDataSet() != null;
        assert !doubleFilterSet.getDataSet().isEmpty();

        assert doubleFilterSet.containsAll(doubleList);
        assert doubleFilterSet.containsAny(doubleList);
        assert doubleFilterSet.isFilterValue(doubleList.get(0));

        assert !doubleFilterSet.containsAll(floatList.stream().map(Float::doubleValue).collect(Collectors.toList()));
        assert !doubleFilterSet.containsAny(floatList.stream().map(Float::doubleValue).collect(Collectors.toList()));
        assert !doubleFilterSet.isFilterValue(floatList.get(0).doubleValue());
    }

    @Test
    void validateFloatFilterSets() {
        assert !floatList.isEmpty();
        assert !intList.isEmpty();

        assert floatFilterSet.getDataSet() != null;
        assert !floatFilterSet.getDataSet().isEmpty();

        assert floatFilterSet.containsAll(floatList);
        assert floatFilterSet.containsAny(floatList);
        assert floatFilterSet.isFilterValue(floatList.get(0));

        assert !floatFilterSet.containsAll(intList.stream().map(Float::valueOf).collect(Collectors.toList()));
        assert !floatFilterSet.containsAny(intList.stream().map(Float::valueOf).collect(Collectors.toList()));
        assert !floatFilterSet.isFilterValue(Float.valueOf(intList.get(0)));
    }

}
