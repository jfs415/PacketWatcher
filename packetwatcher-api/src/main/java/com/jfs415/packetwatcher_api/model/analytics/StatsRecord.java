package com.jfs415.packetwatcher_api.model.analytics;

import com.jfs415.packetwatcher_api.views.StatsView;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class StatsRecord implements Serializable {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "records_caught")
    private int recordsCaught;

    @Column(name = "last_caught")
    private Timestamp lastCaught;

    @Column(name = "first_caught")
    private Timestamp firstCaught;

    @Column(name = "last_collection_time")
    private Timestamp lastCollectionTime;

    public StatsView toStatsView() {
        return new StatsView(name, recordsCaught, firstCaught, lastCaught, lastCollectionTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StatsRecord statsRecord) {
            return this.name.equals(statsRecord.getName())
                    && this.firstCaught.equals(statsRecord.getFirstCaught())
                    && this.lastCaught.equals(statsRecord.getLastCaught())
                    && this.recordsCaught == statsRecord.getRecordsCaught()
                    && this.lastCollectionTime.equals(statsRecord.lastCollectionTime);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode()
                + firstCaught.hashCode()
                + lastCaught.hashCode()
                + lastCollectionTime.hashCode()
                + recordsCaught;
    }
}
