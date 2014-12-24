package com.idp.workflow.itf.dao;

import org.apache.ibatis.session.SqlSessionFactory;

import com.idp.workflow.impl.dao.BaseDAO;

/**
 * DAO创建者
 * 
 * 如果想使用mybatis-spring.jar替换，在这里修改，并且修改SqlSessionFactory的默认事务
 * 
 * @author panfei
 * 
 */
public class DaoMapperCreater {

	protected SqlSessionFactory dbSqlSessionFactory;

	public DaoMapperCreater(SqlSessionFactory dbSqlSessionFactory) {
		this.dbSqlSessionFactory = dbSqlSessionFactory;
	}

	public IDaoMapper createDaoMapper() {
		return new BaseDAO(dbSqlSessionFactory);
	}

}
