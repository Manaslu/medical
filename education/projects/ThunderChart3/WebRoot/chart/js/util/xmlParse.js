function getTopWindow(){
	var win = window;
	for(;;){
		if(win.opener && win.opener !== win ){
			win = win.opener.top;
		}
		else
			break;
	}
	return win;
} 
function FC_Rendered(domId){
		 var top = getTopWindow();
		 var chart = Ext.StoreMgr.lookup(domId);
	         chart.chartLoaded = true;
	         if(chart.renderCount&&chart.renderCount>1)
	         	{
	        	 return ;
	        	 }
		    Thunder.Charts--;
     		if(chart.getDataModelPara()){
     			window.PageBus.publish(chart.getDataModel()+".init","para=" + chart.getDataModelPara());
     		}else{
     			window.PageBus.publish(chart.getDataModel()+".init","para={}" );
     		}
	         return;
	      } 
Thunder.Chart = {        
			 "Column3D":"single",
			 "Column2D":"single",
			 "Line":"single",
			 "Area2D":"single",
			 "Bar2D":"single",
			 "Pie2D":"single",
			 "Pie3D":"single",
			 "Doughnut2D":"single",
			 "Doughnut3D":"single",
			 "MSColumn2D":"multi",
			 "MSColumn3D":"multi",
			 "MSLine":"multi",
			 "MSBar2D":"multi",
			 "MSBar3D":"multi",
			 "MSArea":"multi",
			 "MSCombiDY2D":"multi",
			 "StackedColumn3D":"multi",
			 "StackedColumn2D":"multi",
			 "StackedBar2D":"multi",
			 "StackedBar3D":"multi",
			 "StackedArea2D":"multi",
			 "ScrollColumn2D":"multi",
			 "ScrollLine2D":"multi",
			 "ScrollArea2D":"multi",
			 "ScrollStackedColumn2D":"multi",
			 "ScrollCombi2D":"multi",
			 "ScrollCombiDY2D":"multi",
			 "MSColumn3DLineDY":"multi",
			 "Radar":"multi",
			 "MSStackedColumn2DLineDY":"combine",
			 "AngularGauge":"gauglar",
			 "listgrid":"listgrid",
			 "ext-listgrid":"ext-listgrid",
			 "ext-crossgrid":"ext-crossgrid",
			 "text":"text",
			 "Arrow":"arrow",
			 "mapChina":"chinamap",
			 "Legend":"single",
			 "AngularGaugeRPM":"gauglarRPM",
			 "HLinearGauge":"hLinearGauge"
//			 "Bulb":"Bulb",
//			 "HLED":"HLED"
			 }
Thunder.extgridArray = [];
Thunder.LoadConfigXML = function(file,isOnReady){
	Ext.Ajax.request({
	            url :app.path+ '/XmlReader?file=modules/'+file, 
	            method: 'POST',
	            argument:isOnReady,
	            success: function ( result, request ) {
	            		var isOnReady = result["argument"];
	            		Thunder.ParseConfigXML(result.responseText,isOnReady);
	            },
	            failure: function ( result, request) { 
	                Ext.MessageBox.alert("系统消息", result.responseText); 
	            } 
			});
		}


Thunder.ParseConfigXML = function(xmlStr,params,isOnReady,args){
	var indobj =  Ext.decode(params.indobj);
	var dims = Ext.decode(params.dims);
	
	
	Thunder.datamodels = {};
	Thunder.ChartsAdd = Thunder.Charts = 0;
	Thunder.chartObject = {};
	Thunder.isOnReady = null;
	//var indobj = Ext.decode(indobj);
	Thunder.isOnReady = isOnReady;
	var top = getTopWindow();
	Thunder.top = top;
	if(typeof(subs) =='undefined'){
		subs= {};
	}
 
	var paraObject = {};
	paraObject.name = indobj.NAME;
	paraObject.unit = indobj.UNIT || " ";
	paraObject.datatype  = indobj.DATATYPE||'2';
	Thunder.unit=params.conttype;
	Thunder.acc = (Thunder.unit=="10000"?"1":"2");
	Thunder.format = "'0.";
	if(args && args.spliter == "true"){
		Thunder.showValues = params.showValues;
	}
 
	Thunder.showValues ='1';
	for(var a=0;a<Thunder.acc;a++){
		Thunder.format+="0";
	}
	Thunder.format+="'";
	var pageDoc = Thunder.XML.loadXMLStr(xmlStr);
	var cmps = pageDoc.getElementsByTagName("ts:cmpnt");
	var marquees = pageDoc.getElementsByTagName("ts:marquee");
	var outerDiv = document.createElement("div");
	document.getElementById("chartdiv").appendChild(outerDiv);
	var layout = pageDoc.getElementsByTagName("ts:layout")[0];
  
	if(layout){
		var layoutName = layout.getAttribute("template");
		var hasLine = layout.getAttribute("hasLine");
		var layoutWidth = layout.getAttribute("width");
		var layoutHeight = layout.getAttribute("height");
		}
	switch (layoutName) {
		case "vert" :
			for (var i = 0; i < cmps.length; i++) {
				var innerDiv = document.createElement("div");
				innerDiv.id = layoutName +"_" +i;
				outerDiv.appendChild(innerDiv);
				innerDiv.style.padding="10 0 0 0";
				//sinnerDiv.style.textalign="center";
				//innerDiv.style.align="center";
				//sinnerDiv.style.margin="0 auto";
				//if(cmps[i].getAttribute("style"))
				//	innerDiv.style = cmps[i].getAttribute("style");
				if (hasLine == "true"&&i!==cmps.length-1) {
					var horiLine = document.createElement("div");
					horiLine.className = "horiLine";
					horiLine.style.width = layoutWidth;
					outerDiv.appendChild(horiLine);
				}

			}
			break;
		case "hori" :
			for (var i = 0; i < cmps.length; i++) {
				var innerDiv = document.createElement("div");
				innerDiv.id = layoutName +"_" + i;
				outerDiv.appendChild(innerDiv);
				
				innerDiv.className = "groupWrapper";
				 
				if (hasLine == "true"&& i!==cmps.length-1) {
					var vertLine = document.createElement("div");
					vertLine.className = "vertLine";
					vertLine.style.height = layoutHeight;
					outerDiv.appendChild(vertLine);
					
				}
			}
			break;
		default:
			var innerDiv = document.createElement("div");
			outerDiv.appendChild(innerDiv);
			innerDiv.innerHTML = eval("Free."+layoutName);
		
	}
	Thunder.Charts = Thunder.Chartcount= cmps.length;
	for (var i = 0; i < cmps.length; i++) {
		var type = cmps[i].getAttribute("type")||"Pie2D";
		var para = cmps[i].getAttribute("para"); //获得图形配置文件里的参数,这些参数主要用来当查询参数非常多的时候描述默认值
		var querypara = params.para; //从url处得到的参数,一般是动态的纬度参数,和配置文件里的参数合并
		var paraStr = {};
		if(para){
			para = Ext.decode(para);
			for(var p in para){
				if(para[p]&& para[p].indexOf("#")>=0)
					paraStr[p] = "'"+eval(para[p].split("#")[1])+"'" ;
				else{
					paraStr[p] = para[p];
				}
			}
		}
		if(querypara){ //将纬度参数合并
			querypara = Ext.decode(querypara);
			for(var p in querypara){
					paraStr[p] = querypara[p];
				 
			}
			
		}
		paraStr = Ext.encode(paraStr);
		var datamodel = cmps[i].getAttribute("datamodel")||null;
		var pos = cmps[i].getAttribute("pos")||(i-1);
		switch(Thunder.Chart[type]){
			case "multi":
				var chart = new Thunder.MultiChart(layoutName +"_"+ pos,type, cmps[i],{para:paraStr,showValues:Thunder.showValues,dims:dims,prefix:paraObject.name,acc:parseInt(paraObject.datatype)});
				break;
				
			case "gauglarRPM":
				var chart = new Thunder.GaugularRPM(layoutName+"_" + pos,type, cmps[i],{para:paraStr,showValues:Thunder.showValues,dims:dims,prefix:paraObject.name,acc:parseInt(paraObject.datatype)});
				break;				
					
			case "combine":
				var chart = new Thunder.CombineChart(layoutName +"_"+ pos,type, cmps[i],{para:paraStr,showValues:Thunder.showValues,dims:dims,prefix:paraObject.name,acc:parseInt(paraObject.datatype)});
				break;
			case "single":
				var chart = new Thunder.SingleChart(layoutName +"_"+ pos,type, cmps[i],{para:paraStr,showValues:Thunder.showValues,dims:dims,prefix:paraObject.name,acc:parseInt(paraObject.datatype)});
				break;
			case "gauglar":
				var chart = new Thunder.Gaugular(layoutName+"_" + pos,type, cmps[i],{para:paraStr,showValues:Thunder.showValues,dims:dims,prefix:paraObject.name,acc:parseInt(paraObject.datatype)});
				break;
			case "ext-listgrid":
			        var chart = new Thunder.ExtListGrid({el:layoutName+"_" + pos,xmlDoc:cmps[i],dims:dims});
			        break;
			case "hLinearGauge":
				var chart = new Thunder.HLinearGauge(layoutName+"_" + pos,type, cmps[i]);
				break;	
			case "text":
			        var chart = new Thunder.TextParser({el:layoutName+"_" + pos,xmlDoc:cmps[i]});
			        break;
			case "arrow":
			        var chart = new Thunder.Arrow({el:layoutName+"_" + pos,xmlDoc:cmps[i]});
			        break;
			case "chinamap":
			        var chart = new Thunder.mapChina(layoutName +"_"+ pos,type, cmps[i]);
			        break;
/*			case "Bulb":
			case "HLED":
			        var chart = new Thunder.Bulb(layoutName +"_"+ pos,type, cmps[i]);
			        break;*/
			case "ext-crossgrid":
		        break;
		}
		
		if(datamodel){
			 
		 
				var proxy = Ext.StoreMgr.lookup(datamodel);
			 
				var sub= window.PageBus.subscribe(proxy.DATA_LOADED_SUBJECT,null,Thunder.setXMLData,{id:i,chart:chart,type:Thunder.Chart[type],el:layoutName+"_" + pos,xmlDoc:cmps[i]});
				if(!Thunder.datamodels[datamodel]){
					Thunder.datamodels[datamodel] =  window.PageBus.subscribe(proxy.DATA_REQUEST_SUBJECT,null,proxy.request,datamodel);
				}
			 
			if(Thunder.Chart[type]!=="ext-crossgrid")
				chart.setDataModel(datamodel);
		}
		if(Thunder.Chart[type]!=="ext-crossgrid"){
			//alert(Thunder.Chart[type]);
			chart.storeId = chart.getId();
			Ext.StoreMgr.register(chart);
			if(para){
				chart.setDataModelPara(paraStr);
			}
			chart.render();
			 
		}
		else{
         	for(var t in Thunder.datamodels){
         		var proxy = Ext.StoreMgr.lookup(t);
         		if(para){
         			window.PageBus.publish(proxy.DATA_REQUEST_SUBJECT,"para=" + paraStr);
         		}
         		else{
         			window.PageBus.publish(proxy.DATA_REQUEST_SUBJECT,"para={}");
         		}
         	}
          
		 
		}
		
		if(marquees && ("text".indexOf(Thunder.Chart[type])>-1)){
			for(var m=0;m < marquees.length;m++){
				var marNode =marquees[m];
				var id = marNode.getAttribute("id");
				if(id==pos){
					var marquee1 = new Marquee(chart.getEl());	
					marquee1.Direction = parseInt(marNode.getAttribute("direction")||0);
					marquee1.Step = parseInt(marNode.getAttribute("step")||1);
					marquee1.Width = parseInt(marNode.getAttribute("width")||760);
					marquee1.Height = parseInt(marNode.getAttribute("height")||52);
					marquee1.Timer = parseInt(marNode.getAttribute("timer")||50);
					marquee1.DelayTime = parseInt(marNode.getAttribute("delayTime")||4000);
					marquee1.WaitTime = parseInt(marNode.getAttribute("waitTime")||500);
					marquee1.ScrollStep = parseInt(marNode.getAttribute("scrollStep")||52);
					chart.setMarquee(marquee1);	
				}
			}
		}
		
	}
}
Thunder.setXMLData = function(subject,data,chart){
		var top = getTopWindow();
		Thunder.ChartsAdd++;
		if(chart.type=="ext-crossgrid"){
			document.getElementById(chart.el).innerHTML="";
			var cross = new Thunder.LockGrid({el:chart.el,xmlDoc: chart.xmlDoc,xmlData:data });
//			var cross = new Thunder.ExtCrossGrid({el:chart.el,xmlDoc: chart.xmlDoc,xmlData:data });
			cross.storeId = cross.getId();
			Ext.StoreMgr.register(cross);
			cross.render();
			Thunder.chartObject[cross.getId()] = data;
			Thunder.extgridArray.push(cross);
		}
		else{
			chart.chart.setXMLData(data);
			//Thunder.chartObject[chart.chart.getId()] = data;
			//Thunder.extgridArray.push(chart.chart);
		}
//		 var dm = subject.split(".")[1];
//		 if(Thunder.Chartcount==Thunder.ChartsAdd){
//		 	for(var c in Thunder.datamodels){
//		 		 window.PageBus.unsubscribe(Thunder.datamodels[c]);
//		 		 delete Thunder.datamodels[c];
//		 	}
//		 }
}

//Thunder.showValue = function(obj,subscribe){
//	window.PageBus.publish(subscribe,obj);
//}
//window.onunload = function(){
//	var top = getTopWindow();
//	//alert(top==window);
//	for(var p in Thunder.datamodels){
//		  try{
//		  	window.PageBus.unsubscribe(Thunder.datamodels[p]);
//		  	delete Thunder.datamodels[p];
//		  }catch(e){
//		  	for(var p in subs){
//				alert(p);
//			}
//		  }
////		window.PageBus.unsubscribe(Thunder.datamodels[p]);
////		  	delete Thunder.datamodels[p];
//	}
//	
//}
//Thunder.changeData = function(unit,acc){
//	var top = getTopWindow();
//	for(var s in Thunder.chartObject){
//		var chart = Ext.StoreMgr.lookup(s);
//		chart.unit=unit;
//		Thunder.unit=unit;
//		Thunder.acc=acc;
//		chart.setXMLData(Thunder.chartObject[s]);
//	}
//	if(document.getElementById('theform')){
//		var urlstr=self.location.href;
//		var url = urlstr.split("conttype")[0];
//		if(url.indexOf("?") > -1){
//			urlstr = url + "conttype="+Thunder.unit;
//		}else{
//			urlstr = url + "?conttype="+Thunder.unit;
//		}
//		document.getElementById('theform').action=urlstr;
//	}
//}
//Thunder.zoomchart = function(ratio){
//	var top = getTopWindow();
//	for(var d in Thunder.chartObject){
//		var chart = Ext.StoreMgr.lookup(d);
//		if(chart && ((chart instanceof Thunder.ExtListGrid) == false) && ((chart instanceof Thunder.ExtCrossGrid)==false)){
//			chart.resizeChart(ratio);
//		}
//	}
//}

