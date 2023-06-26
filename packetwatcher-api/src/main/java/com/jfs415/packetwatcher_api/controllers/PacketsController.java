package com.jfs415.packetwatcher_api.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfs415.packetwatcher_api.views.collections.CapturedPacketsCollectionView;
import com.jfs415.packetwatcher_api.views.FlaggedPacketView;
import com.jfs415.packetwatcher_core.model.services.PacketService;

@RestController
public class PacketsController {

	private final PacketService packetService;

	@Autowired
	public PacketsController(PacketService packetService) {
		this.packetService = packetService;
	}

	@GetMapping("/packets")
	public ResponseEntity<?> getDefaultCapturedPacketsView() {
		return ResponseEntity.ok(new CapturedPacketsCollectionView(packetService.getLast30FlaggedPacketRecords().stream().map(FlaggedPacketView::new).collect(Collectors.toList())));
	}

}
