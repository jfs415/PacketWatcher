package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecordKey;

import java.util.List;

public interface RawPacketRepository extends LimitedAccessRepository<RawPacketRecord, RawPacketRecordKey> {

    List<RawPacketRecord> findTop30ByOrderByKey_TimestampDesc();
    
    List<RawPacketRecord> findAll();

}
