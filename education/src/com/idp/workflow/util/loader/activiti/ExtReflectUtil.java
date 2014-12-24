package com.idp.workflow.util.loader.activiti;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 获取类相关属性信息
 * 
 * @author panfei
 * 
 */
public class ExtReflectUtil {

	public static Object invokeMethoad(Object obj, String methodName,
			Object[] args) throws WfBusinessException {
		Method invokemethoad = findMethod(obj.getClass(), methodName, args);
		try {
			invokemethoad.setAccessible(true);
			return invokemethoad.invoke(obj, args);
		} catch (IllegalArgumentException e) {
			throw new WfBusinessException(e);
		} catch (IllegalAccessException e) {
			throw new WfBusinessException(e);
		} catch (InvocationTargetException e) {
			throw new WfBusinessException(e);
		}
	}

	public static Object invokeField(Object obj, String fieldName)
			throws WfBusinessException {
		Field field = getField(fieldName, obj);
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new WfBusinessException(e);
		} catch (IllegalAccessException e) {
			throw new WfBusinessException(e);
		}
	}

	public static Field getField(String fieldName, Object object)
			throws WfBusinessException {
		return getField(fieldName, object.getClass());
	}

	public static Field getField(String fieldName, Class<?> clazz)
			throws WfBusinessException {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new WfBusinessException("not allowed to access field "
					+ field + " on class " + clazz.getCanonicalName());
		} catch (NoSuchFieldException e) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null) {
				return getField(fieldName, superClass);
			}
		}
		return field;
	}

	public static Method findMethod(Class<? extends Object> clazz,
			String methodName, Object[] args) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)
					&& matches(method.getParameterTypes(), args)) {
				return method;
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return findMethod(superClass, methodName, args);
		}
		return null;
	}

	private static boolean matches(Class<?>[] parameterTypes, Object[] args) {
		if ((parameterTypes == null) || (parameterTypes.length == 0)) {
			return ((args == null) || (args.length == 0));
		}
		if ((args == null) || (parameterTypes.length != args.length)) {
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			if ((args[i] != null)
					&& (!parameterTypes[i].isAssignableFrom(args[i].getClass()))) {
				return false;
			}
		}
		return true;
	}
}
