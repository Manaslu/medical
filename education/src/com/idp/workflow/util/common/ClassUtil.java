package com.idp.workflow.util.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.idp.workflow.itf.service.ILocater;
import com.idp.workflow.util.service.LocaterServiceFactory;

/**
 * 工具类
 * 
 * @author panfei
 * 
 */
public class ClassUtil {

	/**
	 * 获取子类范型
	 * 
	 * @param obj
	 * @param index
	 * @return
	 */
	public static Class<?> createReflectionChildClass(Object obj, int index) {
		Type genType = obj.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<?>) params[index];

	}

	/**
	 * 获取当前类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader classLoader = null;
		classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			ILocater context = LocaterServiceFactory.GetInstance();
			if (context != null) {
				classLoader = context.getClass().getClassLoader();
			}
		}
		return classLoader;
	}
}
