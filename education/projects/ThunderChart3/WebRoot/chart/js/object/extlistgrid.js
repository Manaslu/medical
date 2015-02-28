Ext.namespace("Thunder");
if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText  : "正序",
      sortDescText : "逆序",
      lockText     : "锁列",
      unlockText   : "解锁列",
      columnsText  : "列"
   });
}
/*
 * Thunder.ExtListGrid = function(el,xmldoc){ this.el = el; var thisObj =
 * xmldoc; this.columns = [] , this.fields = [] ; var cols =
 * thisObj.getElementsByTagName("ts:Column"); for(var i = 0 ; i < cols.length ;
 * i ++){ var col = cols[i]; this.columns.push({header:
 * col.getAttribute("name"), width:70, dataIndex: col.getAttribute("code"),
 * sortable: true}); this.fields.push({name : col.getAttribute("code"),mapping :
 * col.getAttribute("code")}); }
 * 
 * var node = thisObj; this.gridWidth = parseInt(node.getAttribute("width"));
 * this.gridHeight = parseInt(node.getAttribute("height")); this.gridTitle =
 * node.getAttribute("title"); } Thunder.ExtListGrid.prototype ={
 * genDataStore:function(){ this.store = new Ext.data.Store({ reader : new
 * Ext.ux.XmlReaderBuilder({ totalRecords: "results", record : "Row", id:
 * Ext.id() },this.fields) }); },
 * 
 * setXMLData:function(data){ this.store.loadData(data); },
 * 
 * render: function(){ this.genDataStore(); var listGrid = new
 * Ext.grid.GridPanel({ viewConfig: { forceFit: true }, store : this.store,
 * border : true, title : this.gridTitle, columns : this.columns, frame : true,
 * width : this.gridWidth/1, height : this.gridHeight/1 });
 * listGrid.render(this.el || document.body) } }
 */
Thunder.ExtListGrid= Ext.extend(Ext.grid.GridPanel, {
	/**
	 * 生成数据访问源


	 * 
	 * @param {}
	 *            fields
	 * @return {}
	 */
	resizeChart:function(ratio){
		this.getView().forceFit = true;
		this.width = (this.xmlDoc.getAttribute("width")||"300") * ratio;
		this.height =(this.xmlDoc.getAttribute("height")||"200") * ratio;
		this.setSize(this.width,this.height);
	},
	genDataStore : function(fields) {
		var store = new Ext.data.Store({
			reader : new Ext.ux.XmlReaderBuilder({
				totalRecords : "results",
				record : "Row",
				id : Ext.id()
			}, fields)
		});
		return store;
	},
	readKpiStyle	: function(v,m,r){
		var lvl = r.data['indLvl'];
	 	var temp = "";
	 	for(var i = 0 ; i < lvl ; i ++){
	 		temp += "&nbsp;&nbsp;";
	 	}
	 	var val = ""
	 	if(v == ""){
	 		val = temp + r.data['indName']
	 	} else {
	 		val = temp + v
	 	}
	 	return val;
	},
	/**
	 * 
	 * @return {} grid的行配置和列数据显示
	 */
	initColumn : function() {
		var xmlDoc = this.xmlDoc;
		var cols = xmlDoc.getElementsByTagName("ts:Column");
		var columnsOpt = [], fields = [];
		
		for (var i = 0; i < cols.length; i++) {
			var col = cols[i];
	                var name = col.getAttribute("name");
	                if(new RegExp("{(\\w)+}").test(name)){
	                	name = eval("this.dims."+name.substr(1,name.length-2));
	                }
			var issortable=col.getAttribute("sortable");
//			columns.push({
//				header : col.getAttribute("name"),
//				width : parseInt(col.getAttribute("width") || 80),
//				dataIndex : col.getAttribute("code"),
//				sortable : true
//			});
			var column={
				header :'' + name + '',
				width : parseInt(col.getAttribute("width") || 50),
				dataIndex : col.getAttribute("code"),
				type : col.getAttribute("type")||"number", 
				align	:col.getAttribute("align")!= null?col.getAttribute("align"):"center",
				hidden	: col.getAttribute("hidden") == "1" ? true : false ,
				listeners :{
					
				},
				renderer	: col.getAttribute("renderer")!= null ? eval(col.getAttribute("renderer")) : function(v){return v;}
			}
			if(issortable&&issortable=="1"){
			   this.enableHdMenu = true;
			   column.sortable=true
			}else{
			   column.sortable=false;
			}
			columnsOpt.push(column);
			
			//wangwei@teamsun.com.cn 2010-10-21 增加对排序规则支持
			var valuetype = col.getAttribute('type');
			var sortType = col.getAttribute('sortType');
			var convert = col.getAttribute('convert');
			var field = {};
			field.name = col.getAttribute("code");
			field.mapping = col.getAttribute("code");
			if(valuetype){
				field.type = valuetype;
			}
			if(sortType){
				field.sortType = sortType;
			}
			if(convert){
				field.convert = eval(convert);
			}
			fields.push(field);
		}
		var columns = new Ext.grid.ColumnModel(columnsOpt);
		return {
			columns : columnsOpt,
			fields : fields
		}
	},
	/**
	 * 从配置文件中读取grid的基本配置


	 * 
	 * @return {}
	 */
	initGridParam : function() {
		var node = this.xmlDoc;
		var gridWidth = parseInt(node.getAttribute("width"));
		var gridHeight = parseInt(node.getAttribute("height"));
		var gridTitle = node.getAttribute("title");
		var hideHeaders = (node.getAttribute("hideHeaders")=="1")?true:false;
		var enableColumnResize = node.getAttribute("enableColumnResize") ? (node.getAttribute("enableColumnResize")=="1")?true:false : true;
		var border 		= this.xmlDoc.getAttribute("showBorder");
		border 			= (border && border == "1") ? true : false;
		var frame 		= this.xmlDoc.getAttribute("useRoundEdge");
		frame			= (frame && frame == "1") ? true : false;
		return {
			title : gridTitle,
			border	: border,
			frame	: frame,
			height : gridHeight,
			width : gridWidth,
			hideHeaders:hideHeaders,
			enableColumnResize :enableColumnResize
		}
	},
	/**
	 * 详细配置grid的属性


	 * 
	 * @return {}
	 */
	genGridParam : function() {
		var gridParam = this.initGridParam();
		var columns = this.initColumn();
		gridParam.store = this.genDataStore(columns.fields);
		gridParam.viewConfig = {
			forceFit : true
		};
		gridParam.columns = columns.columns;
		gridParam.frame = this.frame;
		gridParam.border = this.border;
		return gridParam;
	},
	getExportListData:function (){
		 var thisGrid=this;
		 var listData=new Object();
		 var cms = thisGrid.getColumnModel(); // 获得网格的列模型
		 var columnNames = []; 
	     var columnTitle=new Object();
	      for (var i = 0; i < cms.getColumnCount(); i++) {// 处理当前显示的列
			         	//cms.config[i].type 配置文件中的type，也就是类型
				       if (!cms.isHidden(i)) {
                             var titleTemp=new Object();
                             var tmpheader="";
                             if(cms.getColumnHeader(i).indexOf("div") > -1){
                            	 tmpheader=cms.getColumnHeader(i).split(">")[1].split("<")[0];
                             }else{
                            	 tmpheader=cms.getColumnHeader(i);
                             }
                             titleTemp[cms.getDataIndex(i)]=tmpheader;
                             titleTemp["type"]=cms.config[i].type;
					         columnNames.push(cms.getDataIndex(i));
					         columnTitle[i]=titleTemp;
				       }
				       
		}
		listData["exportData"]=this.getGridData(columnNames);
		listData["columnTitle"]=columnTitle;
		return listData;
	},
	// 创建一个导出Excel的按钮

			createButton:function(){
				var thisGrid=this;
	            var btnExport2Excel = new Ext.Button({
		           text : "导出Excel",// 标题
		            handler : function() { // 单击事件
     
			        var cms = thisGrid.getColumnModel(); // 获得网格的列模型
			        var columnNames = []; 
			        var columnTitle=new Object();
			   
			         for (var i = 0; i < cms.getColumnCount(); i++) {// 处理当前显示的列
			         	//cms.config[i].type 配置文件中的type，也就是类型
				       if (!cms.isHidden(i)) {
                             var titleTemp=new Object();
                             titleTemp[cms.getDataIndex(i)]=cms.getColumnHeader(i);
                           
                             titleTemp["type"]=cms.config[i].type;
					         columnNames.push(cms.getDataIndex(i));
					         columnTitle[i]=titleTemp;
				       }
				       
			        }
			        if(Ext.get("exportForm") != null){
						Ext.get("exportForm").remove();
					}
					var formEle = ["<form name='exportForm' method='POST' id='exportForm' action='" + app.path + "/exportAction'  target='_blank'>"];
					formEle.push("<input type='hidden' name='exportData'  value='" + thisGrid.getGridData(columnNames)  + "'>");
					formEle.push("<input type='hidden' name='columnTitle'  value='" + Ext.encode(columnTitle)  + "'>");
					formEle.push("</form>");
					Ext.DomHelper.append(document.body,formEle.join(""));
					var form = document.getElementById("exportForm");
					form.submit();
		           }
	              });
	              return btnExport2Excel;
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
	/***
	 * 生成多层表头数据
	 */
	initPlugin : function(){
	    var xmlDoc = this.xmlDoc;
	    var cols=xmlDoc.getElementsByTagName("ts:ColPlugin")
	    var colplugins=[];
	    for(var i=0;i<cols.length;i++){
	        var col=cols[i];
	        var tmpcol={};
	        if(col.getAttribute("name")){
	           tmpcol.header=col.getAttribute("name");
	           tmpcol.align="center";
	        }
	        if(col.getAttribute("colspan")){
	            tmpcol.colspan=col.getAttribute("colspan")/1;
	        }
	        colplugins.push(tmpcol);
	    }
	    return colplugins;
	},
	initComponent : function() {
		var param = this.initGridParam();
		var colObj = this.initColumn();
		var plugins=this.initPlugin();
		this.title = param.title;
		this.width = parseInt(window.screen.width / 1280 * param.width);
		this.height = parseInt(window.screen.height / 800 * param.height);
		this.store = this.genDataStore(colObj.fields);
		this.columns = colObj.columns;
		this.frame = param.frame;
		this.hideHeaders = param.hideHeaders || false;
		this.enableColumnHide = true;
		this.enableColumnMove = false;
		this.enableHdMenu = false;
		this.enableColumnResize = param.enableColumnResize;
		this.border = param.border;
		this.autoScroll = false;
		//this.tbar= this.createButton();// 顶部工具栏,把导出按钮添加进来
		this.viewConfig = {
			forceFit : true
		}
		this.stripeRows = true;
		this.id = this.el  + Ext.id() + new Date().valueOf();
		this.on("render",function(){
			FC_Rendered(this.id);
		});
		this.chartLoaded = false;
		if(plugins.length>0){
	       this.plugins=[new Ext.ux.plugins.GroupHeaderGrid({
	       	                    rows : [plugins],
								hierarchicalColMenu : true
							})];
	    }
	    
		Thunder.ExtListGrid.superclass.initComponent.call(this);
		this.on('rowclick',function(){
		var xmlDoc = this.xmlDoc;
		var cols = xmlDoc.getElementsByTagName("ts:Column");
		for (var i = 0; i < cols.length; i++) {
			var col = cols[i];
			if(col.getAttribute("selectvalue")&&col.getAttribute("selectvalue")=="1"){
				var val=this.getSelectionModel().getSelected().get(col.getAttribute("code"));
				top.window.PageBus.publish("cond22","para={datatime:'"+val+"'}");
			}
		}
           		   
		})
		this.on('headerclick',function(g,index){
			var xmlDoc=this.xmlDoc;
			var colheader=xmlDoc.getElementsByTagName("ts:Columns");
			for(var i=0;i<colheader.length;i++){
			   if(colheader[i].getAttribute("selectheader")&&colheader[i].getAttribute("selectheader")=="1"){
			       var gridID=colheader[i].getAttribute("gridid");
			       top.window.PageBus.publish('gridmessage_'+gridID,{header:g.getColumnModel().getDataIndex(index)});
			   }
			}
			
		})
	},
	setXMLData : function(data) {
		this.store.loadData(data);
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

