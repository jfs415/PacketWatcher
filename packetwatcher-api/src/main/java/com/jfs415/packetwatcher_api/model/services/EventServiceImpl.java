package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherEvent;
import com.jfs415.packetwatcher_api.exceptions.EventAnnotationNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import com.jfs415.packetwatcher_api.model.services.inf.RepositoryManager;
import com.jfs415.packetwatcher_api.util.InetAddressValidator;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.persistence.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventServiceImpl implements EventService {

    private final RepositoryManager repositoryManager;
    private final InetAddressValidator inetAddressValidator;
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    public EventServiceImpl(RepositoryManager repositoryManager, InetAddressValidator inetAddressValidator) {
        this.repositoryManager = repositoryManager;
        this.inetAddressValidator = inetAddressValidator;
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public void save(EventMappedSuperclass event) throws InvalidEventArgumentException {
        try {
            validateNotNull(event);
            validateEvent(event.getClass());
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return;
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(event.getClass());
        repository.save(event);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeAndIpAddress(Class<?> eventType, String ipAddress) {
        try {
            validateNotNull(eventType, ipAddress);
            validateIpAddress(ipAddress);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events =
                StreamSupport.stream(repository.findAllByIpAddress(ipAddress).spliterator(), true);

        return Optional.of(new EventsCollectionView(
                events.map(EventMappedSuperclass::toEventView).toList()));
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeAndIpAddressWithTimeframe(
            Class<?> eventType, String ipAddress, SearchTimeframe timeframe) {
        try {
            validateNotNull(eventType, ipAddress, timeframe);
            validateIpAddress(ipAddress);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        return lookupEventsWithIpAddressAndTimeframe(repository, ipAddress, timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeAndIpAddressAndUsername(
            Class<?> eventType, String ipAddress, String username) {
        try {
            validateNotNull(eventType, ipAddress, username);
            validateUsername(username);
            validateIpAddress(ipAddress);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events = StreamSupport.stream(
                repository.findAllByIpAddressAndUsername(ipAddress, username).spliterator(), false);

        return Optional.of(new EventsCollectionView(
                events.map(EventMappedSuperclass::toEventView).toList()));
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeAndIpAddressAndUsernameWithTimeframe(
            Class<?> eventType, String ipAddress, String username, SearchTimeframe timeframe) {
        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> eventRepository;
                
        try {
            eventRepository = repositoryManager.getEventRepository(eventType);
            validateNotNull(eventType, ipAddress, username, timeframe);
            validateUsername(username);
            validateIpAddress(ipAddress);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        return lookupEventsWithUsernameAndIpAddressAndTimeframe(
                eventRepository, username, ipAddress, timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeAndUsernameWithTimeframe(
            Class<?> eventType, String username, SearchTimeframe timeframe) {
        try {
            validateNotNull(eventType, username, timeframe);
            validateUsername(username);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        return lookupEventsWithUsernameAndTimeframe(
                repositoryManager.getEventRepository(eventType), username, timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeAndUsername(Class<?> eventType, String username) {
        try {
            validateNotNull(eventType, username);
            validateUsername(username);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);
        Stream<EventMappedSuperclass> events =
                StreamSupport.stream(repository.findAllByUsername(username).spliterator(), true);

        return Optional.of(new EventsCollectionView(
                events.map(EventMappedSuperclass::toEventView).toList()));
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByTypeWithTimeframe(Class<?> eventType, SearchTimeframe timeframe) {
        try {
            validateNotNull(eventType, timeframe);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        return lookupEventsWithTimeframeOnly(repositoryManager.getEventRepository(eventType), timeframe);
    }

    @Override
    @Transactional(noRollbackFor = EventAnnotationNotFoundException.class)
    public Optional<EventsCollectionView> getEventsByType(Class<?> eventType) {
        try {
            validateNotNull(eventType);
            validateEvent(eventType);
        } catch (EventAnnotationNotFoundException | InvalidEventArgumentException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }

        PacketWatcherEventRepository<EventMappedSuperclass, Serializable> repository =
                repositoryManager.getEventRepository(eventType);

        return Optional.of(new EventsCollectionView(repository.findAll().stream()
                .map(EventMappedSuperclass::toEventView)
                .toList()));
    }

    private Optional<EventsCollectionView> lookupEventsWithUsernameAndIpAddressAndTimeframe(
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndIpAddressAndTimestampAfter(
                                        username, ipAddress, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private Optional<EventsCollectionView> lookupEventsWithIpAddressAndTimeframe(
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByIpAddressAndTimestampAfter(
                                        ipAddress, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private Optional<EventsCollectionView> lookupEventsWithUsernameAndTimeframe(
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByUsernameAndTimestampAfter(
                                        username, Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private Optional<EventsCollectionView> lookupEventsWithTimeframeOnly(
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            case AFTER -> {
                events = StreamSupport.stream(
                        repository
                                .findAllByTimestampAfter(Timestamp.valueOf(searchTimeframe.getTimestamp()))
                                .spliterator(),
                        true);
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
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
                return Optional.of(new EventsCollectionView(
                        events.map(EventMappedSuperclass::toEventView).toList()));
            }
            default -> {
                return Optional.empty();
            }
        }
    }
    
    private void validateNotNull(Object... args) throws InvalidEventArgumentException {
        for (Object arg : args) {
            if (arg == null) {
                throw new InvalidEventArgumentException("Null Argument");
            }
        }
    }
    
    private void validateUsername(String username) throws InvalidEventArgumentException {
        if (username.isEmpty() && username.isBlank()) {
            throw new InvalidEventArgumentException("Invalid Username: " + username);
        }
    }
    
    private void validateIpAddress(String address) throws InvalidEventArgumentException {
        if (!inetAddressValidator.isValidInet4Address(address) && !inetAddressValidator.isValidInet6Address(address)) {
            throw new InvalidEventArgumentException("Invalid IpAddress: " + address);
        }
    }

    private void validateEvent(Class<?> eventType) throws EventAnnotationNotFoundException {
        if (!eventType.isAnnotationPresent(PacketWatcherEvent.class)) {
            throw new EventAnnotationNotFoundException();
        }

        if (!eventType.isAnnotationPresent(Entity.class)) {
            throw new EventAnnotationNotFoundException("PacketWatcherEvents must be annotated with @Entity!");
        }
    }
}
