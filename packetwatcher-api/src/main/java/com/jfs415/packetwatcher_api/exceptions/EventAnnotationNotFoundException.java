package com.jfs415.packetwatcher_api.exceptions;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherEvent;

public class EventAnnotationNotFoundException extends RuntimeException {

    public EventAnnotationNotFoundException() {
        super("PacketWatcherEvents must be annotated with @" + PacketWatcherEvent.class.getSimpleName() + "!");
    }

    public EventAnnotationNotFoundException(String message) {
        super(message);
    }

}
