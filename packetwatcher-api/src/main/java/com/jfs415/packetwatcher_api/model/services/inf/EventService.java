package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;
import java.util.Optional;

public interface EventService {

    void save(EventMappedSuperclass event) throws InvalidEventArgumentException;

    Optional<EventsCollectionView> getEventsByTypeAndIpAddress(Class<?> eventType, String ipAddress);

    Optional<EventsCollectionView> getEventsByTypeAndIpAddressWithTimeframe(
            Class<?> eventType, String ipAddress, SearchTimeframe timeframe);

    Optional<EventsCollectionView> getEventsByTypeAndIpAddressAndUsername(
            Class<?> eventType, String ipAddress, String username);

    Optional<EventsCollectionView> getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
            Class<?> eventType, String ipAddress, String username, SearchTimeframe timeframe);

    Optional<EventsCollectionView> getEventsByTypeAndUsernameWithTimeframe(
            Class<?> eventType, String username, SearchTimeframe timeframe);

    Optional<EventsCollectionView> getEventsByTypeAndUsername(Class<?> eventType, String username);

    Optional<EventsCollectionView> getEventsByTypeWithTimeframe(Class<?> eventType, SearchTimeframe timeframe);

    Optional<EventsCollectionView> getEventsByType(Class<?> eventType);
}
