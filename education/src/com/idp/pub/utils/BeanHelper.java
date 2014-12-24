package com.idp.pub.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class反射处理类
 * 
 * @author panfei
 * 
 */
public class BeanHelper {

	/**
	 * 缓存实现--反射信息
	 */
	private static Map<String, ReflectionInfo> cache = new HashMap<String, ReflectionInfo>();

	/**
	 * 读写锁
	 */
	private ReentrantReadWriteLock rwlocker = new ReentrantReadWriteLock();

	protected static final Object[] NULL_ARGUMENTS = {};

	private static BeanHelper bhelp = null;

	static {
		bhelp = new BeanHelper();
	}

	private BeanHelper() {
		super();
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static BeanHelper getInstance() {
		return bhelp;
	}

	/**
	 * 获取属性列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<String> getPropertys(Object bean) {
		return Arrays.asList(getPropertiesAry(bean));
	}

	/**
	 * 获取属性列表
	 * 
	 * @param bean
	 * @return
	 */
	public String[] getPropertiesAry(Object bean) {
		ReflectionInfo reflectionInfo = null;
		rwlocker.readLock().lock();
		try {
			reflectionInfo = cachedReflectionInfo(bean.getClass());
			Set<String> propertys = new HashSet<String>();
			for (String key : reflectionInfo.writeMap.keySet()) {
				if (reflectionInfo.writeMap.get(key) != null) {
					propertys.add(key);
				}
			}
			return propertys.toArray(new String[0]);
		} finally {
			rwlocker.readLock().unlock();
		}
	}

	/**
	 * 根据属性名称获取对象中实际存储的值数据类型
	 * 
	 * @param bean
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @return
	 */
	public Class<?> getPropertyDataType(Object bean, String propertyName) {
		try {
			Method method = getMethod(bean, propertyName, false);
			if (propertyName != null && method == null) {
				return null;
			} else if (method == null) {
				return null;
			}
			Class<?> retClassType = method.getReturnType();
			return retClassType;
		} catch (Exception e) {
			String errStr = "Failed to get property: " + propertyName;
			throw new RuntimeException(errStr, e);
		}
	}

	/**
	 * 根据属性名称获取对象中实际存储的值
	 * 
	 * @param bean
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @return
	 */
	public Object getPropertyValue(Object bean, String propertyName) {

		try {
			Method method = getMethod(bean, propertyName, false);
			if (propertyName != null && method == null) {
				return null;
			} else if (method == null) {
				return null;
			}
			return method.invoke(bean, NULL_ARGUMENTS);
		} catch (Exception e) {
			String errStr = "Failed to get property: " + propertyName;
			throw new RuntimeException(errStr, e);
		}
	}

	/**
	 * 根据属性名称集合获取对象中实际存储的值集合
	 * 
	 * @param bean
	 *            对象
	 * @param propertys
	 *            属性集合
	 * @return
	 */
	public Object[] getPropertyValues(Object bean, String[] propertys) {
		Object[] result = new Object[propertys.length];
		try {
			Method[] methods = getMethods(bean, propertys, false);
			for (int i = 0; i < propertys.length; i++) {
				if (propertys[i] == null || methods[i] == null) {
					result[i] = null;
				} else {
					result[i] = methods[i].invoke(bean, NULL_ARGUMENTS);
				}
			}
		} catch (Exception e) {
			String errStr = "Failed to get getPropertys from "
					+ bean.getClass();
			throw new RuntimeException(errStr, e);
		}
		return result;
	}

	/**
	 * 根据对象的属性名称设置对应的值
	 * 
	 * @param bean
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 */
	public void setPropertyValue(Object bean, String propertyName, Object value) {
		try {
			Method method = getMethod(bean, propertyName, true);
			if (propertyName != null && method == null) {
				return;
			} else if (method == null) {
				return;
			}
			method.invoke(bean, value);
		} catch (java.lang.IllegalArgumentException e) {
			String errStr = "Failed to set property: " + propertyName
					+ " at bean: " + bean.getClass().getName() + " with value:"
					+ value + " type:"
					+ (value == null ? "null" : value.getClass().getName());
			throw new IllegalArgumentException(errStr, e);
		} catch (Exception e) {
			String errStr = "Failed to set property: " + propertyName
					+ " at bean: " + bean.getClass().getName() + " with value:"
					+ value;
			throw new RuntimeException(errStr, e);
		}
	}

	/**
	 * 根据对象的属性名称集合获取对应的set/get方法对象集合
	 * 
	 * @param bean
	 *            对象
	 * @param propertys
	 *            属性名称集合
	 * @param isSetMethod
	 *            是否返回set方法
	 * @return
	 */
	private Method[] getMethods(Object bean, String[] propertys,
			boolean isSetMethod) {
		Method[] methods = new Method[propertys.length];
		rwlocker.readLock().lock();
		ReflectionInfo reflectionInfo = null;
		try {
			reflectionInfo = cachedReflectionInfo(bean.getClass());
			for (int i = 0; i < propertys.length; i++) {
				Method method = null;
				if (isSetMethod) {
					method = reflectionInfo.getWriteMethod(propertys[i]);
				} else {
					method = reflectionInfo.getReadMethod(propertys[i]);
				}
				methods[i] = method;
			}
			return methods;
		} finally {
			rwlocker.readLock().unlock();
		}
	}

	/**
	 * 根据对象的属性名称获取对应的set/get方法对象
	 * 
	 * @param bean
	 *            对象
	 * @param propertyName
	 *            属性名称
	 * @param isSetMethod
	 *            是否返回set方法
	 * @return
	 */
	private Method getMethod(Object bean, String propertyName,
			boolean isSetMethod) {
		Method method = null;
		rwlocker.readLock().lock();
		ReflectionInfo reflectionInfo = null;
		try {
			reflectionInfo = cachedReflectionInfo(bean.getClass());
			if (isSetMethod) {
				method = reflectionInfo.getWriteMethod(propertyName);
			} else {
				method = reflectionInfo.getReadMethod(propertyName);
			}
			return method;
		} finally {
			rwlocker.readLock().unlock();
		}
	}

	/**
	 * method缓存加载
	 * 
	 * @param beanCls
	 * @return
	 */
	private ReflectionInfo cachedReflectionInfo(Class<?> beanCls) {
		return cacheReflectionInfo(beanCls, null);
	}

	private ReflectionInfo cacheReflectionInfo(Class<?> beanCls,
			List<PropDescriptor> pdescriptor) {
		String key = beanCls.getName();
		ReflectionInfo reflectionInfo = cache.get(key);
		if (reflectionInfo == null) {
			reflectionInfo = cache.get(key);
			if (reflectionInfo == null) {
				try {
					rwlocker.readLock().unlock();
					rwlocker.writeLock().lock();
					rwlocker.readLock().lock();
					reflectionInfo = new ReflectionInfo();
					List<PropDescriptor> propDesc = new ArrayList<PropDescriptor>();
					if (pdescriptor != null) {
						propDesc.addAll(pdescriptor);
					} else {
						propDesc = getPropertyDescriptors(beanCls);
					}
					for (PropDescriptor pd : propDesc) {
						Method readMethod = pd.getReadMethod(beanCls);
						Method writeMethod = pd.getWriteMethod(beanCls);
						if (readMethod != null)
							reflectionInfo.readMap.put(pd.getName()
									.toLowerCase(), readMethod); // store as
						// lower
						// case
						if (writeMethod != null)
							reflectionInfo.writeMap.put(pd.getName()
									.toLowerCase(), writeMethod);
					}
					cache.put(key, reflectionInfo);
				} finally {
					rwlocker.writeLock().unlock();
				}
			}
		}
		return reflectionInfo;
	}

	/**
	 * 获取class所有的get和set方法类型,并且method缓存起来
	 * 不支持收录缓存范围：非get和set开头的,非字母的和非首字母大写的,入口参数和返回参数个数不满足标准javabean的,
	 * 常见系统函数getClass等等
	 * 
	 * @param clazz
	 * @return
	 */
	private List<PropDescriptor> getPropertyDescriptors(Class<?> clazz) {
		List<PropDescriptor> descList = new ArrayList<PropDescriptor>();
		// List<PropDescriptor> superDescList = new ArrayList<PropDescriptor>();
		List<String> propsList = new ArrayList<String>();
		Class<?> propType = null;
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().length() < 4) {
				continue;
			}
			if (method.getName().charAt(3) < 'A'
					|| method.getName().charAt(3) > 'Z') {
				continue;
			}
			if (method.getName().startsWith("set")) {
				if (method.getParameterTypes().length != 1) {
					continue;
				}
				if (method.getReturnType() != void.class) {
					continue;
				}
				propType = method.getParameterTypes()[0];
			} else if (method.getName().startsWith("get")) {
				if (method.getParameterTypes().length != 0) {
					continue;
				}
				propType = method.getReturnType();
			} else {
				continue;
			}
			String propname = method.getName().substring(3, 4).toLowerCase();
			if (method.getName().length() > 4) {
				propname = propname + method.getName().substring(4);
			}
			if (propname.equals("class")) {
				continue;
			}
			if (propsList.contains(propname)) {
				continue;
			} else {
				propsList.add(propname);
			}
			descList.add(new PropDescriptor(clazz, propType, propname));
		}
		// 遍历父类
		/*
		 * 目前不需要遍历父类属性，没有VO属性继承，若放开必须实现filter timestamp，primarykeys，status
		 * Class<?> superClazz = clazz.getSuperclass(); if (superClazz != null)
		 * { superDescList = getPropertyDescriptors(superClazz);
		 * descList.addAll(superDescList); if (!isBeanCached(superClazz)) {
		 * cacheReflectionInfo(superClazz, superDescList); } }
		 */
		return descList;
	}

	/**
	 * 判断缓存是否为空
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isBeanCached(Class<?> bean) {
		String key = bean.getName();
		ReflectionInfo cMethod = cache.get(key);
		if (cMethod == null) {
			rwlocker.readLock().lock();
			try {
				cMethod = cache.get(key);
				// 高效处理
				if (cMethod == null) {
					return false;
				}
			} finally {
				rwlocker.readLock().unlock();
			}
		}
		return true;
	}

	public static class ReflectionInfo {
		/**
		 * all stored as lowercase
		 */
		Map<String, Method> readMap = new HashMap<String, Method>();

		/**
		 * all stored as lowercase
		 */
		Map<String, Method> writeMap = new HashMap<String, Method>();

		Method getReadMethod(String prop) {
			return prop == null ? null : readMap.get(prop.toLowerCase());
		}

		Method getWriteMethod(String prop) {
			return prop == null ? null : writeMap.get(prop.toLowerCase());
		}
	}
}
