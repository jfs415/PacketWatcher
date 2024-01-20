package com.jfs415.packetwatcher_api.util;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Immutable
public class SearchAmount {

    private final int amount;
    private final Operator operator;

    protected SearchAmount(Operator operator) {
        this.operator = operator;
        this.amount = 0;
    }

    private SearchAmount(int amount, Operator operator) {
        this.amount = amount;
        this.operator = operator;
    }

    public static SearchAmount greaterThan(int amount) {
        return new SearchAmount(amount, Operator.GREATER_THAN);
    }

    public static SearchAmount lessThan(int amount) {
        return new SearchAmount(amount, Operator.LESS_THAN);
    }

    public static SearchAmount greaterThanOrEqualTo(int amount) {
        return new SearchAmount(amount, Operator.GREATER_THAN_OR_EQUAL_TO);
    }

    public static SearchAmount lessThanOrEqualTo(int amount) {
        return new SearchAmount(amount, Operator.LESS_THAN_OR_EQUAL_TO);
    }

    public enum Operator {
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL_TO,
        LESS_THAN,
        LESS_THAN_OR_EQUAL_TO,
        BETWEEN;
    }
}
