package com.idp.pub.sqlresolver.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.sqlresolver.dao.SqlPartResolverDaoStrategyFactory;
import com.idp.pub.sqlresolver.entity.PageParams;
import com.idp.pub.sqlresolver.service.ISqlPartResolverService;

/**
 * 
 * @author panfei
 * 
 */
@Service("sqlResolverService")
public class SqlPartResolverServiceImpl implements ISqlPartResolverService,
		InitializingBean {

	// @Resource(name = "sqlPartResolverDaoFactory")
	@Autowired(required = false)
	@Qualifier("sqlPartResolverDaoFactory")
	private SqlPartResolverDaoStrategyFactory sqlPartResolverDaoFactory;

	//private ISqlPartResolverService sqlPartResolverService;

	@Override
	public String createPageQuerySql(Pager<?> page, PageParams params) throws BusinessException {
		return  sqlPartResolverDaoFactory.createSqlPartResolverService().createPageQuerySql(page, params);
	}

	@Override
	public String createTimeVersionSql(String tsValue) throws BusinessException{
		return sqlPartResolverDaoFactory.createSqlPartResolverService().createTimeVersionSql(tsValue);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		/*if (sqlPartResolverDaoFactory == null) {
			this.sqlPartResolverService = SqlPartResolverDaoStrategyFactory
					.getDefaultSqlPartResolverService();
		} else {
			this.sqlPartResolverService = sqlPartResolverDaoFactory
					.createSqlPartResolverService();
		}*/
	}
}
