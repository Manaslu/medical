package com.teamsun.thunderreport.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.thunderreport.util.BeanLoader;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.thread.WorkUnitFusionChart;
import com.teamsun.thunderreport.velocity.TempletBuilder;


/**
 * Servlet implementation class for Servlet: ServletFusionxmlService
 * 
 */

public class ServletFusionxmlService extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {

	private static final Log log = LogFactory
			.getLog(ServletFusionxmlService.class);

	public void init() throws ServletException {
		super.init();
		String classpath = this.getClass().getClassLoader().getResource("/").getPath();
		String WEB_INF = classpath.substring(0, classpath.indexOf("WEB-INF")+7);
		//tomcat 版本
		//TempletBuilder.getInstancce().init(this.getServletContext().getRealPath("WEB-INF/velocity"));		
		
		// weblogic 版本
		TempletBuilder.getInstancce().init(WEB_INF+"/velocity");
	}

	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ServletFusionxmlService() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String filename = request.getParameter("file");

		long b = System.currentTimeMillis();

		WorkUnitFusionChart chart = (WorkUnitFusionChart) BeanLoader
				.getBean("workUnitFusionChart");

		Condition[] cs = chart.getConditions(filename);

		Map map = new HashMap(cs.length);
		for (int i = 0; i < cs.length; i++) {
			String value = request.getParameter(cs[i].getParaname());
			if (value != null)
				map.put(cs[i].getParaname(), new String(value
						.getBytes("utf-8"), "utf-8"));
		}
		String index = request.getParameter("index");
		String chartid = request.getParameter("chartid");
		response.setCharacterEncoding("utf-8");
		if (index.equals("1")) {
			String text = chart.getResponseLayout(filename, map);
			response.getWriter().write(text);
		} else {
			log.info(filename + " " + chartid + " " + map);
			String tt = chart.getResponseXml(filename, chartid, map);
			log.info("file:" + filename + "生成时间："
					+ (System.currentTimeMillis() - b));
			response.getWriter().write(tt);
		}

	}
}