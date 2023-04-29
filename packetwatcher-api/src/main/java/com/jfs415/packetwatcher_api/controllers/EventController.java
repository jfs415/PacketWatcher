package com.jfs415.packetwatcher_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;
import com.jfs415.packetwatcher_api.events.authorization.AuthorizationEventType;
import com.jfs415.packetwatcher_api.model.services.EventService;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;

	@GetMapping("/events/authentication")
	public ResponseEntity<?> getAuthenticationEvents() {
		return ResponseEntity.ok(eventService.getAllEventsByEventType(AuthenticationEventType.class));
	}

	@GetMapping("/events/authorization")
	public ResponseEntity<?> getAuthorizationEvents() {
		return ResponseEntity.ok(eventService.getAllEventsByEventType(AuthorizationEventType.class));
	}
	
}
