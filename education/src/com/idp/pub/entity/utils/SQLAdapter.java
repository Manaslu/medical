package com.idp.pub.entity.utils;

/**
 * 适配传入自定义完整sql语句
 * 
 * @author panfei
 * 
 */
public class SQLAdapter {

	private String sql;

	public SQLAdapter(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
