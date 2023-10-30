package com.jfs415.packetwatcher_core.filter;

import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.IpV6Packet.IpV6Header;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.TcpPacket.TcpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class FilterRulesManager implements PacketListener {

    private static final Logger logger = LoggerFactory.getLogger(FilterRulesManager.class);

    private final EnumMap<FilterRule, FilterConfigurationManager> configuration = new EnumMap<>(FilterRule.class);
    private final FilterConfigurationManager filterConfigurationManager;
    private final RangedFilter<String> localNetRange;

    private final FilterYamlConfiguration filterYamlConfiguration;

    @Autowired
    public FilterRulesManager(FilterConfigurationManager filterConfigurationManager, FilterYamlConfiguration filterYamlConfiguration) {
        this.filterYamlConfiguration = filterYamlConfiguration;
        this.filterConfigurationManager = filterConfigurationManager;
        this.localNetRange = new RangedFilter<>(filterYamlConfiguration.getLocalIpRangeStart(), filterYamlConfiguration.getLocalIpRangeEnd(), new IpComparator());

        try {
            load();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void gotPacket(PcapPacket packet) {
        if (packet.getPacket() instanceof EthernetPacket && isTcpPacket(packet)) {
            TcpHeader tcpHeader = ((TcpPacket) packet.getPacket().getPayload().getPayload()).getHeader();

            configuration.forEach((k, v) -> {
                switch (k) {
                    case INGRESS:
                        processIngressPacket(v, packet, tcpHeader);
                        break;
                    case EGRESS:
                        processEgressPacket(v, packet, tcpHeader);
                        break;
                    default:
                        throw new UnknownFilterRuleException();
                }
            });
        }
    }

    private void processIngressPacket(FilterConfigurationManager filterConfigurationManager, PcapPacket pcapPacket, TcpHeader tcpHeader) {
        if (pcapPacket.getPacket() instanceof EthernetPacket ethernetPacket && isTcpPacket(pcapPacket)) {
            getDestinationIp(ethernetPacket).ifPresent(ipToTest -> {
                Timestamp timestamp = new Timestamp(pcapPacket.getTimestamp().getEpochSecond());

                if (!ipToTest.isBlank() && !isIpInLocalRange(ipToTest)) {
                    filterConfigurationManager.checkFilterConfiguration(timestamp, ethernetPacket, tcpHeader);
                }
            });
        }
    }

    private void processEgressPacket(FilterConfigurationManager filterConfigurationManager, PcapPacket pcapPacket, TcpHeader tcpHeader) {
        if (pcapPacket.getPacket() instanceof EthernetPacket ethernetPacket && isTcpPacket(pcapPacket)) {
            getDestinationIp(ethernetPacket).ifPresent(ipToTest -> {
                Timestamp timestamp = new Timestamp(pcapPacket.getTimestamp().getEpochSecond());

                if (!ipToTest.isBlank() && !isIpInLocalRange(ipToTest)) {
                    filterConfigurationManager.checkFilterConfiguration(timestamp, ethernetPacket, tcpHeader);
                }
            });
        }
    }

    private Optional<String> getDestinationIp(EthernetPacket ethernetPacket) {
        if (ethernetPacket.getPayload().getHeader() instanceof IpV4Header ipV4Header) {
            return Optional.of(ipV4Header.getDstAddr().getHostAddress());
        } else if (ethernetPacket.getPayload().getHeader() instanceof IpV6Header ipV6Header) {
            return Optional.of(ipV6Header.getDstAddr().getHostAddress());
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public void load() throws IOException {
        InputStream inputStream = new FileInputStream(filterYamlConfiguration.getFilterRulesPath());

        Yaml yaml = new Yaml();
        Map<String, Object> fileData = yaml.load(inputStream); //Map of <FilterRule, FilterConfiguration>

        if (fileData == null || fileData.isEmpty()) {
            throw new FilterException("Filter-Rules.yml is empty!");
        }

        fileData.forEach((rule, filterConfigurations) -> {
            if (!LinkedHashMap.class.isAssignableFrom(filterConfigurations.getClass())) {
                throw new FilterException("Filter-Rules option data is not in the proper format!");
            }

            FilterRule filterRule = FilterRule.valueOf(rule);
            LinkedHashMap<String, Object> configurationManagerData = (LinkedHashMap<String, Object>) filterConfigurations; //Map of <FilterConfiguration, FilterOptions>
            filterConfigurationManager.load(configurationManagerData);

            configuration.put(filterRule, filterConfigurationManager);
        });

        inputStream.close();
    }

    protected boolean isTcpPacket(PcapPacket packet) {
        return packet.getPacket().getPayload() != null && packet.getPacket().getPayload().getPayload() != null && (packet.getPacket().getPayload().getPayload() instanceof TcpPacket);
    }

    public boolean isIpInLocalRange(@Nullable String ipToTest) {
        return localNetRange.isFilterValue(ipToTest);
    }

}
