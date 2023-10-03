package com.jfs415.packetwatcher_core.filter;

import com.axlabs.ip2asn2cc.Ip2Asn2Cc;
import com.axlabs.ip2asn2cc.exception.RIRNotDownloadedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.PacketRecordKey;
import com.jfs415.packetwatcher_core.model.services.PacketServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.TcpPacket.TcpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@JsonSerialize(using = FilterOptionsDataSerializer.class)
@Component
public class FilterOptionsManager {

    @JsonIgnore
    private final EnumMap<FilterOption, List<IFilter<?>>> filterOptions = new EnumMap<>(FilterOption.class);

    @JsonIgnore
    private final PacketServiceImpl packetService;

    @JsonIgnore
    private final PacketWatcherCore packetWatcherCore;

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(FilterOptionsManager.class);

    @JsonIgnore
    private Ip2Asn2Cc ipLookupUtility;

    @JsonIgnore
    private boolean tracksCountryCode = false;

    @JsonIgnore
    @Value("${packetwatcher-core.survive-rir-exception}")
    private boolean surviveRirException;

    @Autowired
    private FilterOptionsManager(PacketServiceImpl packetService, PacketWatcherCore packetWatcherCore) {
        this.packetService = packetService;
        this.packetWatcherCore = packetWatcherCore;
    }

    protected EnumMap<FilterOption, List<IFilter<?>>> getOptions() { //Protected to provide visibility within package
        return filterOptions;
    }

    private void registerOption(FilterOption option, IFilter<?> filter) {
        tracksCountryCode = option == FilterOption.COUNTRY || tracksCountryCode;

        filterOptions.computeIfPresent(option, (o, f) -> {
            f.add(filter);
            return f;
        });

        filterOptions.computeIfAbsent(option, o -> {
            List<IFilter<?>> newOptionsSet = new ArrayList<>();
            newOptionsSet.add(filter);

            return newOptionsSet;
        });
    }

    public void load(LinkedHashMap<String, Object> configOptionData) {
        configOptionData.forEach((option, data) -> {
            if (List.class.isAssignableFrom(data.getClass())) {
                processOptionData(FilterOption.valueOf(option), data);
            }
        });

        if (tracksCountryCode) {
            setupLookupUtility();
        }
    }

    @SuppressWarnings("unchecked")
    private void processOptionData(FilterOption option, Object data) {
        if (List.class.isAssignableFrom(data.getClass())) {
            ArrayList<?> optionData = (ArrayList<?>) data;

            if (optionData.isEmpty()) {
                throw new FilterException("Filter-Rules option data is empty!");
            }

            if (optionData.get(0) instanceof String) {
                processFilterSet(option, (ArrayList<String>) optionData);
            } else if (LinkedHashMap.class.isAssignableFrom(optionData.get(0).getClass())) {
                optionData.forEach(value -> {
                    processRangedFilter(option, (LinkedHashMap<String, Object>) value);
                });
            } else {
                throw new FilterException("Filter-Rules option data is not in the proper format!");
            }

        }
    }

    private void processFilterSet(FilterOption option, ArrayList<String> data) {
        registerOption(option, new FilterSet<>(data));
    }

    private void processRangedFilter(FilterOption option, LinkedHashMap<String, Object> data) {
        int start = (int) data.get("start");
        int end = (int) data.get("end");

        registerOption(option, new RangedFilter<>(start, end));
    }

    protected void checkFilterOptions(Timestamp timestamp, IpV4Header ipv4Header, TcpHeader tcpHeader) {
        String destIp = ipv4Header.getDstAddr().getHostAddress();
        String destPort = tcpHeader.getDstPort().toString();
        String destHostName = ipv4Header.getDstAddr().getHostName();

        String srcIp = ipv4Header.getSrcAddr().getHostAddress();
        String srcHostName = ipv4Header.getSrcAddr().getHostName();
        String srcPort = tcpHeader.getSrcPort().toString();

        String countryName = ipLookupUtility != null ? getFlaggedCountry(destIp) : null;

        PacketData packetData = new PacketData(destIp, destHostName, destPort, srcHostName, srcIp, srcPort, countryName, timestamp);

        filterOptions.forEach((k, v) -> {
            switch (k) {
                case COUNTRY:
                    processOption(packetData, v, countryName);
                    break;
                case DESTINATION_HOST:
                    processOption(packetData, v, destHostName);
                    break;
                case DESTINATION_IP:
                case DESTINATION_IP_RANGE:
                    processOption(packetData, v, destIp);
                    break;
                case DESTINATION_PORT:
                case DESTINATION_PORT_RANGE:
                    processOption(packetData, v, destPort);
                    break;
                case SOURCE_HOST:
                    processOption(packetData, v, srcHostName);
                    break;
                case SOURCE_IP:
                case SOURCE_IP_RANGE:
                    processOption(packetData, v, srcIp);
                    break;
                case SOURCE_PORT:
                case SOURCE_PORT_RANGE:
                    processOption(packetData, v, srcPort);
                    break;
                default:
                    logger.debug("Encountered unknown FilterOption"); //TODO: Allow custom filters
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void processOption(PacketData packetData, List<IFilter<?>> filterList, String stringToMatch) {
        for (IFilter<?> filter : filterList) {
            if (filter instanceof FilterSet<?>) {
                FilterSet<String> filterSet = (FilterSet<String>) filter;

                if (filterSet.isFilterValue(stringToMatch)) {
                    createPacket(packetData);
                }
            } else if (filter instanceof RangedFilter<?>) {
                RangedFilter<String> rangedFilter = (RangedFilter<String>) filter;

                if (rangedFilter.isFilterValue(stringToMatch)) {
                    createPacket(packetData);
                }
            } else {
                logger.debug("Encountered unknown Filter"); //TODO: Allow custom filters
            }
        }
    }

    private void createPacket(PacketData packetData) {
        PacketRecordKey key = new PacketRecordKey(packetData.getTimestamp(), packetData.getDestinationIp(), packetData.getDestinationPort(), packetData.getSourceHost(), packetData.getSourcePort());
        packetService.addToSaveQueue(new FlaggedPacketRecord(key, packetData.getDestinationHost(), packetData.getCountryName()));
    }

    @SuppressWarnings("unchecked")
    private void setupLookupUtility() {
        if (ipLookupUtility != null) {
            return;
        }

        try {
            List<IFilter<?>> countryList = filterOptions.get(FilterOption.COUNTRY);
            if (countryList == null || countryList.isEmpty()) {
                throw new FilterException("Country filter is present but no country filters are added");
            } else if (!countryList.stream().allMatch(f -> f.isType(String.class))) {
                throw new FilterException("Country filter is configured but non-string country filter was provided!");
            }

            ipLookupUtility = new Ip2Asn2Cc(filterOptions.get(FilterOption.COUNTRY).stream().flatMap(f -> ((FilterSet<String>) f).getDataSet().stream()).collect(Collectors.toList()));
        } catch (RIRNotDownloadedException e) {
            if (!surviveRirException) {
                packetWatcherCore.fail("Encountered a fatal exception when creating ipLookupUtility");
            }
        }
    }

    protected String getFlaggedCountry(String destinationIp) {
        return ipLookupUtility.getRIRCountryCode(destinationIp);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class PacketData {

        private final String destinationIp;
        private final String destinationHost;
        private final String destinationPort;
        private final String sourceHost;
        private final String sourceIp;
        private final String sourcePort;
        private final String countryName;
        private final Timestamp timestamp;

    }

}
