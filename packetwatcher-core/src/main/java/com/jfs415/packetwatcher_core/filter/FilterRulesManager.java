package com.jfs415.packetwatcher_core.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.TcpPacket.TcpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

@Component
public class FilterRulesManager implements PacketListener {

	private static final String FILENAME = "packetwatcher-core/filter-rules.yml";
	private static final Logger logger = LoggerFactory.getLogger(FilterRulesManager.class);

	private final EnumMap<FilterRule, FilterConfigurationManager> configuration = new EnumMap<>(FilterRule.class);
	private final FilterConfigurationManager filterConfigurationManager;
	private final RangedFilter<String> localNetRange;

	@Value("${packetwatcher-core.local-ip-range-start}")
	private String localIpRangeStart;

	@Value("${packetwatcher-core.local-ip-range-end}")
	private String localIpRangeEnd;

	@Autowired
	public FilterRulesManager(FilterConfigurationManager filterConfigurationManager) {
		this.localNetRange = new RangedFilter<>(localIpRangeStart, localIpRangeEnd, new IpComparator());
		this.filterConfigurationManager = filterConfigurationManager;

		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void destroy() {
		try {
			shutdown();
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("Encountered an exception shutting down FilterRulesManager");
		}
	}

	@Override
	public void gotPacket(PcapPacket packet) {
		System.out.println("GOT PACKET");
		
		if (packet.getPacket() instanceof EthernetPacket && isTcpPacket(packet)) {
			TcpHeader tcpHeader = ((TcpPacket) packet.getPacket().getPayload().getPayload()).getHeader();

			configuration.forEach((k, v) -> {
				switch (k) {
					case INGRESS:
						processIngressPacket(v, packet, tcpHeader);
					case EGRESS:
						processEgressPacket(v, packet, tcpHeader);
					default:
						throw new UnknownFilterRuleException();
				}
			});
		}
	}

	private void processIngressPacket(FilterConfigurationManager filterConfigurationManager, PcapPacket pcapPacket, TcpHeader tcpHeader) {
		IpV4Header ipv4Header = getIpv4Header(pcapPacket);
		String ipToTest = getDestinationIp(ipv4Header);
		Timestamp timestamp = new Timestamp(pcapPacket.getTimestamp().getEpochSecond());

		if (ipv4Header != null && ipToTest.isBlank() && isIpInLocalRange(ipToTest)) {
			filterConfigurationManager.checkFilterConfiguration(timestamp, ipv4Header, tcpHeader);
		}
	}

	private void processEgressPacket(FilterConfigurationManager filterConfigurationManager, PcapPacket pcapPacket, TcpHeader tcpHeader) {
		IpV4Header ipv4Header = getIpv4Header(pcapPacket);
		String ipToTest = getDestinationIp(ipv4Header);
		Timestamp timestamp = new Timestamp(pcapPacket.getTimestamp().getEpochSecond());

		if (ipv4Header != null && ipToTest.isBlank() && !isIpInLocalRange(ipToTest)) {
			filterConfigurationManager.checkFilterConfiguration(timestamp, ipv4Header, tcpHeader);
		}
	}

	private IpV4Header getIpv4Header(PcapPacket pcapPacket) {
		if (pcapPacket.getPacket() instanceof EthernetPacket && isTcpPacket(pcapPacket)) {
			EthernetPacket eth = (EthernetPacket) pcapPacket.getPacket();
			return ((IpV4Header) eth.getPayload().getHeader());
		}

		return null;
	}

	private String getDestinationIp(IpV4Header ipv4Header) {
		if (ipv4Header != null) {
			return ipv4Header.getDstAddr().getHostAddress();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public void load() throws IOException {
		InputStream inputStream = new FileInputStream(FILENAME);

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

	public void shutdown() throws IOException {
		write();
	}

	public void write() throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory().enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR));
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); //Allow access to private fields
		mapper.writeValue(new File(FILENAME), configuration);
	}

}
