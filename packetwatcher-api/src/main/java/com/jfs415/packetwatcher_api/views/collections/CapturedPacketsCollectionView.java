package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.FlaggedPacketView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Immutable
public class CapturedPacketsCollectionView {

    private final List<FlaggedPacketView> packets;

    public CapturedPacketsCollectionView() {
        this.packets = new ArrayList<>();
    }

}
