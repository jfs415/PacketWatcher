package com.jfs415.packetwatcher_api.views.collections;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;

@Immutable
public class LockedUserHistoryCollectionView {

	private final List<LockedUserHistoryView> lockedUserHistoryViews;

	public LockedUserHistoryCollectionView() {
		this.lockedUserHistoryViews = new ArrayList<>();
	}

	public LockedUserHistoryCollectionView(List<LockedUserHistoryView> lockedUserHistoryViews) {
		this.lockedUserHistoryViews = lockedUserHistoryViews;
	}

	public List<LockedUserHistoryView> getLockedUserHistoryViews() {
		return lockedUserHistoryViews;
	}

}
