Ext.namespace("Thunder");

 
  	
Thunder.Arrow = Ext.extend(Ext.Panel, {
	initComponent : function() {
		var gridParam = this.genGridParam();
		this.frame = false;
		this.randomId = Ext.id();
		this.border = false;
		this.width = gridParam.width ;
		this.height = gridParam.height ;
		this.title = gridParam.title;
		this.bgColor = gridParam.bgColor || 'D4E5FB';
		this.margin = gridParam.margin;
		this.borderColor = gridParam.borderColor || 'D4E5FB';
		this.template = gridParam.template;
		this.style = gridParam.style;
		//var str = Ext.DomQuery.selectNode("text", this.xmlDoc,"");
		this.id = this.el  + Ext.id();
		this.on("render",function(){
			FC_Rendered(this.id);
		});
		
		

		this.chartLoaded = false;
		var str = this.xmlDoc.getElementsByTagName("text")[0].xml;
		if(str==null){		
		   var string = (new XMLSerializer()).serializeToString(this.xmlDoc);
		   str=string.substring((string.indexOf("<text>")+6),string.indexOf("</text>"));
		}
		
		this.html = String.format(
				"<div id='text-show-body-{0}' style='{1}' >{2}</div>",
				this.randomId, this.style, str);
		//Thunder.TextParser.superclass.initComponent.call(this);
	},
	genGridParam : function() {
		var width = this.xmlDoc.getAttribute("width");
		var height = this.xmlDoc.getAttribute("height");
		var bgColor = this.xmlDoc.getAttribute("bgColor");
		var margin = this.xmlDoc.getAttribute("margin");
		var borderColor = this.xmlDoc.getAttribute("borderColor");
		
		var title = this.xmlDoc.getAttribute("title");
		var style = this.xmlDoc.getAttribute("style");
		var datamodel = this.xmlDoc.getAttribute("datamodel");
		var template = this.xmlDoc.getAttribute("template") || null;
		return {
			width : parseInt(width || "120"),
			height : parseInt(height || "120"),
			margin : margin || "0 0 0 0 ",
			bgColor : bgColor || "D4E5FB",
			borderColor : borderColor || "D4E5FB",
			
			title : title,
			datamodel : datamodel,
			style : style,
			template:template
		}
	},
	getId:function(){
		return this.id;
	},
	splitText : function(str) {
		return str.spleaceAll("\n", "<br />");
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
	setXMLData : function(data) {
		
		var displaystr=data.substring((data.indexOf("<text>")+6),data.indexOf("</text>"));
		var arrowdiv=Ext.DomQuery.selectNode(String.format("div[id=text-show-body-{0}]",this.randomId), document.body);
		arrowdiv.innerHTML = "";
		arrowdiv.style.margin=this.margin;
		roundDiv(arrowdiv,this.width,this.height,displaystr)
		
		
//		var marginArray= new Array(); 
//		marginArray[0]=0;
//		marginArray[1]=0;
//		marginArray[2]=0;
//		marginArray[3]=0;
//		if(this.margin!==null){
//			 if(this.margin.length>4){
//			 marginArray=this.margin.split(" ");
//			 }else {
//			 			marginArray[0]=this.margin;
//		                marginArray[1]=this.margin;
//						marginArray[2]=this.margin;
//						marginArray[3]=this.margin;
//			 }
//		}


//  	var t=document.getElementById("test");
//  	var rbroundbox=document.createElement("div");
//  	    rbroundbox.setAttribute("id","rbroundbox");
//  	    
//  	var rbtop=document.createElement("div");
//  	    rbtop.setAttribute("id","rbtop");
//  	    
//  	var a=document.createElement("div");
//  	    a.setAttribute("id","rbtopdiv");
//  	
//  	var rbcontent=document.createElement("div");
//  	    rbcontent.setAttribute("id","rbcontent");  	
//  	    
//  	var rbbot=document.createElement("div");
//  	    rbbot.setAttribute("id","rbbot");
//  	    
//  	var b=document.createElement("div");
//  	    b.setAttribute("id","rbbotdiv");
//  	
//  	rbtop.appendChild(a);
//  	rbbot.appendChild(b);		
//  	
//  	rbroundbox.appendChild(rbtop);
//  	rbroundbox.appendChild(rbcontent);
//  	rbroundbox.appendChild(rbbot);
//  	arrowdiv.appendChild(rbroundbox);
//  	
//  	rbroundbox.style.height=this.width+"px";
//  	rbroundbox.style.width=(this.height-7)+"px";		
	//rbcontent.innerHTML=displaystr;	
		
		
		
	//	arrowdiv.style.width= (this.width-marginArray[1]-marginArray[3]-2)+"px";
	//	arrowdiv.style.height=(this.height-marginArray[0]-marginArray[2]-2)+"px";
	//	arrowdiv.innerHTML=displaystr; 	
	//	arrowdiv.style.margin=this.margin+"px";
		

		
		
	//	arrowdiv.style.backgroundColor = "#"+this.bgColor;
	//	arrowdiv.style.border = "1px solid #"+this.borderColor;
		
	//	style="width:"+(this.width-marginArray[1]-marginArray[3]-2)+"px;"
	//	     +"height:"+(this.height-marginArray[0]-marginArray[2]-2)+"px;"
	//	     +"background-color:#"+this.bgColor
	//	     +";border: 1px solid #"+this.borderColor
	//	     +";margin:"+this.margin
		     
		   
        		
		
	//	arrowdiv.setAttribute("style",style);
//		var settings = {tl: { radius: 20 },tr: { radius: 20 },bl: { radius: 20 },br: { radius: 20 },antiAlias: true};
		
//		alert("ddddddd");
 //       curvyCorners(settings, arrowdiv);
	}
});
String.prototype.replaceAll = function(oldStr, newStr) {
	return this.replace(new RegExp(oldStr, "gm"), newStr);}
