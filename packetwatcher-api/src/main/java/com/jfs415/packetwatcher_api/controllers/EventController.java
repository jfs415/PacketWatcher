package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

public interface EventController {

    default Optional<SearchTimeframe> createBeforeSearch(long before, Logger logger) {
        try {
            return Optional.of(SearchTimeframe.before(before));
        } catch (InvalidEventArgumentException eventArgException) {
            logger.error(eventArgException.getMessage(), eventArgException);
            return Optional.empty();
        }
    }

    default Optional<SearchTimeframe> createAfterSearch(long after, Logger logger) {
        try {
            return Optional.of(SearchTimeframe.after(after));
        } catch (InvalidEventArgumentException eventArgException) {
            logger.error(eventArgException.getMessage(), eventArgException);
            return Optional.empty();
        }
    }

    default Optional<RangedSearchTimeframe> createBetweenSearch(long start, long stop, Logger logger) {
        try {
            return Optional.of(RangedSearchTimeframe.between(start, stop));
        } catch (InvalidEventArgumentException eventArgException) {
            logger.error(eventArgException.getMessage(), eventArgException);
            return Optional.empty();
        }
    }

    default ResponseEntity<EventsCollectionView> events(Class<?> impl, EventService eventService) {
        Optional<EventsCollectionView> optionalEvents = eventService.getEventsByType(impl);

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsBefore(
            Class<?> impl, EventService eventService, Logger logger, long before) {
        Optional<SearchTimeframe> searchTimeframe = createBeforeSearch(before, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeWithTimeframe(impl, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsAfter(
            Class<?> impl, EventService eventService, Logger logger, long after) {
        Optional<SearchTimeframe> searchTimeframe = createAfterSearch(after, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeWithTimeframe(impl, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsBetween(
            Class<?> impl, EventService eventService, Logger logger, long start, long stop) {
        Optional<RangedSearchTimeframe> rangedTimeframe = createBetweenSearch(start, stop, logger);

        if (rangedTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeWithTimeframe(impl, rangedTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsByUsername(
            Class<?> impl, EventService eventService, String username) {
        Optional<EventsCollectionView> optionalEvents = eventService.getEventsByTypeAndUsername(impl, username);

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithUsernameBefore(
            Class<?> impl, EventService eventService, Logger logger, String username, long before) {
        Optional<SearchTimeframe> searchTimeframe = createBeforeSearch(before, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndUsernameWithTimeframe(impl, username, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<?> eventsWithUsernameAfter(
            Class<?> impl, EventService eventService, Logger logger, String username, long after) {
        Optional<SearchTimeframe> searchTimeframe = createAfterSearch(after, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndUsernameWithTimeframe(impl, username, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithUsernameBetween(
            Class<?> impl, EventService eventService, Logger logger, String username, long start, long stop) {
        Optional<RangedSearchTimeframe> rangedTimeframe = createBetweenSearch(start, stop, logger);

        if (rangedTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndUsernameWithTimeframe(impl, username, rangedTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsByIpAddress(
            Class<?> impl, EventService eventService, String ipAddress) {
        Optional<EventsCollectionView> optionalEvents = eventService.getEventsByTypeAndIpAddress(impl, ipAddress);

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithIpAddressBefore(
            Class<?> impl, EventService eventService, Logger logger, String ipAddress, long before) {
        Optional<SearchTimeframe> searchTimeframe = createBeforeSearch(before, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressWithTimeframe(impl, ipAddress, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithIpAddressAfter(
            Class<?> impl, EventService eventService, Logger logger, String ipAddress, long after) {
        Optional<SearchTimeframe> searchTimeframe = createAfterSearch(after, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressWithTimeframe(impl, ipAddress, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithIpAddressBetween(
            Class<?> impl, EventService eventService, Logger logger, String ipAddress, long start, long stop) {
        Optional<RangedSearchTimeframe> rangedTimeframe = createBetweenSearch(start, stop, logger);

        if (rangedTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressWithTimeframe(impl, ipAddress, rangedTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithUsernameAndIpAddress(
            Class<?> impl, EventService eventService, String username, String ipAddress) {
        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressAndUsername(impl, ipAddress, username);

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithUsernameAndIpAddressBefore(
            Class<?> impl, EventService eventService, Logger logger, String username, String ipAddress, long before) {
        Optional<SearchTimeframe> searchTimeframe = createBeforeSearch(before, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
                        impl, username, ipAddress, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithUsernameAndIpAddressAfter(
            Class<?> impl, EventService eventService, Logger logger, String username, String ipAddress, long after) {
        Optional<SearchTimeframe> searchTimeframe = createAfterSearch(after, logger);

        if (searchTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
                        impl, username, ipAddress, searchTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }

    default ResponseEntity<EventsCollectionView> eventsWithUsernameAndIpAddressBetween(
            Class<?> impl,
            EventService eventService,
            Logger logger,
            String username,
            String ipAddress,
            long start,
            long stop) {
        Optional<RangedSearchTimeframe> rangedTimeframe = createBetweenSearch(start, stop, logger);

        if (rangedTimeframe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<EventsCollectionView> optionalEvents =
                eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
                        impl, ipAddress, username, rangedTimeframe.get());

        return optionalEvents.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }
}
