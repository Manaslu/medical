package com.idp.pub.entity.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Date;

import com.idp.pub.entity.constants.IDataType;

/**
 * 数据类型转换
 * 
 * @author panfei
 * 
 */
public class SqlDataConvert {

	/**
	 * 值转换
	 * 
	 * @param calss
	 * @param value
	 * @return
	 */
	public static Object valueConvert(Class<?> calss, Object value) {
		if (value != null && calss != null) {
			if (value instanceof String) {
				return stringConvert(calss, String.valueOf(value));
			}

			if (value instanceof Date) {
				return dateConvert(calss, (Date) value);
			}

			if (value instanceof Boolean) {
				return boolConvert(calss, (Boolean) value);
			}

			if (value.getClass().isArray()
					&& (Byte.class.isAssignableFrom(Array.get(value, 0)
							.getClass()))) {
				return bitConvert(calss, (Byte[]) value);
			}
		}
		return value;
	}

	/**
	 * 字节转换
	 * 
	 * @param calss
	 * @param value
	 * @return
	 */
	protected static Object bitConvert(Class<?> calss, Byte[] value) {

		return value;
	}

	/**
	 * 布尔值转换
	 * 
	 * @param calss
	 * @param value
	 * @return
	 */
	protected static Object boolConvert(Class<?> calss, Boolean value) {

		if (String.class.isAssignableFrom(calss)) {
			return String.valueOf(value);
		}

		if (Number.class.isAssignableFrom(calss)) {
			return value.booleanValue();
		}

		return value;
	}

	/**
	 * 时间转换
	 * 
	 * @param calss
	 * @param value
	 * @return
	 */
	protected static Object dateConvert(Class<?> calss, Date value) {

		if (String.class.isAssignableFrom(calss)) {
			return String.valueOf(value);
		}

		return value;
	}

	/**
	 * string值根据vo主键实际类型转换
	 * 
	 * @param calss
	 * @param value
	 * @return
	 */
	protected static Object stringConvert(Class<?> calss, String value) {

		if (Integer.class.isAssignableFrom(calss)) {
			return Integer.valueOf(value);
		}

		if (Double.class.isAssignableFrom(calss)) {
			return Double.valueOf(value);
		}

		if (Float.class.isAssignableFrom(calss)) {
			return Float.valueOf(value);
		}

		if (Long.class.isAssignableFrom(calss)) {
			return Long.valueOf(value);
		}

		if (BigDecimal.class.isAssignableFrom(calss)) {
			return BigDecimal.valueOf(Double.valueOf(value));
		}

		return value;
	}

	/**
	 * 检查数据类型
	 * 
	 * @param value
	 * @return
	 */
	public static int checkDataType(Object value) {
		if (value != null) {
			return checkDataType(value.getClass());
		}
		return IDataType.NONE;
	}

	/**
	 * 检查数据类型
	 * 
	 * @param value
	 * @return
	 */
	public static int checkDataType(Class<?> value) {
		if (value != null) {
			if (String.class.isAssignableFrom(value)) {
				return IDataType.VACHAR;
			}

			if (Date.class.isAssignableFrom(value)) {
				return IDataType.VACHAR;
			}

			if (Number.class.isAssignableFrom(value)) {
				return IDataType.NUMBER;
			}

			if (Boolean.class.isAssignableFrom(value)) {
				return IDataType.NUMBER;
			}
		}
		return IDataType.NONE;
	}
}
