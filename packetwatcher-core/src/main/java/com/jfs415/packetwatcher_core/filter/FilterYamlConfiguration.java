package com.jfs415.packetwatcher_core.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("packetwatcher-core.filter")
public record FilterYamlConfiguration(String localIpRangeStart, String localIpRangeEnd, String filterRulesPath) {}
