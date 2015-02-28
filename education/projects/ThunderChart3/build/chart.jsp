<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:directive.page import="org.dom4j.io.SAXReader"/>
<jsp:directive.page import="org.dom4j.Document"/>
<jsp:directive.page import="java.io.FileInputStream"/>
<jsp:directive.page import="java.io.FileNotFoundException"/>
<jsp:directive.page import="org.dom4j.DocumentException"/>
<jsp:directive.page import="java.io.InputStream"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ThunderChart</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chart/css/thunderchart.css" type="text/css" />
	<script>
		app = {}; 
		app.cmps = [];//保存当前页面的所有图形、表格数据的ID和type
		app.chartTypes=[];
		app.gridTypes=[];
		app.path = '${pageContext.request.contextPath}';
	</script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/thunderchart.js"></script>
</head>
<% 
	
	String file = request.getParameter("file");
	String zoom = request.getParameter("zoom");
	String spliter = request.getParameter("spliter");
	String showValues = request.getParameter("showValues");
	String para = request.getParameter("para") != null ? java.net.URLDecoder.decode(request.getParameter("para"),"UTF-8") : "{}" ;
	String indobj = request.getParameter("indobj") != null ? java.net.URLDecoder.decode(request.getParameter("indobj"),"UTF-8") : "{}" ;
	String isOnReady = request.getParameter("isOnReady")!= null? request.getParameter("isOnReady"):"false";
	String dims = request.getParameter("dim")!= null? java.net.URLDecoder.decode(request.getParameter("dim"),"UTF-8") : "{}" ;
	String conttype = request.getParameter("conttype")!=null?request.getParameter("conttype"):"10000";
	String path = request.getRequestURL().toString();
	String   contPath   =   request.getContextPath();   
	int   nIndex   =   path.indexOf(contPath);   
	path   =   path.substring(0,nIndex   +   contPath.length()); 
	//String xmlPath = request.getRealPath("WEB-INF") + "/modules/"+file; 
	String xmlPath = "/WEB-INF/modules/"+file;
	SAXReader reader = new SAXReader();
	String xmlStr = new String();
	if(para!=null)
		para = para.replaceAll("'","\\\\'").replaceAll("\n","");
	try {
		InputStream fs = getServletConfig().getServletContext().getResourceAsStream(xmlPath);
		Document doc = reader.read(fs);
		xmlStr = doc.asXML().replaceAll("'","\\\\'").replaceAll("\n","");
		fs.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	 
	
%>

<script type="text/javascript">
Ext.BLANK_IMAGE_URL = app.path + '/chart/images/default/s.gif';
var constParams = {
	file 	: '<%=file%>',
	//zoom	: '<%=zoom%>',
	//spliter	: '<%=spliter%>',
	//showValues	: '<%=showValues%>',
	para	: '<%=para%>',
	indobj	: '<%=indobj%>',
	isOnReady	: '<%=isOnReady%>',
	dim	: '<%=dims%>',
	conttype	: '<%=conttype%>'
	
}
var chartData=new Object();
	var gridData=null;
	window.onload = function(){
		ctxpath = '<%=path%>';
		var arr;
		top.sysSubject = 'default'; 
		Thunder.ParseConfigXML('<%=xmlStr%>',{showValues:'<%=showValues%>',param:'<%=para%>',indobj:'<%=indobj%>',dims:'<%=dims%>',conttype:'<%=conttype%>'},'<%=isOnReady%>',{zoom:'<%=zoom%>',spliter:'<%=spliter%>'});

	}
	
	//导出单个图形和数据
   	/*window.onunload = function(){
   		for(var i = 0 ; i < Thunder.extgridArray.length ; i ++){
   			var grid = Thunder.extgridArray[i];
   			if(grid instanceof Thunder.ExtCrossGrid || grid instanceof Thunder.ReportGrid || grid instanceof Thunder.ExtListGrid){
   				grid.destroy();
   				Thunder.extgridArray.remove(grid);
   			}
   		}
   	}*/
</script>
<body  style="margin-top:0">

 	<div id="chartdiv" style="text-align:center;overflow:auto;margin:0 auto;"></div>
 	<div id="fcexpDiv" style="display:none;" align="text-align:center;overflow:auto">FusionCharts Export Handler Component</div>
 	
</body>

</html>



