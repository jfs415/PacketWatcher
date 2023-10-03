package com.jfs415.packetwatcher_core.filter;

import java.util.StringTokenizer;

public class IpComparator implements RangedComparator<String, String, String> {

    @Override
    public boolean isInRangeInclusive(String value, String floor, String ceiling) {
        if (value == null || floor == null || ceiling == null || value.isBlank() || floor.isBlank() || ceiling.isBlank()) {
            return false;
        }

        StringTokenizer valueTokenizer = new StringTokenizer(value, ".");
        StringTokenizer floorTokenizer = new StringTokenizer(floor, ".");
        StringTokenizer ceilingTokenizer = new StringTokenizer(ceiling, ".");

        if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
            return false;
        }

        while (valueTokenizer.hasMoreTokens()) {
            int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
            int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
            int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

            if (valueOctet < floorOctet || valueOctet > ceilingOctet) {
                return false;
            }

        }

        return true;
    }

    @Override
    public boolean isInRangeExclusive(String value, String floor, String ceiling) {
        if (value == null || floor == null || ceiling == null || value.isBlank() || floor.isBlank() || ceiling.isBlank()) {
            return false;
        }

        StringTokenizer valueTokenizer = new StringTokenizer(value, ".");
        StringTokenizer floorTokenizer = new StringTokenizer(floor, ".");
        StringTokenizer ceilingTokenizer = new StringTokenizer(ceiling, ".");

        if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
            return false;
        }

        while (valueTokenizer.hasMoreTokens()) {
            int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
            int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
            int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

            if (!valueTokenizer.hasMoreTokens() && (valueOctet <= floorOctet || valueOctet >= ceilingOctet)) { //Only last octet is bounded
                return false;
            } else if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
                return false;
            }

        }

        return true;
    }

    @Override
    public boolean isInRangeLowerExclusiveUpperInclusive(String value, String floor, String ceiling) {
        if (value == null || floor == null || ceiling == null || value.isBlank() || floor.isBlank() || ceiling.isBlank()) {
            return false;
        }

        StringTokenizer valueTokenizer = new StringTokenizer(value, ".");
        StringTokenizer floorTokenizer = new StringTokenizer(floor, ".");
        StringTokenizer ceilingTokenizer = new StringTokenizer(ceiling, ".");

        if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
            return false;
        }

        while (valueTokenizer.hasMoreTokens()) {
            int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
            int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
            int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

            if (!valueTokenizer.hasMoreTokens() && (valueOctet <= floorOctet || valueOctet > ceilingOctet)) { //Only last octet is bounded
                return false;
            } else if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
                return false;
            }

        }

        return true;
    }

    @Override
    public boolean isInRangeLowerInclusiveUpperExclusive(String value, String floor, String ceiling) {
        if (value == null || floor == null || ceiling == null || value.isBlank() || floor.isBlank() || ceiling.isBlank()) {
            return false;
        }

        StringTokenizer valueTokenizer = new StringTokenizer(value, ".");
        StringTokenizer floorTokenizer = new StringTokenizer(floor, ".");
        StringTokenizer ceilingTokenizer = new StringTokenizer(ceiling, ".");

        if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
            return false;
        }

        while (valueTokenizer.hasMoreTokens()) {
            int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
            int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
            int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

            if (!valueTokenizer.hasMoreTokens() && (valueOctet < floorOctet || valueOctet >= ceilingOctet)) { //Only last octet is bounded
                return false;
            } else if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
                return false;
            }

        }

        return true;
    }

}
