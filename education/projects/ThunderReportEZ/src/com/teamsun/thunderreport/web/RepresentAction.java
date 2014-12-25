package com.teamsun.thunderreport.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.thunderreport.core.bean.Page;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.thread.ThreadContentBean;
import com.teamsun.thunderreport.thread.WorkUnitFactory;
import com.teamsun.thunderreport.thread.WorkUnitFusionChart;
import com.teamsun.thunderreport.thread.support.ConfigurationFileService;
import com.teamsun.thunderreport.util.BeanLoader;

/**
 * Servlet implementation class for Servlet: RepresentAction
 * 
 */
public class RepresentAction extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	private static final Log log = LogFactory.getLog(RepresentAction.class);

	static final long serialVersionUID = 1L;
	private static final Set set = new HashSet();

	private static final Map cap_set = new HashMap();
	
	public String RandomString(int length)
	 {
	  String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	  Random  random = new Random();
	  StringBuffer buf = new StringBuffer();
	  
	  for(int i = 0 ;i < length ; i ++)
	  {
	   int num = random.nextInt(36);
	   buf.append(str.charAt(num));
	  }
	  
	  return buf.toString();
	 }

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RepresentAction() {
		super();
	}
	
	public void init() throws ServletException {
		super.init();
		set.add("SWLH_Id");
		set.add("Cde_Tsp_Cd");
		set.add("Prd_Id");
		set.add("Prd_Mid_Id");
		set.add("MyE_Id");
		set.add("Cde_Acct_Item_Class_Cd");
		set.add("Cde_Accs_Nbr_Cd");
		set.add("Latn_Id");

		cap_set.put("SWLH_Name", "全部");
		cap_set.put("Cde_Tsp_Desc", "全部");
		cap_set.put("Prd_Name", "所有产品");
		cap_set.put("Prd_Mid_Name", "所有增值业务产品");
		cap_set.put("MyE_Name", "全部产品");
		cap_set.put("Cde_Acct_Item_Class_Desc", "所有类型");
		cap_set.put("Card_Prd_Desc", "全部");
		cap_set.put("Latn_Name", "全区");
	}
	
	public static interface WordEncoding {
		public String getValue(String value);
	}
	
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doAction(request, response, new WordEncoding() {
			public String getValue(String value) {
				try {
					return new String(URLDecoder.decode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return value;
			}

		});
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
			this.doAction(request, response, new WordEncoding() {
				public String getValue(String value) {
					try {
						return new String(URLDecoder.decode(value, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return value;
				}

			});
		}
		
		
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
		public void doAction(HttpServletRequest request,
				HttpServletResponse response, WordEncoding wordEncoding)
				throws ServletException, IOException {
		WorkUnitFactory unitFactory = (WorkUnitFactory) BeanLoader
				.getBean("workUnitFactory");
		// tomcat 版本
		//unitFactory.setRealDir(this.getServletContext().getRealPath("page"));
		
		//weblogic 版本
		String classpath = this.getClass().getClassLoader().getResource("/").getPath();
		String WEB_INF = classpath.substring(0, classpath.indexOf("WEB-INF")+7);
		unitFactory.setRealDir(WEB_INF+"/page");
		
	
		String index = request.getParameter("index");
		// TODO index==null||index.equals("")

		String filename = "";
		Condition[] conds = null;
		int index_ = Integer.parseInt(index);
		switch (index_) {
		case 1:
			// 首页显示
			break;
		case 2:
			// 预先生成
			if (!unitFactory.isRun()) {
				unitFactory.doAction(null);
			} else {
				ThreadContentBean bean = unitFactory.getThreadContentBean();
				response.getWriter().write(bean.toString());
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().write("预生成报表已经启动！");
			break;		
		case 3:
			// 单个文件查询，列表展示

			ConfigurationFileService fileservice = (ConfigurationFileService) BeanLoader
					.getBean("fileService");
			request.setAttribute("fboolean", new Boolean(true));
			request.setAttribute("files", fileservice
					.listAllConfigurationFiles());
			request.getRequestDispatcher("list.jsp").forward(request, response);
			break;
		case 4:
			// 单个文件的参数展示

			filename = request.getParameter("filename");
			conds = unitFactory.getConditions(filename);
			request.setAttribute("conds", conds);
			request.setAttribute("filename", filename);
			request.getRequestDispatcher("reportsubmit.jsp").forward(request,
					response);
			break;
		case 5:
			// 查询单个文件
			filename = request.getParameter("filename");
			if(filename==null||filename.equals(""))
				filename= request.getParameter("file");
			conds = unitFactory.getConditions(filename);
			boolean back=false;
			
			if(request.getParameter("back")!=null&&request.getParameter("back")!=""&&request.getParameter("back").equals("1")){				
				back=true;
			}
			Map map = new HashMap(conds.length);
			String pageindex = request.getParameter("pageindex");
			for (int i = 0; i < conds.length; i++) {
				//request.setCharacterEncoding("gb2312");
				String value = request.getParameter(conds[i].getParaname());				
				if (value != null) {
					if (value.split("\\,").length > 1) {

						if (set.contains(conds[i].getParaname())&&(!set.contains("Latn_Comp_Id")))
							value = "99999";

						if (cap_set.keySet().contains(conds[i].getParaname()))
							value = new String(cap_set.get(
									conds[i].getParaname()).toString()
									.getBytes("utf-8"), "utf-8");
						

					}
					map.put(conds[i].getParaname(), wordEncoding
							.getValue(value));			
				}
			}
			log.info("请求 xml file:" + filename + "  表格参数：" + map);
			Page p = new Page();
			;
			if (pageindex != null && !pageindex.equals("")) {
				p.setPageindex(Integer.parseInt(pageindex));
			} else {
				p.setPageindex(1);
			}
			String text = unitFactory.doAction(filename, map, p,back);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().write(text);
			break;
		case 6:
			//文件导出
			filename = request.getParameter("filename");
			conds = unitFactory.getConditions(filename);
			String fileType=request.getParameter("fileType");
			if(conds!=null){
			Map map1 = new HashMap(conds.length);
			for (int i = 0; i < conds.length; i++) {
				String value = request.getParameter(conds[i].getParaname());
				if (value != null) {
					map1.put(conds[i].getParaname(),value);
				}
			}
			
			String text1 = unitFactory.doExport(filename, map1,fileType);			
			if(fileType.equals("csv")){
				response.setCharacterEncoding("ansi");
			}else{
			    response.setCharacterEncoding("utf-8");
			}
			if (text1.indexOf("<script") < 0) {
				response.setContentType("application/vnd.ms-excel;charset=gb2312");
				response.setHeader("Content-Disposition", "inline; filename=\""
						+  this.RandomString(10) + "." + fileType
						+ "\"");
				response.getWriter().write(text1);
			}else{
				response.setContentType("text/html");
				
				response.getWriter().write(text1);
			}
			}
			break;
		case 7:
			//按条件预生成
			filename = request.getParameter("filename");
			conds = unitFactory.getConditions(filename);
			Map param = new HashMap(conds.length);
			for (int i = 0; i < conds.length; i++) {
				String value = request.getParameter(conds[i].getParaname());
				if (value != null) {
					param.put(conds[i].getParaname(), value);
				}
			}
			filename = filename.replaceAll(".xml", "");
			final String[] fileid = { filename };

			if (!unitFactory.isRun()) {
				unitFactory.doAction(fileid, param);
			} else {
				ThreadContentBean bean = unitFactory.getThreadContentBean();
				response.getWriter().write(bean.toString());
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().write("预生成报表已经启动！");
			break;
		case 8:
			//查询预生成的缓存文件，如果没有查询到，则临时生成文件并访问
			filename = request.getParameter("filename");
			if(filename==null||filename.equals(""))
				filename= request.getParameter("file");
			conds = unitFactory.getConditions(filename);
			String htmlFileName = "r_";

			for (int i = 0; i < conds.length; i++) {
				String value = request.getParameter(conds[i].getParaname());
				if (value != null && conds[i].getRefervalue() != null) {
					htmlFileName += value;
					htmlFileName += "_";
				}
			}
			htmlFileName += ".html";
			if (filename.indexOf(".xml") > -1)
				filename = filename.replaceAll(".xml", "");
			//tomcat 版本
			//String realPath = this.getServletContext().getRealPath("page") + System.getProperty("file.separator") + filename + System.getProperty("file.separator") + htmlFileName;
			
			// weblogic 版本
			String realPath =WEB_INF+"/page" + System.getProperty("file.separator") + filename + System.getProperty("file.separator") + htmlFileName;
			FileReader target = null;
			try {
				target = new FileReader(realPath);
			} catch (FileNotFoundException e) {
				request.getRequestDispatcher("/RepresentAction?index=5")
						.forward(request, response);
				return;
			}
			if (target != null) {
				try {
					target.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String targetHtmlFile = "page"
					+ System.getProperty("file.separator") + filename
					+ System.getProperty("file.separator") + htmlFileName;
			response.sendRedirect(targetHtmlFile);
			break;
			
		case 9:
			System.out.println("99999999");
			filename = request.getParameter("filename");
			if(filename==null||filename.equals(""))
				filename= request.getParameter("file");
			WorkUnitFusionChart chart = (WorkUnitFusionChart) BeanLoader
					.getBean("workUnitFusionChart");
			Condition[] cs = chart.getConditions(filename);
			if (cs == null)
				return;
			Map m = new HashMap(cs.length);
			for (int i = 0; i < cs.length; i++) {
				String value = request.getParameter(cs[i].getParaname());
				if (value != null)
					m.put(cs[i].getParaname(), wordEncoding.getValue(value));
			}

			log.info("请求 xml file:" + filename + "  图形参数：" + m);
			String xml = chart.getResponseLayout(filename, m);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(xml);
			break;
		case 10:
			//刷新缓存
			if (!unitFactory.isRun()) {
				unitFactory.freshXml();
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html");
				response.getWriter().write("刷新缓存成功！");
			} else {
				ThreadContentBean bean = unitFactory.getThreadContentBean();
				response.getWriter().write(bean.toString());
			}
		default:
			break;
		}

	}
}