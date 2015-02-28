package com.teamsun.export.action;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.fusioncharts.exporter.encoders.JPEGEncoder;
import com.fusioncharts.exporter.generators.ImageGenerator;
import com.hjtp.incas.chart.helper.ExportHelperBean;
import com.hjtp.incas.chart.helper.FusionChartsExportHelper;
import com.teamsun.export.util.ChartMetaData;
import com.teamsun.export.util.ColumnInfoVO;
import com.teamsun.export.util.ExportExcel;

public class ExportAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExportAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.setCharacterEncoding("utf-8");
		
		
		String jsonData = request.getParameter("exportData")==null?"":request.getParameter("exportData").toString();
		String jsonColumnTitle = request.getParameter("columnTitle")==null?"":request.getParameter("columnTitle").toString();
		String reportName = request.getParameter("reportName");// 报表名称，下载的报表文件名，也有可能展现在excel中
		if (StringUtils.isEmpty(reportName))
			reportName = "temp";
		response.reset();
		response.resetBuffer();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition", "attachment; filename="
				+ reportName + ".xls");
		jsonData = URLDecoder.decode(jsonData, "UTF-8");
		jsonColumnTitle = URLDecoder.decode(jsonColumnTitle, "UTF-8");
		String sheetName = request.getParameter("sheetName");// excel的sheet名字
		if (StringUtils.isEmpty(sheetName))
			sheetName = reportName;

		String allPicInfo = request.getParameter("allPicInfo");
		
	
			try {
				LinkedHashMap tableData=getTableData(jsonData, jsonColumnTitle, response);
				List chartData=getChartData(allPicInfo);
				ExportExcel ee = new ExportExcel(chartData,tableData, "sheet1",
						response.getOutputStream());
			 ee.expordDataAndChart();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }	 
	
	/**
	 * 得到图形数据
	 * @param allPicInfo
	 * @return
	 */
    private List getChartData(String allPicInfo){
    	 List picLis=getPicMetaList(allPicInfo);
 	    JPEGEncoder je=new JPEGEncoder();
 	    ImageGenerator ig=new ImageGenerator();
 	   
 	    for(int i=0;i<picLis.size();i++){
 	    	 ChartMetaData pmd=(ChartMetaData)picLis.get(i);
 	    	 BufferedImage bi=ig.getChartImage(pmd);
 	    	 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
 	    	 try {
 				je.encode(bi, byteArrayOut);
 			} catch (Throwable e) {
 				e.printStackTrace();
 			}
 			pmd.setBaos(byteArrayOut);
 	    }
 	    return picLis;
    }
	private LinkedHashMap getTableData(String json, String jsonColumnTitle,
			HttpServletResponse response) throws ClassNotFoundException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		LinkedHashMap<String, ColumnInfoVO> tableHeader = new LinkedHashMap();
		if(StringUtils.isEmpty(json))
			return tableHeader;
		JSONObject joData = JSONObject.fromObject(json);
		JSONObject tempTitle = JSONObject.fromObject(jsonColumnTitle);
		for (int i = 0; i < tempTitle.length(); i++) {
			JSONObject columnTitle = (JSONObject) tempTitle.getJSONObject(i
					+ "");
			Iterator ite = columnTitle.keys();
			String columnEName = ite.next().toString();
			if ("type".equals(columnEName))
				columnEName = ite.next().toString();

			String columnCName = columnTitle.getString(columnEName);
			String typeName = "type";
			String typeNameValue = columnTitle.getString(typeName);
			ColumnInfoVO civ = new ColumnInfoVO();
			civ.setColumnCName(columnCName);
			civ.setColumnEName(columnEName);
			civ.setColumnType(typeNameValue);
			tableHeader.put(columnEName, civ);

		}

		for (int i = 0; i < joData.length(); i++) {
			JSONObject lineData = (JSONObject) joData.get(i + "");
			for (Iterator ite = lineData.keys(); ite.hasNext();) {
				String key = ite.next().toString();
				String value = lineData.getString(key);
				ColumnInfoVO civ = (ColumnInfoVO) tableHeader.get(key);
				civ.getLineData().add(value);
			}

		}
//		ExportExcel ee = null;
//		try {
//			ee = new ExportExcel(tableHeader, "sheet1", response
//					.getOutputStream());
//			ee.expordExcel();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
       return tableHeader;
	}

	private List getPicMetaList(String jsonStr) {
		List picList = new ArrayList();
		JSONObject jo = JSONObject.fromObject(jsonStr);
		if(!jo.isNullObject())
		for (Iterator ite = jo.keys(); ite.hasNext();) {
			JSONObject item = jo.getJSONObject(ite.next() + "");
			if (item != null) {
				ChartMetaData pmd = null;
				pmd = (ChartMetaData) JSONObject.toBean(item,
						ChartMetaData.class);
				picList.add(pmd);
			}
		}
		return picList;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
