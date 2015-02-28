package com.teamsun.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class BeanLoader implements ApplicationContextAware {

	private static ApplicationContext ctx = null;

	private BeanLoader() {

	}

	public static Object getBean(String name) {
		return ctx.getBean(name);
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = arg0;

	}

}
