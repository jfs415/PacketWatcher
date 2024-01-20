package com.jfs415.packetwatcher_core.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("packetwatcher-core.filter")
@Getter
@Setter
@NoArgsConstructor
public class FilterYamlConfiguration {

    private String localIpRangeStart;
    private String localIpRangeEnd;
    private String filterRulesPath;
}
