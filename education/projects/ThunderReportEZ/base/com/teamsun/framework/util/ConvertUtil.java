/* 
 * Copyright (c) 1994-2006 Teamsun
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Finalist IT Group, Inc.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Teamsun.
 * 
 */
package com.teamsun.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.framework.exception.ConvertException;

/**
 * ConvertUtil.java 从一个对象转换成另一个对象的工具类.
 * 
 * @author lf</a>
 * @version Revision:1.0 Date:2006-3-6 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 * 
 * 
 * 
 */

public final class ConvertUtil {
	// ~ Static fields/initializers
	// =============================================

	private static Log log = LogFactory.getLog(ConvertUtil.class);

	// ~ Methods
	// ================================================================

	/**
	 * 将资源绑定的国际化信息转化成MAP对象.
	 * 
	 * @param rb
	 *            a given resource bundle
	 * @return Map a populated map
	 */
	public static Map convertBundleToMap(ResourceBundle rb) {
		Map map = new HashMap();

		for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
			String key = (String) keys.nextElement();
			map.put(key, rb.getString(key));
		}

		return map;
	}

	/**
	 * 把List转化成Map类型
	 * 
	 * @param list
	 * @return Map java.util
	 */
	public static Map convertListToMap(List list) {
		Map map = new LinkedHashMap();

		for (Iterator it = list.iterator(); it.hasNext();) {
			LabelValue option = (LabelValue) it.next();
			map.put(option.getLabel(), option.getValue());
		}

		return map;
	}

	/**
	 * Method to convert a ResourceBundle to a Properties object.
	 * 
	 * @param rb
	 *            a given resource bundle
	 * @return Properties a populated properties object
	 */
	public static Properties convertBundleToProperties(ResourceBundle rb) {
		Properties props = new Properties();

		for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
			String key = (String) keys.nextElement();
			props.put(key, rb.getString(key));
		}

		return props;
	}

	/**
	 * Convenience method used by tests to populate an object from a
	 * ResourceBundle
	 * 
	 * @param obj
	 *            an initialized object
	 * @param rb
	 *            a resource bundle
	 * @return a populated object
	 */
	public static Object populateObject(Object obj, ResourceBundle rb) {
		try {
			Map map = convertBundleToMap(rb);

			BeanUtils.copyProperties(obj, map);
		} catch (Exception e) {
			log.error("Exception occured populating object: " + e.getMessage());
		}

		return obj;
	}

	/**
	 * @param o
	 *            源对象
	 * 
	 * 
	 * @param target
	 *            目标对象
	 * @throws ConvertException
	 */
	public static Object convert(Object o, Object target)
			throws ConvertException {
		if (o == null) {
			return null;
		}
		try {
			BeanUtils.copyProperties(target, o);
			return target;
		} catch (IllegalAccessException e) {
			throw new ConvertException("Bean对象复制异常", e);
		} catch (InvocationTargetException e) {
			throw new ConvertException("Bean对象复制异常", e);
		}
	}

	public static Object[] convertListToObjctArray(ArrayList src, Object dest)
			throws ConvertException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			NoSuchMethodException {

		if (src == null || src.size() < 1)
			return null;

		BeanUtilsBean ub = new BeanUtilsBean();
		Object[] result = new Object[src.size()];

		for (int i = 0; i < src.size(); i++) {

			Map obj = (Map) src.get(i);
			Iterator names = obj.keySet().iterator();

			while (names.hasNext()) {

				String name = (String) names.next();

				String reName = dealField(name);
				Object value = obj.get(name);
				ub.copyProperty(dest, reName, value);

			}

			Object clone = BeanUtils.cloneBean(dest);
			result[i] = clone;

			// 		

		}

		return result;
	}

	/**
	 * 
	 * @param cbigtosmall
	 * @return
	 */
	public String toUpperCase(char c) {
		int i = c;
		log.debug("assii" + i);
		if (c >= 97 && c <= 122)
			return new String(new char[] { (char) (c + 32) });
		else {
			String re = new String(new char[] { (char) (c + 32) });
			log.debug("99999" + re);
			return re;
		}
	}

	/**
	 * 判断字母大小写，大写返回TRUE
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBorS(String s) {
		char c = s.charAt(0);
		if (c < 97 || c > 122)
			return true;
		return false;
	}

	/**
	 * @param dest
	 *            源对象
	 * 
	 * 
	 * @param src
	 *            目标对象
	 * @param omit
	 *            忽略对象数组
	 * @throws ConvertException
	 * 
	 */
	public static Object convertOmit(Object src, Object dest, String[] omit)
			throws ConvertException {
		try {
			return new com.teamsun.framework.util.BeanUtils().convertOmit(dest,
					src, omit);
		} catch (IllegalAccessException e) {
			throw new ConvertException("Bean对象复制异常", e);
		} catch (InvocationTargetException e) {
			throw new ConvertException("Bean对象复制异常", e);
		}
	}

	/**
	 * 转化list中的bean类型
	 * 
	 * @param list
	 * @return @
	 */
	public static List convert(List list, Class classObj)
			throws ConvertException {
		if (list == null) {
			return null;
		}
		try {
			List targetList = new ArrayList(list.size());
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				Object target = classObj.newInstance();
				convert(o, target);
				targetList.add(target);
			}
			return targetList;
		} catch (InstantiationException e) {
			throw new ConvertException("Bean对象复制异常", e);
		} catch (IllegalAccessException e) {
			throw new ConvertException("Bean对象复制异常", e);
		}
	}

	// 辅助,
	/**
	 * 将数组转换为字符串
	 * 
	 * 
	 * 
	 * @param ss
	 * @return String
	 */
	public static String converArrayStr(String[] ss) {
		String str = "";
		if (ss.length == 0) {
			return null;
		} else {
			for (int i = 0; i < ss.length; i++)
				str = str + ss[i] + ",";
		}
		if (!str.equals("")) {
			str = str.substring(0, str.length() - 1);
			return str;
		} else {
			return "";
		}
	}

	// 把191920--->19:19:20
	public static String getTimeFormat(String input) {
		if (StringUtils.isEmpty(input)) {
			return null;
		}
		String s = null;
		String result = "";
		if (input.length() <= 6) {
			int i = 6 - input.length();
			s = input;
			result = "";
			if (i == 0) {
				s = input;
			}
			for (int j = 0; j < i; j++) {
				s = "0" + s;
			}
			String strHour = s.substring(0, 2);
			String strMinute = s.substring(2, 4);
			String strSecond = s.substring(4, 6);
			result = strHour + ":" + strMinute + ":" + strSecond;
		} else {
			s = input;
		}
		return result;
	}

	// 把19:19:20--->191920

	public static Integer getTime(String input) {
		if (StringUtils.isEmpty(input)) {
			return null;
		}

		String[] t = input.split(":");
		Integer result = null;
		if (t.length <= 2) {
			result = new Integer(Integer.parseInt(input));
			return result;
		}
		result = new Integer(Integer.parseInt((t[0].toString()
				+ t[1].toString() + t[2].toString()).trim()));
		return result;
	}

	// 把1992-01-01---->19920101

	public static String getDate(String input) {
		if (StringUtils.isEmpty(input)) {
			return null;
		}
		String result = null;
		String[] t = input.split("-");
		if (t.length <= 2) {
			result = input.trim();
			return result;
		}

		result = (t[0].toString() + t[1].toString() + t[2].toString()).trim();
		return result;
	}

	/**
	 * lf add 把一个不规则的字段转换为规则字段
	 */
	public static String dealField(String field) {

		String result = "";
		if (StringUtils.isEmpty(field))
			return result;
		if (field.indexOf("_") > -1) {
			StringBuffer sb = new StringBuffer();
			StringTokenizer st = new StringTokenizer(field, "_");
			int i = 0;
			while (st.hasMoreTokens()) {
				if (i == 0)
					sb.append(st.nextToken().toLowerCase());
				else {
					String sec = st.nextToken();
					String one = sec.substring(0, 1);
					String two = sec.substring(1);
					sec = one.toLowerCase() + two.toLowerCase();
					sb.append(sec);
				}
				i++;
			}
			result = sb.toString();
		} else {
			// 如果没有_只把首字母小写，其他保持不变
			result = field.toLowerCase();
		}
		return result;
	}

	/**
	 * 把从数据库查询出来的LIST对象转换成相应类型的LIST对象， 这里的VO中字段是用工具生成的，与数据库字段不对应
	 * 
	 * @param src
	 * @param dest
	 * @return
	 * @throws ConvertException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 */
	public static List convertListToObjctList(ArrayList src, Object dest) {

		if (src == null || src.size() < 1)
			return null;

		BeanUtilsBean ub = new BeanUtilsBean();
		List result = new ArrayList();
		try {
			for (int i = 0; i < src.size(); i++) {

				Map obj = (Map) src.get(i);
				Iterator names = obj.keySet().iterator();

				while (names.hasNext()) {
					String name = (String) names.next();
					String reName = dealField(name);
					Object value = obj.get(name);
					ub.copyProperty(dest, reName, value);
				}

				Object clone = BeanUtils.cloneBean(dest);
				result.add(clone);
			}

		} catch (Exception e) {
			throw new ConvertException("convertListToObjctList转换异常", e);
		}

		return result;
	}

	public static void main(String[] args) {
		String temp = dealField("VALUE");
		System.out.print(temp);
	}
}
