package com.jfs415.packetwatcher_api.views;

public class SystemSettingView {

	private final String settingKey;
	private final String settingValue;

	public SystemSettingView(String settingKey, String settingValue) {
		this.settingKey = settingKey;
		this.settingValue = settingValue;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public String getSettingValue() {
		return settingValue;
	}

}
