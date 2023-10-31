package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import com.jfs415.packetwatcher_api.model.repositories.RawPacketRepository;
import com.jfs415.packetwatcher_api.model.services.inf.PacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PacketServiceImpl implements PacketService {

    private final RawPacketRepository packetRepository;

    private static final Logger logger = LoggerFactory.getLogger(PacketServiceImpl.class);

    @Autowired
    public PacketServiceImpl(RawPacketRepository packetRepository) {
        this.packetRepository = packetRepository;
    }

    public List<RawPacketRecord> getAllFlaggedPacketRecords() {
        return packetRepository.findAll();
    }

    public List<RawPacketRecord> getLast30FlaggedPacketRecords() {
        return packetRepository.findTop30ByOrderByKey_TimestampDesc();
    }

    private long convertLocalDateTimeToEpochSecond(LocalDateTime localDateTime, int offset) {
        return localDateTime.minusDays(offset).atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    private void validateNotNullOrEmpty(List<RawPacketRecord> records) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("PacketRecord Lists cannot be null or empty!");
        }
    }

}
