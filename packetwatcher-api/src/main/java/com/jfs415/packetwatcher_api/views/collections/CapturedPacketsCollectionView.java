package com.jfs415.packetwatcher_api.views.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.views.FlaggedPacketView;

@Immutable
public class CapturedPacketsCollectionView implements Serializable {

	private final List<FlaggedPacketView> packets;

	public CapturedPacketsCollectionView() {
		this.packets = new ArrayList<>();
	}

	public CapturedPacketsCollectionView(List<FlaggedPacketView> packets) {
		this.packets = packets;
	}

	public List<FlaggedPacketView> getPackets() {
		return packets;
	}

}
