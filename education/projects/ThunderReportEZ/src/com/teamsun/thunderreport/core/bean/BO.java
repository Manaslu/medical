package com.teamsun.thunderreport.core.bean;

public class BO {

	/**
	 * 通过id标示数据源 要同spring 配置文件中配置的一样
	 */
	private String id;

	private String database;

	private String index;

	private String sqltext;

	/**
	 * 实际查询的表名称，程序通过这个域来进行替换，换成内存数据库的名称
	 */
	private String table;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSqltext() {
		return sqltext;
	}

	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}
