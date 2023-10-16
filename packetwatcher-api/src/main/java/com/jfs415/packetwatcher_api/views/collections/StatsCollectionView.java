package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.StatsView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Immutable
public class StatsCollectionView {

    private final List<StatsView> statsViews;

    public StatsCollectionView() {
        this.statsViews = new ArrayList<>();
    }

}
