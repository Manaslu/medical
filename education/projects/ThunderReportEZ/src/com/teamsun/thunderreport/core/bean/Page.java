package com.teamsun.thunderreport.core.bean;

public class Page {

	/**
	 * 每页记录数
	 */
	private int pagesize = 0;

	/**
	 * 当前页
	 */
	private int pageindex = 0;

	/**
	 * 一共的记录数
	 */
	private int recordcount = 0;
	
	public void init(){
		pageindex = 0;
		recordcount = 0;		
	}

	public boolean hasNext() {
		return this.pageindex < this.getPagecount();
	}

	public boolean hasPrevious() {
		return this.getPageindex() > 1;
	}

	public static Page[] splitPage(int recordcount, int pagesize) {
		int count = (int) Math.ceil((double) recordcount / (double) pagesize);
		Page[] result = new Page[count];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Page();
			result[i].setPageindex(i + 1);
			result[i].setRecordcount(recordcount);
			result[i].setPagesize(pagesize);
		}
		return result;
	}

	/**
	 * 每页多少记录数
	 * 
	 * @return
	 */
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * 一共有多少页
	 * 
	 * @return
	 */
	public int getPagecount() {
		int count = (int) Math.ceil((double) recordcount / (double) pagesize);
		return count;
	}

	/**
	 * 当前是第多少页
	 * 
	 * @return
	 */
	public int getPageindex() {
		return pageindex;
	}

	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}

	/**
	 * 一共多少记录数
	 * 
	 * @return
	 */
	public int getRecordcount() {
		return recordcount;
	}

	public void setRecordcount(int recordcount) {
		this.recordcount = recordcount;
	}

	/**
	 * 当前页有多少记录数
	 * 
	 * @return
	 */
	public int getIndexpagesize() {
		int n = this.recordcount - (this.getPageindex() - 1)
				* this.getPagesize();
		if (n < 0)
			return this.recordcount;
		else
			return n > this.getPagesize() ? this.getPagesize() : n;
	}

}
