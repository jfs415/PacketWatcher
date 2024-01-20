package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.model.analytics.StatsRecord;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class StatsView implements Serializable {

    private final String name;
    private final int recordsCaught;
    private final Timestamp firstCaught;
    private final Timestamp lastCaught;
    private final Timestamp lastCollectionTime;

    public StatsView(StatsRecord statsRecord) {
        this.name = statsRecord.getName();
        this.recordsCaught = statsRecord.getRecordsCaught();
        this.firstCaught = statsRecord.getFirstCaught();
        this.lastCaught = statsRecord.getLastCaught();
        this.lastCollectionTime = statsRecord.getLastCollectionTime();
    }
}
