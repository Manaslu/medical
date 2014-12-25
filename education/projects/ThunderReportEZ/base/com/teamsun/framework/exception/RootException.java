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

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * RootException.java 所有异常类的基类
 * 
 * 
 * @author lf</a>
 * @version Revision:1.0 Date:2006-3-4 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 * 
 * 
 */

public class RootException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Throwable cause;

	/**
	 * Constructs a new exception with null as its detail message.
	 * 
	 */
	public RootException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * 
	 */
	public RootException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause.
	 * 
	 * @param t
	 *            the cause. A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.
	 * 
	 */
	public RootException(Throwable t) {
		super(t.getMessage());
		try {
			initCause(t);
		} catch (Throwable e) {
		}
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message
	 *            the detail message.
	 * @param t
	 *            the cause. A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.
	 */
	public RootException(String message, Throwable t) {
		super(message);
		try {
			initCause(t);
		} catch (Throwable e) {
		}
	}

	/**
	 * Initializes the cause of this exception to the specified value.
	 * 
	 * @param t
	 *            the cause.
	 * 
	 * @return a reference to this instance.
	 * 
	 * @exception IllegalArgumentException
	 * @exception IllegalStateException
	 */
	public Throwable initCause(Throwable t) throws IllegalArgumentException,
			IllegalStateException {
		cause = t;
		return this;
	}

	/**
	 * Returns the cause of this exception.
	 * 
	 * @return a cause for this exception if any, null otherwise
	 * 
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * Method printStackTrace prints the stack trace to the <i>ps</i>
	 * PrintStream.
	 * 
	 * @param ps
	 *            PrintStream used for the output.
	 */
	public void printStackTrace(PrintStream ps) {
		if (null != cause) {
			cause.printStackTrace(ps);
		}
		super.printStackTrace(ps);
	}

	/**
	 * Method printStackTrace prints the stack trace to the <i>pw</i>
	 * PrintWriter.
	 * 
	 * @param pw
	 *            PrintWriter used for the output.
	 */
	public void printStackTrace(PrintWriter pw) {
		if (null != cause) {
			cause.printStackTrace(pw);
		}
		super.printStackTrace(pw);
	}

	/**
	 * Method printStackTrace prints the stack trace to the standard error
	 * stream.
	 */
	public void printStackTrace() {
		printStackTrace(System.err);
	}
}