package com.jfs415.packetwatcher_api.util;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Getter
@Immutable
public final class RangedSearchAmount extends SearchAmount {

    private final int start;
    private final int end;

    private RangedSearchAmount(int start, int end, Operator operator) {
        super(operator);
        this.start = start;
        this.end = end;
    }

    public static RangedSearchAmount between(int start, int end) {
        return new RangedSearchAmount(start, end, Operator.BETWEEN);
    }

}
