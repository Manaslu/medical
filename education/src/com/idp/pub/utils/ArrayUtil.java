package com.idp.pub.utils;

import java.lang.reflect.Array;

/**
 * 数组转换工具
 * 
 * @author panfei
 * 
 */
public class ArrayUtil {

	/**
	 * 根据范型创建一个数组
	 * 
	 * @param c
	 *            范型
	 * @param size
	 *            长度
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] createArrayBySize(Class<T> c, int size) {
		return (T[]) Array.newInstance(c, size);
	}

	/**
	 * 一个对象转换对象数组
	 * 
	 * @param source
	 *            转换对象
	 * @return
	 */
	public static Object[] toObjectArray(Object source) {
		if (source == null) {
			return null;
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: "
					+ source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class<?> wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}

}
