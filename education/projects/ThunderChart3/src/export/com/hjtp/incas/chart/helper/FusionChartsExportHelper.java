package com.hjtp.incas.chart.helper;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * <p>解析flash请求数据流</p>
 * @author qingbao-gao
 * @version version1.0
 * <p>Date:2010-03-03 AM 09:58</p>
 */
public class FusionChartsExportHelper 
{

	/**
	 * <p>解析request数据流,并且封装为ExportHelperBean对象</p>
	 * @param request：flash
	 * @author qingbao-gao
	 * <p>Date:2010-03-03 AM 10:10</p>
	 * @return ExportHelperBean对象
	 */
	  public static ExportHelperBean parseExportRequestStream(HttpServletRequest request)
	  {
			ExportHelperBean exportHelperBean = new ExportHelperBean();
	
		    String stream = request.getParameter("stream");
	         if(StringUtils.isEmpty(stream))
	        	 return null;
		    String parameters = request.getParameter("parameters");
		    ChartMetadatas metadatas = new ChartMetadatas();	
		    String strWidth = request.getParameter("meta_width");
		    metadatas.setWidth(Integer.parseInt(strWidth));
		    String strHeight = request.getParameter("meta_height");
		    metadatas.setHeight(Integer.parseInt(strHeight));
	
		    String bgColor = request.getParameter("meta_bgColor");
	
		    String DOMId = request.getParameter("meta_DOMId");
	
		    metadatas.setDOMId(DOMId);
	
		    metadatas.setBgColor(bgColor);
	
		    exportHelperBean.setMetadatas(metadatas);
		    exportHelperBean.setStream(stream);
	
		    HashMap <String, String>exportParamsFromRequest = parse(parameters);
	
		    exportHelperBean.addExportParametersFromMap(exportParamsFromRequest);
	
		    return exportHelperBean;
	  }
		/**
		 * <p>解析stream,并且放入HashMap</p>
		 * @param request：flash
		 * @author qingbao-gao
		 * <p>Date:2010-03-03 AM 10:10</p>
		 * @return ExportHelperBean对象
		 */
	  public static HashMap<String, String> parse(String strParams)
	  {
		    HashMap<String, String> hash = new HashMap<String, String>();
		    StringTokenizer stPipe = new StringTokenizer(strParams, "|");
		    while (stPipe.hasMoreTokens()) 
		    {
			      String keyValue = stPipe.nextToken();
			      String[] keyValueArr = keyValue.split("=");
			      if (keyValueArr.length > 1) 
			      {
			        hash.put(keyValueArr[0].toLowerCase(), keyValueArr[1]);
			      }
		    }
	        return hash;
	  }
}
