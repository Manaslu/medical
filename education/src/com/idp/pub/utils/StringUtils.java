package com.idp.pub.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 字符串工具类
 * 
 * @author panfei
 * 
 */
public abstract class StringUtils {

	public static String decode(String obj) {
		String results = "";
		if (StringUtils.isEmpty(obj)) {
			return "";
		}
		try {
			results = URLDecoder.decode(obj, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 判断是否为null 或 ""
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ("".equals(obj.toString().trim())) {
			return true;
		}
		return false;
	}

	public static String toString(List<? extends Object> list, char ch) {
		StringBuffer str = new StringBuffer("");
		int cnt = 0;
		for (Object o : list) {
			str.append("'" + o.toString() + "'");
			cnt++;
			if (cnt < list.size()) {
				str.append(ch);
			}
		}
		return str.toString();
	}

	public static String toString(Object obj) {
		if (isEmpty(obj)) {
			return "";
		}
		return obj.toString();
	}

	public static String convertString(String str) {
		String upStr = str.toUpperCase();
		String lowStr = str.toLowerCase();
		StringBuffer buf = new StringBuffer(str.length());

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == upStr.charAt(i)) {
				buf.append(lowStr.charAt(i));
			} else {
				buf.append(upStr.charAt(i));
			}
		}
		return buf.toString();
	}

	public static String replace(String title) {
		if (isEmpty(title))
			return "";
		return title.trim().replaceAll("&quot;", "\"")
				.replaceAll("&#180;", "\'").replaceAll("&#58;", ":")
				.replaceAll("&#92;", "\\\\");
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param obj
	 *            检查对象
	 * @return
	 */
	public static boolean isEmpty(String obj) {
		if (obj == null || obj.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串的首字母大写转换
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(true, str);
	}

	/**
	 * 字符串的首字母小写转换
	 * 
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(false, str);
	}

	/**
	 * 字符串的首字母 大小写转换
	 * 
	 * @param capitalize
	 *            大小写标志
	 * @param str
	 *            需要转换的字符串
	 * @return
	 */
	private static String changeFirstCharacterCase(boolean capitalize,
			String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(strLen);
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * 字符串转换
	 * 
	 * @param obj
	 *            需要的对象
	 * @return
	 */
	public static String convertString(Object obj) {
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}

	/**
	 * 判断是否结尾，忽视大小写
	 * 
	 * @param value
	 * @param checkstr
	 * @return
	 */
	public static boolean isEndWith(String value, String checkstr) {
		if (!isEmpty(value) && !isEmpty(checkstr)) {
			if (value.length() <= checkstr.length()) {
				int begindex = checkstr.length() - value.length();
				String comparevaule = checkstr.substring(begindex);
				if (value.equalsIgnoreCase(comparevaule)) {
					return true;
				}
			}
		}
		return false;
	}
}
