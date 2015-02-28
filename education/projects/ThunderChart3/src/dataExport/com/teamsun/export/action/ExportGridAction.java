package com.teamsun.export.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.teamsun.export.util.ColumnInfoVO;
import com.teamsun.export.util.ExportExcel;

/**
 * Servlet implementation class ExportGridAction
 */
public class ExportGridAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportGridAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonData = request.getParameter("exportData")==null?"":request.getParameter("exportData").toString();
		String jsonColumnTitle = request.getParameter("columnTitle")==null?"":request.getParameter("columnTitle").toString();
		String reportName = request.getParameter("reportName");// 报表名称，下载的报表文件名，也有可能展现在excel中
		if (StringUtils.isEmpty(reportName)){
			reportName = "temp";
		}
			
		response.reset();
		response.resetBuffer();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		reportName = new String(reportName.getBytes("GBK"),"ISO-8859-1");
		response.setHeader("Content-disposition", "attachment; filename="
				+ reportName + ".xls");
//		jsonData = URLDecoder.decode(jsonData, "UTF-8");
		//jsonColumnTitle = URLDecoder.decode(jsonColumnTitle, "UTF-8");
		String sheetName = request.getParameter("sheetName");// excel的sheet名字
		if (StringUtils.isEmpty(sheetName)){
			sheetName = reportName;
		}

		try {
			LinkedHashMap tableData=getTableData(jsonData, jsonColumnTitle, response);
			ExportExcel ee = new ExportExcel(tableData, "sheet1",
					response.getOutputStream());
		 ee.expordGridExcel();
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
	
	private LinkedHashMap getTableData(String json, String jsonColumnTitle,
			HttpServletResponse response) throws ClassNotFoundException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		LinkedHashMap<String, ColumnInfoVO> tableHeader = new LinkedHashMap();
		if (StringUtils.isEmpty(json))
			return tableHeader;
		JSONObject joData = JSONObject.fromObject(json);
		JSONObject tempTitle = JSONObject.fromObject(jsonColumnTitle);
		for (int i = 0; i < tempTitle.length(); i++) {
			JSONObject columnTitle = (JSONObject) tempTitle.getJSONObject(i
					+ "");
			Iterator ite = columnTitle.keys();
			String columnEName = ite.next().toString();
			if ("type".equals(columnEName)){
                if(ite.hasNext()){
                	columnEName = ite.next().toString();
                }else{
                	continue;
                }
				
				
			}
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
				if(civ!=null)
				civ.getLineData().add(value);
			}

		}
		return tableHeader;
	}
}
