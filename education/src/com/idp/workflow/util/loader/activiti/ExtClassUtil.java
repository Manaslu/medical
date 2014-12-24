package com.idp.workflow.util.loader.activiti;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;

import com.idp.workflow.util.common.ClassUtil;

/**
 * 获取类加载器
 * 
 * @author panfei
 * 
 */
public class ExtClassUtil {

	public static ClassLoader getCustomClassLoader() {
		ProcessEngineConfigurationImpl processEngineConfiguration = Context
				.getProcessEngineConfiguration();
		if (processEngineConfiguration != null) {
			final ClassLoader classLoader = processEngineConfiguration
					.getClassLoader();
			if (classLoader != null) {
				return classLoader;
			}
		}
		return ClassUtil.getClassLoader();
	}
}
