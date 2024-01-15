package com.jfs415.packetwatcher_api.model.analytics;

public enum StatsDashboardTopic implements StatsTopic {
    HOSTNAME("hostnames"),
    COUNTRY("country's"),
    TIME_OF_DAY("times of the day"),
    DAY_OF_WEEK("days of the week"),
    DAY_OF_MONTH("days of the month");

    private final String statsTopic;

    StatsDashboardTopic(String statsTopic) {
        this.statsTopic = statsTopic;
    }

    @Override
    public String getTopicString() {
        return statsTopic;
    }
}
