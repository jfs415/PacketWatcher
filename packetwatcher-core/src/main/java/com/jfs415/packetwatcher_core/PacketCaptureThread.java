package com.jfs415.packetwatcher_core;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.util.NifSelector;

public class PacketCaptureThread extends Thread {

	private static final int SNAPLEN = 65536; //[bytes]
	private static final int READ_TIMEOUT = 10; //In ms

	private PcapHandle handle = null;

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
			PacketWatcherCore.fail("Encountered an exception creating live view, application is exiting");
		}

		Objects.requireNonNull(handle, "Handle Was somehow null!");
		startLoop(handle);
	}

	private PcapNetworkInterface choosePcapNetworkInterface() throws IOException, PcapNativeException {
		PcapNetworkInterface nif = searchForLocalInterface(Pcaps.findAllDevs());
		return nif == null ? new NifSelector().selectNetworkInterface() : nif;
	}

	private PcapNetworkInterface searchForLocalInterface(List<PcapNetworkInterface> interfaces) {
		for (PcapNetworkInterface nif : interfaces) {
			for (PcapAddress addr : nif.getAddresses()) {
				if (addr.getAddress().getHostAddress().length() >= PacketWatcherCore.getHostAddr().length()) {
					String hostAddSub = addr.getAddress().getHostAddress().substring(0, PacketWatcherCore.getHostAddr().length());
					if (hostAddSub.equalsIgnoreCase(PacketWatcherCore.getHostAddr())) {
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
		PacketWatcherCore.debug("PacketCaptureThread stopped");
	}

	private void startLoop(@NotNull PcapHandle handle) {
		CorePacketListener listener = new CorePacketListener();
		try {
			handle.loop(-1, listener);
		} catch (NotOpenException e) {
			e.printStackTrace();
			PacketWatcherCore.fail("Packet Handler is not open");
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			PacketWatcherCore.fail("Live packet capturing interrupted");
		} catch (PcapNativeException pne) {
			pne.printStackTrace();
			PacketWatcherCore.fail("Encountered an exception opening a live capture view");
		}
	}

}
