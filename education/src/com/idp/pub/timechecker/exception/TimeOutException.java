package com.idp.pub.timechecker.exception;

import com.idp.pub.exception.BusinessException;

/**
 * 前台的ts与数据库不一致的时候抛出异常
 * 
 * @author panfei
 * 
 */
public class TimeOutException extends BusinessException {

	public TimeOutException(String errorMessage) {
		super(errorMessage);
	}

	public TimeOutException(Throwable e) {
		super(e);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7501738772370070222L;

}
