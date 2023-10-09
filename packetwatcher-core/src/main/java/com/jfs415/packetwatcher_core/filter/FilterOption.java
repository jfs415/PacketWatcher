package com.jfs415.packetwatcher_core.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <P>This enum denotes the different filtering options for each filter configuration.
 * FilterSets have a 1:1 relationship with their filter option, meaning that 1
 * FilterOption can only have 1 FilterSet associated with them.</P>
 *
 * <P>Ranged Filters however have a 1:N relationship with their filter options. Meaning 1 FilterOption
 * could have many RangedFilters. This is because the FilterSet is meant to encompass all
 * data that could possibly be associated with their FilterOption. With RangedFilters though,
 * there could be multiple disjointed ranges i.e 1-5, 7-8, etc. This necessitates the need
 * for their to be a 1:N relationship between these objects.</P>
 */
@AllArgsConstructor
@Getter
public enum FilterOption {
    COUNTRY(FilterSet.class),
    DESTINATION_IP(FilterSet.class),
    SOURCE_IP(FilterSet.class),
    DESTINATION_HOST(FilterSet.class),
    SOURCE_HOST(FilterSet.class),
    DESTINATION_IP_RANGE(RangedFilter.class),
    SOURCE_IP_RANGE(RangedFilter.class),
    EXCLUDED_HOSTS(FilterSet.class),
    EXCLUDED_HOSTS_RANGE(RangedFilter.class),
    DESTINATION_PORT(FilterSet.class),
    SOURCE_PORT(FilterSet.class),
    DESTINATION_PORT_RANGE(RangedFilter.class),
    SOURCE_PORT_RANGE(RangedFilter.class);

    @SuppressWarnings("rawtypes")
    private final Class<? extends IFilter> filterType;
    
}
