package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.EventView;
import java.util.ArrayList;
import java.util.List;

public record EventsCollectionView(List<EventView> events) {
    public EventsCollectionView {
        events = events == null ? new ArrayList<>() : events;
    }
}
