package com.jfs415.packetwatcher_core.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfs415.packetwatcher_core.PacketWatcherCore;
import com.jfs415.packetwatcher_core.filter.FilterRulesManager;

@SpringBootTest(classes = PacketWatcherCore.class)
class FilterRuleManagerTest {

    private final FilterRulesManager filterRulesManager;

    @Autowired
    public FilterRuleManagerTest(FilterRulesManager filterRulesManager) {
        this.filterRulesManager = filterRulesManager;
    }

    @Test
    void ipRangeInclusiveTests() {

        assert filterRulesManager != null;

        //Tests outside range
        assert !filterRulesManager.isIpInLocalRange("0.0.0.0");
        assert !filterRulesManager.isIpInLocalRange("192.168.1.0");
        assert !filterRulesManager.isIpInLocalRange("192.168.2.0");
        assert !filterRulesManager.isIpInLocalRange("192.168.1.266");
        assert !filterRulesManager.isIpInLocalRange("192.168.1.1.192.168");
        assert !filterRulesManager.isIpInLocalRange("192...");
        assert !filterRulesManager.isIpInLocalRange("1.168.1.");
        assert !filterRulesManager.isIpInLocalRange("1.168.1");

        //Tests inside range
        assert filterRulesManager.isIpInLocalRange("192.168.1.1");
        assert filterRulesManager.isIpInLocalRange("192.168.1.255");
        assert filterRulesManager.isIpInLocalRange("192.168.1.200");
        assert filterRulesManager.isIpInLocalRange("192.168.1.20");
    }

}
