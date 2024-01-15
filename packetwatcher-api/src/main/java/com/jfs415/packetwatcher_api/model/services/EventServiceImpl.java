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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventServiceImpl implements EventService {

    private final RepositoryManager repositoryManager;

    @Autowired
    public EventServiceImpl(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public void save(EventMappedSuperclass event) throws InvalidEventArgumentException {
        try {
            validate(event.getClass());
        } catch (EventAnnotationNotFoundException ignored) {
            return;
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(event.getClass());
        repository.save(event);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddress(Class<?> eventType, String ipAddress) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events =
                StreamSupport.stream(repository.findAllByIpAddress(ipAddress).spliterator(), true);

        return new EventsCollectionView(
                events.map(EventMappedSuperclass::toEventView).toList());
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddressWithTimeframe(
            Class<?> eventType, String ipAddress, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        return lookupEventsWithIpAddressAndTimeframe(repository, ipAddress, timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddressAndUsername(
            Class<?> eventType, String ipAddress, String username) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events = StreamSupport.stream(
                repository.findAllByIpAddressAndUsername(ipAddress, username).spliterator(), false);

        return new EventsCollectionView(
                events.map(EventMappedSuperclass::toEventView).toList());
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
            Class<?> eventType, String ipAddress, String username, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        return lookupEventsWithUsernameAndIpAddressAndTimeframe(
                repositoryManager.getEventRepository(eventType), username, ipAddress, timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndUsernameWithTimeframe(
            Class<?> eventType, String username, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        return lookupEventsWithUsernameAndTimeframe(
                repositoryManager.getEventRepository(eventType), username, timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeAndUsername(Class<?> eventType, String username) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events =
                StreamSupport.stream(repository.findAllByUsername(username).spliterator(), true);

        return new EventsCollectionView(
                events.map(EventMappedSuperclass::toEventView).toList());
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByTypeWithTimeframe(Class<?> eventType, SearchTimeframe timeframe) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        return lookupEventsWithTimeframeOnly(repositoryManager.getEventRepository(eventType), timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public EventsCollectionView getEventsByType(Class<?> eventType) {
        try {
            validate(eventType);
        } catch (EventAnnotationNotFoundException ignored) {
            return new EventsCollectionView(new ArrayList<>());
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);

        return new EventsCollectionView(repository.findAll().stream()
                .map(EventMappedSuperclass::toEventView)
                .toList());
    }

    private EventsCollectionView lookupEventsWithUsernameAndIpAddressAndTimeframe(
            PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository,
            String username,
            String ipAddress,
            SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndIpAddressAndTimestampBefore(
                                        username, ipAddress, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndIpAddressAndTimestampAfter(
                                        username, ipAddress, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case BETWEEN -> {
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndIpAddressAndTimestampBetween(
                                        username,
                                        ipAddress,
                                        Timestamp.valueOf(dualTimeframe.getStart()),
                                        Timestamp.valueOf(dualTimeframe.getEnd()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            default -> {
                return new EventsCollectionView(new ArrayList<>());
            }
        }
    }

    private EventsCollectionView lookupEventsWithIpAddressAndTimeframe(
            PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository,
            String ipAddress,
            SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByIpAddressAndTimestampBefore(
                                        ipAddress, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByIpAddressAndTimestampAfter(
                                        ipAddress, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case BETWEEN -> {
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(
                        repository
                                .findAllByIpAddressAndTimestampBetween(
                                        ipAddress,
                                        Timestamp.valueOf(dualTimeframe.getStart()),
                                        Timestamp.valueOf(dualTimeframe.getEnd()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            default -> {
                return new EventsCollectionView(new ArrayList<>());
            }
        }
    }

    private EventsCollectionView lookupEventsWithUsernameAndTimeframe(
            PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository,
            String username,
            SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndTimestampBefore(
                                        username, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndTimestampAfter(
                                        username, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case BETWEEN -> {
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndTimestampBetween(
                                        username,
                                        Timestamp.valueOf(dualTimeframe.getStart()),
                                        Timestamp.valueOf(dualTimeframe.getEnd()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            default -> {
                return new EventsCollectionView(new ArrayList<>());
            }
        }
    }

    private EventsCollectionView lookupEventsWithTimeframeOnly(
            PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository,
            SearchTimeframe searchTimeframe) {
        Stream<EventMappedSuperclass> events;

        switch (searchTimeframe.getTimeframe()) {
            case BEFORE -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByTimestampBefore(Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByTimestampAfter(Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            case BETWEEN -> {
                RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) searchTimeframe;
                events = StreamSupport.stream(
                        repository
                                .findAllByTimestampBetween(
                                        Timestamp.valueOf(dualTimeframe.getStart()),
                                        Timestamp.valueOf(dualTimeframe.getEnd()))
                                .spliterator(),
                        true);
                return new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList());
            }
            default -> {
                return new EventsCollectionView(new ArrayList<>());
            }
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
