package com.jfs415.packetwatcher_core.filter;

import java.util.StringTokenizer;

public class IpComparator implements RangedComparator<String, String, String> {

    @Override
    public boolean isInRangeInclusive(String value, String floor, String ceiling) {
        if (value != null && floor != null && ceiling != null && !value.isBlank() && !floor.isBlank() && !ceiling.isBlank()) {
            return validate(value, floor, ceiling, TokenizerEvaluator.RANGE_INCLUSIVE);
        } else {
            return false;
        }
    }

    @Override
    public boolean isInRangeExclusive(String value, String floor, String ceiling) {
        if (value != null && floor != null && ceiling != null && !value.isBlank() && !floor.isBlank() && !ceiling.isBlank()) {
            return validate(value, floor, ceiling, TokenizerEvaluator.RANGE_EXCLUSIVE);
        } else {
            return false;
        }
    }

    @Override
    public boolean isInRangeLowerExclusiveUpperInclusive(String value, String floor, String ceiling) {
        if (value != null && floor != null && ceiling != null && !value.isBlank() && !floor.isBlank() && !ceiling.isBlank()) {
            return validate(value, floor, ceiling, TokenizerEvaluator.RANGE_LOWER_EXCLUSIVE_UPPER_INCLUSIVE);
        } else {
            return false;
        }

    }

    @Override
    public boolean isInRangeLowerInclusiveUpperExclusive(String value, String floor, String ceiling) {
        if (value != null && floor != null && ceiling != null && !value.isBlank() && !floor.isBlank() && !ceiling.isBlank()) {
            return validate(value, floor, ceiling, TokenizerEvaluator.RANGE_LOWER_INCLUSIVE_UPPER_EXCLUSIVE);
        } else {
            return false;
        }

    }

    private boolean validate(String value, String floor, String ceiling, TokenizerEvaluator evaluator) {
        StringTokenizer valueTokenizer = new StringTokenizer(value, ".");
        StringTokenizer floorTokenizer = new StringTokenizer(floor, ".");
        StringTokenizer ceilingTokenizer = new StringTokenizer(ceiling, ".");

        if (valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens()) {
            return false;
        }

        return evaluator.processTokenizers(valueTokenizer, floorTokenizer, ceilingTokenizer);
    }

    private enum TokenizerEvaluator {
        RANGE_INCLUSIVE {
            @Override
            public boolean processTokenizers(StringTokenizer valueTokenizer, StringTokenizer floorTokenizer, StringTokenizer ceilingTokenizer) {
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
        },

        RANGE_EXCLUSIVE {
            @Override
            public boolean processTokenizers(StringTokenizer valueTokenizer, StringTokenizer floorTokenizer, StringTokenizer ceilingTokenizer) {
                while (valueTokenizer.hasMoreTokens()) {
                    int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
                    int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
                    int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

                    if ((!valueTokenizer.hasMoreTokens() && (valueOctet <= floorOctet || valueOctet >= ceilingOctet))
                            || tokensNotAlike(valueTokenizer, floorTokenizer, ceilingTokenizer)) { //Only last octet is bounded
                        return false;
                    }
                }

                return true;
            }
        },

        RANGE_LOWER_EXCLUSIVE_UPPER_INCLUSIVE {
            @Override
            public boolean processTokenizers(StringTokenizer valueTokenizer, StringTokenizer floorTokenizer, StringTokenizer ceilingTokenizer) {
                while (valueTokenizer.hasMoreTokens()) {
                    int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
                    int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
                    int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

                    if ((!valueTokenizer.hasMoreTokens() && (valueOctet <= floorOctet || valueOctet > ceilingOctet))
                            || tokensNotAlike(valueTokenizer, floorTokenizer, ceilingTokenizer)) { //Only last octet is bounded
                        return false;
                    }
                }

                return true;
            }
        },

        RANGE_LOWER_INCLUSIVE_UPPER_EXCLUSIVE {
            @Override
            public boolean processTokenizers(StringTokenizer valueTokenizer, StringTokenizer floorTokenizer, StringTokenizer ceilingTokenizer) {
                while (valueTokenizer.hasMoreTokens()) {
                    int valueOctet = Integer.parseInt(valueTokenizer.nextToken());
                    int floorOctet = Integer.parseInt(floorTokenizer.nextToken());
                    int ceilingOctet = Integer.parseInt(ceilingTokenizer.nextToken());

                    if ((!valueTokenizer.hasMoreTokens() && (valueOctet < floorOctet || valueOctet >= ceilingOctet))
                            || tokensNotAlike(valueTokenizer, floorTokenizer, ceilingTokenizer)) { //Only last octet is bounded
                        return false;
                    }
                }

                return true;
            }
        };

        public boolean processTokenizers(StringTokenizer valueTokenizer, StringTokenizer floorTokenizer, StringTokenizer ceilingTokenizer) {
            return valueTokenizer != null && floorTokenizer != null && ceilingTokenizer != null;
        }

        protected boolean tokensNotAlike(StringTokenizer valueTokenizer, StringTokenizer floorTokenizer, StringTokenizer ceilingTokenizer) {
            return valueTokenizer.countTokens() != floorTokenizer.countTokens() || valueTokenizer.countTokens() != ceilingTokenizer.countTokens();
        }

    }

}
