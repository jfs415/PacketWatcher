package com.jfs415.packetwatcher_core.test.unit;

import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.factory.PacketFactoryBinderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(3)
class Pcap4jTests {

    private static final String HOST_ADDR = "192.168";
    private static final Logger logger = LoggerFactory.getLogger(Pcap4jTests.class);

    @Test
    void testForPrimaryInterface() {
        try {
            List<PcapNetworkInterface> nifs = Pcaps.findAllDevs();
            System.out.println(nifs.size());
            for (PcapNetworkInterface n : nifs) {
                if (n.isRunning() && n.isUp() && isPrimaryInterface(n.getAddresses())) {
                    logger.info(n.getAddresses().toString());
                }
            }
        } catch (PcapNativeException e) {
            e.printStackTrace();
            logger.info("Pcap exception when trying to run network interface collection test");
        }
    }

    private boolean isPrimaryInterface(List<PcapAddress> addresses) {
        for (PcapAddress addr : addresses) {
            if (addr.getAddress().getHostAddress().length() >= HOST_ADDR.length()) {
                String hostAddSub = addr.getAddress().getHostAddress().substring(0, HOST_ADDR.length());
                if (hostAddSub.equalsIgnoreCase(HOST_ADDR)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    void findBinderClasses() {
        try {
            ServiceLoader<PacketFactoryBinderProvider> loader = ServiceLoader.load(PacketFactoryBinderProvider.class);
            Iterator<PacketFactoryBinderProvider> iter = loader.iterator();
            if (iter.hasNext()) {
                PacketFactoryBinderProvider packetFactoryBinderProvider = iter.next();
                assert packetFactoryBinderProvider != null;

                ProtectionDomain pd = packetFactoryBinderProvider.getClass().getProtectionDomain();
                assert pd != null;
                assert packetFactoryBinderProvider.getBinder() != null;

                logger.info("Succeeded in PacketFactoryBinderProvider.getBinder()");
            } else {
                logger.info(
                        "No PacketFactoryBinder is available. Ensure you have the pcap4j-packetfactory-static-2.0.0-alpha.7-SNAPSHOT.jar as a library!");
            }
        } catch (ServiceConfigurationError e) {
            logger.info(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
