package com.idp.pub.web.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * spring环境上下文
 * 
 * @author panfei
 * 
 */
public class WebApplicationContextHelper {

	private static WebApplicationContext context;

	public static void setWebApplicationContext(ServletContext servletcontext) {
		context = WebApplicationContextUtils
				.getWebApplicationContext(servletcontext);
	}

	public static WebApplicationContext getWebApplicationContext() {
		return context;
	}
}
