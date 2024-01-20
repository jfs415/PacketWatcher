package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.RawPacketView;
import java.util.ArrayList;
import java.util.List;

public record RawPacketsCollectionView(List<RawPacketView> packets) {
    public RawPacketsCollectionView {
        packets = packets == null ? new ArrayList<>() : packets;
    }
}
