package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.EventView;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public final class EventsCollectionView {

    private final List<EventView> events;

    public EventsCollectionView() {
        this.events = new ArrayList<>();
    }
}
