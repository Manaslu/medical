Ext.namespace("Thunder");
Thunder.ReportGrid = Ext.extend(Ext.grid.GridPanel,{
	loadMask	: {
		msg	: "装载中......"
	},
	reData		: function(){
		
	},
	viewConfig 	: {
		forceFit:false	
	},
	readKpiStyle	: function(v, cellmeta, r, rowIndex, columnIndex, store){
		var d = this.name;
		var lvl = r.data['indLvl' + "_" +  d.split("_")[1]];
	 	var temp = "";
	 	for(var i = 0 ; i < lvl ; i ++){
	 		temp += "&nbsp;&nbsp;";
	 	}
	 	var val = ""
	 	if(v == ""){
	 		val = temp + r.data['indName' + "_" + d.split("_")[1]]
	 	} else {
	 		val = temp + v
	 	}
	 	return val;
	},
	/**
	 * 从配置文件中读取grid的基本配置
	 * 
	 * @return {}
	 */
	initGridParam : function() {
		var node = this.xmlDoc;
		gridWidth = parseInt(node.getAttribute("width"));
		gridHeight = parseInt(node.getAttribute("height"));
		gridTitle = node.getAttribute("title");
		hideHeaders = (node.getAttribute("hideHeaders")=="1")?true:false;
		var border 		= this.xmlDoc.getAttribute("showBorder");
		border 			= (border && border == "1") ? true : false;
		var frame 		= this.xmlDoc.getAttribute("useRoundEdge");
		frame			= (frame && frame == "1") ? true : false;
		return {
			title : gridTitle,
			height : gridHeight,
			width : gridWidth,
			border	: border,
			frame	: frame,
			hideHeaders:hideHeaders
		}
	},
	/**
	 * 获取单列的标题
	 * @return {}
	 */
	getColumeHead	: function(){
		var xmlHeads = this.xmlDoc.getElementsByTagName("ts:Column");
		var array = [];
		for(var i = 0 ; i < xmlHeads.length ; i ++){
			var head = xmlHeads[i];
			var obj = {name:head.getAttribute("name"),align:head.getAttribute("align")!= null?head.getAttribute("align"):"center",code:head.getAttribute("code"),width:head.getAttribute("width")/1,renderer:head.getAttribute("renderer"),hidden : (head.getAttribute("hidden")!= null && head.getAttribute("hidden") == "1") ? true : false,type : head.getAttribute("type")};
			array.push(obj);
		}
		return array;
	},
	/**
	 * 获取所有的数据
	 */
	getMetaData		: function(){
		var array = this.getColumeHead();
		var doc = Thunder.XML.loadXMLStr(this.xmlData);
		var datas = doc.getElementsByTagName("Row");
		var rtnValues = [];
		for(var i = 0 ; i < datas.length ; i ++){
			var row = datas[i];
			var obj = {};
			for(var j = 0 ; j < array.length ; j ++){
				obj[array[j].code] = Ext.DomQuery.selectValue(array[j].code,row,"");
			}
			rtnValues.push(obj);
		}
		return rtnValues;
	},
	getColumnAndField	: function(){
		
		var datas = this.getMetaData();
		var count = this.xmlDoc.getAttribute("columnCount")!=null? (parseInt(datas.length/(this.xmlDoc.getAttribute("columnCount")/1))+1):this.xmlDoc.getAttribute("rowCount") / 1;
		var heads = this.getColumeHead();
		var columnCount = parseInt(datas.length / count + (datas.length % count == 0 ? 0 : 1));
		var fields = [],columns = [];
		var dt = [];
		var jj = 0;
		for(var i = 0 ; i < columnCount ; i ++){
			for(var j = 0 ; j < heads.length ; j ++){ // 循环取出fields
				fields.push({name : heads[j].code + "_" + i}); // 避免field有重复
				columns.push({width:heads[j].width,align:heads[j].align!= null?heads[j].align:"center",header: heads[j].name,hidden: heads[j].hidden == "1" ? true : false, dataIndex: heads[j].code + "_" + i, sortable: false,type:heads[j].type,renderer	: (heads[j].renderer && heads[j].renderer.length > 0) ? eval(heads[j].renderer) : function(v){return v;}});
			}
		}
		for(var i = 0 ; i < count ; i ++){
			var dtArray = [];
			for(var j = 0 ; j < heads.length ; j ++){
				if(jj >= datas.length){
					dtArray.push("&nbsp;");
				} else {
					dtArray.push(datas[jj][heads[j].code]);
				}
			}
			jj ++;
			dt.push(dtArray)
		}
		for(var i = 1 ; i < columnCount ; i ++){
			for(var j = 0 ; j < count ; j ++){
				var dtAr = dt[j];
				for(var k = 0 ; k < heads.length ; k ++){
					if(jj >= datas.length){
						break;
					} else {
						dtAr.push(datas[jj][heads[k].code]);
					}
				}
				jj ++;
			}
		}
//		for(var i = 0 ; i < columnCount ; i ++){
//			var dtArray = [];
//			for(var j = 0 ; j < count ; j ++){
//				for(var j = 0 ; j < heads.length ; j ++){
//					if(jj >= datas.length){
//						dtArray.push(" ");
//					} else {
//						dtArray.push(datas[jj][heads[j].code]);
//					}
//				}
//				jj ++;
//			}
//			dt.push(dtArray);
//		}
		return {
			datas 	: dt,
			fields	: fields,
			columns	: columns
		}
	},
	genDataStore : function(fields) {
		var store = new Ext.data.SimpleStore({
			fields	: fields
		});
		return store;
	},
	initComponent : function() {
		var param = this.initGridParam();
		this.title = param.title;
		this.width = parseInt(window.screen.width / 1280 * param.width);
		this.height = parseInt(window.screen.height / 800 * param.height);
		var datas = this.getColumnAndField();
		this.store = this.genDataStore(datas.fields);
		this.columns = datas.columns;
		//this.frame = true;
		this.frame = param.frame;
		this.hideHeaders = param.hideHeaders || false;
		//this.border = true;
		this.border = param.border;
		this.autoScroll = false;
		this.store.loadData(datas.datas);
		this.enableColumnHide = false;
		this.enableColumnMove = false;
		this.enableHdMenu = false;
		this.stripeRows = true;
		this.viewConfig = {
			forceFit : true
		}
		Thunder.ReportGrid.superclass.initComponent.call(this);
	},
	setXMLData: function(xmlData){
		var obj = this.getColumeHead();
		this.xmlData = xmlData;
		var dataObj = this.getColumnAndField();
		try{
			this.store.loadData(dataObj.datas);
		}catch(e){}
	},
	getExportListData : function(){
		 var thisGrid=this;
		 var listData=new Object();
		 var cms = thisGrid.getColumnModel(); // 获得网格的列模型
		 var columnNames = []; 
	     var columnTitle=new Object();
	      for (var i = 0; i < cms.getColumnCount(); i++) {// 处理当前显示的列
			         	//cms.config[i].type 配置文件中的type，也就是类型
				       if (!cms.isHidden(i)) {
                             var titleTemp=new Object();
                             var tmpheader=cms.getColumnHeader(i);
                            // var test = "wangwei";
//                             if(tmpheader.indexOf && tmpheader.indexOf("div")){
//                            	 tmpheader=cms.getColumnHeader(i).split(">")[1].split("<")[0];
//                             }
                             titleTemp[cms.getDataIndex(i)]=tmpheader;
                             titleTemp["type"]=cms.config[i].type;
					         columnNames.push(cms.getDataIndex(i));
					         columnTitle[i]=titleTemp;
				       }
				       
		}
		listData["exportData"]=this.getGridData(columnNames);
		listData["columnTitle"]=columnTitle;
		var i = 1;
		return listData;
	},
	
	getGridData:function(columnNames){
		  var gridData=new Object();
		  var cm = this.getColumnModel();
		  
		  var store = this.getStore(); 
		  var recordCount = store.getCount(); 
		 // var view = this.getView(); 
		 
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
		  	 var lineData = {};
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
	}
});
