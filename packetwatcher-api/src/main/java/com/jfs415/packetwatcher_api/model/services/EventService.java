package com.jfs415.packetwatcher_api.model.services;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherEvent;
import com.jfs415.packetwatcher_api.exceptions.EventAnnotationNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.events.DualEventTimeframe;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.events.EventTimeframe;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;

@Service
public class EventService {

	@Autowired
	private RepositoryManager repositoryManager;

	@Transactional
	public void save(EventMappedSuperclass event) throws InvalidEventArgumentException {
		try {
			validate(event.getClass());
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
		}

		PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getRepository(event.getClass());
		repository.save(event);
	}

	public EventsCollectionView getEventsByTypeAndIpAddress(Class<?> eventType, String ipAddress) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getRepository(eventType);
		Stream<EventMappedSuperclass> events = StreamSupport.stream(repository.findAllByIpAddress(ipAddress).spliterator(), true);
		
		return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
	}

	public EventsCollectionView getEventsByTypeAndIpAddressWithTimeframe(Class<?> eventType, String ipAddress, EventTimeframe timeframe) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getRepository(eventType);
		return lookupEventsWithIpAddressAndTimeframe(repository, ipAddress, timeframe);
	}

	public EventsCollectionView getEventsByTypeAndIpAddressAndUsername(Class<?> eventType, String ipAddress, String username) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getRepository(eventType);
		Stream<EventMappedSuperclass> events = StreamSupport.stream(repository.findAllByIpAddressAndUsername(ipAddress, username).spliterator(), false);
		
		return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
	}

	public EventsCollectionView getEventsByTypeAndIpAddressAndUsernameWithTimeframe(Class<?> eventType, String ipAddress, String username, EventTimeframe timeframe) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		return lookupEventsWithUsernameAndIpAddressAndTimeframe(repositoryManager.getRepository(eventType), username, ipAddress, timeframe);
	}

	public EventsCollectionView getEventsByTypeAndUsernameWithTimeframe(Class<?> eventType, String username, EventTimeframe timeframe) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		return lookupEventsWithUsernameAndTimeframe(repositoryManager.getRepository(eventType), username, timeframe);
	}

	public EventsCollectionView getEventsByTypeAndUsername(Class<?> eventType, String username) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getRepository(eventType);
		Stream<EventMappedSuperclass> events = StreamSupport.stream(repository.findAllByUsername(username).spliterator(), true);

		return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
	}

	public EventsCollectionView getEventsByTypeWithTimeframe(Class<?> eventType, EventTimeframe timeframe) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		return lookupEventsWithTimeframeOnly(repositoryManager.getRepository(eventType), timeframe);
	}

	public EventsCollectionView getEventsByType(Class<?> eventType) {
		try {
			validate(eventType);
		} catch (EventAnnotationNotFoundException e) {
			e.printStackTrace();
			return new EventsCollectionView();
		}

		PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getRepository(eventType);

		return new EventsCollectionView(repository.findAll().stream().map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
	}

	private EventsCollectionView lookupEventsWithUsernameAndIpAddressAndTimeframe(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, String username, String ipAddress, EventTimeframe eventTimeframe) {
		Stream<EventMappedSuperclass> events;

		switch (eventTimeframe.getTimeframe()) {
			case BEFORE:
				events = StreamSupport.stream(repository.findAllByUsernameAndIpAddressAndTimestampBefore(username, ipAddress, eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case AFTER:
				events = StreamSupport.stream(repository.findAllByUsernameAndIpAddressAndTimestampAfter(username, ipAddress, eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case BETWEEN:
				DualEventTimeframe dualTimeframe = (DualEventTimeframe) eventTimeframe;
				events = StreamSupport.stream(repository.findAllByUsernameAndIpAddressAndTimestampBetween(username, ipAddress, dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			default:
				return new EventsCollectionView();
		}
	}

	private EventsCollectionView lookupEventsWithIpAddressAndTimeframe(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, String ipAddress, EventTimeframe eventTimeframe) {
		Stream<EventMappedSuperclass> events;

		switch (eventTimeframe.getTimeframe()) {
			case BEFORE:
				events = StreamSupport.stream(repository.findAllByIpAddressAndTimestampBefore(ipAddress, eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case AFTER:
				events = StreamSupport.stream(repository.findAllByIpAddressAndTimestampAfter(ipAddress, eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case BETWEEN:
				DualEventTimeframe dualTimeframe = (DualEventTimeframe) eventTimeframe;
				events = StreamSupport.stream(repository.findAllByIpAddressAndTimestampBetween(ipAddress, dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			default:
				return new EventsCollectionView();
		}
	}

	private EventsCollectionView lookupEventsWithUsernameAndTimeframe(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, String username, EventTimeframe eventTimeframe) {
		Stream<EventMappedSuperclass> events;

		switch (eventTimeframe.getTimeframe()) {
			case BEFORE:
				events = StreamSupport.stream(repository.findAllByUsernameAndTimestampBefore(username, eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case AFTER:
				events = StreamSupport.stream(repository.findAllByUsernameAndTimestampAfter(username, eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case BETWEEN:
				DualEventTimeframe dualTimeframe = (DualEventTimeframe) eventTimeframe;
				events = StreamSupport.stream(repository.findAllByUsernameAndTimestampBetween(username, dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			default:
				return new EventsCollectionView();
		}
	}

	private EventsCollectionView lookupEventsWithTimeframeOnly(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, EventTimeframe eventTimeframe) {
		Stream<EventMappedSuperclass> events;
		
		switch (eventTimeframe.getTimeframe()) {
			case BEFORE:
				events = StreamSupport.stream(repository.findAllByTimestampBefore(eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case AFTER:
				events = StreamSupport.stream(repository.findAllByTimestampAfter(eventTimeframe.getTimestamp()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			case BETWEEN:
				DualEventTimeframe dualTimeframe = (DualEventTimeframe) eventTimeframe;
				events = StreamSupport.stream(repository.findAllByTimestampBetween(dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
				return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).collect(Collectors.toList()));
			default:
				return new EventsCollectionView();
		}
	}

	private void validate(Class<?> eventType) throws EventAnnotationNotFoundException {
		if (!eventType.isAnnotationPresent(PacketWatcherEvent.class)) {
			throw new EventAnnotationNotFoundException();
		}

		if (!eventType.isAnnotationPresent(Entity.class)) {
			throw new EventAnnotationNotFoundException("PacketWatcherEvents must be annotated with @Entity!");
		}
	}

}
