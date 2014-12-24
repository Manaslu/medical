package com.idp.pub.serverlocker.exception;

import com.idp.pub.exception.BusinessException;

/**
 * 同步操作锁冲突异常
 * 
 * @author panfei
 * 
 */
public class LockFailedException extends BusinessException {

	public LockFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockFailedException(String message) {
		super(message);
	}

	public LockFailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6442906582935156803L;

}
