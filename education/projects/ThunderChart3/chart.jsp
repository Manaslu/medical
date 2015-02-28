<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>ThunderChart</title>
		<link rel="stylesheet"	href="${pageContext.request.contextPath}/chart/css/ext-all1.css" type="text/css" />
		<link rel="stylesheet"	href="${pageContext.request.contextPath}/chart/css/ext-ux1.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chart/css/portal.css"/>
		<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chart/css/portlet.css"/>
		<script language="JavaScript"   src="${pageContext.request.contextPath}/chart/js/ext1/ext-base.js"></script>
		<script language="JavaScript"	src="${pageContext.request.contextPath}/chart/js/ext1/ext-all.js"></script>
		<script language="JavaScript"	src="${pageContext.request.contextPath}/chart/js/pagebus/pagebus.js"></script>
		<script>
			app = {};
			app.path = "${pageContext.request.contextPath}";
			app.params={};
		app.params.screentype=[window.screen.width,window.screen.height];
			Ext.BLANK_IMAGE_URL = app.path + '/chart/images/default/s.gif';
		</script>
  	</head>
 	 <body>
  		<script type="text/javascript"	src="${pageContext.request.contextPath}/chart/js/dataproxy/crossDomain/ajaxcdr.js"></script>
		<script language="JavaScript"	src="${pageContext.request.contextPath}/chart/js/dataproxy/StoreMgr.js"></script>
		<script language="JavaScript"	src="${pageContext.request.contextPath}/chart/js/dataproxy/init.js"></script>
		<div
			style="text-align:center;font:bold 18px tahoma,arial,helvetica;margin-top:20px">
			ThunderChart图展示
		</div>
		<div style=" border:1px solid #6593cf;float:left; margin-left:2px;padding-left:2px;border-bottom:0 none;border-right:0 none;">

			<ul class="links">
				<li class="link1">
					<a href="#" onClick="renderChart(this); return false">Column2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">Column3D</a>
				</li>
				<li class="link1">
					<a href="#" onClick="renderChart(this); return false">Line</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">Area2D</a>
				</li>
				<li class="link1">
					<a href="#" onClick="renderChart(this); return false">Bar2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">Pie2D</a>
				</li>
				<li class="link1">
					<a href="#" onClick="renderChart(this); return false">Pie3D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">Doughnut2D</a>
				</li>
				<li class="link1">
					<a href="#" onClick="renderChart(this); return false">Doughnut3D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSColumn2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSColumn3D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSLine</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSBar2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSBar3D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSArea</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">StackedColumn3D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">StackedColumn2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">StackedBar2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">StackedBar3D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">StackedArea2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ScrollColumn2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ScrollLine2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ScrollArea2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ScrollStackedColumn2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ScrollCombi2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ScrollCombiDY2D</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">AngularGauge</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">Radar</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">MSStackedColumn2DLineDY</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ext-listgrid</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">text</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">radio</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ext-crossgrid</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">ext-propertygrid</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">arrow</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">marquee</a>
				</li>
				<li class="link2">
					<a href="#" onClick="renderChart(this); return false">trend</a>
				</li>
			</ul>
		</div>
		<div  style=" border:1px solid #6593cf;float:left">
			<iframe id="chartdisplay" width="1000px" height="578px" scrolling="yes"
				frameborder="0" ></iframe>
		</div>
		<div id="xmldisplay"></div>
	<script type="text/javascript">
	 function renderChart(type){ 
       		charttype=type.innerText||type.text;
       		document.getElementById("chartdisplay").src = "${pageContext.request.contextPath}/chart/jsp/chart.jsp?file=/system/"+charttype+".xml"
       		}
      		
	</script>

	</body>

</html>


