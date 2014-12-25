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

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * BeanUtils.java 类型转换工具类
 * 
 * 
 * @author lf</a>
 * @version Revision:1.0 Date:2006-3-6 10:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 * 
 * 
 */

public class BeanUtils extends BeanUtilsBean {
	private Log log = LogFactory.getLog(getClass().getName());

	public BeanUtils() {
		super(new ConvertUtilsBean(), new PropertyUtilsBean());
	}

	// private PropertyUtilsBean propertyUtilsBean = null;
	/**
	 * @param dest
	 *            源对象
	 * 
	 * @param src
	 *            目标对象
	 * @param omit
	 *            忽略对象数组
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object convertOmit(Object dest, Object src, String[] omit)
			throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (src == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + src + ")");
		}

		// Copy the properties, converting as necessary
		if (src instanceof DynaBean) {
			log.debug(")))))))))))");
			copyDynaBean(dest, src);
		} else if (src instanceof Map) {
			log.debug("(((((((((");
			copyMap(dest, src);
		} else /* if (src is a standard JavaBean) */{
			log.debug("---------------iii");
			copyJavaBean(dest, src, omit);
		}
		return dest;
	}

	// public void convertOmit(Object dest, Object src, String[] omit)
	// throws IllegalAccessException, InvocationTargetException {
	// copyProperties(dest, src, omit);
	// }

	/**
	 * @param dest
	 * @param src
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void copyJavaBean(Object dest, Object src, String[] omit)
			throws IllegalAccessException, InvocationTargetException {
		PropertyDescriptor srcDescriptors[] = getPropertyUtils()
				.getPropertyDescriptors(src);
		for (int i = 0; i < srcDescriptors.length; i++) {
			String name = srcDescriptors[i].getName();

			if (omitted(name, omit)) {
				continue;
			}
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (getPropertyUtils().isReadable(src, name)
					&& getPropertyUtils().isWriteable(dest, name)) {
				try {
					Object value = getPropertyUtils().getSimpleProperty(src,
							name);
					copyProperty(dest, name, value);
				} catch (NoSuchMethodException e) {
					; // Should not happen
				}
			}
		}
	}

	/**
	 * 判断name是否在忽略列表omit中
	 * 
	 * 
	 * @param name
	 * @param omit
	 * @return
	 */
	private boolean omitted(String name, String[] omit) {
		if (omit == null)
			return true;
		for (int i = 0; i < omit.length; i++) {
			if (name.equals(omit[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param dest
	 * @param src
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void copyDynaBean(Object dest, Object src)
			throws IllegalAccessException, InvocationTargetException {
		DynaProperty origDescriptors[] = ((DynaBean) src).getDynaClass()
				.getDynaProperties();
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if (getPropertyUtils().isWriteable(dest, name)) {
				Object value = ((DynaBean) src).get(name);
				copyProperty(dest, name, value);
			}
		}
	}

	/**
	 * @param dest
	 * @param src
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void copyMap(Object dest, Object src)
			throws IllegalAccessException, InvocationTargetException {
		Iterator names = ((Map) src).keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			if (getPropertyUtils().isWriteable(dest, name)) {
				Object value = ((Map) src).get(name);
				copyProperty(dest, name, value);
			}
		}
	}
}
