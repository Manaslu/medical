<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:directive.page import="org.dom4j.io.SAXReader"/>
<jsp:directive.page import="org.dom4j.Document"/>
<jsp:directive.page import="java.io.FileInputStream"/>
<jsp:directive.page import="java.io.FileNotFoundException"/>
<jsp:directive.page import="org.dom4j.DocumentException"/>
<jsp:directive.page import="java.io.InputStream"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.io.FileInputStream"/>
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ThunderChart</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chart/css/ext-all.css" type="text/css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chart/css/Style.css" type="text/css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chart/css/layout.css" type="text/css" />

	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/ext2/ext-base.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/ext2/ext-all.js"></script>

<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/ext2/LockingGridView.js"></script>
	
	<script>
		app = {}; 
		 
		app.chartTypes=[];
		app.gridTypes=[];
		app.chartDataModel=[]
		app.path = '${pageContext.request.contextPath}';
		 
		Ext.BLANK_IMAGE_URL = app.path + '/chart/images/default/s.gif';
	</script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/chart/js/dataproxy/crossDomain/ajaxcdr.js"></script>
	<script language="JavaScript"	src="${pageContext.request.contextPath}/chart/js/dataproxy/StoreMgr.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/dataModel.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/FusionCharts.js"></script>
	 
 
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/pagebus/pagebus.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/FreeLayout.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/MSClass.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/xmlProcess.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/paraInChartXML.js"></script>
	

	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/dataproxy/init.js"></script>

		
		
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/FusionChartsExportComponent.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/singleChart.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/FusionMaps.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/mapChina.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/multiChart.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/ReportGrid.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/gauge.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/TextParse.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/GroupHeaderPlugin.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/Ext.ux.XMLReaderBuilder.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/extlistgrid.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/CrossGridParser.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/SliderTip.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/LockGrid.js"></script>
	
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/util/xmlParse.js"></script>

	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/Map.js"></script>
		<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/gaugeRPM.js"></script>
	<script language="JavaScript"  src="${pageContext.request.contextPath}/chart/js/object/hLinearGauge.js"></script>	
	

		
 

</head>
<% 
	
	String file = request.getParameter("file").trim();
	String zoom = request.getParameter("zoom");
	String spliter = request.getParameter("spliter");
	String showValues = request.getParameter("showValues");
	String para = request.getParameter("param") != null ? java.net.URLDecoder.decode(request.getParameter("param"),"UTF-8") : "{}" ;

	String indobj = request.getParameter("indobj") != null ? java.net.URLDecoder.decode(request.getParameter("indobj"),"UTF-8") : "{}" ;
	String isOnReady = request.getParameter("isOnReady")!= null? request.getParameter("isOnReady"):"false";
	String dims = request.getParameter("dim")!= null? java.net.URLDecoder.decode(request.getParameter("dim"),"UTF-8") : "{}" ;
	String conttype = request.getParameter("conttype")!=null?request.getParameter("conttype"):"10000";
	String path = request.getRequestURL().toString();
	String   contPath   =   request.getContextPath();   
	int   nIndex   =   path.indexOf(contPath);   
	path   =   path.substring(0,nIndex   +   contPath.length()); 
	
	String classpath = this.getClass().getClassLoader().getResource("/").getPath();
	String WEB_INF = classpath.substring(0, classpath.indexOf("WEB-INF")+7);
	
	String xmlPath = WEB_INF + "/modules/"+file+".xml"; 
	SAXReader reader = new SAXReader();
	String xmlStr = new String();
	if(para!=null)
		para = para.replaceAll("'","\\\\'").replaceAll("\n","");
	try {
	     System.out.println("配置文件路径:----->"+xmlPath);
	     File f = new File(xmlPath);
	     InputStream fs = new FileInputStream(f);  
		Document doc = reader.read(fs);
		xmlStr = doc.asXML().replaceAll("'","\\\\'").replaceAll("\n","");
		fs.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	 
	
%>


<body  style="margin-top:0">

 	<div id="chartdiv" style="text-align:center;overflow:auto;margin:0 auto;"></div>
 
 	
</body>

</html>

<script language="JavaScript"  type="text/javascript">
var constParams = {
	file 	: '<%=file%>',
	//zoom	: '<%=zoom%>',
	//spliter	: '<%=spliter%>',
	//showValues	: '<%=showValues%>',
	param	: '<%=para%>',
	indobj	: '<%=indobj%>',
	isOnReady	: '<%=isOnReady%>',
	dim	: '<%=dims%>',
	conttype	: '<%=conttype%>'
	
}
var chartData=new Object();
	var gridData=null;
 
		ctxpath = '<%=path%>';
		
			window.onload = function(){
 
        AddDataModel();
		Thunder.ParseConfigXML('<%=xmlStr%>',{showValues:'<%=showValues%>',para:'<%=para%>',indobj:'<%=indobj%>',dims:'<%=dims%>',conttype:'<%=conttype%>'},'<%=isOnReady%>',{zoom:'<%=zoom%>',spliter:'<%=spliter%>'});

	}
	 
 
</script>



