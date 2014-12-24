package com.idp.pub.sqlresolver.entity;

/**
 * 分页参数
 * 
 * @author panfei
 * 
 */
public class PageParams {

	private String tableName;

	private String[] colNames;

	private String wherePartStr;

	private String orderPartStr;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public String getWherePartStr() {
		return wherePartStr;
	}

	public void setWherePartStr(String wherePartStr) {
		this.wherePartStr = wherePartStr;
	}

	public String getOrderPartStr() {
		return orderPartStr;
	}

	public void setOrderPartStr(String orderPartStr) {
		this.orderPartStr = orderPartStr;
	}

}
