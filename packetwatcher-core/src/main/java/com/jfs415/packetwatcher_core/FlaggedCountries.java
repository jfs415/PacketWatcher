package com.jfs415.packetwatcher_core;

public enum FlaggedCountries {

	CHINA("CN"),
	RUSSIA("RU"),
	BELARUS("BY"),
	IRAN("IR"),
	CUBA("CU"),;

	private final String countryCode;

	FlaggedCountries(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

}
