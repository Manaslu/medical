package com.teamsun.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamsun.framework.dao.JdbcSupport;
import com.teamsun.framework.util.BeanLoader;
import com.teamsun.service.action.ResultToDataAction;
import com.teamsun.service.action.SqlResult;

public class DataService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * 	实现数据服务 dataservice/query?instance=1&otype=xml，	 *	otype: 输出类型，取值分别是 xml,json1,json2,csv。	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request,  response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String entity=request.getParameter("instance");
		String otype=request.getParameter("otype");
		ResultToDataAction action = new ResultToDataAction();
		String param=request.getParameter("para");
//		param="{begindate:'20090501',enddate:'20090601',entityid:'3000',begintime:'210000',endtime:''}";
//		param="{log_user:'',log_enity:''}";
		if(param==null){
			param="{}";
		}
		String page=request.getParameter("page");
		SqlResult sqlResult;
//		page="{}";
		String sql="";//用于存储DB中配置的SQL
		int flag=0;//SQL Type标示，0代表是SQL;1代表是存储过程
		JdbcSupport xxx=null;
		Connection con=null;
		try {
			
//			String url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
//            String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/dataaction.properties";
//            Properties config = new Properties();
//            config.load(new FileInputStream(path));
//            System.out.println(path);
//            System.out.println("pagesize=="+config.getProperty("pagesize"));
            
			xxx = (JdbcSupport) BeanLoader.getBean("jdbcSupport_oracle");
			con = xxx.getDatasConn();
			action.setConnection(con);
			if(entity!=null && !"".equals(entity)){
				sqlResult=action.getSQL(entity);
				sql=sqlResult.getSql();
//				System.out.println(sql);
				flag=sqlResult.getFlag();
			}
			if(!"".equals(sql) && "xml".equals(otype)){
				if(flag==0) out.print(action.toXML(sql,param,page)); // 0 代表sql 1 代表存储过程
//				System.out.println(action.toXML(sql,param,page));
				if(flag==1) out.print(action.execToXML(sql,param,page));
			}
			if(!"".equals(sql) && "json".equals(otype)){
				if(flag==0)	out.print(action.toJSON(sql,param,page));
				if(flag==1)	out.print(action.execToJSON(sql,param,page));
			}
			if(!"".equals(sql) && "csv".equals(otype)){
//				 BufferedWriter writer = new BufferedWriter(new
//				 FileWriter("d:\\data.csv"));
//				 writer.write(action.toCSV(sql,param,pageNo));
//				 writer.flush();
//				 writer.close();
//				out.print(action.toCSV(sql,param,page));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(action.getConnection()!=null){
				action.closeConnection();
			}
		}
		out.flush();
		out.close();
	}

}
