package com.idp.workflow.exception.pub;

import com.idp.pub.exception.BusinessException;

/**
 * 工作流异常基类
 * 
 * @author panfei
 * 
 */
public class WfBusinessException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7526092219872636428L;

	public WfBusinessException() {
		super();
	}

	public WfBusinessException(String message) {
		super(message);
	}

	public WfBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public WfBusinessException(Throwable cause) {
		super(cause);
	}

}
