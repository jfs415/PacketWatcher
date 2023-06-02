package com.jfs415.packetwatcher_core;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.util.NifSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_core.filter.FilterRulesManager;

@Component
public class PacketCaptureThread extends Thread {

	private static final int SNAPLEN = 65536; //[bytes]
	private static final int READ_TIMEOUT = 10; //In ms
	private static final String HOST_ADDR = "192.168"; //TODO: Refactor this, should be configurable

	private final Logger logger = LoggerFactory.getLogger(PacketCaptureThread.class);

	private final FilterRulesManager filterRulesManager;
	private final PacketWatcherCore packetWatcherCore;

	private PcapHandle handle = null;

	@Autowired
	public PacketCaptureThread(FilterRulesManager filterRulesManager, PacketWatcherCore packetWatcherCore) {
		this.filterRulesManager = filterRulesManager;
		this.packetWatcherCore = packetWatcherCore;
		
		createLiveView();
	}

	@Override
	public void run() {
		createLiveView();
	}

	private void createLiveView() {
		PcapNetworkInterface nif;

		try {
			nif = choosePcapNetworkInterface();
			handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
		} catch (PcapNativeException | IOException e) {
			e.printStackTrace();
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

	public void shutdown() {
		if (handle != null) {
			try {
				handle.breakLoop();
			} catch (NotOpenException ignored) {
			}
		}

		interrupt(); //Cleanup thread
		logger.debug("PacketCaptureThread stopped");
	}

	private void startLoop(@NotNull PcapHandle handle) {
		try {
			handle.loop(-1, filterRulesManager);
		} catch (NotOpenException e) {
			e.printStackTrace();
			packetWatcherCore.fail("Packet Handler is not open");
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			packetWatcherCore.fail("Live packet capturing interrupted");
		} catch (PcapNativeException pne) {
			pne.printStackTrace();
			packetWatcherCore.fail("Encountered an exception opening a live capture view");
		}
	}

	@PreDestroy
	public void onShutdown() {
		shutdown();
	}

}
