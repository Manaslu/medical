Ext.namespace("Thunder");
Thunder.CombineChart = function(el,type, xmlDoc){
	this.el = el;
	this.type = type;
	this.width = xmlDoc.getAttribute("width")||"300";
	this.height = xmlDoc.getAttribute("height")||"200";
	var xdataset = Thunder.getChildByNodeName(xmlDoc, "ts:dataset");
	var xchart = Thunder.getChildByNodeName(xmlDoc, "ts:chart");
	this.xlink = Thunder.getChildByNodeName(xmlDoc, "ts:link");
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
	this.xmlData = "";
	if(xmlDoc.getElementsByTagName("ts:htmlDom").length > 0){
		this.htmlDom = xmlDoc.getElementsByTagName("ts:htmlDom")[0].xml;
		if(this.htmlDom == null){		
		   var xmlStr = (new XMLSerializer()).serializeToString(xmlDoc);
		   this.htmlDom = xmlStr.substring(xmlStr.indexOf("<ts:htmlDom>")+12),xmlStr.indexOf("</ts:htmlDom>");
		}
	}
	 
};
Thunder.CombineChart.prototype = {
	getId:function(){
		return this.id;
	},
	setDataModel:function(dm){
		this.dataModel = dm;
	},
	getDataModel:function(){
		return this.dataModel;
	},
	setDataModelPara:function(para){
		this.dataModelPara = para;
	},
	getDataModelPara:function(){
		return this.dataModelPara;
	},
	swfPath : app.path + "/chart/swf/multiChart/",
	render: function(){
		this.chart = new FusionCharts(this.swfPath + this.type + ".swf?ChartNoDataText=无数据可显示", this.id, this.width,this.height, "0", "1");
		this.chart.setDataXML("<chart></chart>");
		this.chart.render(this.el);
		Ext.DomHelper.insertHtml("beforeend",Ext.getDom(this.el),this.htmlDom);
	},
	setWidth:function(width){
		this.chart.setAttribute("width",width);
	
	},
	setHeight:function(height){
		this.chart.getAttribute("height",height);
	
	},
	getWidth:function(){
		return this.chart.getAttribute("width");
	},
	getHeight:function(){
		return this.chart.getAttribute("height");
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
	setXMLData:function(data){
		if(this.chartLoaded){
			var xmlDoc = Thunder.XML.loadXMLStr(data);
			var xslStr = this.createMultiXslXml(this.chartdata, this.dataset);
			var xslDoc = Thunder.XML.loadXMLStr(xslStr);
			var xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
			xmlData = xmlData.replace(/"/g, "'");
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML(xmlData);
			this.xmlData = xmlData;
			//top.document.getElementById("xmldisplay").innerText = xmlData;
		}
		//alert(xmlData);
	},
	createMultiXslXml:function(chart, _set) {
		var dim1, dim2, value;
		var chartStr = "";
		var defaultChart = {};
		for (var p in chart) {
			defaultChart[p] = chart[p];
		}
		for (var p in defaultChart) {
			chartStr += p + '="' + defaultChart[p] + '" ';
		}
		dim1 = _set.d1;
		dim2 = _set.d2;
		lineSet = _set.lineSet;
		value = _set.value;
		var colorsXml = chart.colorsFile || 'colors.xml';
		var xslStr = '<?xml version="1.0" ?>';
			xslStr	+= '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">'
				xslStr	+= '<xsl:key name="keyDim1" match="Row" use="'+ dim1 + '" />'
				xslStr	+= '<xsl:key name="keyDim2" match="Row" use="'+ dim2 + '" />'
				if(chart.customerColor && chart.customerColor == "1"){
				xslStr += '<xsl:variable name="colors" select="document(\''+ ctxpath +'/chart/colors/default/' + colorsXml +'\')"/>';}
				xslStr	+= '<xsl:template match="/">'
			xslStr	+= '<chart '+ chartStr + 'rotateYAxisName=\'0\'' + '>'
				xslStr	+='<categories>'
					xslStr	+= '<xsl:for-each select="//Row[generate-id(.) = generate-id(key(\'keyDim1\','+ dim1+ '))]">'
						//xslStr	+= '<xsl:sort select="'+ dim1+ '"/>'
						xslStr	+= '<category label="{'+ dim1+ '}"/>'
					xslStr	+='</xsl:for-each>'
				xslStr	+= '</categories>'
					xslStr	+= '<dataset>';
			xslStr	+='<xsl:for-each select="//Row[generate-id(.) = generate-id(key(\'keyDim2\','+ dim2+ '))]">'
				xslStr	+= '<xsl:sort select="'+ dim2+ '"/>'
				xslStr += '<xsl:variable name="pos" select="position()"/>'
				xslStr	+= '<xsl:variable name="d2" select="'+ dim2+ '"/>'
				xslStr	+=	'<xsl:if test="not($d2=\''+lineSet+'\')">'//1996
				xslStr	+= '<dataset  seriesName="{'+ dim2+ '}">'
				if(chart.customerColor && chart.customerColor == "1"){
					xslStr += '<xsl:variable name="totalcolors" select="count($colors/colors/color)"/>';
					xslStr += '<xsl:choose>'
					xslStr += '<xsl:when test="($pos mod $totalcolors)=0">';
					xslStr += '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$totalcolors]"/></xsl:attribute>'
					xslStr += '</xsl:when>'
					xslStr += '<xsl:otherwise>'
					xslStr += '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$pos mod $totalcolors]"/></xsl:attribute>';
					xslStr += '</xsl:otherwise>';
					xslStr += '</xsl:choose>'
				}
			xslStr	+= '<xsl:for-each select="//Row[generate-id()=generate-id(key(\'keyDim1\','+ dim1 + '))]">' 
				//xslStr	+= '<xsl:sort select="' + dim1 + '"/>'
				xslStr	+= '<xsl:variable name="d1" select="' + dim1 + '"/>' 
				xslStr	+= '<xsl:variable name="value" select="sum(//Row[' + dim2 + '=$d2 and ' + dim1+ '=$d1]/' + value + ')"/>' 
				xslStr	+= '<set>'
				xslStr	+= '<xsl:attribute name="value">'
				xslStr	+= '<xsl:value-of select="$value"/></xsl:attribute>' ;
				if(this.xlink){
					var params= this.lParams;
				for(var p=0;p<this.lParam.length;p++){
					xslStr += '<xsl:variable name="' + this.lParam[p] + '" select="//Row[' + dim2 + '=$d2 and ' + dim1+ '=$d1]/'+ this.lParam[p]+ '"/>'	;
					params +=(',<xsl:value-of select="$' + this.lParam[p]+'"/>');
				}
				params +='\"';
				xslStr += '<xsl:attribute name="link">JavaScript:'+this.lFunc+'('+ params +')</xsl:attribute>';
			}
			xslStr	+= '</set>'
			xslStr	+= '</xsl:for-each>'
			xslStr	+= '</dataset>'
			xslStr	+= '</xsl:if>'
			xslStr	+= '</xsl:for-each>' 
					xslStr	+= '</dataset>';
					xslStr	+= '<lineSet seriesname="'+lineSet+'">';
					if(chart.customerColor && chart.customerColor == "1"){
						xslStr += '<xsl:variable name="pos2" select="count(//Row[generate-id(.) = generate-id(key(\'keyDim2\','+ dim2+ '))])"/>'
						xslStr += '<xsl:variable name="totalcolors" select="count($colors/colors/color)"/>';
						xslStr += '<xsl:choose>'
						xslStr += '<xsl:when test="(($pos2+1) mod $totalcolors)=0">';
						xslStr += '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$totalcolors]"/></xsl:attribute>'
						xslStr += '</xsl:when>'
						xslStr += '<xsl:otherwise>'
						xslStr += '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=($pos2 + 1) mod $totalcolors]"/></xsl:attribute>';
						xslStr += '</xsl:otherwise>';
						xslStr += '</xsl:choose>'
						}
     					xslStr	+= '	<xsl:for-each select="//Row[generate-id()=generate-id(key(\'keyDim1\', '+ dim1 +'))]">'
				     //   xslStr	+= '	<xsl:sort select="' + dim1 + '" />'
					xslStr	+= '	   <xsl:variable name="d1" select="' + dim1 + '"/>';
					xslStr	+= '	   <xsl:variable name="value" select="sum(//Row[' + dim2 + '=\''+lineSet+'\' and ' + dim1 + '=$d1]/' + value + ')"/>'
					xslStr	+= '			<set>'
					xslStr	+= '				<xsl:attribute name="value">'
					xslStr	+= '						<xsl:value-of select="$value"/>'
					xslStr	+= '				</xsl:attribute>';
					if(this.xlink){
						var params= this.lParams;
						for(var p=0;p<this.lParam.length;p++){
							xslStr += '<xsl:variable name="' + this.lParam[p] + '" select="//Row[' + dim2 + '=\''+lineSet+'\' and ' + dim1 + '=$d1]/'+ this.lParam[p]+ '"/>'	;
							params +=(',<xsl:value-of select="$' + this.lParam[p]+'"/>');
						}
						params +='\"';
						xslStr += '<xsl:attribute name="link">JavaScript:'+this.lFunc+'('+ params +')</xsl:attribute>';
					}
					xslStr	+= '			</set>'
					xslStr	+= '			</xsl:for-each>'
   					xslStr	+= ' </lineSet>'
			xslStr	+='</chart>' 
			xslStr	+= '</xsl:template>'
			xslStr	+= '</xsl:stylesheet>';
		return xslStr;
	}

}
