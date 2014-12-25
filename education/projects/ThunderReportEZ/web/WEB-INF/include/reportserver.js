function isNumber(oNum){
  if(!oNum) return false;
  var strP=/^(-|)\d+(\.\d+)?$/;
  if(!strP.test(oNum)) return false;
  try{
  if(parseFloat(oNum)!=oNum) return false;
  }
  catch(ex)
  {
   return false;
  }
  return true;
}


function fnDrawErea(t_dim_period,total_amt,total_cnt)
{
    return t_dim_period+total_amt+total_cnt;
}

function fnDrawColor(value)
{
	if (!isNumber(value))
	{
		return "<span style=\"color:black\">"+value+"</span>";
	}
	
	if (value<-20 || value>20)
	{
		return "<span style=\"color:red\">"+value+"</span>";
	}else{
		return "<span style=\"color:black\">"+value+"</span>";
	}
}

function formateDateYYYYMM(date){
	var y = date.substring(0,4);
	var m = date.substring(4,6);
	return y+"年"+m+"月";
}

function formateDateYYYYMMDD(date){
	java.lang.System.out.println(date);
	var y = date.substring(0,4);
	java.lang.System.out.println(y);
	var m = date.substring(4,6);
	java.lang.System.out.println(m);
	var d = date.substring(6,8);
	java.lang.System.out.println(d);
	return y+"年"+m+"月"+d+"日";
}
function formateDateYYYY(date){
	var y = date.substring(0,4);
	return y+"年";

}
function DrawColorFormatNumber(v, text){
	try{
	var bd = new java.math.BigDecimal(text);
	bd = bd.setScale(v, java.math.BigDecimal.ROUND_DOWN);
	var fn = java.text.NumberFormat.getInstance();
	fn.setMinimumFractionDigits(2);
	if (bd.doubleValue()>20 || bd.doubleValue()<-20)
		return "<span style=\"color:red\">"+fn.format(bd.doubleValue())+"</span>";
	else
		return "<span style=\"color:black\">"+fn.format(bd.doubleValue())+"</span>";
	}catch(e){return text;}
}
function doHref(value1,value2){
	if (value2!='总计' && value2!='合计' && value2!='总量'){
		var href = "RepresentAction?";
		for (var p in value1){
			href += p+"="+value1[p]+"&";
		}
		href += "index=5&back=1";
		href = encodeURI(href);
		return "<span><a href='"+href+"'>"+value2+"</a></span>";
	}
	else
	{
		return "<span>"+value2+"</span>";
	}
}

function doCacheHref(value1,value2){
	if (value2!='总计' && value2!='合计' && value2!='总量'){
		var href = "/ThunderReportEZ/RepresentAction?";
		for (var p in value1){
			href += p+"="+value1[p]+"&";
		}
		href += "index=8&back=1";
		href = encodeURI(href);
		return "<span><a href='"+href+"'>"+value2+"</a></span>";
	}
	else
	{
		return "<span>"+value2+"</span>";
	}
}
function formateNumber(patten, text){
	java.lang.System.out.println("hello");
	var nb = new java.text.DecimalFormat(patten);
	nb.setMaximumFractionDigits(4);
	java.lang.System.out.println(nb);
	var bd = new java.math.BigDecimal(text);
	java.lang.System.out.println(bd.doubleValue());
	var s = nb.format(bd.doubleValue());
	java.lang.System.out.println(nb.format(bd.doubleValue()));
	return s;
	
}

//function formatNumber(v, text){
//	var bd = new java.math.BigDecimal(text);
//	bd = bd.setScale(v, java.math.BigDecimal.ROUND_DOWN);
//	return bd.toString();
//}
function formatNumber(v, text){
	if(text == '--'){
		return text;
		}else{
	var bd = new java.math.BigDecimal(text);
	bd = bd.setScale(v, java.math.BigDecimal.ROUND_DOWN);
	return bd.toString();
}
}

function print(){
	java.lang.System.out.println("OK");
}
function getName(){
	return "帐目类型";
}
function multiply(a,b){
   return (a*b)+"--a";
}
 function changeNodeName(nodeName){
	var realName = "";
	//var nodeName="2002,2003,2004,|收入";
	var fNodeName = nodeName.split("|");
	var fPathcode = fNodeName[0].split(",");
	for(var i=1;i<fPathcode.length-1;i++)
		realName+="&nbsp;&nbsp;&nbsp;&nbsp;"
	realName+=fNodeName[1];
	return realName;
}
function fmis_size(value){
		return "<span style=\"color:red\">"+value+"</span>";
}