package com.idp.pub.entity.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * VO实体缓存
 * 
 * @author panfei
 * 
 */
public class SamrtEntityCache {

	/**
	 * 读写锁
	 */
	private ReentrantReadWriteLock rwlocker = new ReentrantReadWriteLock();

	/**
	 * cache map
	 */
	private Map<Class<?>, Map<String, Object>> keymap = new HashMap<Class<?>, Map<String, Object>>();

	private static SamrtEntityCache instance;

	private SamrtEntityCache() {
	}

	static {
		instance = new SamrtEntityCache();
	}

	public static SamrtEntityCache getInstance() {
		return instance;
	}

	public boolean isExist(Class<?> check) {
		rwlocker.readLock().lock();
		try {
			return keymap.containsKey(check);
		} finally {
			rwlocker.readLock().unlock();
		}
	}

	/**
	 * 赋值
	 * 
	 * @param check
	 * @param voinfo
	 */
	public void putValue(Class<?> check, Map<String, Object> voinfo) {
		rwlocker.writeLock().lock();
		try {
			if (!keymap.containsKey(check)) {
				keymap.put(check, voinfo);
			}
		} finally {
			rwlocker.writeLock().unlock();
		}
	}

	/**
	 * 获取值
	 * 
	 * @param check
	 * @return
	 */
	public Map<String, Object> getValue(Class<?> check) {
		rwlocker.readLock().lock();
		try {
			return keymap.get(check);
		} finally {
			rwlocker.readLock().unlock();
		}
	}
}
