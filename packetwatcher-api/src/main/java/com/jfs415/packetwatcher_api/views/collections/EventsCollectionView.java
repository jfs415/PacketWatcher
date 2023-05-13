package com.jfs415.packetwatcher_api.views.collections;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.views.EventView;

@Immutable
public final class EventsCollectionView {

	private final List<EventView> events;

	public EventsCollectionView() {
		this.events = new ArrayList<>();
	}

	public EventsCollectionView(List<EventView> events) {
		this.events = events;
	}

	public List<EventView> getEvents() {
		return events;
	}

}
