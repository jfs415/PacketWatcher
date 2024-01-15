package com.jfs415.packetwatcher_api.util;

public enum SearchAmountOperator implements SearchOperator {
    GREATER_THAN("greater than"),
    GREATER_THAN_OR_EQUAL_TO("greater than or equal to"),
    EQUAL_TO("equal to"),
    LESS_THAN("less than"),
    LESS_THAN_OR_EQUAL_TO("les than or equal to"),
    BETWEEN("between");

    private final String operatorString;

    SearchAmountOperator(String operatorString) {
        this.operatorString = operatorString;
    }

    @Override
    public String getOperatorString() {
        return operatorString;
    }
}
