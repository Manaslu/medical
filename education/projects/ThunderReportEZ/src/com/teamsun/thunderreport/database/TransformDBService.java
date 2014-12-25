package com.teamsun.thunderreport.database;

import java.sql.ResultSet;

public interface TransformDBService {

	/**
	 * 返回tablename
	 * 
	 * @param rs
	 * @return
	 */
	public String transformDB(ResultSet rs,String tableName);

	/**
	 * 设置数据名
	 * 
	 * @param name
	 */
	public void setDatabaseName(String name);

	
	public  String createTable(ResultSet rs);
	/**
	 * 创建索引
	 * 
	 * @param tablename
	 * @param indexField
	 */
	public void createIndexField(String tablename, String indexField);

	/**
	 * 删除表
	 * 
	 * @param tables
	 */
	public void dropTables(String[] tables);

}
