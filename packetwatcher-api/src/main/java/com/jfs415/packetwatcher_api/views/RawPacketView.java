package com.jfs415.packetwatcher_api.views;

public record RawPacketView(
        String timestamp,
        String destinationHost,
        String destinationPort,
        String sourceHost,
        String sourcePort,
        String flaggedCountry) {}
