package unit;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.testng.Assert;

import com.axlabs.ip2asn2cc.Ip2Asn2Cc;
import com.axlabs.ip2asn2cc.exception.RIRNotDownloadedException;
import com.jfs415.packetwatcher_core.FlaggedCountries;
import com.jfs415.packetwatcher_core.PacketWatcherCore;

public class Ip2Asn2CcTest {

	private Ip2Asn2Cc ipLookupUtility = null;

	@Test
	public void testIp2Cc() {
		Assert.assertEquals(getIpLookupUtility().getRIRCountryCode("8.8.8.8"), "US");
		Assert.assertEquals(getIpLookupUtility().getRIRCountryCode("221.192.199.49"), FlaggedCountries.CHINA.getCountryCode());
		Assert.assertEquals(getIpLookupUtility().getRIRCountryCode("80.92.32.0"), FlaggedCountries.RUSSIA.getCountryCode());
		Assert.assertEquals(getIpLookupUtility().getRIRCountryCode("37.212.59.152"), FlaggedCountries.BELARUS.getCountryCode());
		Assert.assertEquals(getIpLookupUtility().getRIRCountryCode("190.15.150.165"), FlaggedCountries.CUBA.getCountryCode());
		Assert.assertEquals(getIpLookupUtility().getRIRCountryCode("5.134.128.0"), FlaggedCountries.IRAN.getCountryCode());
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
			PacketWatcherCore.fail("Encountered a fatal exception when creating ipLookupUtility");
		}
		return ipLookupUtility;
	}

}
