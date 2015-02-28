<%@ page language="java" import="com.hjtp.incas.chart.*" pageEncoding="utf-8"%>
<%@ page import="com.hjtp.incas.chart.helper.*"%>
<%@ page import="com.hjtp.incas.chart.exporthelper.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>导出图片</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
<%

	try
	{
	    //FusionChartsExportHelper fusionChartsExportHelper=new FusionChartsExportHelper();
	    ExportHelperBean exportHelperBean=FusionChartsExportHelper.parseExportRequestStream(request);
	    ExportFactory exportFactory=ExportFactory.getInstance();
	    String type=(String)exportHelperBean.getExportParameterValue("exportformat");
	    String fileName = (String)exportHelperBean.getExportParameterValue("exportfilename");
	    String str=request.getParameter("parameters");
	    String exportParameters = (String)exportHelperBean.getExportParameterValue("exportparameters");
	    System.out.println(str+"\n---exportParameters:"+exportParameters);
		ExportFormat exportFormat=new ExportFormat(type);
		String contentType=exportFormat.getContentType();
		String fix=exportFormat.getPostfix();
		
		response.reset();		
		response.setContentType(contentType);
		response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"."+fix+"\"");
       		java.io.OutputStream os=response.getOutputStream();
		IFusionCharts iFusionCharts=exportFactory.getFusionCharts(type);
		iFusionCharts.export(exportHelperBean,os);
	    	os.flush();
	    	os.close();			 
       		out.clear();
       		out=pageContext.pushBody(); 
	}
	catch (Exception e) 
	{
	    e.printStackTrace();
	}
 %>
  </body>
</html>
