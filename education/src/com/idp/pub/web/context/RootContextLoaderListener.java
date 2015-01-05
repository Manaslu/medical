package com.idp.pub.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
 
/**
 * 保证listener加载顺序
 * 
 * @author panfei
 */
public class RootContextLoaderListener extends ContextLoaderListener {

	ServletContextListener logbackListener = new LogbackConfigListener();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 自定义logback配置文件加载路径
		logbackListener.contextInitialized(event);
		super.contextInitialized(event);
		// 获取上下文
		WebApplicationContextHelper.setWebApplicationContext(event
				.getServletContext());
	}

}
