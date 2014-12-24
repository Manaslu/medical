package com.idp.pub.entity;

import java.util.List;

/**
 * 
 * @author Snail
 * 
 */
public class Pager<T> {

	public static final String TOTAL = "total";

	/**
	 * 当前页码
	 * */
	private int current = 0;

	/**
	 * 每页显示的条数
	 */
	private int limit = 5;

	/**
	 * 所有记录的总条数
	 */
	private int total = 0;

	/**
	 * 显示的list
	 */
	private List<T> data;

	// 总页数
	private int totalPage;

	// 当总条数发生变化时，需要重新加载，将该至设置为true
	private boolean reload = false;// 重新加载

	public boolean isReload() {
		return reload;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotalPage() {
		this.totalPage = this.total % this.limit == 0 ? this.total / this.limit
				: (this.total / this.limit) + 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
