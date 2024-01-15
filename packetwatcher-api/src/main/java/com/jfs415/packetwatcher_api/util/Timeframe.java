package com.jfs415.packetwatcher_api.util;

public enum Timeframe implements SearchOperator {
    BEFORE("before"),
    AFTER("after"),
    BETWEEN("between");

    private final String operatorString;

    Timeframe(String operatorString) {
        this.operatorString = operatorString;
    }

    @Override
    public String getOperatorString() {
        return operatorString;
    }
}
