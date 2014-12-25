package com.teamsun.thunderreport.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransformDBSource {

	private TransformDBService transformDBService;

	private org.springframework.jdbc.core.JdbcTemplate template;

	public void setTemplate(org.springframework.jdbc.core.JdbcTemplate template) {
		this.template = template;
	}

	public void setTransformDBService(TransformDBService transformDBService) {
		this.transformDBService = transformDBService;
	}

	/**
	 * 返回的是实际的表名称
	 * 
	 * @param sql
	 * @param index
	 * @return
	 */
	public String transformDB(String sql, String index) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = this.template.getDataSource().getConnection()
					.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("Bo sql="+sql);
			String tableName=this.transformDBService.createTable(rs);
			this.transformDBService.createIndexField(tableName, index);
			String tablename1 = this.transformDBService.transformDB(rs,tableName);			
			return tableName;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return "";
	}
	
	public void dropTables(String[] tables){
		this.transformDBService.dropTables(tables);
	}
}
