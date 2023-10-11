package com.jfs415.packetwatcher_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.model.services.EventService;

@Controller
public class AuthenticationEventController {

    private final EventService eventService;
    private static final Class<?> IMPL = AuthenticationEvent.class;

    @Autowired
    public AuthenticationEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/authentication")
    public ResponseEntity<?> getAuthenticationEvents() {
        return ResponseEntity.ok(eventService.getEventsByType(IMPL));
    }

    @GetMapping(value = "/events/authentication", params = "before")
    public ResponseEntity<?> getAuthenticationEventsBefore(@RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeWithTimeframe(IMPL, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authentication", params = "after")
    public ResponseEntity<?> getAuthenticationEventsAfter(@RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeWithTimeframe(IMPL, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authentication", params = { "start", "stop" })
    public ResponseEntity<?> getAuthenticationEventsBetween(@RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeWithTimeframe(IMPL, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/events/authentication", params = "username")
    public ResponseEntity<?> getAuthenticationEventsByUsername(@RequestParam String username) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsername(IMPL, username));
    }

    @GetMapping(value = "/events/authentication", params = { "username", "before" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameBefore(@RequestParam String username, @RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsernameWithTimeframe(IMPL, username, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/Authentication", params = { "username", "after" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameAfter(@RequestParam String username, @RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsernameWithTimeframe(IMPL, username, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authentication", params = { "username", "start", "stop" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameBetween(@RequestParam String username, @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsernameWithTimeframe(IMPL, username, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/events/authentication", params = "ipAddress")
    public ResponseEntity<?> getAuthenticationEventsByIpAddress(@RequestParam String ipAddress) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddress(IMPL, ipAddress));
    }

    @GetMapping(value = "/events/authentication", params = { "ipAddress", "before" })
    public ResponseEntity<?> getAuthenticationEventsWithIpAddressBefore(@RequestParam String ipAddress, @RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressWithTimeframe(IMPL, ipAddress, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authentication", params = { "ipAddress", "after" })
    public ResponseEntity<?> getAuthenticationEventsWithIpAddressAfter(@RequestParam String ipAddress, @RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressWithTimeframe(IMPL, ipAddress, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authentication", params = { "ipAddress", "start", "stop" })
    public ResponseEntity<?> getAuthenticationEventsWithIpAddressBetween(@RequestParam String ipAddress, @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressWithTimeframe(IMPL, ipAddress, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/events/authentication", params = { "username", "ipAddress" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameAndIpAddress(@RequestParam String username, @RequestParam String ipAddress) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsername(IMPL, ipAddress, username));
    }

    @GetMapping(value = "/events/authentication", params = { "username", "ipAddress", "before" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameAndIpAddressBefore(@RequestParam String username, @RequestParam String ipAddress, @RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(IMPL, username, ipAddress, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authentication", params = { "username", "ipAddress", "after" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameAndIpAddressAfter(@RequestParam String username, @RequestParam String ipAddress, @RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(IMPL, username, ipAddress, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authentication", params = { "username", "ipAddress", "start", "stop" })
    public ResponseEntity<?> getAuthenticationEventsWithUsernameAndIpAddressBetween(@RequestParam String username, @RequestParam String ipAddress, @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(IMPL, ipAddress, username, RangedSearchTimeframe.between(start, stop)));
    }

}
