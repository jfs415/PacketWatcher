package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;

public interface EventService {

    void save(EventMappedSuperclass event) throws InvalidEventArgumentException;

    EventsCollectionView getEventsByTypeAndIpAddress(Class<?> eventType, String ipAddress);

    EventsCollectionView getEventsByTypeAndIpAddressWithTimeframe(
            Class<?> eventType, String ipAddress, SearchTimeframe timeframe);

    EventsCollectionView getEventsByTypeAndIpAddressAndUsername(Class<?> eventType, String ipAddress, String username);

    EventsCollectionView getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
            Class<?> eventType, String ipAddress, String username, SearchTimeframe timeframe);

    EventsCollectionView getEventsByTypeAndUsernameWithTimeframe(
            Class<?> eventType, String username, SearchTimeframe timeframe);

    EventsCollectionView getEventsByTypeAndUsername(Class<?> eventType, String username);

    EventsCollectionView getEventsByTypeWithTimeframe(Class<?> eventType, SearchTimeframe timeframe);

    EventsCollectionView getEventsByType(Class<?> eventType);
}
