Ext.namespace("Thunder");
Ext.override(Ext.grid.GridView,{
	 initTemplates : function(){
    var ts = this.templates || {};
    if(!ts.master){
        ts.master = new Ext.Template(
                '<div class="x-grid3" hidefocus="true">',
                    '<div class="x-grid3-viewport">',
                        '<div class="x-grid3-header"><div class="x-grid3-header-inner" align="left"><div class="x-grid3-header-offset">{header}</div></div><div class="x-clear"></div></div>',
                        '<div class="x-grid3-scroller"><div class="x-grid3-body" align="left">{body}</div><a href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',
                    "</div>",
                    '<div class="x-grid3-resize-marker">&#160;</div>',
                    '<div class="x-grid3-resize-proxy">&#160;</div>',
                "</div>"
                );
    }

    if(!ts.header){
        ts.header = new Ext.Template(
                '<table border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                '<thead><tr class="x-grid3-hd-row">{cells}</tr></thead>',
                "</table>"
                );
    }

    if(!ts.hcell){
        ts.hcell = new Ext.Template(
                '<td class="x-grid3-hd x-grid3-cell x-grid3-td-{id}" style="{style}"><div {tooltip} {attr} class="x-grid3-hd-inner x-grid3-hd-{id}" unselectable="on" style="{istyle}">', this.grid.enableHdMenu ? '<a class="x-grid3-hd-btn" href="#"></a>' : '',
                '{value}<img class="x-grid3-sort-icon" src="', Ext.BLANK_IMAGE_URL, '" />',
                "</div></td>"
                );
    }

    if(!ts.body){
        ts.body = new Ext.Template('{rows}');
    }

    if(!ts.row){
        ts.row = new Ext.Template(
                '<div class="x-grid3-row {alt}" style="{tstyle}"><table class="x-grid3-row-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                '<tbody><tr>{cells}</tr>',
                (this.enableRowBody ? '<tr class="x-grid3-row-body-tr" style="{bodyStyle}"><td colspan="{cols}" class="x-grid3-body-cell" tabIndex="0" hidefocus="on"><div class="x-grid3-row-body">{body}</div></td></tr>' : ''),
                '</tbody></table></div>'
                );
    }

    if(!ts.cell){
        ts.cell = new Ext.Template(
                '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
                '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>',
                "</td>"
                );
    }

    for(var k in ts){
        var t = ts[k];
        if(t && typeof t.compile == 'function' && !t.compiled){
            t.disableFormats = true;
            t.compile();
        }
    }

    this.templates = ts;
    this.colRe = new RegExp("x-grid3-td-([^\\s]+)", "");
},

// private
fly : function(el){
    if(!this._flyweight){
        this._flyweight = new Ext.Element.Flyweight(document.body);
    }
    this._flyweight.dom = el;
    return this._flyweight;
}
});
Ext.override(Ext.grid.GridView,{
	 initTemplates : function(){
    var ts = this.templates || {};
    if(!ts.master){
        ts.master = new Ext.Template(
                '<div class="x-grid3" hidefocus="true">',
                    '<div class="x-grid3-viewport">',
                        '<div class="x-grid3-header"><div class="x-grid3-header-inner" align="left"><div class="x-grid3-header-offset">{header}</div></div><div class="x-clear"></div></div>',
                        '<div class="x-grid3-scroller"><div class="x-grid3-body" align="left">{body}</div><a href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',
                    "</div>",
                    '<div class="x-grid3-resize-marker">&#160;</div>',
                    '<div class="x-grid3-resize-proxy">&#160;</div>',
                "</div>"
                );
    }

    if(!ts.header){
        ts.header = new Ext.Template(
                '<table border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                '<thead><tr class="x-grid3-hd-row">{cells}</tr></thead>',
                "</table>"
                );
    }

    if(!ts.hcell){
        ts.hcell = new Ext.Template(
                '<td class="x-grid3-hd x-grid3-cell x-grid3-td-{id}" style="{style}"><div {tooltip} {attr} class="x-grid3-hd-inner x-grid3-hd-{id}" unselectable="on" style="{istyle}">', this.grid.enableHdMenu ? '<a class="x-grid3-hd-btn" href="#"></a>' : '',
                '{value}<img class="x-grid3-sort-icon" src="', Ext.BLANK_IMAGE_URL, '" />',
                "</div></td>"
                );
    }

    if(!ts.body){
        ts.body = new Ext.Template('{rows}');
    }

    if(!ts.row){
        ts.row = new Ext.Template(
                '<div class="x-grid3-row {alt}" style="{tstyle}"><table class="x-grid3-row-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                '<tbody><tr>{cells}</tr>',
                (this.enableRowBody ? '<tr class="x-grid3-row-body-tr" style="{bodyStyle}"><td colspan="{cols}" class="x-grid3-body-cell" tabIndex="0" hidefocus="on"><div class="x-grid3-row-body">{body}</div></td></tr>' : ''),
                '</tbody></table></div>'
                );
    }

    if(!ts.cell){
        ts.cell = new Ext.Template(
                '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
                '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>',
                "</td>"
                );
    }

    for(var k in ts){
        var t = ts[k];
        if(t && typeof t.compile == 'function' && !t.compiled){
            t.disableFormats = true;
            t.compile();
        }
    }

    this.templates = ts;
    this.colRe = new RegExp("x-grid3-td-([^\\s]+)", "");
},

// private
fly : function(el){
    if(!this._flyweight){
        this._flyweight = new Ext.Element.Flyweight(document.body);
    }
    this._flyweight.dom = el;
    return this._flyweight;
}
});
Thunder.ExtCrossGrid = Ext.extend(Ext.grid.GridPanel,{
	dataCells	: [],
	datas		: [],
	loadMask	: {
		msg	: "装载中......"
	},
	resizeChart:function(ratio){
		this.getView().forceFit = true;
		this.width = (this.xmlDoc.getAttribute("width")||"300") * ratio;
		this.height =(this.xmlDoc.getAttribute("height")||"200") * ratio;
		this.setSize(this.width,this.height);
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
		var gridHeight	= this.xmlDoc.getAttribute("height");
//		var colWidth	= this.xmlDoc.getAttribute("colWidth");
//		var rowWidth	= this.xmlDoc.getAttribute("colWidth");
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
			//colWidth	: parseInt(colWidth),
			height	: parseInt(top.app.params.screentype[1] / 800 * gridHeight),
			title	: gridTitle,
			border 	: border,
			frame	: frame,
			event   : event,
			enableColumnResize : enableColumnResize,
			width   : parseInt(top.app.params.screentype[0] / 1280 * width)
		}
	},
	/**
	 * 生成gird的数据集
	 * @param {} url
	 * @return {}
	 */
	genDataArray : function(gridCfg){
		var dataXmlDoc 	= loadXMLFromString(this.xmlData);
		var rows 		= [],cells = [],data = [];
		var datas 		= Ext.query("Row", dataXmlDoc);
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
		cells.unshift(crossColName||"");
		for(var j = 1 ; j < cells.length ; j ++){
			columns.push({header: cells[j] ,  dataIndex: (cells[j] + "_" + j),sortable: true,align : 'right',type:type,renderer	: renderer!= null ? eval(renderer) : function(v){return v;}});
			fields.push({name	: (cells[j] + "_" + j)});
		}
		columns.unshift({header: cells[0] , dataIndex: (cells[0] + "_" + 0), sortable: false,align : 'left',resizable :true,type:"string"})
		fields.unshift({name	: (cells[0] + "_" + 0)});
		this.dataCells = cells;
		return {
			columns	: columns,
			fields	: fields
		}
	},

	getGridData:function(columnNames){
			  var gridData=new Object();
			  var cm = this.getColumnModel(); 
              var store = this.getStore(); 
              var recordCount = store.getCount(); 
              var view = this.getView(); 
             
              var getColunmByDataIndex = function(colname){
		  		for(var ni=0;ni < cm.config.length;ni++){
		  			if(colname === cm.config[ni].dataIndex){
		  				if(cm.config[ni].renderer){
		  					return cm.config[ni].renderer;
		  				}else{
		  					return null;
		  				}
		  			}
		  		}
		  		return null;
		  	 };
              
              for(j=0;j<store.data.items.length;j++){
              	 var record = store.data.items[j];
              	 var lineData=new Object();
                  for(i=0;i<columnNames.length;i++){
              	      var columnName=columnNames[i];
              	      var renderer = getColunmByDataIndex(columnName);
			  	      if(renderer){
			  	      	lineData[columnName]=renderer(record.data[columnName]);
			  	      }else{
			  	      	lineData[columnName]=record.data[columnName];
			  	      }
                  }
                  gridData[j]=lineData;
              }
              
              return gridData;
			}, 
	initComponent : function(){
		var thisObj = this;
		var obj = this.initGridConfig();
		this.id = this.el  + Ext.id() + new Date().valueOf();
		var param = this.initGridParam();
		var dataObj = this.genDataArray(obj);
		var colWidth = parseInt(obj.colWidth || 200 );//?obj.colWidth:(param.width?((param.width/1-10)/(dataObj.cells.length+1)):70);
		var rowWidth = parseInt(obj.rowWidth?obj.rowWidth:colWidth);
		var fields = this.genColumnAndField(dataObj.cells,colWidth,rowWidth,dataObj.crossColName,obj.dataType,obj.renderer);
		this.store = new Ext.data.SimpleStore({
			fields	: fields.fields
		});
		this.enableColumnResize = param.enableColumnResize;
		this.store.loadData(dataObj.data);
		this.width = parseInt(param.width ? param.width: obj.colWidth?(obj.colWidth/1 * dataObj.cells.length/1 + 10):400);
		this.height = parseInt(param.height);
		this.title = param.title;
		this.border = param.border;
		this.event = param.event;
		this.autoScroll = true;
		this.frame	= param.frame;
		this.viewConfig = {
			forceFit:false	
		}
		this.enableColumnHide = false;
		this.enableColumnMove = false;
		this.enableHdMenu = false;
		this.columns = fields.columns;
		this.on(this.event);
		Thunder.ExtCrossGrid.superclass.initComponent.call(this)
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
