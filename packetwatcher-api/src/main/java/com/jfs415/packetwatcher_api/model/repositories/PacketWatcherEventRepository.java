package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.sql.Timestamp;

@NoRepositoryBean
public interface PacketWatcherEventRepository<T extends EventMappedSuperclass, E extends Serializable> extends JpaRepository<T, E> {

    Iterable<T> findAllByUsername(String username);

    Iterable<T> findAllByIpAddress(String ipAddress);

    Iterable<T> findAllByTimestampBefore(Timestamp before);

    Iterable<T> findAllByTimestampAfter(Timestamp after);

    Iterable<T> findAllByTimestampBetween(Timestamp start, Timestamp end);

    Iterable<T> findAllByUsernameAndTimestampBefore(String username, Timestamp before);

    Iterable<T> findAllByUsernameAndTimestampAfter(String username, Timestamp after);

    Iterable<T> findAllByUsernameAndTimestampBetween(String username, Timestamp start, Timestamp stop);

    Iterable<T> findAllByIpAddressAndTimestampBefore(String ipAddress, Timestamp before);

    Iterable<T> findAllByIpAddressAndTimestampBetween(String ipAddress, Timestamp start, Timestamp end);

    Iterable<T> findAllByIpAddressAndUsername(String ipAddress, String username);

    Iterable<T> findAllByIpAddressAndTimestampAfter(String ipAddress, Timestamp after);

    Iterable<T> findAllByUsernameAndIpAddressAndTimestampBefore(String username, String ipAddress, Timestamp timestamp);

    Iterable<T> findAllByUsernameAndIpAddressAndTimestampBetween(String username, String ipAddress, Timestamp start, Timestamp end);

    Iterable<T> findAllByUsernameAndIpAddressAndTimestampAfter(String username, String ipAddress, Timestamp after);

}
