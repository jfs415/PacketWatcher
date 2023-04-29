package com.jfs415.packetwatcher_api.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;

@Entity
@Table(name = "locked_user_history", schema = "packetwatcher")
public class LockedUserHistory implements Serializable {

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "first_locked")
	private Timestamp firstLocked;

	@Column(name = "last_locked")
	private Timestamp lastLocked;

	@Column(name = "number_of_times_locked")
	private int numberOfTimesLocked;

	public LockedUserHistory(String username, long timeLocked) {
		Timestamp timestamp = new Timestamp(timeLocked);

		this.username = username;
		this.firstLocked = timestamp;
		this.lastLocked = timestamp;
		this.numberOfTimesLocked = 1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getFirstLocked() {
		return firstLocked;
	}

	public void setFirstLocked(Timestamp firstLocked) {
		this.firstLocked = firstLocked;
	}

	public Timestamp getLastLocked() {
		return lastLocked;
	}

	public void setLastLocked(Timestamp lastLocked) {
		this.lastLocked = lastLocked;
	}

	public int getNumberOfTimesLocked() {
		return numberOfTimesLocked;
	}

	public void setNumberOfTimesLocked(int numberOfTimesLocked) {
		this.numberOfTimesLocked = numberOfTimesLocked;
	}

	public LockedUserHistoryView toLockedUserHistoryView() {
		return new LockedUserHistoryView(this);
	}

}
