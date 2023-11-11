package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherEvent;
import com.jfs415.packetwatcher_api.exceptions.EventAnnotationNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import com.jfs415.packetwatcher_api.model.services.inf.RepositoryManager;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService {

    private final RepositoryManager repositoryManager;

    @Autowired
    public EventServiceImpl(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public void save(EventMappedSuperclass event) throws InvalidEventArgumentException {
        try {
            validate(event.getClass());
        } catch (EventAnnotationNotFoundException ignored) {
            return;
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getEventRepository(event.getClass());
        repository.save(event);
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddress(Class<?> eventType, String ipAddress) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events = StreamSupport.stream(repository.findAllByIpAddress(ipAddress).spliterator(), true);

        return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddressWithTimeframe(Class<?> eventType, String ipAddress, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getEventRepository(eventType);
        return lookupEventsWithIpAddressAndTimeframe(repository, ipAddress, timeframe);
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddressAndUsername(Class<?> eventType, String ipAddress, String username) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events = StreamSupport.stream(repository.findAllByIpAddressAndUsername(ipAddress, username).spliterator(), false);

        return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddressAndUsernameWithTimeframe(Class<?> eventType, String ipAddress, String username, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        return lookupEventsWithUsernameAndIpAddressAndTimeframe(repositoryManager.getEventRepository(eventType), username, ipAddress, timeframe);
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndUsernameWithTimeframe(Class<?> eventType, String username, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        return lookupEventsWithUsernameAndTimeframe(repositoryManager.getEventRepository(eventType), username, timeframe);
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndUsername(Class<?> eventType, String username) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events = StreamSupport.stream(repository.findAllByUsername(username).spliterator(), true);

        return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeWithTimeframe(Class<?> eventType, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        return lookupEventsWithTimeframeOnly(repositoryManager.getEventRepository(eventType), timeframe);
    }

    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByType(Class<?> eventType) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository = repositoryManager.getEventRepository(eventType);

        return new EventsCollectionView(repository.findAll().stream().map(EventMappedSuperclass::toEventView).toList());
    }

    private EventsCollectionView lookupEventsWithUsernameAndIpAddressAndTimeframe(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, String username, String ipAddress, SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE:
                events = StreamSupport.stream(repository.findAllByUsernameAndIpAddressAndTimestampBefore(username, ipAddress, searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case AFTER:
                events = StreamSupport.stream(repository.findAllByUsernameAndIpAddressAndTimestampAfter(username, ipAddress, searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case BETWEEN:
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(repository.findAllByUsernameAndIpAddressAndTimestampBetween(username, ipAddress, dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            default:
                return new EventsCollectionView();
        }
    }

    private EventsCollectionView lookupEventsWithIpAddressAndTimeframe(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, String ipAddress, SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE:
                events = StreamSupport.stream(repository.findAllByIpAddressAndTimestampBefore(ipAddress, searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case AFTER:
                events = StreamSupport.stream(repository.findAllByIpAddressAndTimestampAfter(ipAddress, searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case BETWEEN:
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(repository.findAllByIpAddressAndTimestampBetween(ipAddress, dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            default:
                return new EventsCollectionView();
        }
    }

    private EventsCollectionView lookupEventsWithUsernameAndTimeframe(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, String username, SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE:
                events = StreamSupport.stream(repository.findAllByUsernameAndTimestampBefore(username, searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case AFTER:
                events = StreamSupport.stream(repository.findAllByUsernameAndTimestampAfter(username, searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case BETWEEN:
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(repository.findAllByUsernameAndTimestampBetween(username, dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            default:
                return new EventsCollectionView();
        }
    }

    private EventsCollectionView lookupEventsWithTimeframeOnly(PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository, SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE:
                events = StreamSupport.stream(repository.findAllByTimestampBefore(searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case AFTER:
                events = StreamSupport.stream(repository.findAllByTimestampAfter(searchTimeframe.getTimestamp()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
            case BETWEEN:
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(repository.findAllByTimestampBetween(dualTimeframe.getStart(), dualTimeframe.getEnd()).spliterator(), true);
                return new EventsCollectionView(events.map(EventMappedSuperclass::toEventView).toList());
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
