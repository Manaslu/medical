package com.idp.pub.serverlocker.dao;

import com.idp.pub.dao.AbstractDaoStrategyFactory;
import com.idp.pub.serverlocker.entity.LockerType;
 
/**
 * 加锁策略工厂
 * 
 * @author panfei
 * 
 */
public class LockerDaoStrategyFactory extends
		AbstractDaoStrategyFactory<String, ILockerDaoStrategy> {

	/**
	 * 获取默认排它锁策略服务
	 * 
	 * @return
	 */
	public static ILockerDaoStrategy getDefaultExcLockerDaoStrategy() {
		return new MeELockerServicesStrategy();
	}

	public static LockerDaoStrategyFactory getLockerDaoStrategyFactory() {
		LockerDaoStrategyFactory factory = new LockerDaoStrategyFactory();
		// 默认注册为 内存排它锁
		factory.getDaoStrategyMap().put(LockerType.meElLockType,
				getDefaultExcLockerDaoStrategy());
		return factory;
	}
}
