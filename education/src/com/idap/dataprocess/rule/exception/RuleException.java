package com.idap.dataprocess.rule.exception;

public class RuleException extends Exception {
	private static final long serialVersionUID = -4693696925812141609L;

	public RuleException() {
		super();
	}

	public RuleException(String message) {
		super(message);
	}

	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuleException(Throwable cause) {
		super(cause);
	}
}
