package com.jfs415.packetwatcher_core.filter;

import java.util.List;

/**
 * This is the top level configuration for ingress and egress filtering.
 * These enums hold the different options available for each configuration.
 */
public enum FilterConfiguration {

	COUNTRY_ONLY(List.of(FilterOption.COUNTRY)),
	COUNTRY_AND_SOURCE_HOST(List.of(FilterOption.COUNTRY, FilterOption.SOURCE_HOST)),
	PORT_RANGE_ONLY(List.of(FilterOption.DESTINATION_PORT_RANGE, FilterOption.SOURCE_PORT_RANGE)),
	RANGED_ONLY(List.of(FilterOption.DESTINATION_IP_RANGE, FilterOption.SOURCE_IP_RANGE)),
	IP_ONLY(List.of(FilterOption.DESTINATION_IP, FilterOption.SOURCE_IP)),
	HOST_ONLY(List.of(FilterOption.DESTINATION_HOST, FilterOption.SOURCE_HOST, FilterOption.EXCLUDED_HOSTS));

	private final List<FilterOption> options;

	FilterConfiguration(List<FilterOption> options) {
		this.options = options;
	}

	public List<FilterOption> getOptions() {
		return options;
	}
}
