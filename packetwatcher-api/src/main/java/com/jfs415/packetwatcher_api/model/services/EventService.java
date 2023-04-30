package com.jfs415.packetwatcher_api.model.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfs415.packetwatcher_api.events.IAuthEventType;
import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;
import com.jfs415.packetwatcher_api.events.authorization.AuthorizationEventType;
import com.jfs415.packetwatcher_api.exceptions.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.exceptions.InvalidEventTypeArgumentException;
import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;
import com.jfs415.packetwatcher_api.model.events.PacketWatcherEvent;
import com.jfs415.packetwatcher_api.model.repositories.AuthenticationEventRepository;
import com.jfs415.packetwatcher_api.model.repositories.AuthorizationEventRepository;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;

@Service
public class EventService {

	@Autowired
	private AuthenticationEventRepository authenticationEventRepository;

	@Autowired
	private AuthorizationEventRepository authorizationEventRepository;

	@Transactional
	public void saveAuthEvent(PacketWatcherEvent event) throws InvalidEventArgumentException {
		if (event instanceof AuthorizationEvent) {
			authorizationEventRepository.saveAndFlush((AuthorizationEvent) event);
		} else if (event instanceof AuthenticationEvent) {
			authenticationEventRepository.saveAndFlush((AuthenticationEvent) event);
		} else {
			throw new InvalidEventArgumentException();
		}
	}

	public EventsCollectionView getAllEventsByEventType(Class<? extends IAuthEventType> eventType) {
		if (eventType.isAssignableFrom(AuthenticationEventType.class)) {
			return new EventsCollectionView(authenticationEventRepository.findAll().stream().map(PacketWatcherEvent::toEventView).collect(Collectors.toList()));
		} else if (eventType.isAssignableFrom(AuthorizationEventType.class)) {
			return new EventsCollectionView(authorizationEventRepository.findAll().stream().map(PacketWatcherEvent::toEventView).collect(Collectors.toList()));
		} else {
			throw new InvalidEventTypeArgumentException();
		}
	}

}
