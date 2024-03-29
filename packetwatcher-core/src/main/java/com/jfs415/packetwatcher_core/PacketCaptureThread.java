package com.jfs415.packetwatcher_core;

import com.jfs415.packetwatcher_core.filter.FilterRulesManager;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.validation.constraints.NotNull;
import org.pcap4j.core.*;
import org.pcap4j.util.NifSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PacketCaptureThread implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PacketCaptureThread.class);

    private static final int SNAPLEN = 65536; // [bytes]
    private static final int READ_TIMEOUT = 10; // In ms
    private static final String HOST_ADDR = "192.168"; // TODO: Refactor this, should be configurable

    private final FilterRulesManager filterRulesManager;
    private final PacketWatcherCore packetWatcherCore;

    private PcapHandle handle = null;

    @Autowired
    public PacketCaptureThread(FilterRulesManager filterRulesManager, PacketWatcherCore packetWatcherCore) {
        this.filterRulesManager = filterRulesManager;
        this.packetWatcherCore = packetWatcherCore;
    }

    private void createLiveView() {
        PcapNetworkInterface nif;

        try {
            nif = choosePcapNetworkInterface();
            handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        } catch (PcapNativeException | IOException e) {
            logger.error(e.getMessage(), e);
            packetWatcherCore.fail("Encountered an exception creating live view, application is exiting");
        }

        Objects.requireNonNull(handle, "Handle was somehow null!");
        startLoop(handle);
    }

    private PcapNetworkInterface choosePcapNetworkInterface() throws IOException, PcapNativeException {
        PcapNetworkInterface nif = searchForLocalInterface(Pcaps.findAllDevs());
        return nif == null ? new NifSelector().selectNetworkInterface() : nif;
    }

    private PcapNetworkInterface searchForLocalInterface(List<PcapNetworkInterface> interfaces) {
        for (PcapNetworkInterface nif : interfaces) {
            for (PcapAddress addr : nif.getAddresses()) {
                if (addr.getAddress().getHostAddress().length() >= HOST_ADDR.length()) {
                    String hostAddSub = addr.getAddress().getHostAddress().substring(0, HOST_ADDR.length());
                    if (hostAddSub.equalsIgnoreCase(HOST_ADDR)) {
                        return nif;
                    }
                }
            }
        }
        return null;
    }

    private void startLoop(@NotNull PcapHandle handle) {
        CompletableFuture.runAsync(() -> {
            try {
                handle.loop(-1, filterRulesManager);
            } catch (NotOpenException e) {
                logger.error(e.getMessage(), e);
                packetWatcherCore.fail("Packet Handler is not open");
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // "InterruptedException" and "ThreadDeath" should not be ignored -
                // java:S2142
                logger.error(ie.getMessage(), ie);
                packetWatcherCore.fail("Live packet capturing interrupted");
            } catch (PcapNativeException pne) {
                logger.error(pne.getMessage(), pne);
                packetWatcherCore.fail("Encountered an exception opening a live capture view");
            }
        });
    }

    @Override
    public void run(String... args) {
        createLiveView();
    }
}
