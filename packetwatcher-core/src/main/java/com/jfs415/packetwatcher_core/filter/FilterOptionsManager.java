package com.jfs415.packetwatcher_core.filter;

import com.axlabs.ip2asn2cc.Ip2Asn2Cc;
import com.axlabs.ip2asn2cc.exception.RIRNotDownloadedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.PacketRecordKey;
import com.jfs415.packetwatcher_core.model.services.PacketServiceImpl;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.IpV6Packet.IpV6Header;
import org.pcap4j.packet.TcpPacket.TcpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

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

    public void load(Map<String, Object> configOptionData) {
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
                optionData.forEach(value -> processRangedFilter(option, (LinkedHashMap<String, Object>) value));
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

    private Optional<PacketHeaderInfo> parseHeader(EthernetPacket ethernetPacket) {
        if (ethernetPacket.getPayload().getHeader() instanceof IpV4Header ipV4Header) {
            logger.debug("Processing a IPv4 Packet");
            return parseIpv4Header(ipV4Header);
        } else if (ethernetPacket.getPayload().getHeader() instanceof IpV6Header ipV6Header) {
            logger.debug("Processing a IPv6 Packet");
            return parseIpv6Header(ipV6Header);
        }

        return Optional.empty();
    }

    private Optional<PacketHeaderInfo> parseIpv4Header(IpV4Header ipV4Header) {
        return ipV4Header != null && ipV4Header.getDstAddr() != null
                ? Optional.of(new PacketHeaderInfo(ipV4Header.getDstAddr().getHostAddress(),
                ipV4Header.getDstAddr().getHostName(),
                ipV4Header.getSrcAddr().getHostAddress(),
                ipV4Header.getSrcAddr().getHostName()))
                : Optional.empty();
    }

    private Optional<PacketHeaderInfo> parseIpv6Header(IpV6Header ipV6Header) {
        return ipV6Header != null && ipV6Header.getDstAddr() != null
                ? Optional.of(new PacketHeaderInfo(ipV6Header.getDstAddr().getHostAddress(),
                ipV6Header.getDstAddr().getHostName(),
                ipV6Header.getSrcAddr().getHostAddress(),
                ipV6Header.getSrcAddr().getHostName()))
                : Optional.empty();
    }

    protected void checkFilterOptions(Timestamp timestamp, EthernetPacket ethernetPacket, TcpHeader tcpHeader) {
        parseHeader(ethernetPacket).ifPresent(headerInfo -> {
            String destPort = tcpHeader.getDstPort().toString();
            String srcPort = tcpHeader.getSrcPort().toString();

            String countryName = ipLookupUtility != null ? getFlaggedCountry(headerInfo.destIp()) : null;

            PacketData packetData = new PacketData(headerInfo.destIp, headerInfo.destHostName(), destPort, headerInfo.srcHostName(), headerInfo.srcIp(), srcPort, countryName, timestamp);

            filterOptions.forEach((k, v) -> {
                switch (k) {
                    case COUNTRY:
                        processOption(packetData, v, countryName);
                        break;
                    case DESTINATION_HOST:
                        processOption(packetData, v, headerInfo.destHostName());
                        break;
                    case DESTINATION_IP, DESTINATION_IP_RANGE:
                        processOption(packetData, v, headerInfo.destIp());
                        break;
                    case DESTINATION_PORT, DESTINATION_PORT_RANGE:
                        processOption(packetData, v, destPort);
                        break;
                    case SOURCE_HOST:
                        processOption(packetData, v, headerInfo.srcHostName());
                        break;
                    case SOURCE_IP, SOURCE_IP_RANGE:
                        processOption(packetData, v, headerInfo.srcIp());
                        break;
                    case SOURCE_PORT, SOURCE_PORT_RANGE:
                        processOption(packetData, v, srcPort);
                        break;
                    default:
                        logger.debug("Encountered unknown FilterOption"); //TODO: Allow custom filters
                }
            });
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
        PacketRecordKey key = new PacketRecordKey(packetData.timestamp(), packetData.destinationIp(), packetData.destinationPort(), packetData.sourceHost(), packetData.sourcePort());
        packetService.addToSaveQueue(new FlaggedPacketRecord(key, packetData.destinationHost(), packetData.countryName()));
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

            ipLookupUtility = new Ip2Asn2Cc(filterOptions.get(FilterOption.COUNTRY).stream().flatMap(f -> ((FilterSet<String>) f).getDataSet().stream()).toList());
        } catch (RIRNotDownloadedException e) {
            if (!surviveRirException) {
                packetWatcherCore.fail("Encountered a fatal exception when creating ipLookupUtility");
            }
        }
    }

    protected String getFlaggedCountry(String destinationIp) {
        return ipLookupUtility.getRIRCountryCode(destinationIp);
    }

    private record PacketHeaderInfo(String destIp, String destHostName, String srcIp, String srcHostName) {

    }

    private record PacketData(String destinationIp, String destinationHost, String destinationPort, String sourceHost,
                              String sourceIp, String sourcePort, String countryName, Timestamp timestamp) {

    }

}
