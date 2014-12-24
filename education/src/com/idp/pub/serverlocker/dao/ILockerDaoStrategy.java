package com.idp.pub.serverlocker.dao;

import com.idp.pub.serverlocker.entity.Paramters;

/**
 * 加锁策略<br>
 * 排他锁和等待锁 都实现此接口
 * 
 * @author panfei
 * 
 */
public interface ILockerDaoStrategy {

	/**
	 * 加锁
	 * 
	 * @param key
	 * @return
	 */
	boolean lock(String key, Paramters params);

	/**
	 * 批量加锁
	 * 
	 * @param keys
	 * @return
	 */
	boolean lock(String[] keys, Paramters params);

	/**
	 * 解锁
	 * 
	 * @param key
	 * @return
	 */
	boolean unlock(String key, Paramters params);

	/**
	 * 批量解锁
	 * 
	 * @param keys
	 * @return
	 */
	boolean unlock(String[] keys, Paramters params);

	/**
	 * 检查是否被锁
	 * 
	 * @param key
	 * @return
	 */
	boolean islocked(String key, Paramters params);
}
