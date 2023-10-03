package com.jfs415.packetwatcher_core.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.TcpPacket.TcpHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.LinkedHashMap;

@Component
public class FilterConfigurationManager {

    @JsonValue
    private final EnumMap<FilterConfiguration, FilterOptionsManager> configurationOptions = new EnumMap<>(FilterConfiguration.class);

    @JsonIgnore
    private final FilterOptionsManager filterOptionsManager;

    @Autowired
    public FilterConfigurationManager(FilterOptionsManager filterOptionsManager) {
        this.filterOptionsManager = filterOptionsManager;
    }

    protected void checkFilterConfiguration(Timestamp timestamp, IpV4Header ipV4Header, TcpHeader tcpHeader) {
        configurationOptions.forEach((k, v) -> v.checkFilterOptions(timestamp, ipV4Header, tcpHeader));
    }

    @SuppressWarnings("unchecked")
    public void load(LinkedHashMap<String, Object> configManagerData) { //Map of <FilterConfiguration, FilterOptions>
        configManagerData.forEach((config, options) -> {
            if (!LinkedHashMap.class.isAssignableFrom(options.getClass())) {
                throw new FilterException("Filter-Rules configuration data is not in the proper format!");
            }

            FilterConfiguration filterConfiguration = FilterConfiguration.valueOf(config);
            LinkedHashMap<String, Object> configurationOptionData = (LinkedHashMap<String, Object>) options; //Map of <FilterOptions, IFilters> 
            filterOptionsManager.load(configurationOptionData);

            configurationOptions.put(filterConfiguration, filterOptionsManager);
        });
    }

}
