package com.idp.workflow.itf.dao;

/**
 * 
 * @author panfei
 * 
 */
public class SqlPartHandler {

	public SqlPartHandler(String sqlwherepart) {
		this.setSqlPart(sqlwherepart);
	}

	private String sqlPart;

	public String getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}
}
