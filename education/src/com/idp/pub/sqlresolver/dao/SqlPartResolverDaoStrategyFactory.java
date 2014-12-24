package com.idp.pub.sqlresolver.dao;

import java.util.Map;

import com.idp.pub.dao.AbstractDaoStrategyFactory;
import com.idp.pub.dao.impl.SmartBaseDao;
import com.idp.pub.entity.BaseEntity;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.sqlresolver.service.ISqlPartResolverService;

/**
 * 数据库sql差异适配访问dao
 * 
 * @author panfei
 * 
 */
public class SqlPartResolverDaoStrategyFactory extends SmartBaseDao<BaseEntity> {

	private SqlPartResolverStrategyFactory sqlPartResolverStrategyFactory = new SqlPartResolverStrategyFactory();

	@Override
	public String getUserDefineNamespace() {
		return null;
	}

	public ISqlPartResolverService createSqlPartResolverService()
			throws BusinessException {
		return sqlPartResolverStrategyFactory.createDaoStrategy(this
				.getDataBaseProductName().toLowerCase());
	}

	public Map<String, ISqlPartResolverService> getDaoStrategyMap() {
		return sqlPartResolverStrategyFactory.getDaoStrategyMap();
	}

	public void setDaoStrategyMap(
			Map<String, ISqlPartResolverService> daoStrategyMap) {
		this.sqlPartResolverStrategyFactory.setDaoStrategyMap(daoStrategyMap);
	}

	public class SqlPartResolverStrategyFactory extends
			AbstractDaoStrategyFactory<String, ISqlPartResolverService> {
	}

	public static ISqlPartResolverService getDefaultSqlPartResolverService() {
		return new OraclePartResolverDaoStrategy();
	}
}
