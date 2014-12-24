package com.idp.workflow.exception.pub;

/**
 * 
 * @author panfei
 * 
 */
public class ProcessNotFoundException extends WfBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4223789585987445030L;

	public ProcessNotFoundException(String message) {
		super(message);
	}

	public ProcessNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessNotFoundException(Throwable cause) {
		super(cause);
	}
}
