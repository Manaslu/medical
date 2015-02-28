Ext.namespace("Thunder");
Thunder.GaugularRPM = function(el, type, xmlDoc) {
	
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
		this.id = this.el + this.type + Ext.id()+ new Date().valueOf();
	this.chartLoaded = false;
}
Thunder.GaugularRPM.prototype = {
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
			var xslStr = this.createAngularXslXml(this.dataset, this.chartdata,
					this.width, this.height);

			var xslDoc = Thunder.XML.loadXMLStr(xslStr);
			var xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
			xmlData = xmlData.replace(/"/g, "'");
			xmlData = xmlData.replace(/&lt;/g, "<");
			xmlData = xmlData.replace(/&gt;/g, ">");
			var chartObj = getChartFromId(this.id);
			chartObj.setDataXML(xmlData);
		}
	},
	createAngularXslXml : function(dataset, chartdata, width, height) {
		var chartStr = "";
		var radius = chartdata.radius || "30";
		var caption = chartdata.caption;
		var captionPadding = chartdata.captionPadding || "30"
		var captionFontSize = chartdata.captionFontSize || '11'
		
		
		var arcFillColor= chartdata.arcFillColor || "E8F2FE"
		var arcBorderColor= chartdata.arcBorderColor || "E8F2FE"
        var arcFillAlpha= chartdata.arcFillAlpha || "50"

        var dialPlateFillColor= chartdata.dialPlateFillColor || "EDEDED"
        var dialPlateBorderColor= chartdata.dialPlateBorderColor || "EDEDED"
        var dialPlateFillRatio= chartdata.dialPlateFillRatio || "100"

        var circleFillColor= chartdata.circleFillColor || "87BCE5"
        var circleFillRatio= chartdata.circleFillRatio || "100"
        var circleBorderColor= chartdata.circleBorderColor || "87BCE5"

        var dialColor= chartdata.dialColor || "A19CFF"
		
		
		
		var width = width
		var height = height
		var defaultChart = {
	     bgColor:'FFFFFF' ,
	     upperLimit:'5000' ,
	     lowerLimit:'0' ,
	      
	     baseFontColor:'646F8F'  ,
	     showGaugeBorder:'0',
	     gaugeFillMix:'',
	     placeTicksInside:'0' ,
	     placeValuesInside:'1',
	     majorTMColor:'333333' ,
	     majorTMAlpha:'100' ,
	     majorTMHeight:'8' ,
	     majorTMThickness:'1' ,
	     minorTMColor:'666666' ,
	     minorTMAlpha:'100' ,
	     minorTMHeight:'3',
         showValue:'1',
         valueBelowPivot:'1',
	     gaugeScaleAngle:'280' ,
	     pivotRadius:'6'
			
			
			
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
		xslStr += '<chart '
				+ chartStr
				+ ' gaugeOriginY='
				+ ((caption == null) ? ('"' + height / 2 + '"') : ('"'
						+ (height / 2  ) + '" '))

				+ ' gaugeInnerRadius=' + '"' + radius * 0.94 + '" '
				+ ' gaugeOuterRadius=' + '"' + radius * 1.11 + '" '
				+ ' gaugeOriginX=' + '"' + width / 2 + '"' + ' >'

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
		xslStr += '	<dials>                                                                               '
		xslStr += '<xsl:for-each select="child::*[(contains(\',' + dataset.v1
				+ ',' + dataset.v2 + ',\',concat(\',\',name(),\',\')))]"> '
		xslStr += '    	<xsl:sort select="number(text())"/>                                               '
		xslStr += '  		<dial   baseWidth=\'7\' topWidth=\'1\'    valueX="'+ (width / 2) +'" valueY="'+ (height*4/5)+'" bgColor="'+ dialColor  
		   + '"  radius="' + radius * 1.08
				+ '" rearExtension="' + radius * 0.15    +'" > '
		xslStr += ' 			<xsl:attribute name="value"><xsl:value-of select="current()"/></xsl:attribute> '
		xslStr += ' 	  	</dial>    '
		xslStr += '    </xsl:for-each>  '
		xslStr += ' 	</dials> '

		xslStr += '<annotations>'
		

		
		xslStr +=	'<annotationGroup xPos="'+ (width / 2) +'" yPos="'+ ( height / 2 ) +'" showBelow="1">' //计算中心位置	

		xslStr +=	'<annotation type="circle" xPos="0" yPos="0" radius="'+ radius * 1.4 +'" fillColor="'+ circleFillColor  +'"  fillRatio="'+ circleFillRatio +'" borderColor="'+ circleBorderColor +'" />'

		xslStr +=	'<annotation type="circle" xPos="0" yPos="0" radius="'+ radius * 1.25+'" fillColor="'+ dialPlateFillColor +'" fillRatio="'+ dialPlateFillRatio +'" borderColor="'+  dialPlateBorderColor +'" />'

		xslStr +=	'<annotation type="arc" xPos="0" yPos="0" radius="'+radius * 1.25+'" innerRadius="'+radius * 1.16+'" startAngle="-60" endAngle="240" fillColor="'+ arcFillColor +'" fillAlpha="'+ arcFillAlpha +'" borderColor="'+  arcBorderColor +'" />'		
		xslStr +=   '</annotationGroup>'
		



       
		
		
		
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
