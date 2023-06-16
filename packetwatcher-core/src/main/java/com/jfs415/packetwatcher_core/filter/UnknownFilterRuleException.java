package com.jfs415.packetwatcher_core.filter;

public class UnknownFilterRuleException extends RuntimeException {

	public UnknownFilterRuleException() {
		super("Unknown filter rule used!");
	}

	public UnknownFilterRuleException(String message) {
		super(message);
	}

	public UnknownFilterRuleException(Throwable cause) {
		super(cause);
	}

	public UnknownFilterRuleException(String message, Throwable cause) {
		super(message, cause);
	}

}
