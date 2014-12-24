package com.idp.workflow.exception.pub;

/**
 * 该功能 工作流不支持或者未实现时抛出
 * 
 * @author panfei
 * 
 */
public class WfNotSupprotException extends WfBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6903338563715806218L;

	public WfNotSupprotException() {
		super();
	}

	public WfNotSupprotException(String message) {
		super(message);
	}

	public WfNotSupprotException(String message, Throwable cause) {
		super(message, cause);
	}

	public WfNotSupprotException(Throwable cause) {
		super(cause);
	}
}
