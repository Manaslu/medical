package com.idp.pub.generatekey.dao;

import com.idp.pub.dao.AbstractDaoStrategyFactory;

/**
 * 主键生成策略工厂
 * 
 * @author panfei
 * 
 */
public class GenerateKeyDaoStrategyFactory extends
		AbstractDaoStrategyFactory<String, IGenerateKeyDaoStrategy> {

	private static final String Type = "uuid";

	public static IGenerateKeyDaoStrategy getDefaultGenerateKeyDaoStrategy() {
		return new UuIdGenerateKeyMangerDao();
	}

	public static GenerateKeyDaoStrategyFactory getGenerateKeyDaoStrategyFactoryy() {
		GenerateKeyDaoStrategyFactory factory = new GenerateKeyDaoStrategyFactory();
		factory.getDaoStrategyMap().put(Type,
				getDefaultGenerateKeyDaoStrategy());
		factory.setChioceKey(Type);
		return factory;
	}
}
