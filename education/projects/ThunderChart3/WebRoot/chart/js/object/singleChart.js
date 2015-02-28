Ext.namespace("Thunder");
Thunder.SingleChart = function(el, type, xmlDoc,extraPara) {
	this.el = el;
	this.ratio = 1;
	this.type = type;
	this.xmlDoc = xmlDoc;
	this.unit = Thunder.unit;
	this.extraPara = extraPara;
	this._caption = false;
	this.showValues = this.extraPara.showValues;
	 
		this.width =xmlDoc.getAttribute("width")||"300";
		this.height = xmlDoc.getAttribute("height")||"200";
	 
	var xdataset = Thunder.getChildByNodeName(xmlDoc, "ts:dataset");
	var xchart = Thunder.getChildByNodeName(xmlDoc, "ts:chart");
	this.xlink = Thunder.getChildByNodeName(xmlDoc, "ts:link");
	this.trendLine = Thunder.getChildByNodeName(xmlDoc, "ts:trendline");
	this.hasShowValue = xmlDoc.getAttribute("hasShowValue") || "false";
	this.htmlDom = "";
	if(xmlDoc.getElementsByTagName("ts:htmlDom").length > 0){
		this.htmlDom = xmlDoc.getElementsByTagName("ts:htmlDom")[0].xml;
		if(this.htmlDom == null){		
		   var xmlStr = (new XMLSerializer()).serializeToString(xmlDoc);
		   this.htmlDom = xmlStr.substring(xmlStr.indexOf("<ts:htmlDom>")+12),xmlStr.indexOf("</ts:htmlDom>");
		}
	}
	this.xchart=xchart;
	if (this.xlink) {
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
	this.id = this.el + this.type + Ext.id()+ new Date().valueOf()
	//this.id = this.type;
	this.xmlData = "";
	this.chartLoaded = false;
	
 
}
Thunder.SingleChart.prototype = {
	swfPath : app.path + "/chart/swf/Charts/",
	renderCount : 0,
	rerender :function(){
		this.renderCount++;
		document.getElementById(this.el).innerHTML = "";
		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=数据加载中，请稍等……&XMLLoadingText=数据加载中，请稍等……&InvalidXMLText=没有符合条件的数据.", this.id, this.width,
				this.height, "0", "1");
		this.chart.setDataXML(this.xmlData);
		this.chart.render(this.el);
		this.chart.storeId = this.id;
		//Ext.StoreMgr.register(this.chart);
	},
	resizeChart:function(ratio){
		this.ratio = ratio;
	 
			this.width = (this.xmlDoc.getAttribute("width")||"300") * ratio;
			this.height =(this.xmlDoc.getAttribute("height")||"200") * ratio;
		 
		var doc = Thunder.XML.loadXMLStr(this.xmlData);
		var chartobj = doc.getElementsByTagName("chart")[0];
//		if(chartobj && chartobj.getAttribute("pieRadius"))
//			chartobj.setAttribute("pieRadius",this.pieRadius /1280*Thunder.top.app.params.screentype[0]* ratio);
		if(doc.xml){
			this.xmlData = doc.xml;
		}
		else{
			var xmls = new XMLSerializer();
			this.xmlData = xmls.serializeToString(doc);
		}
		this.rerender();
	},
	setWidth : function(width) {
		this.chart.setAttribute("width", width);

	},
	setHeight : function(height) {
		this.chart.getAttribute("height", height);

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
	render : function(domId) {
		this.renderCount++;
		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=没有数据&XMLLoadingText=数据加载中，请稍等……&InvalidXMLText=数据无效.", this.id, this.width,
				this.height, "0", "1");
		this.chart.setDataXML("<chart></chart>");
	
		if(this.type=="Legend" ){
			this.chart.addVariable('showBorder', '0');
			this.chart.addVariable('borderColor', 'ffffff');
			this.chart.addVariable('borderThickness', '0');
			this.chart.addVariable('borderAlpha', '100');
			this.chart.addVariable('listRowDividerThickness', '0');
			this.chart.addVariable('listRowDividerColor', '000000');
			this.chart.addVariable('listRowDividerAlpha', '0');
			this.chart.addVariable('colorBoxWidth', '7');
			this.chart.addVariable('colorBoxHeight', '7');
			this.chart.addVariable('baseFont', 'Tahoma');
			this.chart.addVariable('baseFontSize', '10');
			this.chart.addVariable('textVerticalPadding', '1');
		        this.chart.addVariable('showPercentValues', '0');
			this.chart.addVariable('colorBoxPadding', '2');
			this.chart.addVariable('valueColumnPadding', '1');
			this.chart.addVariable('showShadow', '0');
			//this.chart.addVariable('numberItemsPerPage', '100');
			
		for(var p in this.chartdata){
			
			this.chart.addVariable(p,this.chartdata[p]);
		}
		if(this.chartdata['bgColor']){
			this.chart.addVariable('alternateRowBgColor', this.chartdata['bgColor']);
		}
		}
		this.chart.render(this.el);
		Ext.DomHelper.insertHtml("beforeend",Ext.getDom(this.el),this.htmlDom);
	},
	getChartObj : function(obj) {
		this.flashObj = getChartFromId(this.id);
		if (this.flashObj + '' == "undefined" || !this.chartLoaded) {
			obj.getChartObj.defer(30, this, this);
		} else {
			return this.flashObj;
		}
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
	setXMLData : function(data) {
		if(!data || data.indexOf("<Row>") ==-1){
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML("<chart>");
			return;
		}
		if (this.chartLoaded) {
			var xmlDoc = Thunder.XML.loadXMLStr(data);
			if(this.type=="Legend"){
			  	var xslStr = this.createSingleXslXml(this.chartdata, this.dataset);
			} else{
				var xslStr = this.createSingleXslXml(this.chartdata, this.dataset);
			}
			var xslDoc = Thunder.XML.loadXMLStr(xslStr);
			var xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
			xmlData = xmlData.replace(/"/g, "'").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
			if(xmlData.indexOf("transformiix:result")>-1 ){
			xmlData = xmlData.substring(0, xmlData.lastIndexOf("<")).replace("<transformiix:result xmlns:transformiix='http://www.mozilla.org/TransforMiix'>",""); 
			
		}
			//top.document.getElementById("xmldisplay").innerText = xmlData;
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML(xmlData);
			this.xmlData = xmlData;
		}

	},

	createSingleXslXml : function(chart, _set) {
		if(this.extraPara.prefix && this._caption == false){
			var subcaption = ThunderChart.subcaption.calcCreator(this.extraPara.para);
			subcaption = Ext.isEmpty(subcaption,false) ? '' : '('+subcaption+')'; 
			Thunder.bName = chart["caption"] =  this.extraPara.prefix +"--"+ chart["caption"] + subcaption;
			this._caption = true;
		}
		var chartStr = "";
		var defaultChart = {
			showValues:this.showValues,
			chartLeftMargin : "0",
			chartRightMargin : "0",
		    bgColor : "FFFFFF",
			borderColor : "FFFFFF",
			showPercentageValues : '1',
			formatNumberScale:"0",
			forceDecimals:"0",
			decimals:this.unit=="10000"?"1":"2"
		};
		for (var p in chart) {
			defaultChart[p] = chart[p];
			if(p=="pieRadius"){
				this.pieRadius = chart[p];
				 
			}
		}
		for (var p in defaultChart) {
			chartStr += p + '="' + defaultChart[p] + '" ';
		}
		var label = _set.d1;
		var value = _set.value;
		var trendLine = _set.trendLine;
		var colorsXml = chart.colorsFile || 'colors.xml';
		var xslStr = [];
		xslStr.push( '<?xml version="1.0" encoding="utf-8" ?>');
		xslStr.push( '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">')
		xslStr.push( '<xsl:key name="keyDim1" match="//Data/Rowset/Row" use="'
				+ label + '" />')
		xslStr.push( '<xsl:variable name="keycount" select="count(//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
				+ label + ')[1])])"/>')
		if (chart.customerColor && chart.customerColor == "1") {
			xslStr.push( '<xsl:variable name="colors" select="document(\''
					+ ctxpath + '/chart/colors/default/'
					+ colorsXml + '\')"/>');
		}
		xslStr.push( '<xsl:template match="/">')
		xslStr.push( '<xsl:text disable-output-escaping="yes">&lt;chart ' + chartStr +' plotSpacePercent="</xsl:text>')
		xslStr.push('<xsl:choose>')
		xslStr.push('<xsl:when test="$keycount &lt; 6">80')
		xslStr.push('</xsl:when>')
		xslStr.push('<xsl:otherwise>45' )
		xslStr.push('</xsl:otherwise>')
		xslStr.push('</xsl:choose>')
		//xslStr.push( '<xsl:value-of select="160 div $keycount"/>')
		xslStr.push( '<xsl:text disable-output-escaping="yes">"&gt;</xsl:text>')
		xslStr.push( '<xsl:for-each select="//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
				+ label + ')[1])]">')
		xslStr.push( '<xsl:variable name="pos" select="position()"/>')
		xslStr.push( '<xsl:variable name="lbl" select="' + label + '"/>')
		xslStr.push( '<xsl:variable name="value" select="sum(//Data/Rowset/Row['
				+ label + '=$lbl]/' + value + ')"/>')
		// xslStr.push( '<xsl:variable name="lbl1" select="concat('\"',$lbl,'\"')
		// \"/>"
		xslStr.push( '<set>')
		xslStr.push( '<xsl:attribute name="label"><xsl:value-of select="$lbl"/></xsl:attribute>')
		xslStr.push( '<xsl:attribute name="value"><xsl:value-of select="$value div '+ this.unit +'"/></xsl:attribute>');
		if (this.xlink) {
			var params = this.lParams;
			for (var p = 0; p < this.lParam.length; p++) {
				xslStr.push( '<xsl:variable name="' + this.lParam[p]
						+ '" select="' + this.lParam[p] + '"/>');
				
				 params += (',<xsl:value-of select="$' + this.lParam[p] + '"/>');
			}
			params += '\"';
			xslStr.push( '<xsl:attribute name="link">JavaScript:' + this.lFunc
					+ '(' + params + ')</xsl:attribute>');
		}

		if (chart.customerColor && chart.customerColor == "1") {
			xslStr.push( '<xsl:variable name="totalcolors" select="count($colors/colors/color)"/>');
			xslStr.push( '<xsl:choose>')
			xslStr.push( '<xsl:when test="($pos mod $totalcolors)=0">');
			xslStr.push( '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$totalcolors]"/></xsl:attribute>')
			xslStr.push( '</xsl:when>')
			xslStr.push( '<xsl:otherwise>')
			xslStr.push( '<xsl:attribute name="color"><xsl:value-of select="$colors/colors/color[position()=$pos mod $totalcolors]"/></xsl:attribute>');
			xslStr.push( '</xsl:otherwise>');
			xslStr.push( '</xsl:choose>')
		}
	    xslStr.push( '</set>')
		xslStr.push( '</xsl:for-each>')
		if ((chart.showMedian && chart.showMedian == "1")
				|| (chart.showAverage && chart.showAverage == "1") || this.trendLine) {
			xslStr.push( '<trendLines>');
			if(this.trendLine){
				for(var t=0;t<this.trendLine.childNodes.length;t++){
					var node = this.trendLine.childNodes[t];
					xslStr.push( '<xsl:variable name="trendValue" select="//Data/Rowset/Row/' + node.getAttribute("code") + '"/>')
					xslStr.push( '<line  color="' + (node.getAttribute("color")||"FF0000")
						+ '" startValue="{$trendValue div '+this.unit + '}" ');
					if (node.getAttribute("name"))
						xslStr.push( 'displayValue="' + node.getAttribute("name") + '"');
					if(node.getAttribute("valueOnRight")=="1")
						xslStr.push( '  showOnTop="1" valueOnRight="' + node.getAttribute("valueOnRight")+ '"');
					xslStr.push( ' />')
				
				}
			}
			if (chart.showMedian && chart.showMedian == "1") {
				chart.medianColor = chart.medianColor || 'FF0000';
				chart.medianDisplay = chart.medianDisplay
				chart.valueOnRight = chart.valueOnRight || "1";
				xslStr.push( '<xsl:variable name="cnt" select="count(//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label + ')[1])])"/>')
				xslStr.push( '<xsl:variable name="cnt1" select="floor($cnt div 2)"/>')
				xslStr.push( '<xsl:for-each select="//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label + ')[1])]">')
				xslStr.push( '<xsl:sort select="//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label + ')[1])]"/>')
				xslStr.push( '<xsl:if test="(position() = 1)"> ')
				xslStr.push( '<xsl:choose>')
				xslStr.push( '<xsl:when test="$cnt1*2 &lt; $cnt">')
				xslStr.push( '<xsl:variable name="targetValue" select="//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label
						+ ')[1])][position()=($cnt1+1)]/'
						+ value
						+ '"/>')
				xslStr.push( '<line  color="' + chart.medianColor
						+ '" startValue="{$targetValue}" ');
				if (chart.medianDisplay)
					xslStr.push( 'displayValue="' + chart.medianDisplay + '"')
				xslStr.push( '  showOnTop="1" valueOnRight="' + chart.valueOnRight
						+ '" />')
				xslStr.push( '</xsl:when>')
				xslStr.push( '<xsl:otherwise>')
				xslStr.push( '<xsl:variable name="targetValue" select="(//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label
						+ ')[1])][position()=$cnt1]/'
						+ value
						+ ' + //Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label
						+ ')[1])][position()=($cnt1+1)]/'
						+ value
						+ ') div 2"/>')
				xslStr.push( '<line  color="' + chart.medianColor
						+ '" startValue="{$targetValue div '+ this.unit +'}" ');
				if (chart.medianDisplay)
					xslStr.push( 'displayValue="' + chart.medianDisplay + '"')
				xslStr.push( '  showOnTop="1" valueOnRight="' + chart.valueOnRight
						+ '" />')
				xslStr.push( '</xsl:otherwise>')
				xslStr.push( '</xsl:choose>')
				xslStr.push( '</xsl:if>')
				xslStr.push( '</xsl:for-each>')
			}
			if (chart.showAverage && chart.showAverage == "1") {
				chart.averageColor = chart.averageColor || 'FF0000';
				chart.averageDisplay = chart.averageDisplay;
				chart.averageValueOnRight = chart.averageValueOnRight || "1";
				xslStr.push( '<xsl:variable name="targetValue" select="sum(//Data/Rowset/Row/'
						+ value
						+ ') div count(//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ label + ')[1])])"/>')
				xslStr.push( '<line  color="' + chart.averageColor
						+ '" startValue="{$targetValue div '+ this.unit+'}" ');
				if (chart.averageDisplay)
					xslStr.push( 'displayValue="' + chart.averageDisplay + '"')
				xslStr.push( '  showOnTop="1" valueOnRight="'
						+ chart.averageValueOnRight + '" />')
			}
			xslStr.push( ' </trendLines>')
		}
		if(chart.labelStyle && chart.labelStyle=="1"){
			xslStr.push( '<styles>')
			xslStr.push( '<definition>')
			xslStr.push( '   <style name="MyFirstFontStyle" type="font" font="Verdana" size="20"  letterSpacing="50" />')
			xslStr.push( ' </definition>')
			xslStr.push( ' <application>')
			xslStr.push( '  <apply toObject="DATALABELS" styles="MyFirstFontStyle" />')
			xslStr.push( '</application> ')   
			xslStr.push( '</styles>')
		}
       
       if(chart.legendStyle && chart.legendStyle=="1"){
			xslStr.push( '<styles>')
			xslStr.push( '<definition>')
			xslStr.push( '   <style name="myLegendFont" type="font" font="Verdana" size="10"  letterSpacing="50" />')
			xslStr.push( ' </definition>')
			xslStr.push( ' <application>')
			xslStr.push( '  <apply toObject="Legend" styles="myLegendFont" />')
			xslStr.push( '</application> ')   
			xslStr.push( '</styles>')
		}
	
		xslStr.push( '<xsl:text disable-output-escaping="yes">&lt;/chart&gt;</xsl:text>');
		xslStr.push( '</xsl:template>')
		xslStr.push( '</xsl:stylesheet>');
        return xslStr.join("");
		
	}

}
