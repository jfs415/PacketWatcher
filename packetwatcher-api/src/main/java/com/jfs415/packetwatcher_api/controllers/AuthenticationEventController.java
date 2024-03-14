package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
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
public class AuthenticationEventController implements EventController {

    private final EventService eventService;
    private static final Class<?> IMPL = AuthenticationEvent.class;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventController.class);

    @Autowired
    public AuthenticationEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/authentication")
    public ResponseEntity<EventsCollectionView> getAuthenticationEvents() {
        return events(IMPL, eventService);
    }

    @GetMapping(value = "/authentication", params = "before")
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsBefore(@RequestParam long before) {
        return eventsBefore(IMPL, eventService, logger, before);
    }

    @GetMapping(value = "/authentication", params = "after")
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsAfter(@RequestParam long after) {
        return eventsAfter(IMPL, eventService, logger, after);
    }

    @GetMapping(
            value = "/authentication",
            params = {"start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsBetween(
            @RequestParam long start, @RequestParam long stop) {
        return eventsBetween(IMPL, eventService, logger, start, stop);
    }

    @GetMapping(value = "/authentication", params = "username")
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsByUsername(@RequestParam String username) {
        return eventsByUsername(IMPL, eventService, username);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "before"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameBefore(
            @RequestParam String username, @RequestParam long before) {
        return eventsWithUsernameBefore(IMPL, eventService, logger, username, before);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "after"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameAfter(
            @RequestParam String username, @RequestParam long after) {
        return eventsWithUsernameAfter(IMPL, eventService, logger, username, after);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameBetween(
            @RequestParam String username, @RequestParam long start, @RequestParam long stop) {
        return eventsWithUsernameBetween(IMPL, eventService, logger, username, start, stop);
    }

    @GetMapping(value = "/authentication", params = "ipAddress")
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsByIpAddress(@RequestParam String ipAddress) {
        return eventsByIpAddress(IMPL, eventService, ipAddress);
    }

    @GetMapping(
            value = "/authentication",
            params = {"ipAddress", "before"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithIpAddressBefore(
            @RequestParam String ipAddress, @RequestParam long before) {
        return eventsWithIpAddressBefore(IMPL, eventService, logger, ipAddress, before);
    }

    @GetMapping(
            value = "/authentication",
            params = {"ipAddress", "after"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithIpAddressAfter(
            @RequestParam String ipAddress, @RequestParam long after) {
        return eventsWithIpAddressAfter(IMPL, eventService, logger, ipAddress, after);
    }

    @GetMapping(
            value = "/authentication",
            params = {"ipAddress", "start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithIpAddressBetween(
            @RequestParam String ipAddress, @RequestParam long start, @RequestParam long stop) {
        return eventsWithIpAddressBetween(IMPL, eventService, logger, ipAddress, start, stop);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "ipAddress"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameAndIpAddress(
            @RequestParam String username, @RequestParam String ipAddress) {
        return eventsWithUsernameAndIpAddress(IMPL, eventService, username, ipAddress);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "ipAddress", "before"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameAndIpAddressBefore(
            @RequestParam String username, @RequestParam String ipAddress, @RequestParam long before) {
        return eventsWithUsernameAndIpAddressBefore(IMPL, eventService, logger, username, ipAddress, before);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "ipAddress", "after"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameAndIpAddressAfter(
            @RequestParam String username, @RequestParam String ipAddress, @RequestParam long after) {
        return eventsWithUsernameAndIpAddressAfter(IMPL, eventService, logger, username, ipAddress, after);
    }

    @GetMapping(
            value = "/authentication",
            params = {"username", "ipAddress", "start", "stop"})
    public ResponseEntity<EventsCollectionView> getAuthenticationEventsWithUsernameAndIpAddressBetween(
            @RequestParam String username,
            @RequestParam String ipAddress,
            @RequestParam long start,
            @RequestParam long stop) {
        return eventsWithUsernameAndIpAddressBetween(IMPL, eventService, logger, username, ipAddress, start, stop);
    }
}
