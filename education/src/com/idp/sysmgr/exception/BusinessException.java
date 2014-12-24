package com.idp.sysmgr.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 5181139361103044615L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}
}
