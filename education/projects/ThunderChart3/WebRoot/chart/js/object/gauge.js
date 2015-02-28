Ext.namespace("Thunder");
Thunder.Gaugular = function(el, type, xmlDoc) {
	this.el = el;
	this.type = type;
	this.unit = Thunder.unit;
	this.width = xmlDoc.getAttribute("width") || "300";
	this.height = xmlDoc.getAttribute("height") || "200";
	var xdataset = Thunder.getChildByNodeName(xmlDoc, "ts:dataset");
	var xchart = Thunder.getChildByNodeName(xmlDoc, "ts:chart");
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
	this.id = this.el + this.type + Ext.id() + new Date().valueOf(); 
	this.chartLoaded = false;
	this.htmlDom = "";
	if(xmlDoc.getElementsByTagName("ts:htmlDom").length > 0){
		this.htmlDom = xmlDoc.getElementsByTagName("ts:htmlDom")[0].xml;
		if(this.htmlDom == null){		
		   var xmlStr = (new XMLSerializer()).serializeToString(xmlDoc);
		   this.htmlDom = xmlStr.substring(xmlStr.indexOf("<ts:htmlDom>")+12),xmlStr.indexOf("</ts:htmlDom>");
		}
	}
	
	this.xmlData = "";
	this.hasShowValue = xmlDoc.getAttribute("hasShowValue") || "false";
	 
}
Thunder.Gaugular.prototype = {
	swfPath : app.path + "/chart/swf/Charts/",
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
	render : function() {
		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=无数据可显示", this.id, this.width,
				this.height, "0", "1");
		this.chart.setDataXML("<chart></chart>");
		this.chart.render(this.el);
		Ext.DomHelper.insertHtml("beforeend",Ext.getDom(this.el),this.htmlDom);
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
	setXMLData : function(data) {
		if (this.chartLoaded) {
			var xmlDoc = Thunder.XML.loadXMLStr(data);
			var xslStr = this.createAngularXslXml(this.dataset, this.chartdata,
					this.width, this.height);

			var xslDoc = Thunder.XML.loadXMLStr(xslStr);
			var xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
			xmlData = xmlData.replace(/"/g, "'");
			xmlData = xmlData.replace(/&lt;/g, "<");
			xmlData = xmlData.replace(/&gt;/g, ">");
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML(xmlData);
			this.xmlData = xmlData;
		}
	},
	createAngularXslXml : function(dataset, chartdata, width, height) {
		var chartStr = "";
		var radius = chartdata.radius || "30";
		var caption = chartdata.caption;
		var captionPadding = chartdata.captionPadding || "30"
		var captionFontSize = chartdata.captionFontSize || '11'
		var width = width
		var height = height
		var defaultChart = {
			formatNumber : "0",
			formatNumberScale : "0",
			bgColor : "FFFFFF",
			showValues : "1",
			showTickMarks : '1',
			showTickValues : '1',
			valueBelowPivot : "1",
			placeValuesInside : "1",
			gaugeScaleAngle : "180"
		};
		if (chartdata != null) {
			for (var p in chartdata) {
				if (p != "radius")
					defaultChart[p] = chartdata[p];
			}
		}
		for (var p in defaultChart) {
			chartStr += p + '="' + defaultChart[p] + '" ';
		}
		var numberSuffix = chartdata.numberSuffix || "";
		var colorsXml = chartdata.colorsFile || 'colors.xml';
		var xslStr = '<?xml version="1.0" ?>                                                                   '
		xslStr += '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">         '
		if (chartdata.customerColor && chartdata.customerColor == "1") {
			xslStr += '<xsl:variable name="colors" select="document(\''
					+ ctxpath + '/chart/colors/' + top.sysSubject + '/'
					+ colorsXml + '\')"/>';
		}
		xslStr += '<xsl:template match="Row">    '
		xslStr += '<xsl:variable name="syscolors" select="//Data/Rowset/Row"/>';
		xslStr += '<chart '
				+ chartStr
				+ ' gaugeOriginY='
				+ ((caption == null) ? ('"' + height / 2 + '"') : ('"'
						+ (height / 2 + captionPadding / 2 + 8) + '" '))

				+ ' gaugeInnerRadius=' + '"' + radius * 0.85 + '" '
				+ ' gaugeOuterRadius=' + '"' + radius * 1.15 + '" '
				+ ' gaugeOriginX=' + '"' + width / 2 + '"' + ' >'
		xslStr +='     <xsl:for-each select="child::*[not(contains(\',' + dataset.v1 +','+ dataset.v2 + ',colors,\',concat(\',\',name(),\',\')))]"> ' ;   
		xslStr +='    	<xsl:sort data-type="number"  select="number(text())"/> ';
		xslStr +='		<xsl:choose>                                                                    '
		xslStr +='			<xsl:when test="(position() = 1)">  ' 
		xslStr +='     			  <xsl:attribute name="lowerLimit"><xsl:value-of select="current() div '+ this.unit +'"/></xsl:attribute>'
		xslStr +='                      </xsl:when>                                                                   '
		xslStr +='			<xsl:when test="(position() = last())">  ' 
		xslStr +='     			  <xsl:attribute name="upperLimit"><xsl:value-of select="current() div '+ this.unit +'"/></xsl:attribute>'
		xslStr +='                      </xsl:when>   '           
		xslStr +='		</xsl:choose>  '        
		xslStr +='    </xsl:for-each>  '   
		xslStr += '  <colorRange>                                                                          '
		xslStr += '     <xsl:for-each select="child::*[not(contains(\','
				+ dataset.v1 + ',' + dataset.v2+',colors'
				+ ',\',concat(\',\',name(),\',\')))]"> '
		xslStr += '    	<xsl:sort data-type="number"  select="number(text())"/>                                               '
		xslStr += '<xsl:variable name="pos" select="position()"/>'
		xslStr += '		    <xsl:choose>                                                                    '
		xslStr += '					<xsl:when test="(position() = 1)">                                            '
		xslStr += '						<xsl:text disable-output-escaping="yes">&lt;color minValue="</xsl:text>     '
		xslStr += '						<xsl:value-of select="current() div '+ this.unit +'"/>                                          '
		xslStr += '						<xsl:text>"</xsl:text>                                                      '
		if (chartdata.customerColor && chartdata.customerColor == "1") {
			xslStr += '	<xsl:text disable-output-escaping="yes"> code="</xsl:text>     '
			xslStr += '	 	<xsl:value-of select="$colors/colors/color[position()=1]"/>                                          '
			xslStr += '	<xsl:text>"</xsl:text>   '
		}
		else{
			xslStr += '	<xsl:text disable-output-escaping="yes"> code="</xsl:text>     '
			xslStr += '	 	<xsl:value-of select="$syscolors/colors/color[position()=1]"/>                                          '
			xslStr += '	<xsl:text>"</xsl:text>   '
		}
		xslStr += '					</xsl:when>                                                                   '
		xslStr += '					<xsl:when test="position() = last()">                                         '
		xslStr += '							<xsl:text> maxValue="</xsl:text>                                          '
		xslStr += '							<xsl:value-of select="current() div '+ this.unit +'"/>                                        '
		xslStr += '							<xsl:text disable-output-escaping="yes">"/></xsl:text>                    '
		xslStr += '					</xsl:when>                                                                   '
		xslStr += '					<xsl:otherwise>                                                               '
		xslStr += '							<xsl:text> maxValue="</xsl:text>                                          '
		xslStr += '							<xsl:value-of select="current() div '+ this.unit +'"/>                                        '
		xslStr += '							<xsl:text disable-output-escaping="yes">"/>&lt;color minValue="</xsl:text>';
		xslStr += '							<xsl:value-of select="current() div '+ this.unit +'"/>                                        '
		xslStr += '							<xsl:text>"</xsl:text>                                                    '
		if (chartdata.customerColor && chartdata.customerColor == "1") {

			xslStr += '	<xsl:text disable-output-escaping="yes"> code="</xsl:text>     '
			xslStr += '<xsl:variable name="totalcolors" select="count($colors/colors/color)"/>';
			xslStr += '<xsl:choose>'
			xslStr += '<xsl:when test="($pos mod $totalcolors)=0">';
			xslStr += '<xsl:value-of select="$colors/colors/color[position()=$totalcolors]"/>'
			xslStr += '</xsl:when>'
			xslStr += '<xsl:otherwise>'
			xslStr += '<xsl:value-of select="$colors/colors/color[position()=$pos mod $totalcolors]"/>';
			xslStr += '</xsl:otherwise>';
			xslStr += '</xsl:choose>'
			xslStr += '	<xsl:text>"</xsl:text>   '
		}
		else{
			xslStr += '	<xsl:text disable-output-escaping="yes"> code="</xsl:text>     '
			xslStr += '<xsl:variable name="totalcolors" select="count($syscolors/colors/color)"/>';
			xslStr += '<xsl:choose>'
			xslStr += '<xsl:when test="($pos mod $totalcolors)=0">';
			xslStr += '<xsl:value-of select="$syscolors/colors/color[position()=$totalcolors]"/>'
			xslStr += '</xsl:when>'
			xslStr += '<xsl:otherwise>'
			xslStr += '<xsl:value-of select="$syscolors/colors/color[position()=$pos mod $totalcolors]"/>';
			xslStr += '</xsl:otherwise>';
			xslStr += '</xsl:choose>'
			xslStr += '	<xsl:text>"</xsl:text>   '
		}
		xslStr += '					</xsl:otherwise>                                                              '
		xslStr += '				</xsl:choose>                                                                   '
		xslStr += '    </xsl:for-each>                                                                     '
		xslStr += '	</colorRange>                                                                         '
		xslStr += '	<dials>                                                                               '
		xslStr += '<xsl:for-each select="child::*[(contains(\',' + dataset.v1
				+ ',' + dataset.v2 + ',\',concat(\',\',name(),\',\')))]"> '
		xslStr += '    	<xsl:sort select="number(text())"/>                                               '
		xslStr += '  		<dial  bgColor=\'666666,FFFFFF,666666\'  baseWidth="'
				+ radius * 0.15 + '" topWidth="1" radius="' + radius * 1.08
				+ '" rearExtension="' + radius * 0.15 + '"> '
		xslStr += ' 			<xsl:attribute name="value"><xsl:value-of select="current() div '+ this.unit +'"/></xsl:attribute> '
		xslStr += ' 	  	</dial>    '
		xslStr += '    </xsl:for-each>  '
		xslStr += ' 	</dials> '

		xslStr += '<annotations>'
		xslStr += '<annotationGroup>'
		xslStr += '<annotation type="text"  x="' + width / 2 + '" y="'
				+ (height / 2 - captionPadding / 2 - radius)
				+ '" align="center" color="000000" label="' + caption
				+ '" fontSize="' + captionFontSize + '"/>'
		xslStr += '</annotationGroup>'

		xslStr += '</annotations>'
		xslStr += '<styles>'
		xslStr += '<definition>'
		xslStr += '<style type="font" name="myValueFont"     />'
		xslStr += '</definition>' + '<application>'
		xslStr += '   <apply toObject="Value" styles="myValueFont" />'
		xslStr += '</application>' + '</styles>'
		xslStr += '    </chart>  '
		xslStr += '  </xsl:template>  '
		xslStr += ' </xsl:stylesheet>  '
		return xslStr;

	}

}
