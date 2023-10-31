package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.RawPacketView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Immutable
public class RawPacketsCollectionView {

    private final List<RawPacketView> packets;

    public RawPacketsCollectionView() {
        this.packets = new ArrayList<>();
    }

}
