package com.idp.pub.web.context;

import java.io.IOException;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * logback加载配置文件监听器，自动加载config下的配置文件
 * 
 * @author panfei
 * 
 */
public class LogbackConfigListener implements ServletContextListener {

	private static final String CONFIG_LOCATION = "logbackConfigLocation";

	private static final String CLASSPATH = "classpath:";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String logbackConfigLocation = sce.getServletContext()
				.getInitParameter(CONFIG_LOCATION);
		if (logbackConfigLocation != null
				&& logbackConfigLocation.startsWith(CLASSPATH)
				&& logbackConfigLocation.length() > CLASSPATH.length()) {

			logbackConfigLocation = logbackConfigLocation.substring(CLASSPATH
					.length());
			ClassPathResource pathRes = new ClassPathResource(
					logbackConfigLocation);
			if (!pathRes.exists()) {
				pathRes = new ClassPathResource(logbackConfigLocation,
						LogbackConfigListener.class.getClassLoader());
			}
			LoggerContext loggerContext = (LoggerContext) LoggerFactory
					.getILoggerFactory();
			JoranConfigurator joranConfigurator = new JoranConfigurator();
			joranConfigurator.setContext(loggerContext);
			loggerContext.reset();
			try {
				joranConfigurator.doConfigure(pathRes.getInputStream());
			} catch (JoranException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
