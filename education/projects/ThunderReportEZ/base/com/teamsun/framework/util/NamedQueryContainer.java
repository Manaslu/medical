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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/*******************************************************************************
 * NamedQueryContainer.java
 * 
 * @author lf </a> description:用于从NamedQuery.property中读取JDBC SQL语句
 * @version Revision:1.0 Date:2006-3-7 15:36:51 修改记录/revision： 日期： 修改人： 修改说明：
 * 
 ******************************************************************************/

public class NamedQueryContainer {

	private static ResourceBundle resourceBundle;

	//ResourceBundleMessageSource ms;

	static {
		try {
			// ms=ReloadableResourceBundleMessageSource
			resourceBundle = ResourceBundle.getBundle("NamedQuery");
			// chose the default system settings at the start
		} catch (MissingResourceException mre) {
			System.err
					.println("NamedQueryContainer -> NamedQuery.properties not found");
			throw new Error("No property file!", mre);
		}
	}

	public static String getNamedQuery(String name) {
		String str;
		try {
			str = resourceBundle.getString(name);
		} catch (MissingResourceException mre) {
			str = "";
		}
		return str;
	}
}
