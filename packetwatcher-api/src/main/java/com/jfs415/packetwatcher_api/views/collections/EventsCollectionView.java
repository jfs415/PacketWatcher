package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.EventView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Immutable
public final class EventsCollectionView {

    private final List<EventView> events;

    public EventsCollectionView() {
        this.events = new ArrayList<>();
    }

}
