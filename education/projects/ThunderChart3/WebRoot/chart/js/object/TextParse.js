Ext.namespace("Thunder");
Thunder.TextParser = Ext.extend(Ext.Panel, {
	initComponent : function() {
		var gridParam = this.genGridParam();
		this.frame = false;
		this.randomId = Ext.id();
		this.border = false;
 
 
		this.title = gridParam.title;
		this.bgColor = gridParam.bgColor || 'C0C0C0';
		this.template = gridParam.template;
		this.style = gridParam.style;
		//var str = Ext.DomQuery.selectNode("text", this.xmlDoc,"");
		this.id = this.el  + Ext.id()+new Date().valueOf();
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
		Thunder.TextParser.superclass.initComponent.call(this);
	},
	genGridParam : function() {
		var width = this.xmlDoc.getAttribute("width");
		var height = this.xmlDoc.getAttribute("height");
		var title = this.xmlDoc.getAttribute("title");
		var style = this.xmlDoc.getAttribute("style");
		var datamodel = this.xmlDoc.getAttribute("datamodel");
		var template = this.xmlDoc.getAttribute("template") || null;
		return {
			width : parseInt(width || "100"),
			height : parseInt(height || "100"),
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
	setMarquee:function(marquee){
		this.marquee = marquee;
	},
	getMarquee:function(){
		return this.marquee;
	},
	setDataModelPara:function(para){
		this.dataModelPara = para;
	},
	getDataModelPara:function(){
		return this.dataModelPara;
	},
	getEl:function(){
		return "text-show-body-" + this.randomId;
	},
	setXMLData : function(data) {
		document.getElementById(this.el.id).innerHTML="<div id='text-show-body-"+this.randomId +"' style='"+this.style+"' ></div>";
		var displaystr=data.substring((data.indexOf("<text>")+6),data.indexOf("</text>"));
		if(this.template){
			var t = new Ext.Template(eval(this.template));
			t.overwrite(Ext.DomQuery.selectNode(String.format("div[id=text-show-body-{0}]",
				this.randomId), document.body), {data: displaystr});
			Ext.DomQuery.selectNode(String.format("div[id=text-show-body-{0}]",
				this.randomId), document.body).setAttribute("style",this.style)
		}
		else{
			Ext.DomQuery.selectNode(String.format("div[id=text-show-body-{0}]",
				this.randomId), document.body).innerHTML = displaystr;
		}
		if(this.marquee){
			var marquee1 = new Marquee("text-show-body-" + this.randomId);	
			marquee1.Direction = this.marquee.Direction;
			marquee1.Step = this.marquee.Step;
			marquee1.Width = this.marquee.Width;
			marquee1.Height = this.marquee.Height;
			marquee1.Timer = this.marquee.Timer;
			marquee1.DelayTime = this.marquee.DelayTime;
			marquee1.WaitTime = this.marquee.WaitTime;
			marquee1.ScrollStep =this.marquee.ScrollStep;
			marquee1.Start();
			}
	}
});
String.prototype.replaceAll = function(oldStr, newStr) {
	return this.replace(new RegExp(oldStr, "gm"), newStr);
}
