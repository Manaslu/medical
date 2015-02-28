Ext.namespace("Thunder");
Thunder.LockGrid = Ext.extend(Ext.grid.GridPanel,{
	loadMask	: {
		msg	: "装载中......"
	},
	/**
	 * 读取grid的行和列的信息

	 * @return {}
	 */
	initGridConfig : function(){
		var cfgXmlDoc = this.xmlDoc;
		var crossRowValue	= cfgXmlDoc.getElementsByTagName("ts:RowDims")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("code");
		var crossCellValue 	= cfgXmlDoc.getElementsByTagName("ts:ColDims")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("code");
		var crossValue 		= cfgXmlDoc.getElementsByTagName("ts:Measures")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("code");
		var crossColName 	= cfgXmlDoc.getElementsByTagName("ts:RowDims")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("name");
		var colWidth        = cfgXmlDoc.getElementsByTagName("ts:ColDims")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("width");
		var rowWidth        = cfgXmlDoc.getElementsByTagName("ts:RowDims")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("width");
		var dataType		= cfgXmlDoc.getElementsByTagName("ts:Measures")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("type");
		var renderer		= cfgXmlDoc.getElementsByTagName("ts:Measures")[0].childNodes[Ext.isIE ? 0 : 1].getAttribute("renderer");
		return {
			cfgXmlDoc		: cfgXmlDoc,
			crossRowValue	: crossRowValue,
			crossCellValue	: crossCellValue,
			crossValue		: crossValue,
			crossColName    : crossColName,
			colWidth        : parseInt(colWidth),
			rowWidth        : parseInt(rowWidth),
			dataType		: dataType,
			renderer        : renderer
		}
	},
	/**
	 * 读取grid的基本配置信息

	 * @return {}
	 */
	initGridParam : function (){
		var height	= this.xmlDoc.getAttribute("height");
		var width	= this.xmlDoc.getAttribute("width");
		var gridTitle 	= this.xmlDoc.getAttribute("caption");
		var eventobj       = Ext.decode(this.xmlDoc.getAttribute("event"));
		var event = {};
		for(var p in eventobj){
			event[p] = function(a,b,c,d){
				{
					eval(eventobj[p]+"(a,b,c,d)");
				}
			}
		}
		var border 		= this.xmlDoc.getAttribute("showBorder");
		border 			= (border && border == "1") ? true : false;
		var frame 		= this.xmlDoc.getAttribute("useRoundEdge");
		frame			= (frame && frame == "1") ? true : false;
		var enableColumnResize = this.xmlDoc.getAttribute("enableColumnResize") ? (this.xmlDoc.getAttribute("enableColumnResize")=="1")?true:false : true;
		return {
			height	: parseInt(height),
			title	: gridTitle,
			border 	: border,
			frame	: frame,
			event   : event,
			enableColumnResize : enableColumnResize,
			width   : parseInt(width)
		}
	},
	/**
	 * 生成gird的数据集
	 * @param {} url
	 * @return {}
	 */
	genDataArray : function(gridCfg){
		var dataXmlDoc 	= loadXMLFromString(this.xmlData);
		var rows = [],cells = [],data = [];
		var datas = Ext.query("Row", dataXmlDoc);
		for(var i = 0 ; i < datas.length ; i ++ ){
			rows.push(Ext.DomQuery.selectValue(gridCfg.crossRowValue,datas[i]));
			cells.push(Ext.DomQuery.selectValue(gridCfg.crossCellValue,datas[i]))
		}
		rows.distinct();
		cells.distinct();
		for(var i = 0 ; i < rows.length ; i ++ ){
			var rowData = [];
			rowData.push(rows[i]);
			for(var j = 0 ; j < cells.length ; j ++){
				var temp = "";
				for(var k = 0 ; k < datas.length ; k ++){
					if(Ext.DomQuery.selectValue(gridCfg.crossRowValue,datas[k]) == rows[i] && Ext.DomQuery.selectValue(gridCfg.crossCellValue,datas[k]) == cells[j]){
						temp = (Ext.DomQuery.selectValue(gridCfg.crossValue,datas[k],""));
						break;
					} 
				}
				rowData.push(temp);
			}
			data.push(rowData);
			this.datas = data;
		}
		return {
			cells	: cells,
			rows	: rows,
			data	: data,
			crossColName:gridCfg.crossColName
		};
	},
	/**
	 * 生成grid的行和列
	 * @param {} cells
	 * @return {}
	 */
	genColumnAndField : function(cells,colwidth,rowwidth,crossColName,type,renderer){
		var columns = [],fields = [];
		cells.unshift(crossColName||"&nbsp;");
		for(var j = 1 ; j < cells.length ; j ++){
			columns.push({header: cells[j] , width: colwidth, dataIndex: (cells[j] + "_" + j), sortable: false,align : 'right'});
			fields.push({name	: (cells[j] + "_" + j)});
		}
		columns.unshift({header: cells[0] , width: rowwidth, dataIndex: (cells[0] + "_" + 0), sortable: false,align : 'center',resizable :false})
		fields.unshift({name	: (cells[0] + "_" + 0)});
		return {
			columns	: columns,
			fields	: fields
		}
	},
	initComponent : function(){
		var thisObj = this;
		var obj = this.initGridConfig();
		this.id = this.el  + Ext.id() + new Date().valueOf();
		var param = this.initGridParam();
		var dataObj = this.genDataArray(obj);
		var colWidth = obj.colWidth;//update by liushuo
		var rowWidth = parseInt(obj.rowWidth?obj.rowWidth:colWidth);		
		this.height = parseInt(param.height);
		this.width = parseInt(param.width);
		this.autoColWidth = this.xmlDoc.getAttribute('autoColumnWidth') === '1' || this.xmlDoc.getAttribute('autoColumnWidth') === 1;
		this.autoColWidthValue = Math.floor((this.width - this.firstLockColWidth - this.autoOffetWidth)/(dataObj.cells.length));
		if(this.autoColWidthValue < this.defaultColWidth){
			this.autoColWidthValue = this.defaultColWidth;
		}
		var fields = this.genColumnAndField(dataObj.cells,colWidth,rowWidth,dataObj.crossColName,obj.dataType,obj.renderer);
		this.store = new Ext.data.SimpleStore({
			fields	: fields.fields
		});
		this.enableColumnResize = param.enableColumnResize;
		this.store.loadData(dataObj.data);		
		this.title = param.title;
		this.border = param.border;
		this.event = param.event;
		this.autoScroll = false;
		this.frame	= param.frame;
		this.viewConfig = {
			forceFit:false	
		};
		this.stripeRows = true;
		this.enableColumnHide = false;
		this.enableColumnMove = false;
		this.enableHdMenu = false;
		this.colModel = new Ext.grid.ColumnModel(fields.columns);
		this.view =  new Ext.grid.GridView();
		this.on(this.event);
		this.authHeight=true;
		Thunder.LockGrid.superclass.initComponent.call(this)
	},
	setXMLData: function(xmlData){
		var obj = this.initGridConfig();
		this.xmlData = xmlData;
		var dataObj = this.genDataArray(obj);
		try{
			this.store.loadData(dataObj.data);
		}catch(e){}
	},
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
	}
})

/**
 * 针对存String 或存数字类型的数据消除重复行
 * @return {}
 */
Array.prototype.distinct = function() {
	var ret = [];
	for (var i = 0; i < this.length; i++) {
		for (var j = i + 1; j < this.length;) {
			if (this[i] === this[j]) {
				ret.push(this.splice(j, 1)[0]);
			} else {
				j++;
			}
		}
	}
	return ret;

}
