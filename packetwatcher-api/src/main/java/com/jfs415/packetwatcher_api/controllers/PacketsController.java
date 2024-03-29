package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import com.jfs415.packetwatcher_api.model.services.PacketServiceImpl;
import com.jfs415.packetwatcher_api.model.services.inf.PacketService;
import com.jfs415.packetwatcher_api.views.collections.RawPacketsCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacketsController {

    private final PacketService packetService;

    @Autowired
    public PacketsController(PacketServiceImpl packetService) {
        this.packetService = packetService;
    }

    @GetMapping("/packets")
    public ResponseEntity<RawPacketsCollectionView> getDefaultCapturedPacketsView() {
        return ResponseEntity.ok(new RawPacketsCollectionView(packetService.getLast30FlaggedPacketRecords().stream()
                .map(RawPacketRecord::toRawPacketRecordView)
                .toList()));
    }
}
