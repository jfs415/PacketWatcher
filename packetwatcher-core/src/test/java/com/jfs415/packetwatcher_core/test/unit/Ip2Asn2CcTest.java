package com.jfs415.packetwatcher_core.test.unit;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.axlabs.ip2asn2cc.Ip2Asn2Cc;
import com.axlabs.ip2asn2cc.exception.RIRNotDownloadedException;

public class Ip2Asn2CcTest {

	private Ip2Asn2Cc ipLookupUtility = null;

	@Test
	public void testIp2Cc() {
		assert getIpLookupUtility().getRIRCountryCode("8.8.8.8").equals("US");
		assert getIpLookupUtility().getRIRCountryCode("221.192.199.49").equals(FlaggedCountries.CHINA.getCountryCode());
		assert getIpLookupUtility().getRIRCountryCode("80.92.32.0").equals(FlaggedCountries.RUSSIA.getCountryCode());
		assert getIpLookupUtility().getRIRCountryCode("37.212.59.152").equals(FlaggedCountries.BELARUS.getCountryCode());
		assert getIpLookupUtility().getRIRCountryCode("190.15.150.165").equals(FlaggedCountries.CUBA.getCountryCode());
		assert getIpLookupUtility().getRIRCountryCode("5.134.128.0").equals(FlaggedCountries.IRAN.getCountryCode());
	}

	private Ip2Asn2Cc getIpLookupUtility() {
		if (ipLookupUtility == null) {
			ipLookupUtility = createIp2AsnInstance();
		}

		Objects.requireNonNull(ipLookupUtility, "Unable to create ipLookupUtility");
		return ipLookupUtility;
	}

	private Ip2Asn2Cc createIp2AsnInstance() {
		try {
			List<String> flaggedCountries = EnumSet.allOf(FlaggedCountries.class).stream().map(FlaggedCountries::getCountryCode).collect(Collectors.toList());
			flaggedCountries.add("US");
			ipLookupUtility = new Ip2Asn2Cc(flaggedCountries);
		} catch (RIRNotDownloadedException e) {
			e.printStackTrace();
		}

		return ipLookupUtility;
	}

	private enum FlaggedCountries {

		CHINA("CN"),
		RUSSIA("RU"),
		BELARUS("BY"),
		IRAN("IR"),
		CUBA("CU");

		private final String countryCode;

		FlaggedCountries(String countryCode) {
			this.countryCode = countryCode;
		}

		public String getCountryCode() {
			return countryCode;
		}

	}

}
