package com.jfs415.packetwatcher_api.controllers;

import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationEventController {

    private final EventService eventService;

    @Autowired
    public AuthorizationEventController(EventService eventService) {
        this.eventService = eventService;
    }

    private static final Class<?> IMPL = AuthorizationEvent.class;

    @GetMapping("/events/authorization")
    public ResponseEntity<?> getAuthorizationEvents() {
        return ResponseEntity.ok(eventService.getEventsByType(IMPL));
    }

    @GetMapping(value = "/events/authorization", params = "before")
    public ResponseEntity<?> getAuthorizationEventsBefore(@RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeWithTimeframe(IMPL, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authorization", params = "after")
    public ResponseEntity<?> getAuthorizationEventsAfter(@RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeWithTimeframe(IMPL, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authorization", params = { "start", "stop" })
    public ResponseEntity<?> getAuthorizationEventsBetween(@RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeWithTimeframe(IMPL, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/events/authorization", params = "username")
    public ResponseEntity<?> getAuthorizationEventsByUsername(@RequestParam String username) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsername(IMPL, username));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "before" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameBefore(@RequestParam String username, @RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsernameWithTimeframe(IMPL, username, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "after" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameAfter(@RequestParam String username, @RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsernameWithTimeframe(IMPL, username, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "start", "stop" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameBetween(@RequestParam String username, @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndUsernameWithTimeframe(IMPL, username, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/events/authorization", params = "ipAddress")
    public ResponseEntity<?> getAuthorizationEventsByIpAddress(@RequestParam String ipAddress) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddress(IMPL, ipAddress));
    }

    @GetMapping(value = "/events/authorization", params = { "ipAddress", "before" })
    public ResponseEntity<?> getAuthorizationEventsWithIpAddressBefore(@RequestParam String ipAddress, @RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressWithTimeframe(IMPL, ipAddress, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authorization", params = { "ipAddress", "after" })
    public ResponseEntity<?> getAuthorizationEventsWithIpAddressAfter(@RequestParam String ipAddress, @RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressWithTimeframe(IMPL, ipAddress, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authorization", params = { "ipAddress", "start", "stop" })
    public ResponseEntity<?> getAuthorizationEventsWithIpAddressBetween(@RequestParam String ipAddress, @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressWithTimeframe(IMPL, ipAddress, RangedSearchTimeframe.between(start, stop)));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "ipAddress" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameAndIpAddress(@RequestParam String username, @RequestParam String ipAddress) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsername(IMPL, ipAddress, username));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "ipAddress", "before" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameAndIpAddressBefore(@RequestParam String username, @RequestParam String ipAddress, @RequestParam long before) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(IMPL, username, ipAddress, SearchTimeframe.before(before)));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "ipAddress", "after" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameAndIpAddressAfter(@RequestParam String username, @RequestParam String ipAddress, @RequestParam long after) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(IMPL, username, ipAddress, SearchTimeframe.after(after)));
    }

    @GetMapping(value = "/events/authorization", params = { "username", "ipAddress", "start", "stop" })
    public ResponseEntity<?> getAuthorizationEventsWithUsernameAndIpAddressBetween(@RequestParam String username, @RequestParam String ipAddress, @RequestParam long start, @RequestParam long stop) {
        return ResponseEntity.ok(eventService.getEventsByTypeAndIpAddressAndUsernameWithTimeframe(IMPL, ipAddress, username, RangedSearchTimeframe.between(start, stop)));
    }

}
