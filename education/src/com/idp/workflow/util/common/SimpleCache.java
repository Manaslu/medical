package com.idp.workflow.util.common;

import java.util.HashMap;
import java.util.Map;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.exception.pub.WfNotSupprotException;
import com.idp.workflow.itf.service.ILocater;
 
/**
 * 简单缓存
 * 
 * @author panfei
 * 
 */
public class SimpleCache implements ILocater {

	private static Object locker = new Object();

	private static SimpleCache instance;

	private Map<String, Object> cache = new HashMap<String, Object>();

	private SimpleCache() {
	}

	public static SimpleCache GetInstance() {
		// findbug 也有sb的时候
		if (instance == null) {
			synchronized (locker) {
				if (instance == null) {
					instance = new SimpleCache();
				}
			}
		}
		return instance;
	}

	public synchronized void put(String key, Object vaule) {
		if (!cache.containsKey(key)) {
			cache.put(key, vaule);
		}
	}

	public Object get(String key) {
		return cache.get(key);
	}

	@Override
	public void servicesBind(Class<?> itfName, Class<?> implName)
			throws WfBusinessException {
		// 后续支持反射
		throw new WfNotSupprotException("暂不支持！");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E lookup(Class<E> serviceName) {
		return ((E) get(serviceName.getName()));
	}

	@Override
	public void servicesBind(Class<?> itfName, Object implInstace)
			throws WfBusinessException {
		put(itfName.getName(), implInstace);
	}

	@Override
	public Object lookup(String serviceName) {
		return get(serviceName.trim());
	}

	@Override
	public boolean contains(Class<?> itfName) {
		return contains(itfName.getName());
	}

	@Override
	public boolean contains(String serviceName) {
		if (cache.containsKey(serviceName) && (cache.get(serviceName) != null)) {
			return true;
		}
		return false;
	}
}
