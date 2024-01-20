package com.jfs415.packetwatcher_api.model.user;

import com.jfs415.packetwatcher_api.views.LockedUserHistoryView;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    public LockedUserHistoryView toLockedUserHistoryView() {
        return new LockedUserHistoryView(this);
    }
}
