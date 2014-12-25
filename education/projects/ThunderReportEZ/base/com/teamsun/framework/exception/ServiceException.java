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
 * ServiceException.java 业务类异常
 * 
 * 
 * @author lf</a>
 * @version Revision:1.0 Date:2006-3-4 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 * 
 * 
 */

public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ServiceException() {
		super();
	}

	/**
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
