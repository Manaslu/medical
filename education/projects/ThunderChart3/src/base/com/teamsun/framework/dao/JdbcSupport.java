/* 
 * Copyright (c) 1994-2006 Teamsun
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Finalist IT Group, Inc.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Teamsun.
 * 
 */
package com.teamsun.framework.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

 


/*******************************************************************************
 * JdbcSupport.java description:支持复杂查询的SQL语句
 * 
 * @author lf </a>
 * @version Revision:1.0 Date:2006-2-13 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：

 * 
 ******************************************************************************/


public class JdbcSupport extends JdbcDaoSupport {


	public JdbcSupport() {
		super();
	}

	protected void initDao() throws Exception {
		super.initDao();
	}

	public Connection getDatasConn() {
		return super.getConnection();
	}

	public void releaseConn(Connection conn) {
		super.releaseConnection(conn);
	}

	
	
	/**
	 * 说明；该方法由SQL语句和条件参数组成，返回LIST类型
	 * 
	 * @return List
	 */

	public ArrayList queryBySql(String strSQL, Object[] objects)
			throws DataAccessException {
		List l = null;
		if (!StringUtils.isEmpty(strSQL)) {
			l = getJdbcTemplate().queryForList(strSQL, objects);
		}
		return new ArrayList(l);
	}
	
	public int queryForInt(String strSQL, Object[] objects)throws DataAccessException {
		return	getJdbcTemplate().queryForInt(strSQL, objects);
	}
	
	public long queryForLong(String strSQL, Object[] objects)throws DataAccessException {
		return	getJdbcTemplate().queryForLong(strSQL, objects);
	}
	
	

	/**
	 * 说明；该方法传递动态SQL语句，返回LIST类型
	 * 
	 * @return List
	 */

	public ArrayList queryByDynamicSql(String strSQL, Object[] objects,
			String appendSQL) throws DataAccessException {
		String sqlstr = strSQL;
		if (!StringUtils.isEmpty(sqlstr) && !StringUtils.isEmpty(appendSQL)) {
			sqlstr += " " + appendSQL;
		}
		return queryBySql(sqlstr, objects);
	}

	/**
	 * 说明:del,insert,update sql语句操作 by lj
	 * 
	 * @return boolean
	 */
	public boolean executeBySql(String strSQL) throws DataAccessException {
		boolean bl = true;
		if (!StringUtils.isEmpty(strSQL)) {
			getJdbcTemplate().update(strSQL);
		} else {
			bl = false;
		}
		return bl;
	}

	/**
	 * func:批量执行sql
	 * 
	 * @param strSqls
	 * @return boolean
	 * @throws DataAccessException
	 */
	public boolean executeByMultiSql(String[] strSqls)
			throws DataAccessException {
		boolean bl = true;
		if (strSqls != null && strSqls.length > 0) {
			getJdbcTemplate().batchUpdate(strSqls);
		} else {
			bl = false;
		}
		return bl;
	}
	
}
