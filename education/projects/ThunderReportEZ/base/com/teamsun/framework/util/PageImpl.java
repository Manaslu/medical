/* 
 * Copyright (c) 1994-2006 Teamsun
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Finalist IT Group, Inc.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Teamsun.
 * 
 */
package com.teamsun.framework.util;

import java.util.List;

/**
 * 
 * 分页的结果类
 */
public class PageImpl {
	/**
	 * Logger for this class
	 */
	// private static final Logger logger = Logger.getLogger(PageImpl.class);
	/**
	 * 
	 */
	private List results;

	private int pageNo;

	private int pageCount;

	private int pageSize;

	private int recordSize;

	public PageImpl(List results, int pageNo, int pageSize, int recordSize) {
		this.results = results;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.recordSize = recordSize;
	}

	public PageImpl() {
		super();
		this.pageNo = 1;
		this.pageSize = 10;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public boolean hasNext() {
		return this.pageNo < this.pageCount ? true : false;
	}

	public boolean hasPrevious() {
		return this.pageNo > 1 ? true : false;
	}

}