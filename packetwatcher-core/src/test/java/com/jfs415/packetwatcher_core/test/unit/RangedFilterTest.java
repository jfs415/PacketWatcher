package com.jfs415.packetwatcher_core.test.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.jfs415.packetwatcher_core.filter.IpComparator;
import com.jfs415.packetwatcher_core.filter.RangedFilter;

@Order(2)
class RangedFilterTest {

    private final RangedFilter<String> ipRangedFilter = new RangedFilter<>("192.168.1.1", "192.168.1.255", new IpComparator());

    @Test
    void ipRangeInclusiveTests() {

        //Tests outside range
        assert !ipRangedFilter.isFilterValue("0.0.0.0");
        assert !ipRangedFilter.isFilterValue("192.168.1.0");
        assert !ipRangedFilter.isFilterValue("192.168.2.0");
        assert !ipRangedFilter.isFilterValue("192.168.1.266");
        assert !ipRangedFilter.isFilterValue("192.168.1.1.192.168");
        assert !ipRangedFilter.isFilterValue("192...");
        assert !ipRangedFilter.isFilterValue("1.168.1.");
        assert !ipRangedFilter.isFilterValue("1.168.1");

        //Tests inside range
        assert ipRangedFilter.isFilterValue("192.168.1.1");
        assert ipRangedFilter.isFilterValue("192.168.1.255");
        assert ipRangedFilter.isFilterValue("192.168.1.200");
        assert ipRangedFilter.isFilterValue("192.168.1.20");
    }

    @Test
    void ipRangeExclusiveTests() {

        //Tests outside range
        assert !ipRangedFilter.isInRangeExclusive("0.0.0.0");
        assert !ipRangedFilter.isInRangeExclusive("192.168.1.0");
        assert !ipRangedFilter.isInRangeExclusive("192.168.2.0");
        assert !ipRangedFilter.isInRangeExclusive("192.168.1.266");
        assert !ipRangedFilter.isInRangeExclusive("192.168.1.1.192.168");
        assert !ipRangedFilter.isInRangeExclusive("192...");
        assert !ipRangedFilter.isInRangeExclusive("1.168.1.");
        assert !ipRangedFilter.isInRangeExclusive("1.168.1");
        assert !ipRangedFilter.isInRangeExclusive("192.168.1.1");
        assert !ipRangedFilter.isInRangeExclusive("192.168.1.255");

        //Tests inside range
        assert ipRangedFilter.isInRangeExclusive("192.168.1.2");
        assert ipRangedFilter.isInRangeExclusive("192.168.1.254");
        assert ipRangedFilter.isInRangeExclusive("192.168.1.200");
        assert ipRangedFilter.isInRangeExclusive("192.168.1.20");
    }

    @Test
    void ipRangeLowerInclusiveUpperExclusiveTests() {

        //Tests outside range
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("0.0.0.0");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.0");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.2.0");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.266");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.1.192.168");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192...");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("1.168.1.");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("1.168.1");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.255");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.256");
        assert !ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.0");

        //Tests inside range
        assert ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.1");
        assert ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.2");
        assert ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.254");
        assert ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.200");
        assert ipRangedFilter.isInRangeLowerInclusiveUpperExclusive("192.168.1.20");
    }

    @Test
    void ipRangeLowerExclusiveUpperInclusiveTests() {

        //Tests outside range
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("0.0.0.0");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.0");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.2.0");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.266");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.1.192.168");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192...");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("1.168.1.");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("1.168.1");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.256");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.0");
        assert !ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.1");

        //Tests inside range
        assert ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.2");
        assert ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.254");
        assert ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.255");
        assert ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.200");
        assert ipRangedFilter.isInRangeLowerExclusiveUpperInclusive("192.168.1.20");
    }

}
