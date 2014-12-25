package com.teamsun.thunderreport.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 要考虑目标数据库 目前使用 hsqldb
 * 
 */
public class TransformDBDestination implements TransformDBService {

	private static final Log log = LogFactory
			.getLog(TransformDBDestination.class);

	private org.springframework.jdbc.core.JdbcTemplate template;

	public void setDatabaseName(String name) {

	}
	public  String createTable(ResultSet rs) {
		ResultSetMetaData metaData=null;
		try {
			metaData = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tableName = this.createTablename();
		String create_sql = this.createTable(metaData, tableName);;
		this.template.execute(create_sql);
		return tableName;
	}

	public String transformDB(ResultSet rs,String tableName) {

		try {
			ResultSetMetaData metaData = rs.getMetaData();
			String insert_sql = this.createInsertSql(metaData, tableName);

			//
			PreparedStatement ps = this.template.getDataSource()
					.getConnection().prepareStatement(insert_sql);
			int k=0;
			while (rs.next()) {
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					ps.setObject(i + 1, rs.getObject(i + 1));
				}
				k++;				
				ps.addBatch();
				if(k%10000==0){
					ps.executeBatch();
				}
			}
			ps.executeBatch();			
			ps.close();
			return tableName;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	private String createTablename() {
		// TODO CREATE INDEX name2 ON atable(c1)
		return "tablename" + System.currentTimeMillis();
	}

	private String createTable(ResultSetMetaData metaData, String tablename) {
		try {
			int count = metaData.getColumnCount();
			StringBuffer sb = new StringBuffer();
			sb.append("create cached table ").append(tablename).append("(");
			String columntype = "VARCHAR";
			for (int i = 0; i < count; i++) {
				sb.append(metaData.getColumnName(i + 1)).append(" ");

				switch (metaData.getColumnType(i + 1)) {
				case java.sql.Types.NUMERIC:
					columntype = "NUMERIC";
					break;
				case java.sql.Types.INTEGER:
					columntype = "INTEGER";
					break;
				case java.sql.Types.VARCHAR:
					columntype = "VARCHAR";
					break;
				case java.sql.Types.DOUBLE:
					columntype = "DOUBLE";
					break;
				case java.sql.Types.FLOAT:
					columntype = "FLOAT";
					break;
				case java.sql.Types.DATE:
					columntype = "VARCHAR";
					break;
				case java.sql.Types.TIME:
					columntype = "TIME";
					break;
				}
				sb.append(columntype).append(",");
			}
			if (sb.toString().endsWith(","))
				sb.deleteCharAt(sb.toString().length() - 1);
			sb.append(")");
			log.info("CREATE TABLE:" + sb.toString());
			return sb.toString();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";

	}

	private String createInsertSql(ResultSetMetaData metaData, String tablename) {

		try {
			int count = metaData.getColumnCount();
			StringBuffer sb = new StringBuffer();
			sb.append("insert into ").append(tablename).append("(");
			for (int i = 0; i < count; i++) {
				sb.append(metaData.getColumnName(i + 1)).append(",");
			}
			if (sb.toString().endsWith(","))
				sb.deleteCharAt(sb.toString().length() - 1);
			sb.append(")values(");
			for (int i = 0; i < count; i++) {
				sb.append("?,");
			}
			if (sb.toString().endsWith(","))
				sb.deleteCharAt(sb.toString().length() - 1);
			sb.append(")");
			log.info("INSERT SQL:" + sb.toString());
			return sb.toString();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	public void setTemplate(org.springframework.jdbc.core.JdbcTemplate template) {
		this.template = template;
	}

	public void createIndexField(String tablename, String indexField) {	
		if(indexField==null||indexField.equals("")){
			return;
		}
		StringBuffer sql=new StringBuffer();
		sql.append("create index index").append(System.currentTimeMillis());
		sql.append(" on ").append(tablename).append("(");
		sql.append(indexField).append(")");
		log.info("创建索引 sql="+sql.toString());
		this.template.execute(sql.toString());
		// TODO
	}

	public void dropTables(String[] tables) {
		if (tables != null && tables.length > 0)
			for (int i = 0; i < tables.length; i++) {
				System.out.println("删除表：table="+tables[i]);
				this.template.execute(" drop table " + tables[i]);
			}

	}

}
