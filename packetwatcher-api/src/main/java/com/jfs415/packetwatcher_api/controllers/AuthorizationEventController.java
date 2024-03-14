package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events")
public class AuthorizationEventController implements EventController {

    private final EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationEventController.class);

    private static final Class<?> IMPL = AuthorizationEvent.class;

    @Autowired
    public AuthorizationEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/authorization")
    public ResponseEntity<EventsCollectionView> getAuthorizationEvents() {
        return events(IMPL, eventService);
    }

    @GetMapping(value = "/authorization", params = "before")
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsBefore(@RequestParam long before) {
        return eventsBefore(IMPL, eventService, logger, before);
    }

    @GetMapping(value = "/authorization", params = "after")
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsAfter(@RequestParam long after) {
        return eventsAfter(IMPL, eventService, logger, after);
    }

    @GetMapping(
            value = "/authorization",
            params = {"start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsBetween(
            @RequestParam long start, @RequestParam long stop) {
        return eventsBetween(IMPL, eventService, logger, start, stop);
    }

    @GetMapping(value = "/authorization", params = "username")
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsByUsername(@RequestParam String username) {
        return eventsByUsername(IMPL, eventService, username);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "before"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameBefore(
            @RequestParam String username, @RequestParam long before) {
        return eventsWithUsernameBefore(IMPL, eventService, logger, username, before);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "after"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameAfter(
            @RequestParam String username, @RequestParam long after) {
        return eventsWithUsernameAfter(IMPL, eventService, logger, username, after);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameBetween(
            @RequestParam String username, @RequestParam long start, @RequestParam long stop) {
        return eventsWithUsernameBetween(IMPL, eventService, logger, username, start, stop);
    }

    @GetMapping(value = "/authorization", params = "ipAddress")
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsByIpAddress(@RequestParam String ipAddress) {
        return eventsByIpAddress(IMPL, eventService, ipAddress);
    }

    @GetMapping(
            value = "/authorization",
            params = {"ipAddress", "before"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithIpAddressBefore(
            @RequestParam String ipAddress, @RequestParam long before) {
        return eventsWithIpAddressBefore(IMPL, eventService, logger, ipAddress, before);
    }

    @GetMapping(
            value = "/authorization",
            params = {"ipAddress", "after"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithIpAddressAfter(
            @RequestParam String ipAddress, @RequestParam long after) {
        return eventsWithIpAddressAfter(IMPL, eventService, logger, ipAddress, after);
    }

    @GetMapping(
            value = "/authorization",
            params = {"ipAddress", "start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithIpAddressBetween(
            @RequestParam String ipAddress, @RequestParam long start, @RequestParam long stop) {
        return eventsWithIpAddressBetween(IMPL, eventService, logger, ipAddress, start, stop);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "ipAddress"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameAndIpAddress(
            @RequestParam String username, @RequestParam String ipAddress) {
        return eventsWithUsernameAndIpAddress(IMPL, eventService, username, ipAddress);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "ipAddress", "before"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameAndIpAddressBefore(
            @RequestParam String username, @RequestParam String ipAddress, @RequestParam long before) {
        return eventsWithUsernameAndIpAddressBefore(IMPL, eventService, logger, username, ipAddress, before);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "ipAddress", "after"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameAndIpAddressAfter(
            @RequestParam String username, @RequestParam String ipAddress, @RequestParam long after) {
        return eventsWithUsernameAndIpAddressAfter(IMPL, eventService, logger, username, ipAddress, after);
    }

    @GetMapping(
            value = "/authorization",
            params = {"username", "ipAddress", "start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthorizationEventsWithUsernameAndIpAddressBetween(
            @RequestParam String username,
            @RequestParam String ipAddress,
            @RequestParam long start,
            @RequestParam long stop) {
        return eventsWithUsernameAndIpAddressBetween(IMPL, eventService, logger, username, ipAddress, start, stop);
    }
}
