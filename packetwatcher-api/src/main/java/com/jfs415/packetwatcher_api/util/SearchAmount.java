package com.jfs415.packetwatcher_api.util;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Immutable
public class SearchAmount implements SearchFilter {

    private final int amount;
    private final SearchAmountOperator operator;

    protected SearchAmount(SearchAmountOperator operator) {
        this.operator = operator;
        this.amount = 0;
    }

    private SearchAmount(int amount, SearchAmountOperator operator) {
        this.amount = amount;
        this.operator = operator;
    }

    public static SearchAmount greaterThan(int amount) {
        return new SearchAmount(amount, SearchAmountOperator.GREATER_THAN);
    }

    public static SearchAmount lessThan(int amount) {
        return new SearchAmount(amount, SearchAmountOperator.LESS_THAN);
    }

    public static SearchAmount greaterThanOrEqualTo(int amount) {
        return new SearchAmount(amount, SearchAmountOperator.GREATER_THAN_OR_EQUAL_TO);
    }

    public static SearchAmount lessThanOrEqualTo(int amount) {
        return new SearchAmount(amount, SearchAmountOperator.LESS_THAN_OR_EQUAL_TO);
    }

    public boolean passesOperatorCheck(long amountToCheck) {
        switch (operator) {
            case LESS_THAN -> {
                return amountToCheck < amount;
            }
            case LESS_THAN_OR_EQUAL_TO -> {
                return amountToCheck <= amount;
            }
            case EQUAL_TO -> {
                return amountToCheck == amount;
            }
            case GREATER_THAN -> {
                return amountToCheck > amount;
            }
            case GREATER_THAN_OR_EQUAL_TO -> {
                return amountToCheck >= amount;
            }
        }
        return false;
    }

    @Override
    public String getFilterString() {
        return operator.getOperatorString() + amount;
    }
}
