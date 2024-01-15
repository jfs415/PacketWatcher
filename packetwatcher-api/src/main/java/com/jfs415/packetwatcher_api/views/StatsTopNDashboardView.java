package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.model.analytics.StatDashboardData;
import com.jfs415.packetwatcher_api.model.analytics.StatOrderBy;
import com.jfs415.packetwatcher_api.model.analytics.StatsTopic;

import java.util.List;

public record StatsTopNDashboardView(
        int topCount, StatsTopic topic, StatOrderBy breakdown, String name, List<StatDashboardData> data) {}
