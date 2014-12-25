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
 * ConvertException.java 类型转换异常类
 * 
 * 
 * @author lf</a>
 * @version Revision:1.0 Date:2006-3-4 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 * 
 * 
 */

public class ConvertException extends RootException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 */
	public ConvertException() {
		super();

	}

	/**
	 * @param message
	 */
	public ConvertException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConvertException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ConvertException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
