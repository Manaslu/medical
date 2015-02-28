package com.teamsun.export.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;


import com.fusioncharts.exporter.encoders.JPEGEncoder;
import com.fusioncharts.exporter.generators.ImageGenerator;

import com.teamsun.export.util.ChartMetaData;
import com.teamsun.export.util.ExportExcel;


public class ExportChartAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExportChartAction() {
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
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
	   
	    String reportName=request.getParameter("reportName");//报表名称，下载的报表文件名，也有可能展现在excel中
		if(StringUtils.isEmpty(reportName))
			reportName="temp1";
		response.setHeader("Content-disposition",
		"attachment; filename="+reportName+".xls");
		 String allPicInfo=request.getParameter("allPicInfo");
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
	    ExportExcel ee = new ExportExcel(picLis, "sheet1",
				response.getOutputStream());
	    ee.expordChartExcel();
//	    String stream=request.getParameter("stream");
//	    String current_dir = getServletContext().getRealPath("/");
//		String working_dir = current_dir + "temp";
//		File outputFile = new File(working_dir+System.getProperty("file.separator")+"xxx.jpg");
//		 FileOutputStream fos = new FileOutputStream(outputFile);
//		
//		 ImageGenerator ig=new ImageGenerator();
//		 ChartMetadata metadata=new ChartMetadata();
//		 metadata.setDOMId("1");
//		 metadata.setHeight(350);
//		 metadata.setWidth(140);
//		 BufferedImage bi=ig.getChartImage(stream, metadata);
//		 try {
//			je.encode(bi, fos);
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		 fos.close();
		 
		 
	}
    public List getPicMetaList(String jsonStr){
    	List picList=new ArrayList();
    	JSONObject jo=JSONObject.fromObject(jsonStr);  
    	for(Iterator ite=jo.keys();ite.hasNext();){
    		JSONObject item=jo.getJSONObject(ite.next()+"");
    		if(item!=null){
    			ChartMetaData pmd=null;
    			pmd=(ChartMetaData)JSONObject.toBean(item, ChartMetaData.class); 
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
