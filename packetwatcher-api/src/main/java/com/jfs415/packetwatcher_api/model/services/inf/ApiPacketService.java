package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.model.analytics.RawPacketRecord;
import java.util.List;

public interface ApiPacketService {

    List<RawPacketRecord> getAllFlaggedPacketRecords();

    List<RawPacketRecord> getLast30FlaggedPacketRecords();
}
