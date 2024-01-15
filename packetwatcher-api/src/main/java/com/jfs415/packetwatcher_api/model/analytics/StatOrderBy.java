package com.jfs415.packetwatcher_api.model.analytics;

public enum StatOrderBy implements StatsTopic {
    NONE(""),
    OLDEST_SEEN("oldest seen"),
    MOST_RECENT_SEEN("most recently seen");

    private final String orderByString;

    StatOrderBy(String orderByString) {
        this.orderByString = orderByString;
    }

    @Override
    public String getTopicString() {
        return orderByString;
    }
}
