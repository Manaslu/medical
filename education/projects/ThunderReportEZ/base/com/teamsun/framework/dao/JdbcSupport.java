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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.teamsun.framework.util.HjdbcSupportUtilsFroTeradata;
import com.teamsun.framework.util.NamedQueryContainer;
import com.teamsun.framework.util.PageImpl;
import com.teamsun.framework.util.SqlReader;

/*******************************************************************************
 * JdbcSupport.java description:支持复杂查询的SQL语句
 * 
 * @author lf </a>
 * @version Revision:1.0 Date:2006-2-13 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 ******************************************************************************/


public class JdbcSupport extends JdbcDaoSupport {

	private static final Log logger = LogFactory.getLog(JdbcSupport.class);

	// -----------2007-4-11 11:14 hj
	private SqlReader sqlReader;

	public SqlReader getSqlReader() {
		return sqlReader;
	}

	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}

	// 2007-4-11 11:14 hj-------------
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
	 * 传入SQL语句，由JDBC进行查询操作，返回分页信息
	 * 
	 * 
	 * @param sqlName
	 *            在配置文件中的SQL语句名字
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            页面记录数
	 * 
	 * @param orderName
	 *            要排序的列名
	 * @param desc
	 *            升降排序 true:降序 默认升序
	 * @param param
	 *            升降排序 true:参数数组
	 * @return #PageImpl
	 */

	public PageImpl browseJdbcByName(String sqlName, int pageNo, int pageSize,
			Object[] param, String orderName, boolean desc)
			throws DataAccessException {
		String strSQL = NamedQueryContainer.getNamedQuery(sqlName);
		if (StringUtils.isEmpty(strSQL)) {
			logger.debug("Exception:can not find sql mapping !");
			return new PageImpl();
		}

		List ll = queryBySqlAlias(sqlName, param);
		if (ll == null || ll.size() < 1)
			return new PageImpl();
		int count = ll.size();

		return HjdbcSupportUtilsFroTeradata.getResults(ll, strSQL, param,
				pageSize, pageNo, orderName, desc, count);

	}

	/**
	 * 传入SQL语句，由JDBC进行查询操作，返回分页信息
	 * 
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            页面记录数
	 * 
	 * @param orderName
	 *            要排序的列名
	 * @param desc
	 *            升降排序 true:降序 默认升序
	 * @param param
	 *            升降排序 true:参数数组
	 * @return #PageImpl
	 */

	public PageImpl browseJdbcBySql(String sqlstr, int pageNo, int pageSize,
			String[] param, String orderName, boolean desc)
			throws DataAccessException {
		String strSQL = sqlstr;
		if (StringUtils.isEmpty(strSQL)) {
			logger.debug("Exception:sql is null!");
			return new PageImpl();
		}
		List ll = queryBySql(strSQL, param);
		if (ll == null || ll.size() < 1)
			return new PageImpl();
		int count = ll.size();

		return HjdbcSupportUtilsFroTeradata.getResults(ll, strSQL, param,
				pageSize, pageNo, orderName, desc, count);

	}

	/**
	 * 传入SQL语句，由JDBC进行查询操作，返回分页信息 与上面区别是PARAM是LIST类型
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            页面记录数
	 * 
	 * @param orderName
	 *            要排序的列名
	 * @param desc
	 *            升降排序 true:降序 默认升序
	 * @param param
	 *            升降排序 true:参数数组
	 * @return #PageImpl
	 */

	public PageImpl browseJdbcBySql(String sqlstr, int pageNo, int pageSize,
			List param, String orderName, boolean desc)
			throws DataAccessException {
		String strSQL = sqlstr;
		if (StringUtils.isEmpty(strSQL)) {
			logger.debug("Exception:sql is null!");
			return new PageImpl();
		}
		List ll = queryBySql(strSQL, param.toArray());
		if (ll == null || ll.size() < 1)
			return new PageImpl();
		int count = ll.size();

		return HjdbcSupportUtilsFroTeradata.getResults(ll, strSQL, param
				.toArray(), pageSize, pageNo, orderName, desc, count);

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
	 * 说明；该方法由SQL语句名字和条件参数组成，返回LIST类型
	 * 
	 * @return List
	 */

	public ArrayList queryBySqlAlias(String sqlName, Object[] objects)
			throws DataAccessException {
		List l = null;
		String strSQL = this.getSqlReader().getSqlConfig().getString(sqlName);
		if (objects == null || objects.length < 1) {

			l = getJdbcTemplate().queryForList(strSQL);
		} else {
			l = getJdbcTemplate().queryForList(strSQL, objects);

		}
		return new ArrayList(l);

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
	 * 说明；该方法传递动态SQL语句名字，返回LIST类型
	 * 
	 * @return List
	 */

	public ArrayList queryByDynamicSqlAlias(String sqlName, Object[] objects,
			String appendSQL) throws DataAccessException {
		String strSQL = this.getSqlReader().getSqlConfig().getString(sqlName);
		if (!StringUtils.isEmpty(strSQL) && !StringUtils.isEmpty(appendSQL)) {
			strSQL += " " + appendSQL;
		}
		return queryBySql(strSQL, objects);
	}
	public int updateBySqlAlias(String sqlname,Object[] objects) throws DataAccessException {
		String strSQL = this.getSqlReader().getSqlConfig().getString(sqlname);
		return getJdbcTemplate().update(strSQL,objects);
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
	public List getListFromConfig(String str){
		return  this.getSqlReader().getSqlConfig().getList(str);
	}
}
