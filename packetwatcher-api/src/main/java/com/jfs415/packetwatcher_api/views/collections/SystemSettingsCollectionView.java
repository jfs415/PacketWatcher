package com.jfs415.packetwatcher_api.views.collections;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.views.SystemSettingView;

@Immutable
public class SystemSettingsCollectionView {

	private final List<SystemSettingView> settings;

	public SystemSettingsCollectionView() {
		this.settings = new ArrayList<>();
	}

	public SystemSettingsCollectionView(List<SystemSettingView> settings) {
		this.settings = settings;
	}

	public List<SystemSettingView> getSettings() {
		return settings;
	}

}
