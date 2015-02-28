Ext.namespace("Thunder");
Thunder.mapChina = function(el,type,xmlDoc){
	this.el = el;
	this.type = type;
	this.width = xmlDoc.getAttribute("width")||"300";
	this.height = xmlDoc.getAttribute("height")||"200";
	var xchart = Thunder.getChildByNodeName(xmlDoc, "ts:chart");
        var xchartstr="";
	if (xchart) {
		for (var k = 0; k < xchart.attributes.length; k++) {
			xchartstr+=xchart.attributes[k].name;
			xchartstr+="='"+xchart.attributes[k].value +"' ";
		}
	}
	
	this.chartConfig=xchartstr;//得到map的<map>元素的配置
	this.id = this.el + this.type + Ext.id()+ new Date().valueOf();
	this.chartLoaded = false;
}
Thunder.mapChina.prototype ={
	swfPath : app.path + "/chart/swf/Charts/",
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
	getId:function(){
		return this.id;
	},

	render : function(domId){
		
        this.chart = new FusionMaps(this.swfPath + this.type + ".swf?ChartNoDataText=数据加载中，请稍等......", this.id, this.width,this.height, "0", "1");
		this.chart.setDataXML("<map showLabels='0' showCanvasBorder='0' canvasBorderColor='f1f1f1' canvasBorderThickness='2' borderColor='00324A' fillColor='F0FAFF' hoverColor='C0D2F8'><data> </data></map>");
		this.chart.render(this.el);
		this.loadMask = new Ext.LoadMask(this.el, {msg:"请等待......"});
		this.loadMask.show();
	},
	getChartObj:function(obj){
		this.flashObj = getMapFromId(this.id);
		if(this.flashObj+''=="undefined"||!this.chartLoaded){
			obj.getChartObj.defer(30,this,this);
		}
		else{
			return this.flashObj;
			}
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
	setXMLData:function(data){
		this.loadMask.hide();
		if(this.chartLoaded){
			var xmlDoc = Thunder.XML.loadXMLStr(data);
			var chartObj = getMapFromId(this.id);
			var xmlData="<map "+this.chartConfig +">" ;
			if(xmlDoc.getElementsByTagName("map")[0] && xmlDoc.getElementsByTagName("map")[0].childNodes.length>0){
				for(var i=0;i<xmlDoc.getElementsByTagName("map")[0].childNodes.length;i++){
					var string = xmlDoc.getElementsByTagName("map")[0].childNodes[i].xml;
					if(!xmlDoc.getElementsByTagName("map")[0].childNodes[i].xml)
						var string = (new XMLSerializer()).serializeToString(xmlDoc.getElementsByTagName("map")[0].childNodes[i]);
					xmlData+=string;
				}
				}
			xmlData+="</map>";
			//alert(xmlData);
			chartObj.setDataXML(xmlData);
		}
		
	}

}





