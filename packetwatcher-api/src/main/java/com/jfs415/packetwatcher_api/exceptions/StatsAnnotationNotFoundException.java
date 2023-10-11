package com.jfs415.packetwatcher_api.exceptions;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherStats;

public class StatsAnnotationNotFoundException extends RuntimeException {

    public StatsAnnotationNotFoundException() {
        super("PacketWatcherEvents must be annotated with @" + PacketWatcherStats.class.getSimpleName() + "!");
    }

    public StatsAnnotationNotFoundException(String message) {
        super(message);
    }

}
