package com.teamsun.export.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.fusioncharts.exporter.encoders.JPEGEncoder;
import com.fusioncharts.exporter.generators.ImageGenerator;
import com.hjtp.incas.chart.helper.ExportHelperBean;
import com.hjtp.incas.chart.helper.FusionChartsExportHelper;
import com.hjtp.incas.chart.helper.FusionChartsImage;
import com.teamsun.export.util.ChartMetaData;
import com.teamsun.export.util.ColumnInfoVO;
import com.teamsun.export.util.ExportExcel;

public class ExportSinleChartAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExportSinleChartAction() {
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
		request.setCharacterEncoding("GB2312");

		String exportParameters = request.getParameter("exportParameters");
		FusionChartsExportHelper fusionChartsExportHelper = new FusionChartsExportHelper();
		ExportHelperBean exportHelperBean = FusionChartsExportHelper
				.parseExportRequestStream(request);
		Map extendParam = exportHelperBean.getExportParameters();
		String extendParamJson = extendParam.get("exportparameters").toString();
		String reportName  = extendParam.get("exportfilename").toString();
		// extendParamJson = URLDecoder.decode(extendParamJson, "UTF-8");
System.out.println("---------"+reportName);
		String gridData = null;
		String jsonColumnTitle = null;
		if (!"null".equals(extendParamJson)) {
			extendParamJson = "{" + extendParamJson + "}";
			JSONObject joData = JSONObject.fromObject(extendParamJson);

			gridData = joData.get("exportData").toString();// extendParam.get(arg0);
			jsonColumnTitle = joData.get("columnTitle").toString();
			//gridData = URLDecoder.decode(gridData, "GB2312");
			//jsonColumnTitle = URLDecoder.decode(jsonColumnTitle, "GB2312");
		}
		if (StringUtils.isEmpty(reportName))
			reportName = "temp";
		response.reset();
		response.resetBuffer();
		response.setContentType("application/x-msdownload");
		response.setCharacterEncoding("UTF-8");
		
		String reportName1 = new String(reportName.getBytes("GBK"),"iso8859-1");
		if(reportName1.indexOf("(")>0)reportName1=reportName1.substring(0, reportName1.indexOf("("));
		
		response.setHeader("Content-disposition", "attachment; filename="
				+ reportName1 + ".xls");
		
		String sheetName = request.getParameter("sheetName");// excel的sheet名字
		if (StringUtils.isEmpty(sheetName))
			sheetName = reportName;

		try {
			LinkedHashMap tableData = getTableData(gridData, jsonColumnTitle,
					response);
			ChartMetaData cmd = getChartData(exportHelperBean);

			ArrayList al = new ArrayList();
			al.add(cmd);
			ExportExcel ee = new ExportExcel(al, tableData, sheetName, response
					.getOutputStream());
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
	 * 
	 * @param allPicInfo
	 * @return
	 */
	private ChartMetaData getChartData(ExportHelperBean exportHelperBean) {

		JPEGEncoder je = new JPEGEncoder();
		ImageGenerator ig = new ImageGenerator();
		ChartMetaData cmd = new ChartMetaData();

		cmd.setBgColor(exportHelperBean.getMetadatas().getBgColor());
		cmd.setDomId(exportHelperBean.getMetadatas().getDOMId());
		cmd.setWidth(exportHelperBean.getMetadatas().getWidth());
		cmd.setHeight(exportHelperBean.getMetadatas().getHeight());
		FusionChartsImage fci = new FusionChartsImage();

		BufferedImage bi = fci.getChartImage(exportHelperBean);
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		try {
			je.encode(bi, byteArrayOut);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		cmd.setBaos(byteArrayOut);

		return cmd;
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
		// ExportExcel ee = null;
		// try {
		// ee = new ExportExcel(tableHeader, "sheet1", response
		// .getOutputStream());
		// ee.expordExcel();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return tableHeader;
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
