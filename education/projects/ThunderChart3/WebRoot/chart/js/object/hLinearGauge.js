Ext.namespace("Thunder");
Thunder.HLinearGauge = function(el, type, xmlDoc) {
	this.el = el;
	this.type = type;
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
	this.id = this.el + this.type + Ext.id();
	this.chartLoaded = false;
}
Thunder.HLinearGauge.prototype = {
	swfPath : app.path + "/chart/swf/gauge/",
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
	render : function() {
		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=无数据可显示", this.id, this.width,
				this.height, "0", "1");
		this.chart.setDataXML("<chart></chart>");
		this.chart.render(this.el);
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
			var xslStr = this.createHLineGaugeXslXml(this.dataset, this.chartdata,this.width, this.height);
			var xslDoc = Thunder.XML.loadXMLStr(xslStr);
			var xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
			xmlData = xmlData.replace(/"/g, "'");
			xmlData = xmlData.replace(/&lt;/g, "<");
			xmlData = xmlData.replace(/&gt;/g, ">");
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML(xmlData);
		}
	},
	createHLineGaugeXslXml : function(dataset, chartdata, width, height) {
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
			showValue : "1",
			showTickMarks : '1',
			showTickValues : '0',
		    gaugeScaleAngle:'280',
		    gaugeFillRatio:'20',
		    pointerSides:'3',
		    pointerRadius:'80'
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
		xslStr += '<xsl:template match="Row">                                                              '
		xslStr += '<chart '	+ chartStr+ ' >'
		xslStr += '  <colorRange>                                                                          '
		xslStr += '     <xsl:for-each select="child::*[not(contains(\','
				+ dataset.v1 + ',' + dataset.v2
				+ ',\',concat(\',\',name(),\',\')))]"> '
		xslStr += '    	<xsl:sort data-type="number"  select="number(text())"/>                                               '
		xslStr += '<xsl:variable name="pos" select="position()"/>'
		xslStr += '		    <xsl:choose>                                                                    '
		xslStr += '					<xsl:when test="(position() = 1)">                                            '
		xslStr += '						<xsl:text disable-output-escaping="yes">&lt;color minValue="</xsl:text>     '
		xslStr += '						<xsl:value-of select="current()"/>                                          '
		xslStr += '						<xsl:text>"</xsl:text>                                                      '
		if (chartdata.customerColor && chartdata.customerColor == "1") {
			xslStr += '	<xsl:text disable-output-escaping="yes"> code="</xsl:text>     '
			xslStr += '	 	<xsl:value-of select="$colors/colors/color[position()=1]"/>                                          '
			xslStr += '	<xsl:text>"</xsl:text>   '
		}
		xslStr += '					</xsl:when>                                                                   '
		xslStr += '					<xsl:when test="position() = last()">                                         '
		xslStr += '							<xsl:text> maxValue="</xsl:text>                                          '
		xslStr += '							<xsl:value-of select="current()"/>                                        '
		xslStr += '							<xsl:text disable-output-escaping="yes">"/></xsl:text>                    '
		xslStr += '					</xsl:when>                                                                   '
		xslStr += '					<xsl:otherwise>                                                               '
		xslStr += '							<xsl:text> maxValue="</xsl:text>                                          '
		xslStr += '							<xsl:value-of select="current()"/>                                        '
		xslStr += '							<xsl:text disable-output-escaping="yes">"/>&lt;color minValue="</xsl:text>';
		xslStr += '							<xsl:value-of select="current()"/>                                        '
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
		xslStr += '					</xsl:otherwise>                                                              '
		xslStr += '				</xsl:choose>                                                                   '
		xslStr += '    </xsl:for-each>                                                                     '
		xslStr += '	</colorRange>                                                                         '
		
		xslStr += '	<trendpoints>                                                                                '
		xslStr += '		<xsl:for-each select="child::*[(contains(\',' + dataset.v2 + ',\',concat(\',\',name(),\',\')))]"> '
		xslStr += '    	 <xsl:sort select="number(text())"/>                                               '
		xslStr += '  		<point dashed="1" dashLen="1" dashGap="3" color="8BBA00" thickness="2" >  '
		xslStr += ' 			<xsl:attribute name="value"><xsl:value-of select="current()"/></xsl:attribute> '
		xslStr += ' 	  	</point>    '
		xslStr += '     </xsl:for-each>  '
		xslStr += ' </trendpoints> '
		
		xslStr += '	<pointers>                                                                               '
		xslStr += '<xsl:for-each select="child::*[(contains(\',' + dataset.v1 + ',\',concat(\',\',name(),\',\')))]"> '
		xslStr += '    	<xsl:sort select="number(text())"/>                                               '
		xslStr += '  		<pointer  bgColor=\'666666,FFFFFF,666666\' radius="' + radius * 0.1 + '"> '
		xslStr += ' 			<xsl:attribute name="value"><xsl:value-of select="current()"/></xsl:attribute> '
		xslStr += ' 	  	</pointer>    '
		xslStr += '    </xsl:for-each>  '
		xslStr += ' 	</pointers> '
		
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
//		alert(xslStr);
		return xslStr;

	}

}
