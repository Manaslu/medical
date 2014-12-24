package com.idp.pub.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * dao对象抽象工厂
 * 
 * @author panfei
 * 
 */
public abstract class AbstractDaoStrategyFactory<K, V> {

	private K chioceKey;

	private Map<K, V> daoStrategyMap = new HashMap<K, V>();

	public Map<K, V> getDaoStrategyMap() {
		return daoStrategyMap;
	}

	public void setDaoStrategyMap(Map<K, V> daoStrategyMap) {
		this.daoStrategyMap = daoStrategyMap;
	}

	public V createDaoStrategy(K key) {
		return daoStrategyMap.get(key);
	}

	public K getChioceKey() {
		return chioceKey;
	}

	public void setChioceKey(K chioceKey) {
		this.chioceKey = chioceKey;
	}

	public V createDaoStrategy() {
		return createDaoStrategy(chioceKey);
	}
}
