/* 
 * Copyright (c) 1994-2006 Teamsun
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Finalist IT Group, Inc.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Teamsun.
 * 
 */
package com.teamsun.framework.exception;

/**
 * 数据访问对象运行异常.
 * 
 * @author marshal
 * 
 */
public class DaoRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DaoRuntimeException() {
		super();
	}

	/**
	 * @param message
	 */
	public DaoRuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DaoRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DaoRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
