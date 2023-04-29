package com.jfs415.packetwatcher_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.auth.AuthenticationRequest;
import com.jfs415.packetwatcher_api.views.SystemSettingView;
import com.jfs415.packetwatcher_api.views.collections.SystemSettingsCollectionView;

@RestController
public class SystemController {

	@GetMapping(value = "/system/settings", produces = "application/json")
	public ResponseEntity<?> getSystemSettingsView() {
		try {
			SystemSettingsCollectionView collection = PacketWatcherApi.getPropertiesManager().toCollectionView();
			return isValidSystemSettingsCollection(collection) ? ResponseEntity.ok(collection) : ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/system/settings/update", produces = "application/json")
	public ResponseEntity<?> updateSystemSettings(@RequestBody AuthenticationRequest request, List<SystemSettingView> updates) {
		try {
			if (request == null) {
				return ResponseEntity.badRequest().build();
			}

			PacketWatcherApi.getPropertiesManager().updateSystemSettingsFromViews(updates);
			SystemSettingsCollectionView collection = PacketWatcherApi.getPropertiesManager().toCollectionView();

			return isValidSystemSettingsCollection(collection) ? ResponseEntity.ok(collection) : ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	private boolean isValidSystemSettingsCollection(SystemSettingsCollectionView collection) {
		return collection != null && collection.getSettings() != null && !collection.getSettings().isEmpty();
	}

}
