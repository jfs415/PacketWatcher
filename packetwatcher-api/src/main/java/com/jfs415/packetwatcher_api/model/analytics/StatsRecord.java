package com.jfs415.packetwatcher_api.model.analytics;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

	public StatsRecord() { }

	public StatsRecord(String name, int recordsCaught, Timestamp firstCaught, Timestamp lastCaught) {
		this.name = name;
		this.recordsCaught = recordsCaught;
		this.firstCaught = firstCaught;
		this.lastCaught = lastCaught;
		this.lastCollectionTime = new Timestamp(System.currentTimeMillis());
	}

	public int getRecordsCaught() {
		return recordsCaught;
	}

	public void setRecordsCaught(int recordsCaught) {
		this.recordsCaught = recordsCaught;
	}

	public Timestamp getLastCaught() {
		return lastCaught;
	}

	public void setLastCaught(Timestamp lastCaught) {
		this.lastCaught = lastCaught;
	}

	public Timestamp getFirstCaught() {
		return firstCaught;
	}

	public void setFirstCaught(Timestamp firstCaught) {
		this.firstCaught = firstCaught;
	}
	
	public Timestamp getLastCollectionTime() {
		return lastCollectionTime;
	}
	
	public void setLastCollectionTime(Timestamp lastCollectionTime) {
		this.lastCollectionTime = lastCollectionTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String statName) {
		this.name = statName;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof StatsRecord) {
			StatsRecord obj = (StatsRecord) other;

			return this.name.equals(obj.getName()) && this.firstCaught.equals(obj.getFirstCaught()) 
					&& this.lastCaught.equals(obj.getLastCaught()) && this.recordsCaught == obj.getRecordsCaught() 
					&& this.lastCollectionTime.equals(obj.lastCollectionTime);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * name.hashCode() + firstCaught.hashCode() + lastCaught.hashCode() + lastCollectionTime.hashCode() + recordsCaught;
	}

}
