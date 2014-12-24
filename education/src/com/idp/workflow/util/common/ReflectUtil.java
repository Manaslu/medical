package com.idp.workflow.util.common;

import java.io.InputStream;

/**
 * 资源加载工具类
 * 
 * @author panfei
 * 
 */
public class ReflectUtil {

	/**
	 * 获取包资源文件
	 * 
	 * @param name
	 *            路径
	 * @return
	 */
	public static InputStream getResourceAsStream(String name) {
		InputStream resourceStream = null;
		ClassLoader classLoader = ClassUtil.getClassLoader();
		if (classLoader != null) {
			resourceStream = classLoader.getResourceAsStream(name);
		}
		if (resourceStream == null) {
			classLoader = ReflectUtil.class.getClassLoader();
			resourceStream = classLoader.getResourceAsStream(name);
		}
		return resourceStream;
	}
}
