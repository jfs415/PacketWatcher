package com.jfs415.packetwatcher_api.views;

import java.sql.Timestamp;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.model.user.LockedUserHistory;

@Immutable
public final class LockedUserHistoryView {

	private final String username;
	private final Timestamp firstLocked;
	private final Timestamp lastLocked;
	private final int numberOfTimesLocked;

	public LockedUserHistoryView(LockedUserHistory lockedUserHistory) {
		this.username = lockedUserHistory.getUsername();
		this.firstLocked = lockedUserHistory.getFirstLocked();
		this.lastLocked = lockedUserHistory.getLastLocked();
		this.numberOfTimesLocked = lockedUserHistory.getNumberOfTimesLocked();
	}

	public LockedUserHistoryView(String username, Timestamp firstLocked, Timestamp lastLocked, int numberOfTimesLocked) {
		this.username = username;
		this.firstLocked = firstLocked;
		this.lastLocked = lastLocked;
		this.numberOfTimesLocked = numberOfTimesLocked;
	}

	public String getUsername() {
		return username;
	}

	public Timestamp getFirstLocked() {
		return firstLocked;
	}

	public Timestamp getLastLocked() {
		return lastLocked;
	}

	public int getNumberOfTimesLocked() {
		return numberOfTimesLocked;
	}

}
