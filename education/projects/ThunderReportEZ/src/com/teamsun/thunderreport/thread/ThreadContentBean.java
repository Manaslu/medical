package com.teamsun.thunderreport.thread;

/**
 * 描述线程执行情况
 * 
 */
public class ThreadContentBean {

	/**
	 * 当前预生成的文件名
	 */
	private String filename;

	/**
	 * 一共要预生成的文件数
	 */
	private int filecount;

	/**
	 * 已经完成的预生成文件数
	 */
	private int finishcount;

	/**
	 * 当前文件要生成几张预先文件
	 */
	private int subfilecount;

	/**
	 * 当前文件的参数内容
	 */
	private String[] params;

	/**
	 * 操作开始的时间
	 */
	private long begintime;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFilecount() {
		return filecount;
	}

	public void setFilecount(int filecount) {
		this.filecount = filecount;
	}

	public int getFinishcount() {
		return finishcount;
	}

	public void setFinishcount(int finishcount) {
		this.finishcount = finishcount;
	}

	public int getSubfilecount() {
		return subfilecount;
	}

	public void setSubfilecount(int subfilecount) {
		this.subfilecount = subfilecount;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public long getBegintime() {
		return begintime;
	}

	public void setBegintime(long begintime) {
		this.begintime = begintime;
	}

}
