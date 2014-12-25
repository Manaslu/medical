package com.teamsun.thunderreport.web;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.thunderreport.database.DimDataFactory;
import com.teamsun.thunderreport.javascript.JavaScriptParser;
import com.teamsun.thunderreport.util.ApplicationPro;
import com.teamsun.thunderreport.velocity.TempletBuilder;

public class StartupListener implements javax.servlet.ServletContextListener {

	private static final Log log = LogFactory.getLog(StartupListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		DimDataFactory.clear();
	}

	/**
	 * 1、加载js解析器
	 */
	public void contextInitialized(ServletContextEvent event) {
		

		
		//以下代码tomcat可用
		//String filename = event.getServletContext().getInitParameter("jsFilename");
		//JavaScriptParser.init(event.getServletContext().getRealPath(filename));
		//ApplicationPro.load(event.getServletContext().getRealPath(event.getServletContext().getInitParameter("appFilename")));
		//TempletBuilder.getInstancce().init(WEB_INF+"/velocity");
		//log.info(ApplicationPro.getValue("application.hello"));
		
		//以下代码为weblogic可用
		String classpath = this.getClass().getClassLoader().getResource("/").getPath();
		String WEB_INF = classpath.substring(0, classpath.indexOf("WEB-INF")+7);
		System.out.println("JavaScriptParser.init--------------------->"+WEB_INF+"/include/reportserver.js");
		System.out.println("TempletBuilder.getInstancce().init-------->"+WEB_INF+"/velocity");
		System.out.println("ApplicationPro.load----------------------->"+WEB_INF+"/application.properties");
		JavaScriptParser.init(WEB_INF+"/include/reportserver.js");
		TempletBuilder.getInstancce().init(WEB_INF+"/velocity");
		ApplicationPro.load(WEB_INF+"/application.properties");
		

	}

}
