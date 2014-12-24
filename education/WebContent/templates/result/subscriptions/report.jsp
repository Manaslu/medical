<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8" />
<meta name="Language" content="zh-CN" />

<title>数据报告</title>

<link rel="icon" href="favicon.ico" mce_href="favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="favicon.ico" mce_href="favicon.ico"
	type="image/x-icon">
<link rel="icon" href="favicon.gif" type="image/gif">

<link rel="stylesheet" href="base/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="css/report.css" />

<script src="base/es5-shim/es5-shim.min.js"></script>
<script src="base/jquery-1.10.1.min.js"></script>
<script src="base/Highcharts-3.0.10/js/highcharts.js"></script>
<script src="base/Highcharts-3.0.10/js/modules/exporting.src.js"></script>
<script src="base/bootstrap/js/bootstrap.min.js"></script>
<script>
    	function makeChart(el , list){
    		var xAxis,list1,list2,list3;
        	
        	//小包报表x轴
    		xAxis = list.map(function(item){
    			return item.a1;
    		});
    			
    		//业务量
    		list1 = list.map(function(item){
    			return Number(item.a2);
    		});
    		
    		//业务环比增长
    		list2 = list.map(function(item){
    			//console.log(item.b1.toFixed(2));
    			return Number(item.b1*100);
    		});
    		
    		//收入环比增长
    		list3 = list.map(function(item){
    			return Number(item.b2*100);
    		});
    		
    		$(el).highcharts({
    			chart: { 
	    			   plotBackgroundColor: null, 
	    			   plotBorderWidth: null, 
	    			   plotShadow: false 
    			},
    			exporting:{  
	                   filename:'chart',  
	                   enabled:true,
	                   scale :3,
	                   width :1000,
	                   url:'http://localhost:8080/idap/upload.shtml?image=true'//这里是一个重点哦,也可以修改exporting.js中对应的url  
                },  
    			title: {
		              text: null
		          },
		          subtitle: {
		              text: null
		          },
		          xAxis: [{
		              categories: xAxis
		          }],
		          yAxis: [{ // Primary yAxis
		              labels: {
		                  formatter: function() {
		                      return this.value +'%';
		                  },
		                  style: {
		                      color: '#89A54E'
		                  }
		              },
		              title: {
		                  text: '收入环比增长',
		                  style: {
		                      color: '#89A54E'
		                  }
		              },
		              opposite: true
		  
		          }, { // Secondary yAxis
		              gridLineWidth: 0,
		              title: {
		                  text: '业务量',
		                  style: {
		                      color: '#4572A7'
		                  }
		              },
		              labels: {
		                  formatter: function() {
		                      return this.value +' 万件';
		                  },
		                  style: {
		                      color: '#4572A7'
		                  }
		              }
		  
		          }, { // Tertiary yAxis
		              gridLineWidth: 0,
		              title: {
		                  text: '业务环比增长',
		                  style: {
		                      color: '#AA4643'
		                  }
		              },
		              labels: {
		                  formatter: function() {
		                      return this.value +'%';
		                  },
		                  style: {
		                      color: '#AA4643'
		                  }
		              },
		              opposite: true
		          }],
		          tooltip: {
		              shared: true
		          },
		          legend: {
		              layout: 'vertical',
		              align: 'left',
		              x: 450,
		              verticalAlign: 'top',
		              y: 5,
		              floating: true,
		              backgroundColor: '#FFFFFF'
		          },
		          series: [{
		              name: '业务量',
		              color: '#4572A7',
		              type: 'column',
		              yAxis: 1,
		              data: list1,
		              tooltip: {
		                  valueSuffix: ' 万件'
		              }
		  
		          }, {
		              name: '业务环比增长',
		              type: 'spline',
		              color: '#AA4643',
		              yAxis: 2,
		              data: list2,
		              marker: {
		                  enabled: false
		              },
		              dashStyle: 'shortdot',
		              tooltip: {
		                  valueSuffix: ' %'
		              }
		  
		          }, {
		              name: '收入环比增长',
		              color: '#89A54E',
		              type: 'spline',
		              data: list3,
		              tooltip: {
		                  valueSuffix: ' %'
		              }
		          }]
            });
    		
    		//var chart = $(el).highcharts();
    		
    		//chart.exportChart({ //jpg
    		//	type : 'image/jpeg'
    		//} , null , function(imgData){
    		//	console.log("imageData:",imgData);
    		//	console.log('filePath' , unescape(imgData.filePath));
    		//	$(el+"-img").attr("src","${pageContext.request.contextPath}/upload.shtml?drawiamge=true&fileDir="+imgData.filePath+"&fileType="+imgData.fileType);
    		//});
    	}
    </script>
</head>
<body class="my-report">
	<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="javascript:"><img src="css/bg/login-logo.jpg" class="logo" alt=""/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <h2 class="navbar-text logo-text navbar-right">业务发展月度分析报告 <small>${time}</small></h2>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container">
    <div class="jumbotron">
        <h2>尊敬的领导 ，您好！</h2>
        <p class="lead">以下是本月小包业务发展分析<small></small>，如需了解详细信息，请点击： <a href="javascript:">详细信息</a></p>
    </div>

    <h2 class="page-header text-primary ">
        国内小包
    </h2>

    <div class="panel panel-info">
        <div class="panel-heading">一：总体情况</div>
        <div class="panel-body">
            <p><strong>全国本月共收寄国内小包${bb1ywhb}万件，实现收入${bb1ywhb}亿元</strong>，下图展示本月各省的业务量及业务量、收入的增长情况：</p>
        </div>
        <div style="height: 300px;width:1000px" id="gn"></div>
        
        <script type="text/javascript">
        makeChart('#gn' , ${reportlist8});
		</script>
		<!--  
		<img id="gn-img">
        -->
    </div>


    <div class="panel panel-info">
        <div class="panel-heading">二：客户情况</div>
        <div class="panel-body">
            <p>全国有${gnkhz}家客户发生交寄，环比增长${gnhbzz}。流失客户${gnkhls}个，这批客户${month}月份业务量63.9万件，本月各省的交寄客户、流失客户、留存客户及流失预警客户的情况统计如下：</p>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>省份</th>
                <th>本月交寄客户</th>
                <th>环比增长</th>
                <th>流失客户数</th>
                <th>客户流失率</th>
                <th>下降70%客户数</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportlist1}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
					<td>${row.a5}</td>
					<td>${row.a6}</td>
				</tr>

			</c:forEach>
        </table>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">三：重点城市次日递</div>
        <div class="panel-body">
            <p>下表展示重点城市本省、跨省投递量，以及各环节时长统计情况：</p>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>城市</th>
                <th>省份</th>
                <th>来自25个城市的投递量（件）</th>
                <th>本省投递数量（件）</th>
                <th>25个城市跨省（市）投递量（件）</th>
                <th>25个城市跨省投递量占比</th>
                <th>次日递妥投占比</th>
                <th>全程时限（天）</th>
                <th>收寄时限（天）</th>
                <th>交运时限（天）</th>
                <th>妥投时限（天）</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportlist2}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
					<td>${row.a5}</td>
					<td>${row.a6}</td>
					<td>${row.a7}</td>
					<td>${row.a8}</td>
					<td>${row.a9}</td>
					<td>${row.a10}</td>
					<td>${row.a11}</td>
				</tr>

			</c:forEach>
            </tbody>
        </table>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">四：时限指标</div>
        <div class="panel-body">
            <p>全国各省的收寄量、投递量完成比例及各环节时长如下所示：</p>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>投递机构</th>
                <th>收寄总数</th>
                <th>投递总数</th>
                <th>全程时限(天)</th>
                <th>收寄时长(天)</th>
                <th>交运时长(天)</th>
                <th>投递时长(天)</th>
                <th>妥投率</th>
                <th>上月全程时限</th>
                <th>时限变动</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportlist3}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
					<td>${row.a5}</td>
					<td>${row.a6}</td>
					<td>${row.a7}</td>
					<td>${row.a8}</td>
					<td>${row.a9}</td>
					<td>${row.a10}</td>
				</tr>

			</c:forEach>
            </tbody>
        </table>
    </div>


    <blockquote class="blockquote-reverse">
        <footer><cite>数据来源：量收系统、国内小包时限监控系统和营业系统</cite></footer>
    </blockquote>


    <h2 class="page-header text-warning " style="margin-top: 60px;">
        国际小包
    </h2>

    <div class="panel panel-warning">
        <div class="panel-heading">一：总体情况</div>
        <div class="panel-body">
            <p><strong>5月份，全国共收寄国际包2264.50万件，实现收入4.91亿元，业务量环比增幅为2.73%，业务收入环比增幅为1.04%，日均收寄国际小包73.05万件，日均收入1584.05万元。</strong>各省的业务量及业务量、收入的增长展示如下：</p>
        </div>
        
        <div style="height: 300px;width:1000px" id="gj"></div>
        
        <script type="text/javascript">
        makeChart('#gj' , ${reportlist9});
		</script>
		
		<!--  
		<img id="gj-img">
		-->
		
    </div>

    <div class="panel panel-warning">
        <div class="panel-heading">二：客户情况</div>
        <div class="panel-body">
            <p>本月各省交际客户、流失客户及留存客户如下表所示：</p>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>省份</th>
                <th>本月交寄客户</th>
                <th>交寄客户环比</th>
                <th>交寄客户数量变动</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportlist4}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
				</tr>

			</c:forEach>
            
            </tbody>
        </table>
    </div>



    <div class="panel panel-warning">
        <div class="panel-heading">三：流量流向</div>
        <div class="panel-body">
            <p>本月前20名重点邮路业务量及时长情况如下表所示：</p>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>收寄省份</th>
                <th>寄达地</th>
                <th>业务量(万份)</th>
                <th>占总业务量的比率</th>
                <th>收寄时间-妥投时间(天)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportlist5}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
					<td>${row.a5}</td>
				</tr>

			</c:forEach>
            
            </tbody>
        </table>
    </div>


    <div class="panel panel-warning">
        <div class="panel-heading">四：运递指标</div>
        <div class="panel-body">
            <h4>1. 本月20个重点国家的业务量、信息上网率及时长指标：</h4>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>国家</th>
                <th>出口互换局封发量</th>
                <th>出口互换局开拆量</th>
                <th>妥投量</th>
                <th>D信息上网率</th>
                <th>HI信息上网率</th>
            </tr>
            </thead>
            <tbody>
           <c:forEach items="${reportlist6}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
					<td>${row.a5}</td>
					<td>${row.a6}</td>
				</tr>

			</c:forEach>
            </tbody>
        </table>
        <div class="panel-body">
            <h4>2. 本月前10个出口互换局业务量及各环节时长指标：</h4>
        </div>
        <table class="table table-bordered table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>出口互换局</th>
                <th>总邮件数</th>
                <th>有效邮件数</th>
                <th>占比</th>
                <th>收寄-出口互换局开拆(天)</th>
                <th>出口互换局开拆-出口互换局封发(天)</th>
                <th>出口互换局封发-交航(天)</th>
                <th>收寄-交航(天)(合计)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportlist7}" var="row" varStatus="ind">
				<tr class="tr1">
					<td>${row.a1}</td>
					<td>${row.a2}</td>
					<td>${row.a3}</td>
					<td>${row.a4}</td>
					<td>${row.a5}</td>
					<td>${row.a6}</td>
					<td>${row.a7}</td>
					<td>${row.a8}</td>
				</tr>

			</c:forEach>
            </tbody>
        </table>
    </div>

    <blockquote class="blockquote-reverse">
        <footer><cite>数据来源：量收系统，国际普邮跟踪查询系统，营业系统</cite></footer>
    </blockquote>
</div>

<footer class="footer">
    <div class="container">
        <div class="row">
            <div class="col-md-8 text-help" style="float: left;">
                <p>敬祝</p>
                <p>工作愉快 生活精彩！</p>
            </div>
            <div class="col-md-4 text-right">
                <img src="css/report/pc-sh.png" class="img-responsive" alt="" style="width: 230px;float: right;"/>
            </div>
        </div>
        <p>若要从邮寄列表中删除您的姓名，请 <a href="javascript:">单击此处</a>。</p>
        <p>如有任何问题或意见，请按以下地址向我们发送电子邮件: <a href="mailto:someone@example.com">someone@example.com</a> 或拨打电话 555-555-5555</p>
    </div>
</footer>
<div id="container" style="min-width: 200px; height: 400px; margin: 0 auto"></div>
</body>
</html>