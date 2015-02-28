Ext.namespace("Thunder");
Thunder.MultiChart = function(el, type,xmlDoc,extraPara) {
	this.el = el;
	this.type = type;
	this.xmlDoc = xmlDoc;
	this.unit = Thunder.unit;
	this.extraPara = extraPara;
	this.showValues = this.extraPara.showValues;
	this._caption = false;
 
		this.width =xmlDoc.getAttribute("width")||"300";
		this.height = xmlDoc.getAttribute("height")||"200";
	 
	var xdataset = Thunder.getChildByNodeName(xmlDoc, "ts:dataset");
	var xchart = Thunder.getChildByNodeName(xmlDoc, "ts:chart");
	this.xlink = Thunder.getChildByNodeName(xmlDoc, "ts:link");
	this.trendLine = Thunder.getChildByNodeName(xmlDoc, "ts:trendline");
	if(this.xlink){
		this.lFunc = this.xlink.getAttribute("f");
		this.lParam = this.xlink.getAttribute("para").split(",");
		this.lParams = '\"<xsl:value-of select="$value"/>';
	}
	this.dataset = {}, this.chartdata = {};
	if (xdataset) {
		for (var j = 0; j < xdataset.attributes.length; j++) {
			this.dataset[xdataset.attributes[j].name] = xdataset.attributes[j].value;
		}
	}
	if (xchart) {
		for (var k = 0; k < xchart.attributes.length; k++) {
			this.chartdata[xchart.attributes[k].name] = xchart.attributes[k].value;
		}
	}
	this.id = this.el + this.type + Ext.id()+ new Date().valueOf();
	this.chartLoaded = false;
	this.hasShowValue = xmlDoc.getAttribute("hasShowValue") || "false";
	this.htmlDom = "";
	if(xmlDoc.getElementsByTagName("ts:htmlDom").length > 0){
		this.htmlDom = xmlDoc.getElementsByTagName("ts:htmlDom")[0].xml;
		if(this.htmlDom == null){		
		   var xmlStr = (new XMLSerializer()).serializeToString(xmlDoc);
		   this.htmlDom = xmlStr.substring(xmlStr.indexOf("<ts:htmlDom>")+12),xmlStr.indexOf("</ts:htmlDom>");
		}
	}
	this.xmlData = "";
	this.chartLoaded = false;
	 
};
Thunder.MultiChart.prototype = {
	swfPath : app.path + "/chart/swf/Charts/",
	renderCount : 0,
	rerender :function(){
		this.renderCount++;
		document.getElementById(this.el).innerHTML = "";
		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=没有数据&XMLLoadingText=数据加载中……", this.id, this.width,
				this.height, "0", "1");
				//if(  this.extraPara.acc  ){this.xmlData=this.getLinesValueFormat(this.xmlData);}
		this.chart.setDataXML(this.xmlData);		
		this.chart.render(this.el);
		this.chart.storeId =  this.id;
		//Ext.StoreMgr.register(this.chart);
	},
	resizeChart:function(ratio){
		if(Thunder.top.app &&Thunder.top.app.params && Thunder.top.app.params.screentype){
			this.width = ratio * parseInt(this.xmlDoc.getAttribute("width")/1280*Thunder.top.app.params.screentype[0])||"300";
			this.height = ratio * parseInt(this.xmlDoc.getAttribute("height")/800*Thunder.top.app.params.screentype[1])||"200";
		}
		else{
			this.width = (this.xmlDoc.getAttribute("width")||"300") * ratio;
			this.height =(this.xmlDoc.getAttribute("height")||"200") * ratio;
		}
//		this.width = (this.xmlDoc.getAttribute("width")||"300") * ratio;
//		this.height =(this.xmlDoc.getAttribute("height")||"200") * ratio;
		this.rerender();
	},
	setWidth : function(width) {
		this.width = width;
	},
	setHeight : function(height) {
		this.height = height;
	},
	getWidth : function() {
		return this.chart.getAttribute("width");
	},
	getHeight : function() {
		return this.chart.getAttribute("height");
	},
	getId : function() {
		return this.id;
	},
	setDataModel : function(dm) {
		this.dataModel = dm;
	},
	getDataModel : function() {
		return this.dataModel;
	},
	setDataModelPara : function(para) {
		this.dataModelPara = para;
	},
	getDataModelPara : function() {
		return this.dataModelPara;
	},
	refreshData : function(){
	
	},
	setAttribute:function(subject,btn,chart){
		var obj = btn;
		if(obj.checked){
			chart.xmlData = chart.xmlData.replace("showValues='0'","showValues='1'");
			chart.xmlData = chart.xmlData.replace("showValues='1'","showValues='1'");
		} else {
			chart.xmlData = chart.xmlData.replace("showValues='0'","showValues='0'");
			chart.xmlData = chart.xmlData.replace("showValues='1'","showValues='0'");
		}
		chart.chart.setDataXML(chart.xmlData);
	},
	getChartObj : function(obj) {
		this.flashObj = getChartFromId(this.id);
		if(this.flashObj+''=="undefined"||!this.chartLoaded){
			obj.getChartObj.defer(300,this,[""]);
		}
		else{
			return this.flashObj;
			}
	},
	render : function() {
		this.renderCount++;
	 

		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=无数据&XMLLoadingText=数据加载中，请稍等……&InvalidXMLText=没有符合条件的数据.", this.id, this.width,
				this.height, "0", "1");

		 
			this.chart.setDataXML('<chart></chart>');
			this.chart.render(this.el);
			var showVC = Ext.DomHelper.insertHtml("beforeend",Ext.getDom(this.el),this.htmlDom);

	 
		

	},
	setXMLData : function(data) {
		//var chartObj = this.getChartObj(this);
		if(!data || data.indexOf("<Row>") ==-1){
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML("<chart>");
			return;
		}
		if(this.chartLoaded){
		this.data = data;
		var xmlDoc = Thunder.XML.loadXMLStr(data);
		
		var xslStr = this.createMultiXslXml(this.chartdata, this.dataset);
		var xslDoc = Thunder.XML.loadXMLStr(xslStr);
		this.xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
		this.xmlData = this.xmlData.replace(/"/g, "'").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
		if(this.xmlData.indexOf("transformiix:result")>-1 ){
			 this.xmlData = this.xmlData.substring(0, this.xmlData.lastIndexOf("<")).replace("<transformiix:result xmlns:transformiix='http://www.mozilla.org/TransforMiix'>",""); 
			
		}
		//if(  this.extraPara.acc  ){this.xmlData=this.getLinesValueFormat(this.xmlData);}
		if (this.chartdata.showCheckbox !== "1"
				&& this.chartdata.showRadiobox != "1") {
			var chartObj = getChartFromId(this.id);
			//if(  this.extraPara.acc  ){this.xmlData=this.getLinesValueFormat(this.xmlData);}
			chartObj.setDataXML(this.xmlData);

		} else {}
		}
	},
	//********************************************函数处理区******************************************************
	
	getInitData:function(chartdata,dataset,checkstrxsl){
	 	if(this.mapRadiobox!=null && this.mapRadiobox.get("checked")!="")       //有radiobox,并且有已经被选中的项目
	 	{
	 		var xmlDoc1 = Thunder.XML.loadXMLStr(this.mapRadiobox.get(this.mapRadiobox.get("checked")));  // 获得当前数据
	 	}else{
	 		var xmlDoc1 = Thunder.XML.loadXMLStr(this.data);
	 	}
		var xslStr1 = this.createMultiXslXml(chartdata, dataset,checkstrxsl);
		var xslDoc1 = Thunder.XML.loadXMLStr(xslStr1);
		var initXmlData = Thunder.XML.transformNode(xmlDoc1, xslDoc1);
		initXmlData = initXmlData.replace(/"/g, "'");  //初始状态数据
		if(  this.extraPara.acc  ){this.xmlData=this.getLinesValueFormat(this.xmlData);}
		return initXmlData;
	
	 },
	 /*
	 *   根据初始数据得到初始checkbox数据:多少个checkbox和labelͼ
	 */  
	   getCheckboxData:function(initXmlData){
	   	var xmlDoc1 = Thunder.XML.loadXMLStr(initXmlData);
		dataset1=xmlDoc1.getElementsByTagName("dataset");
		var checkboxLabelArray=new Array(dataset1.length);
		for(var i=0;i<dataset1.length;i++){
		 checkboxLabelArray[i]=dataset1[i].attributes[0].nodeValue;
		}
		var checkboxData2={num:dataset1.length,label:checkboxLabelArray};
		return checkboxData2;
	
	 },
	  
	/*
	 *   根据checkbox的数据建立一个map，用来维护checkbox的选中的状态
	 *    @ checkboxdata checkbox的个数和标签ͼ
	 */
	
	 initCheckMap:function (checkboxdata3){
	        var map1=new Map();
	          for(var i=0;i<checkboxdata3.num;i++){
	            map1.put(checkboxdata3.label[i],"1");
	          }
	          return map1;
	  },
	
	
	
	
	
	//位置是否不再同一侧，在同一侧的时候的位置逻辑是否对
	checkoutPosition:function (chartobj){ },
	
	getCheckboxData:function (initXmlData){
		   	var xmlDoc2 = Thunder.XML.loadXMLStr(initXmlData);
			dataset1=xmlDoc2.getElementsByTagName("dataset");
			var checkboxLabelArray=new Array(dataset1.length);
			for(var i=0;i<dataset1.length;i++){
			 checkboxLabelArray[i]=dataset1[i].attributes[0].nodeValue;
			}
			var checkboxData2={num:dataset1.length,label:checkboxLabelArray};
			return checkboxData2;
	
	 },
	 
	
	
	  strLength: function (str){ 
	   var strlength = 0;
	   for(var i=0;i<str.length;i++ )          // 循环遍历各个字符，求出长度
	   { 
	    var intcode=str.charCodeAt(i);         // 定格循环指针的位置为字符串的第一个字付处
	    if(intcode>=0 && intcode<=128)     // 判断所指的字符是否是中文的，如果是中文的长度加2，如果不是长度加1；
	    {
	     strlength+=1;
	    }
	    else
	    {
	    strlength+=2;
	    }
	   }
	   return strlength;                                   
	  },
	 
	createMultiXslXml : function(chart, _set,label1) {
		var dim1, dim2, value;
		var chartStr = "";
		var defaultChart = {};
	for(var p in chart){
		if(p!="showRadiobox" 
		   && p!="radioDataSet" 
		   && p!="radioboxBgColor" 
		   && p!="radioboxPosition" 
		   && p!="radioboxAlign" 
		   && p!="showCheckbox" 
		   && p!="checkboxPosition" 
		   && p!="checkboxAlign" 
		   && p!="checkboxBgColor" 
		   
		   )  //去掉自定义的属性
		   {defaultChart[p] =  chart[p];}
		
	}
		for (var p in defaultChart) {
			chartStr += p + '="' + defaultChart[p] + '" ';
		}
		dim1 = _set.d1;
		dim2 = _set.d2;
		value = _set.value;
		
		
		var labelfilterHead=(label1==null)?"":'<xsl:if test="contains(\''+(label1)+ '\', concat(\',\',$d2,\',\'))">';
	    var labelfilterEnd=(label1==null)?"":'</xsl:if>';
		
		
		var colorsXml = chart.colorsFile || 'colors.xml';
		var xslStr = '<?xml version="1.0" ?>';
		xslStr += '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt">'
		xslStr += '<xsl:key name="keyDim1" match="Row" use="' + dim1 + '" />'
		xslStr += '<xsl:key name="keyDim2" match="Row" use="' + dim2 + '" />'
		if(chart.customerColor && chart.customerColor == "1"){
		xslStr += '<xsl:variable name="colors" select="document(\''+ ctxpath +'/chart/colors/default/' + colorsXml +'\')"/>';}
		xslStr += '<xsl:template match="/">'
		xslStr += '<chart ' + chartStr + 'rotateYAxisName=\'0\'' + '>'
		xslStr += '<categories>'
		xslStr += '<xsl:for-each select="//Row[generate-id(.) = generate-id(key(\'keyDim1\','
				+ dim1 + '))]">'
		//xslStr	+= '<xsl:sort select="'+ dim1+ '"/>'
		xslStr += '<category label="{' + dim1 + '}"/>'
		xslStr += '</xsl:for-each>'
		xslStr += '</categories>'
		xslStr += '<xsl:for-each select="//Row[generate-id(.) = generate-id(key(\'keyDim2\','
				+ dim2 + '))]">'
		//xslStr += '<xsl:sort select="' + dim2 + '"/>'
		xslStr += '<xsl:variable name="pos" select="position()"/>'
		xslStr += '<xsl:variable name="d2" select="' + dim2 + '"/>'
		         + labelfilterHead
		xslStr += '<dataset  seriesName="{' + dim2 + '}">'
		if(chart.customerColor && chart.customerColor == "1"){xslStr += '<xsl:variable name="totalcolors" select="count($colors/colors/color)"/>';
					xslStr += '<xsl:choose>'
					xslStr += '<xsl:when test="($pos mod $totalcolors)=0">';
					xslStr += '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$totalcolors]"/></xsl:attribute>'
					xslStr += '</xsl:when>'
					xslStr += '<xsl:otherwise>'
					xslStr += '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$pos mod $totalcolors]"/></xsl:attribute>';
					xslStr += '</xsl:otherwise>';
					xslStr += '</xsl:choose>'}
		if(_set.sAxis){
			var sAxisArray = _set.sAxis.split(",");
			for(var i=0;i<sAxisArray.length;i++){
				xslStr += '<xsl:if test=" normalize-space($d2) = normalize-space(\''+ sAxisArray[i] +'\')">'
				xslStr += '<xsl:attribute name="parentYAxis">S</xsl:attribute>'
				xslStr += '</xsl:if>';
			}
		}
		if(_set.line){
			var lineArray = _set.line.split(",");
			for(var j=0;j<lineArray.length;j++){
				xslStr += '<xsl:if test="normalize-space($d2) = normalize-space(\''+lineArray[j] +'\')">'
				xslStr += '<xsl:attribute name="renderAs">line</xsl:attribute>'
				xslStr += '</xsl:if>'
			}
		}
		if(_set.column){
			var columnArray = _set.column.split(",");
			for(var k=0;k<columnArray.length;k++){
				xslStr += '<xsl:if test="normalize-space($d2) = normalize-space(\''+columnArray[k] +'\')">'
				xslStr += '<xsl:attribute name="renderAs">column</xsl:attribute>'
				xslStr += '</xsl:if>'
			}
		}
		if(_set.area){
			var areaArray = _set.area.split(",");
			for(var h=0;h<areaArray.length;h++){
				xslStr += '<xsl:if test="normalize-space($d2) = normalize-space(\''+areaArray[h] +'\')">'
				xslStr += '<xsl:attribute name="renderAs">area</xsl:attribute>'
				xslStr += '</xsl:if>'
			}
		}
		
		xslStr += '<xsl:for-each select="//Row[generate-id()=generate-id(key(\'keyDim1\','
				+ dim1 + '))]">'
		//xslStr += '<xsl:sort select="' + dim1 + '"/>'
		xslStr += '<xsl:variable name="d1" select="' + dim1 + '"/>'
		xslStr += '<xsl:variable name="value" select="sum(//Row[' + dim2 + '=$d2 and '
				+ dim1 + '=$d1]/' + value + ')"/>'
		xslStr += '<set>'
		xslStr += '<xsl:if test="count(//Row[' + dim2 + '=$d2 and '+ dim1 + '=$d1]/' + value + ')!=0">'
		xslStr += '<xsl:attribute name="value">'
		xslStr += '<xsl:value-of select="$value"/>' 
		xslStr += '</xsl:attribute>'
		xslStr += '</xsl:if>'
		if(this.xlink){
				var params = this.lParams;
				for(var p=0;p<this.lParam.length;p++){
					xslStr += '<xsl:variable name="' + this.lParam[p] + '" select="//Row[' + dim2 + '=$d2 and '+ dim1 + '=$d1]/'+ this.lParam[p]+ '"/>'	;
					params += (',<xsl:value-of select="$' + this.lParam[p]+'"/>');
				}
				params +='\"';
				xslStr += '<xsl:attribute name="link">JavaScript:'+this.lFunc+'('+ params +')</xsl:attribute>';
			}
		xslStr += '</set>'
		xslStr += '</xsl:for-each>'
		xslStr += '</dataset>'
		  +labelfilterEnd
		xslStr += '</xsl:for-each>'
		if ((chart.showMedian && chart.showMedian == "1")
				|| (chart.showAverage && chart.showAverage == "1")||this.trendLine) {
			xslStr += '<trendLines>'
			if(this.trendLine){
				for(var t=0;t<this.trendLine.childNodes.length;t++){
					var node = this.trendLine.childNodes[t];
					xslStr += '<xsl:variable name="trendValue" select="//Data/Rowset/Row/' + node.getAttribute("code") + '"/>'
					xslStr += '<line  color="' + (node.getAttribute("color")||"FF0000")
						+ '" startValue="{$trendValue}" ';
					if (node.getAttribute("name"))
						xslStr += 'displayValue="' + node.getAttribute("name") + '"';
					if(node.getAttribute("valueOnRight")=="1")
						xslStr += '  showOnTop="1" valueOnRight="' + node.getAttribute("valueOnRight")+ '"';
					xslStr += ' />'
				
				}
			}
			if (chart.showMedian && chart.showMedian == "1") {
				chart.medianColor = chart.medianColor || 'FF0000';
				chart.medianDisplay = chart.medianDisplay ;
				chart.valueOnRight = chart.valueOnRight || "1";
				chart.MedianSeries = chart.MedianSeries;
				xslStr +='<xsl:variable name="cnt" select="count(//Row[generate-id(.) = generate-id(key(\'keyDim1\','+ dim1 +'))])"/>';
				xslStr +='			<xsl:variable name="avgValue" select="(sum(//Row['+dim2+' = \''+chart.MedianSeries+'\']/'+value+') div $cnt)"/>'   ;          
				xslStr +='				<xsl:variable name="tmpArr">         '     ;                                                                 
				xslStr +='					<xsl:for-each select="//Row[normalize-space('+dim2+') = \''+chart.MedianSeries+'\']">  '  ;                                                
				xslStr +='						<xsl:sort select="'+value+'" data-type="number"/>   '  ;                                                 
				xslStr +='						<xsl:copy-of select="."/>';
				xslStr +='					</xsl:for-each>';
				xslStr +='				</xsl:variable>';
				xslStr +='				<xsl:for-each select="//Row[normalize-space('+dim2+') = \''+chart.MedianSeries+'\']">';
				xslStr +='					<xsl:sort select="'+ value+'" data-type="number"/>';
				xslStr +='					<xsl:if test="($cnt mod 2)=0 and (position()=($cnt div 2))">';
				xslStr +='						<xsl:variable name="targetValue1" select="'+value+'"/>';
				xslStr +='						<xsl:for-each   select="msxsl:node-set($tmpArr)/*">   ';
				xslStr +='							<xsl:if test="'+value+'=$targetValue1">';
				xslStr +='								<xsl:variable name="targetValue2" select="./following-sibling::*[1]/'+value+'"></xsl:variable>';
				xslStr +='								<xsl:variable name="targetValue" select="($targetValue1+$targetValue2) div 2"></xsl:variable>';
				xslStr += '<line  color="' + chart.medianColor
						+ '" startValue="{$targetValue}" ';
				if (chart.medianDisplay)
					xslStr += 'displayValue="' + chart.medianDisplay + '"'
				xslStr += '  showOnTop="1" valueOnRight="'
						+ chart.medianValueOnRight + '" />'
				xslStr +='							</xsl:if>';
				xslStr +='						</xsl:for-each>';
				xslStr +='					</xsl:if>'
				xslStr +='					<xsl:if test="not(($cnt mod 2)=0) and (position()=ceiling(($cnt div 2)))">';
				xslStr +='						<xsl:variable name="targetValue" select="'+value+'"/>';
				xslStr += '<line  color="' + chart.medianColor
						+ '" startValue="{$targetValue}" ';
				if (chart.medianDisplay)
					xslStr += 'displayValue="' + chart.medianDisplay + '"'
				xslStr += '  showOnTop="1" valueOnRight="'
						+ chart.medianValueOnRight + '" />'
				xslStr +='					</xsl:if>';
				xslStr +='				</xsl:for-each>';
			}
			if (chart.showAverage && chart.showAverage == "1") {
				chart.AverageSeries = chart.AverageSeries;
				chart.averageColor = chart.averageColor || 'FF0000';
				chart.averageDisplay = chart.averageDisplay;
				chart.averageValueOnRight = chart.averageValueOnRight || "1";
				xslStr += '<xsl:variable name="targetValue" select="sum(//Data/Rowset/Row['
						+ dim2
						+ '= '
						+ chart.AverageSeries
						+ ']/'
						+ value
						+ ') div count(//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ dim1 + '))])"/>'
				xslStr += '<line  color="' + chart.averageColor
						+ '" startValue="{$targetValue}" ';
				if (chart.averageDisplay)
					xslStr += 'displayValue="' + chart.averageDisplay + '"'
				xslStr += '  showOnTop="1" valueOnRight="'
						+ chart.averageValueOnRight + '" />'

			}
			xslStr += ' </trendLines>'
		}
		xslStr += '</chart>'
		xslStr += '</xsl:template>'
		xslStr += '</xsl:stylesheet>';
    //alert(xslStr);
		return xslStr;
		 
	}

}