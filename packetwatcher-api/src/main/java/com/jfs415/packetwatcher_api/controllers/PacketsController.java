package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import com.jfs415.packetwatcher_api.model.services.ApiPacketServiceImpl;
import com.jfs415.packetwatcher_api.model.services.inf.ApiPacketService;
import com.jfs415.packetwatcher_api.views.collections.RawPacketsCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacketsController {

    private final ApiPacketService apiPacketService;

    @Autowired
    public PacketsController(ApiPacketServiceImpl packetService) {
        this.apiPacketService = packetService;
    }

    @GetMapping("/packets")
    public ResponseEntity<RawPacketsCollectionView> getDefaultCapturedPacketsView() {
        return ResponseEntity.ok(new RawPacketsCollectionView(apiPacketService.getLast30FlaggedPacketRecords().stream()
                .map(RawPacketRecord::toRawPacketRecordView)
                .toList()));
    }
}
