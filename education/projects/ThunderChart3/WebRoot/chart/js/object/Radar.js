Ext.namespace("Thunder");
Thunder.Radar = function(el, type, dataurl, dataset, chartdata, width,
		height) {
	this.el = el;
	this.type = type;
	this.dataurl = dataurl;
	this.dataset = dataset;
	this.chartdata = chartdata;
	this.width = width || "240";
	this.height = height || "180";
	this.id = this.el + this.type + Ext.id();
	this.chartLoaded = false;
};
Thunder.Radar.prototype = {
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
	getChartObj : function() {
		for (;;) {
			this.flashObj = getChartFromId(this.id);
			if (this.flashObj && this.flashObj + '' !== "undefined"
					&& this.chartLoaded) {
				return this.flashObj;
				break;
			}
		}
	},
	render : function() {

		//*****************************给所有单选框或者复选框赋给默认值，方便后面的处理**************************************
		if (this.chartdata.showCheckbox == "1"
				|| this.chartdata.showRadiobox == "1") { //*************有单选或者复选框
			if (this.chartdata.showCheckbox == "1"
					&& (this.chartdata.showRadiobox == "0" || this.chartdata.showRadiobox == null)) { ////////////只有复选框
				this.chartdata.checkboxPosition = this.chartdata.checkboxPosition
						|| "top";
				this.chartdata.checkboxAlign = this.chartdata.checkboxAlign
						|| "center";

			}
			if (this.chartdata.showRadiobox == "1"
					&& (this.chartdata.showCheckbox == "0" || this.chartdata.showCheckbox == null)) { //////////只有单选
				this.chartdata.radioboxPosition = this.chartdata.radioboxPosition
						|| "top";
				this.chartdata.radioboxAlign = this.chartdata.radioboxAlign
						|| "center";

			}
			if (this.chartdata.showRadiobox == "1"
					&& this.chartdata.showCheckbox == "1") { //复选,单选都有
				this.chartdata.radioboxPosition = this.chartdata.checkboxPosition
						|| this.chartdata.radioboxPosition || "top";
				this.chartdata.checkboxPosition = this.chartdata.radioboxPosition
						|| this.chartdata.checkboxPosition || "top";

				if (this.chartdata.radioboxAlign == null
						&& this.chartdata.checkboxAlign == null) { //check和radio对齐都为空
					this.chartdata.radioboxAlign = (this.chartdata.radioboxPosition == "top")
							? "left"
							: "top";
					this.chartdata.checkboxAlign = (this.chartdata.checkboxPosition == "top")
							? "right"
							: "bottom";
				}
				if (this.chartdata.radioboxAlign == null
						&& this.chartdata.checkboxAlign != null) { //radio对齐为空
					if (this.chartdata.checkboxAlign == "left") {
						this.chartdata.radioboxAlign = "right";
					}
					if (this.chartdata.checkboxAlign == "right") {
						this.chartdata.radioboxAlign = "left";
					}
					if (this.chartdata.checkboxAlign == "top") {
						this.chartdata.radioboxAlign = "bottom";
					}
					if (this.chartdata.checkboxAlign == "bottom") {
						this.chartdata.radioboxAlign = "top";
					}
					if (this.chartdata.checkboxAlign == "center") {
						this.chartdata.radioboxAlign = (this.chartdata.checkboxPosition == "top")
								? "right"
								: "bottom";
					}
				}
				if (this.chartdata.radioboxAlign != null
						&& this.chartdata.checkboxAlign == null) { //check对齐为空
					if (this.chartdata.radioboxAlign == "left") {
						this.chartdata.checkboxAlign = "right";
					}
					if (this.chartdata.radioboxAlign == "right") {
						this.chartdata.checkboxAlign = "left";
					}
					if (this.chartdata.radioboxAlign == "top") {
						this.chartdata.checkboxAlign = "bottom";
					}
					if (this.chartdata.radioboxAlign == "bottom") {
						this.chartdata.checkboxAlign = "top";
					}
					if (this.chartdata.radioboxAlign == "center") {
						this.chartdata.checkboxAlign = (this.chartdata.checkboxPosition == "top")
								? "right"
								: "bottom";
					}
				}
			}
		}

		if (this.chartdata.showRadiobox == "1") {
			this.chartdata.radioboxBgColor = this.chartdata.radioboxBgColor
					|| "F4F4F4";
			this.chartdata.radioboxFontsize = this.chartdata.radioboxFontsize
					|| "12";
		}

		if (this.chartdata.showCheckbox == "1") {
			this.chartdata.checkboxBgColor = this.chartdata.checkboxBgColor
					|| "F4F4F4"
			this.chartdata.checkboxFontsize = this.chartdata.checkboxFontsize
					|| "12"

		}

		this.chart = new FusionCharts(this.swfPath + this.type
				+ ".swf?ChartNoDataText=无数据可显示", this.id, this.width,
				this.height, "0", "1");

		if (this.chartdata.showCheckbox != "1") {
			this.chart.setDataXML('<chart></chart>');
			this.chart.render(this.el);

		} else {

			var displaydiv = document.getElementById(this.el); //合成的整体图的容器		
			var div1 = document.createElement('div'); //单选或者复选的容器
			var div2 = document.createElement('div'); //chart的容器
			var tabCheckLayout = document.createElement('table'); //check布局的table
			var tabCheckLayoutTbody = document.createElement('tbody');
			var tabChartCheckLayout = document.createElement('table'); //图和组件的结合的table，用于布局,放在displaydiv上
			var tabChartCheckLayoutTbody = document.createElement('tbody');

			displaydiv.setAttribute("id", "displaydiv");
			div1.setAttribute("id", "div1"); //单选或者复选的容器
			div2.setAttribute("id", "div2");
			tabCheckLayout.setAttribute("id", "tabCheckLayout");
			tabChartCheckLayout.setAttribute("id", "tabChartCheckLayout");
			tabCheckLayoutTbody.setAttribute("id", "tabCheckLayoutTbody");

			tabCheckLayout.appendChild(tabCheckLayoutTbody);
			tabCheckLayoutTbody.insertRow(0);
			tabCheckLayoutTbody.rows[0].insertCell(0);

			tabChartCheckLayout.setAttribute("height", this.height);
			tabChartCheckLayout.setAttribute("width", this.width);

			tabChartCheckLayout.appendChild(tabChartCheckLayoutTbody);
			tabChartCheckLayoutTbody.insertRow(0);
			tabChartCheckLayoutTbody.rows[0].insertCell(0);

			div1.appendChild(tabCheckLayout);
			displaydiv.appendChild(tabChartCheckLayout);

			//****************************提供位置********************************************
			if (this.chartdata.showCheckbox == "1"
					|| this.chartdata.showRadiobox == "1") {

				if (this.chartdata.checkboxPosition == "top" //上边或者下边有复选框或者单选框
						|| this.chartdata.checkboxPosition == "bottom"
						|| this.chartdata.radioboxPosition == "top"
						|| this.chartdata.radioboxPosition == "bottom") {

					tabCheckLayoutTbody.rows[0].insertCell(1);
					tabCheckLayoutTbody.rows[0].insertCell(2); //放组件

					tabChartCheckLayoutTbody.insertRow(1); //摆位置
					tabChartCheckLayoutTbody.rows[1].insertCell(0);

				}
				if (this.chartdata.checkboxPosition == "left" //左边或者右边有复选框或者单选框
						|| this.chartdata.checkboxPosition == "right"
						|| this.chartdata.radioboxPosition == "left"
						|| this.chartdata.radioboxPosition == "right") {

					tabCheckLayoutTbody.insertRow(1);
					tabCheckLayoutTbody.insertRow(2);
					tabCheckLayoutTbody.rows[2].insertCell(0);
					tabCheckLayoutTbody.rows[1].insertCell(0);
					tabChartCheckLayoutTbody.rows[0].insertCell(1); //摆位置,左右

				}
			}//............提供位置.......................................................

			//*******************位置关系********************************************************
			if (this.chartdata.checkboxPosition == "top"
					|| this.chartdata.radioboxPosition == "top") { //组件在上边
				div1.setAttribute("style", "width:" + this.width + "px");
				tabChartCheckLayoutTbody.rows[0].cells[0].appendChild(div1);
				tabChartCheckLayoutTbody.rows[1].cells[0].appendChild(div2);

			}
			if (this.chartdata.checkboxPosition == "bottom"
					|| this.chartdata.radioboxPosition == "bottom") { //组件在底部
				div1.setAttribute("style", "width:" + this.width + "px");
				tabChartCheckLayoutTbody.rows[0].cells[0].appendChild(div2);
				tabChartCheckLayoutTbody.rows[1].cells[0].appendChild(div1);

			}
			if (this.chartdata.checkboxPosition == "left"
					|| this.chartdata.radioboxPosition == "left") {

				tabChartCheckLayoutTbody.rows[0].cells[0].appendChild(div1);
				tabChartCheckLayoutTbody.rows[0].cells[1].appendChild(div2);

			}
			if (this.chartdata.checkboxPosition == "right"
					|| this.chartdata.radioboxPosition == "right") { //组件在右边

				tabChartCheckLayoutTbody.rows[0].cells[1].setAttribute(
						"height", this.height);

				tabChartCheckLayoutTbody.rows[0].cells[0].appendChild(div2);
				tabChartCheckLayoutTbody.rows[0].cells[1].appendChild(div1);

			}
			//.............................位置关系........................................................
			this.chart.setDataXML('<chart></chart>');
			this.chart.render(div2);//有组件的render

		}//画图有组件

		

	},
	//........................................render 结束...................................................	

	setXMLData : function(data) {
		//alert(data);
		this.data = data;
		var xmlDoc = Thunder.XML.loadXMLStr(data);
		var xslStr = this.createMultiXslXml(this.chartdata, this.dataset);
		var xslDoc = Thunder.XML.loadXMLStr(xslStr);
		this.xmlData = Thunder.XML.transformNode(xmlDoc, xslDoc);
		this.xmlData = this.xmlData.replace(/"/g, "'");
		//alert(this.xmlData);
		//*******************************************没有附加组件*************************************************** 
		if (this.chartdata.showCheckbox !== "1"
				&& this.chartdata.showRadiobox != "1") {
			//this.chart.render(this.el);
			var chartObj = getChartFromId(this.id);
			//alert("xmlData"+this.xmlData);
			chartObj.setDataXML(this.xmlData);

		} else {
			//............................................没有附加组件............................................................

			//************************************Map的初始化*********************************************************** 
			if (this.chartdata.showRadiobox == "1"
					&& this.chartdata.radioDataSet != null) {
				mapRadiobox = initRadioMap(this.chartdata.radioDataSet); //建立一个维护radiobox的初始状态的map
			}
			if (this.chartdata.showCheckbox == "1") {
				this.mapCheckbox = new Map();
				checkboxData = this.getCheckboxData(this.xmlData);
				this.mapCheckbox = this.initCheckMap(checkboxData); //建立一个维护checkbox状态的map
			}
			//......................................Map的初始化.................................................................. 

			//*********************************************有组件时的布局************************************************    
			//    var displaydiv=document.getElementById(this.el) ;  //合成的整体图的容器		
			//    var div1=document.createElement('div');              //单选或者复选的容器
			//	var div2=document.createElement('div');                  //chart的容器
			//	
			//	var tab = document.createElement('table'); 
			//	var tbody = document.createElement('tbody');
			//    tab.appendChild(tbody);
			//    tbody.insertRow(0);
			//    tbody.rows[0].insertCell(0);
			//    
			//    var tabChartCheckLayout = document.createElement('table');  //图和组件的结合的table，用于布局,放在displaydiv上
			//    tabChartCheckLayout.setAttribute("height",this.height);
			//    tabChartCheckLayout.setAttribute("width",this.width);
			//    var tabChartCheckLayoutTbody = document.createElement('tbody');
			//    tabChartCheckLayout.appendChild(tabChartCheckLayoutTbody);
			//    tabChartCheckLayoutTbody.insertRow(0);
			//    tabChartCheckLayoutTbody.rows[0].insertCell(0);
			//    
			//    div1.appendChild(tab);
			//	displaydiv.appendChild(tabChartCheckLayout);

			//	this.chart.render(div2);

			if (this.chartdata.showCheckbox == "1"
					|| this.chartdata.showRadiobox == "1") {
				var strMaxLen = 0; //取得所有标签的最长的字节的个数
				if (this.mapRadiobox != null) { //取得Radiobox标签的最大长度
					for (var i = 0; i < this.mapRadiobox.size() - 1; i++) {
						if (this.strLength(this.mapRadiobox.arr[i].key) > strMaxLen) {
							strMaxLen = this
									.strLength(this.mapRadiobox.arr[i].key)
						}
					}
				}
				if (this.mapCheckbox != null) { //取得checkbox标签的最大长度
					for (var i = 0; i < this.mapCheckbox.size(); i++) {
						if (this.strLength(this.mapCheckbox.arr[i].key) > strMaxLen) {
							strMaxLen = this
									.strLength(this.mapCheckbox.arr[i].key)
						}
					}
				}

				lableSpace = 37 + 5 * strMaxLen; //在10号字符大小的情况下，一个英文字符长度5个像素，加上复选框的宽度框或者单选按钮的半径，和按钮左边距共40
				var lableSpaceHeight = 0;
				if (this.mapRadiobox != null
						&& (this.mapRadiobox.size() - 1) * lableSpace > width) { //如果所有的标签一行排列超过图的宽度的话，就需要进行分行显示,高度需要计算得出来
					layoutRow = true;
					lableSpaceHeight = Math.ceil((this.mapRadiobox.size() - 1)
							/ Math.floor(width / lableSpace))
							* 25; //排列所有的单选按钮需要的高度
				} else {
					lableSpaceHeight = 15
				}

				if (this.chartdata.checkboxPosition == "top" //上边或者下边有复选框或者单选框
						|| this.chartdata.checkboxPosition == "bottom"
						|| this.chartdata.radioboxPosition == "top"
						|| this.chartdata.radioboxPosition == "bottom") {

					tabCheckLayout.setAttribute("width", this.width);
					tabCheckLayout.setAttribute("height", lableSpaceHeight);
					//	  tabCheckLayoutTbody.rows[0].insertCell(1);
					//      tabCheckLayoutTbody.rows[0].insertCell(2);  //放组件

					//      tabChartCheckLayoutTbody.insertRow(1);  //摆位置
					//      tabChartCheckLayoutTbody.rows[1].insertCell(0);

					// this.chart = new FusionCharts(this.swfPath + this.type + ".swf?ChartNoDataText=无数据可显示", this.id, this.width, (this.height-lableSpaceHeight), "0", "1");

					//var chartObj1 = getChartFromId(this.id);
					//chartObj1.setAttribute(height,(this.height-lableSpaceHeight));

					//  this.chart.setDataXML("<chart></chart>");
					//  this.chart.setDataXML(this.xmlData);
					//  this.chart.render(div2);

				}
				if (this.chartdata.checkboxPosition == "left" //左边或者右边有复选框或者单选框
						|| this.chartdata.checkboxPosition == "right"
						|| this.chartdata.radioboxPosition == "left"
						|| this.chartdata.radioboxPosition == "right") {
					tabCheckLayout.setAttribute("width", lableSpace);
					tabCheckLayout.setAttribute("height", this.height);
					//	  tabCheckLayoutTbody.insertRow(1);
					//      tabCheckLayoutTbody.insertRow(2);
					//      tabCheckLayoutTbody.rows[2].insertCell(0);
					//	  tabCheckLayoutTbody.rows[1].insertCell(0);
					//	  tabChartCheckLayoutTbody.rows[0].insertCell(1); //摆位置,左右

					if (this.mapRadiobox != null
							&& (this.mapRadiobox.size() - 1) * 25 > this.height) { //如果所有的标签竖直一行排列超过图的高度的话，就需要进行分列显示,宽度加倍
						layoutCol = true; //分列标记
						lableSpace *= 2;
					}

					// this.chart = new FusionCharts(this.swfPath + this.type + ".swf?ChartNoDataText=无数据可显示", this.id, (this.width-lableSpace), this.height, "0", "1");
					//  this.chart.(this.xmlData);
					//  this.chart.render(div2); 

					//	var chartObj2 = getChartFromId(this.id);
					//chartObj2.setAttribute(width,(this.width-lableSpace));

				}
			}

			//.......................................有组建布局结束.............................................................

			//******************************************图的大小调整********************************************************
			if (this.chartdata.checkboxPosition == "top"
					|| this.chartdata.radioboxPosition == "top") { //组件在上边
				div1.setAttribute("style", "width:" + this.width + "px");

			}
			if (this.chartdata.checkboxPosition == "bottom"
					|| this.chartdata.radioboxPosition == "bottom") { //组件在底部
				div1.setAttribute("style", "width:" + this.width + "px");

			}
			if (this.chartdata.checkboxPosition == "left"
					|| this.chartdata.radioboxPosition == "left") {
				div1.setAttribute("style", "height:" + this.height + "px;"
						+ "width:" + lableSpace); //组件在左边

				tabChartCheckLayoutTbody.rows[0].cells[0].setAttribute("width",
						lableSpace);
				tabChartCheckLayoutTbody.rows[0].cells[0].setAttribute(
						"height", this.height);

			}
			if (this.chartdata.checkboxPosition == "right"
					|| this.chartdata.radioboxPosition == "right") { //组件在右边
				div1.setAttribute("style", "height:" + this.height + "px;"
						+ "width:" + lableSpace);

				tabChartCheckLayoutTbody.rows[0].cells[1].setAttribute("width",
						lableSpace);
				tabChartCheckLayoutTbody.rows[0].cells[1].setAttribute(
						"height", this.height);

			}

			//................................................................................................................  

			//**************************************************生成图***********************************************

			if (this.chartdata.checkboxPosition == "top" //上边或者下边有复选框或者单选框
					|| this.chartdata.checkboxPosition == "bottom"
					|| this.chartdata.radioboxPosition == "top"
					|| this.chartdata.radioboxPosition == "bottom") {

				// this.chart = new FusionCharts(this.swfPath + this.type + ".swf?ChartNoDataText=无数据可显示", this.id, this.width, (this.height-lableSpaceHeight), "0", "1");

				var chartObj = getChartFromId(this.id);
				chartObj.setAttribute('width', this.width);
				chartObj.setAttribute('height',
						(this.height - lableSpaceHeight));

				this.chart.setDataXML(this.xmlData);

				// this.chart.render(div2);
			}
			if (this.chartdata.checkboxPosition == "left" //左边或者右边有复选框或者单选框
					|| this.chartdata.checkboxPosition == "right"
					|| this.chartdata.radioboxPosition == "left"
					|| this.chartdata.radioboxPosition == "right") {

				//this.chart = new FusionCharts(this.swfPath + this.type + ".swf?ChartNoDataText=无数据可显示", this.id, (this.width-lableSpace), this.height, "0", "1");
				var chartObj = getChartFromId(this.id);
				chartObj.setAttribute('width', (this.width - lableSpace));
				chartObj.setAttribute('height', this.height);
				this.chart.setDataXML(this.xmlData);
				// this.chart.render(div2); 
			}

			// .............................................生成图结束.........................................................	

			//*****************************************只有复选框*****************************************************
			if (this.chartdata.showCheckbox == "1"
					&& (this.chartdata.showRadiobox != "1" || this.chartdata.showRadiobox == null)) {
				var checktable = document.createElement("table")//装check的table
				var checkboxHeight = (this.mapCheckbox.size()) * 24;

				if (this.chartdata.checkboxAlign == "left"
						|| this.chartdata.checkboxAlign == "right"
						|| (this.chartdata.checkboxAlign == "center" && (this.chartdata.checkboxPosition == "top" || this.chartdata.checkboxPosition == "bottom"))) {
					//上下

					var checkboxdata1 = this.getCheckboxData(this.xmlData); //得到checkbox的个数和标签

					checktable.style.fontSize = this.chartdata.checkboxFontsize
					checktable.style.backgroundColor = "#"
							+ this.chartdata.checkboxBgColor //变量

					var checktableBody = document.createElement("tbody")
					checktable.appendChild(checktableBody);
					checktableBody.insertRow(0);
					var j = 0;
					for (var i = 0; i < checkboxdata1.num; i++) {

						checktableBody.rows[0].insertCell(j);
						checktableBody.rows[0].insertCell(j + 1);

						var check = document.createElement("input");
						check.type = 'Checkbox';
						check.defaultChecked = true;
						check.value = checkboxdata1.label[i];
						check.name = checkboxdata1.label[i];
						var thisnew = this; //此处的this指的是multchart对象
						check.onclick = function() {
							//var checkboxObj=document.getElementById(checkid);
							if (this.checked == false) { //此处的this是check控件对象
								thisnew.mapCheckbox.remove(this.name);
							} else {
								thisnew.mapCheckbox.put(this.name, "1");
							}
							var checkstrxsl = ""; //过滤字符串
							for (var t = 0; t < thisnew.mapCheckbox.size(); t++) {
								checkstrxsl += ","
										+ thisnew.mapCheckbox.arr[t].key + ",";
							}
							var xmldataModified = thisnew.getInitData(
									thisnew.chartdata, thisnew.dataset,
									checkstrxsl);//得到改变后的数据
							//alert("xmldataModified:"+xmldataModified);
							var mdchart = getChartFromId(thisnew.id);
							mdchart.setDataXML(xmldataModified);
							// var newwidth=thisnew.chart.getAttribute("width");
							// var newheight=thisnew.chart.getAttribute("height");
							// thisnew.chart=null;
							// thisnew.chart = new FusionCharts(thisnew.swfPath + thisnew.type + ".swf?ChartNoDataText=无数据可显示", thisnew.id, newwidth, newheight, "0", "1");
							// thisnew.chart.setDataXML(xmldataModified);
							// var div2=document.getElementById("chartdiv");
							//  thisnew.chart.render(div2);
						}

						checktableBody.rows[0].cells[j].appendChild(check);
						checktableBody.rows[0].cells[j + 1].innerHTML = checkboxdata1.label[i];
						j += 2;
					}

					// alert(checktable.innerHTML);

				}//.....................................上下......................................
				if (this.chartdata.checkboxAlign == "top"
						|| this.chartdata.checkboxAlign == "bottom"
						|| (this.chartdata.checkboxAlign == "center" && (this.chartdata.checkboxPosition == "left" || this.chartdata.checkboxPosition == "right"))) {
					//左右

					var checkboxdata1 = this.getCheckboxData(this.xmlData); //得到checkbox的个数和标签

					checktable.style.fontSize = this.chartdata.checkboxFontsize
					checktable.style.backgroundColor = "#"
							+ this.chartdata.checkboxBgColor //变量

					var checktableBody = document.createElement("tbody")
					checktable.appendChild(checktableBody);
					for (var i = 0; i < checkboxdata1.num; i++) {

						checktableBody.insertRow(i);
						checktableBody.rows[i].insertCell(0);
						checktableBody.rows[i].insertCell(1);

						var check = document.createElement("input");
						check.type = 'Checkbox';
						check.defaultChecked = true;
						check.value = checkboxdata1.label[i];
						check.name = checkboxdata1.label[i];
						var thisnew = this;
						//ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ              
						check.onclick = function() {

							// var checkboxObj=document.getElementsByName(this) ;
							if (this.checked == false) { //维护map
								thisnew.mapCheckbox.remove(this.name);
							} else {
								thisnew.mapCheckbox.put(this.name, "1");
							}
							var checkstrxsl = ""; //过滤字符串
							for (var t = 0; t < thisnew.mapCheckbox.size(); t++) {
								checkstrxsl += ","
										+ thisnew.mapCheckbox.arr[t].key + ",";
							}
							var xmldataModified = thisnew.getInitData(
									thisnew.chartdata, thisnew.dataset,
									checkstrxsl);//得到改变后的数据
							//alert("xmldataModified:"+xmldataModified);
							var mdchart = getChartFromId(thisnew.id);
							mdchart.setDataXML(xmldataModified);
							// var newwidth=thisnew.chart.getAttribute("width");
							// var newheight=thisnew.chart.getAttribute("height");
							// thisnew.chart=null;
							// thisnew.chart = new FusionCharts(thisnew.swfPath + thisnew.type + ".swf?ChartNoDataText=无数据可显示", thisnew.id, newwidth, newheight, "0", "1");
							// thisnew.chart.setDataXML(xmldataModified);
							// var div2=document.getElementById("chartdiv");
							//  thisnew.chart.render(div2);
						}

						checktableBody.rows[i].cells[0].appendChild(check);
						checktableBody.rows[i].cells[1].innerHTML = checkboxdata1.label[i];
					}

				}//..................左右...................................

				//**********************************装载check******************************  
				if (this.chartdata.checkboxAlign == "center"
						&& (this.chartdata.checkboxPosition == "top" || this.chartdata.checkboxPosition == "bottom")) {

					var tabCheckLayoutTbody = document
							.getElementById("tabCheckLayoutTbody");
					tabCheckLayoutTbody.rows[0].cells[1]
							.appendChild(checktable);
					tabCheckLayoutTbody.rows[0].cells[1].setAttribute("align",
							"center");
				}
				if (this.chartdata.checkboxAlign == "left") {
					tabCheckLayoutTbody.rows[0].cells[0]
							.appendChild(checktable);
					tabCheckLayoutTbody.rows[0].cells[0].setAttribute("align",
							"left");
				}
				if (this.chartdata.checkboxAlign == "right") {
					tabCheckLayoutTbody.rows[0].cells[2].setAttribute("align",
							"right");
					tabCheckLayoutTbody.rows[0].cells[2]
							.appendChild(checktable);
				}
				if (this.chartdata.checkboxAlign == "top") {
					tabCheckLayoutTbody.rows[0].cells[0]
							.appendChild(checktable);
					tabCheckLayoutTbody.rows[0].cells[0].setAttribute("height",
							checkboxHeight + "px");
				}
				if (this.chartdata.checkboxAlign == "center"
						&& (this.chartdata.checkboxPosition == "left" || this.chartdata.checkboxPosition == "right")) {
					tabCheckLayoutTbody.rows[1].cells[0]
							.appendChild(checktable);
					tabCheckLayoutTbody.rows[1].cells[0].setAttribute("height",
							checkboxHeight + "px");
				}
				if (this.chartdata.checkboxAlign == "bottom") {
					tabCheckLayoutTbody.rows[2].cells[0]
							.appendChild(checktable);
					tabCheckLayoutTbody.rows[2].cells[0].setAttribute("height",
							checkboxHeight + "px");
				}

				//...............................装载check....................................................
			}
			//..............................................只有复选框...............................................................  

		}
		//..............................有附加组件的else结束括号...............................................................................

	},//.................................setdata函数结束...............................................................
	createMultiXslXml : function(chart, _set) {
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
		value = _set.value;
		var xslStr = '<?xml version="1.0" ?>';
		xslStr += '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">'
		xslStr += '<xsl:key name="keyDim1" match="Row" use="' + dim1 + '" />'
		xslStr += '<xsl:key name="keyDim2" match="Row" use="' + dim2 + '" />'
		xslStr += '<xsl:template match="/">'
		xslStr += '<chart ' + chartStr + 'rotateYAxisName=\'0\'' + '>'
		xslStr += '<categories>'
		xslStr += '<xsl:for-each select="//Row">'
		//xslStr	+= '<xsl:sort select="'+ dim1+ '"/>'
		xslStr += '<category label="{' + dim1 + '}"/>'
		xslStr += '</xsl:for-each>'
		xslStr += '</categories>'
		xslStr += '<xsl:for-each select="//Row[generate-id(.) = generate-id(key(\'keyDim2\','
				+ dim2 + '))]">'
		xslStr += '<xsl:sort select="' + dim2 + '"/>'
		xslStr += '<xsl:variable name="d2" select="' + dim2 + '"/>'
		xslStr += '<dataset  seriesName="{' + dim2 + '}">'
		xslStr += '<xsl:if test="contains(\'' + (_set.sAxis ? _set.sAxis : "")
				+ '\',$d2)">'
		xslStr += '<xsl:attribute name="parentYAxis">S</xsl:attribute>'
		xslStr += '</xsl:if>'
		xslStr += '<xsl:if test="contains(\'' + (_set.line ? _set.line : "")
				+ '\',$d2)">'
		xslStr += '<xsl:attribute name="renderAs">line</xsl:attribute>'
		xslStr += '</xsl:if>'
		xslStr += '<xsl:if test="contains(\''
				+ (_set.column ? _set.column : "") + '\',$d2)">'
		xslStr += '<xsl:attribute name="renderAs">column</xsl:attribute>'
		xslStr += '</xsl:if>'
		xslStr += '<xsl:if test="contains(\'' + (_set.area ? _set.area : "")
				+ '\',$d2)">'
		xslStr += '<xsl:attribute name="renderAs">area</xsl:attribute>'
		xslStr += '</xsl:if>'
		xslStr += '<xsl:for-each select="//Row">'
		xslStr += '<xsl:sort select="' + dim1 + '"/>'
		xslStr += '<xsl:variable name="d1" select="' + dim1 + '"/>' + '<set>'
		xslStr += '<xsl:attribute name="value">'
		xslStr += '<xsl:value-of select="sum(//Row[' + dim2 + '=$d2 and '
				+ dim1 + '=$d1]/' + value + ')"/>' + '</xsl:attribute>'
				+ '</set>'
		xslStr += '</xsl:for-each>'
		xslStr += '</dataset>'
		xslStr += '</xsl:for-each>'
		if ((chart.showMedian && chart.showMedian == "1")
				|| (chart.showAverage && chart.showAverage == "1")) {
			xslStr += '<trendLines>'
			if (chart.showMedian && chart.showMedian == "1") {
				chart.medianColor = chart.medianColor || 'FF0000';
				chart.medianDisplay = chart.medianDisplay
				chart.valueOnRight = chart.valueOnRight || "1";
				chart.MedianSeries = chart.MedianSeries;
				xslStr += '<xsl:variable name="cnt" select="count(//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ dim1 + ')[1])])"/>'
				xslStr += '<xsl:variable name="cnt1" select="floor($cnt div 2)"/>'
				xslStr += '<xsl:for-each select="//Data/Rowset/Row[generate-id(.) = generate-id(key(\'keyDim1\','
						+ dim1 + ')[1])]">'
				xslStr += '<xsl:sort  select="//Data/Rowset/Row[' + dim2 + '= '
						+ chart.MedianSeries + ']"/>'
				xslStr += '<xsl:if test="(position() = 1)"> '
				xslStr += '<xsl:choose>'
				xslStr += '<xsl:when test="$cnt1*2 &lt; $cnt">'
				xslStr += '<xsl:variable name="targetValue" select="//Data/Rowset/Row['
						+ dim2
						+ '= '
						+ chart.MedianSeries
						+ '][position()=($cnt1+1)]/' + value + '"/>'
				xslStr += '<line  color="' + chart.medianColor
						+ '" startValue="{$targetValue}" ';
				if (chart.medianDisplay)
					xslStr += 'displayValue="' + chart.medianDisplay + '"'
				xslStr += '  showOnTop="1" valueOnRight="' + chart.valueOnRight
						+ '" />'
				xslStr += '</xsl:when>'
				xslStr += '<xsl:otherwise>'
				xslStr += '<xsl:variable name="targetValue" select="(//Data/Rowset/Row['
						+ dim2
						+ '= '
						+ chart.MedianSeries
						+ '][position()=2]/'
						+ value + ' )"/>'
				xslStr += '<line  color="' + chart.medianColor
						+ '" startValue="{$targetValue}" ';
				if (chart.medianDisplay)
					xslStr += 'displayValue="' + chart.medianDisplay + '"'
				xslStr += '  showOnTop="1" valueOnRight="' + chart.valueOnRight
						+ '" />'
				xslStr += '</xsl:otherwise>'
				xslStr += '</xsl:choose>'
				xslStr += '</xsl:if>'
				xslStr += '</xsl:for-each>'
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

		return xslStr;
	}

}
