/************************************
	String
*************************************/
String.prototype.encodeXML=function()
{
	var str=this;
	str=str.replace(/\x26/g,"&#38;");
	str=str.replace(/\x3c/g,"&#60;");
	str=str.replace(/\x3e/g,"&#62;");
	str=str.replace(/\x22/g,"&#34;");
	str=str.replace(/\x27/g,"&#39;");
	str=str.replace(/\x20/g,"&#160;");
	return str;
}
String.prototype.encodeHTML=function()
{
	var str=this;
	str=str.encodeXML();
	str=str.replace(/\x0d\x0a/g,"<br/>");
	str=str.replace(/\t/g,"&#160;&#160;&#160;&#160;&#160;&#160;");
	return str;
}
String.prototype.Length=function()
{
	return this.replace(/[^\x00-\xff]/g,'**').length;
}
String.prototype.trim=function()
{
	return this.replace(/^\s*/g,"").replace(/\s*$/g,"");
}
String.prototype.ltrim=function()
{
	return this.replace(/^\s*/g,"");
}
String.prototype.rtrim=function()
{
	return this.replace(/\s*$/g,"");
}
String.prototype.left=function(count)
{
	return this.substr(0,count);
}
String.prototype.right=function(count)
{
	return this.substr(this.length-count,count);
}
String.prototype.removeBlank=function()
{
	return this.replace(/\s*/g,"");
}
String.prototype.isEnglish=function()
{
	return /^[a-zA-Z]+$/.test(this);
}
String.prototype.isNumber=function(){
	return /^\d+$/.test(this);
}
String.prototype.isEmail=function()
{
	return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(this);
}
/*
[a-zA-Z0-9_]
*/
String.prototype.isValidID=function(){
	return /^\w+$/.test(this);
}
String.prototype.isDate=function()
{
	var str=this;
    var reg =/^(\d+)-(\d{1,2})-(\d{1,2})$/;    
    var r=str.match(reg);    
    if(r==null)return false;    
    r[2]=r[2]-1;    
    var d=new Date(r[1],r[2],r[3]);    
    if(d.getFullYear()!=r[1])return false;    
    if(d.getMonth()!=r[2])return false;    
    if(d.getDate()!=r[3])return false;    
    return true;  
}
String.prototype.isIPAdress=function()
{
	return /^([0-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])$/.test(this);
}
/*
是否整数
*/
String.prototype.isInt=function(){
	return /^(0{1})$|^-?[^0\D]+(\d)*$/.test(this);
}
/*
是否正整数
*/
String.prototype.isPInt=function(){
	return /^(0{1})$|^[^0\D]+(\d)*$/.test(this);
}
/*
是否为n位小数
*/
String.prototype.isDecn=function(n){
	var exstr = "/^(0{1})$|^-?[1-9]+\\d*(\.\\d{1,"+n+"})?$|^0{1}(\.\\d{1,"+n+"})?$|^-?[0]{1}(\.\\d{1,"+n+"})?$/";
	return new RegExp(exstr).test(this);
	
}
/*********************************************
	Array
*********************************************/
Array.prototype.indexOf=function(o){
	for(var i=0;i<this.length;i++)
		if(this[i]==o)return i;
	return-1;
}
Array.prototype.lastIndexOf=function(o){
	for(var i=this.length-1;i>=0;i--)
		if(this[i]==o)return i;
	return-1;
}
Array.prototype.contains=function(o){
	return this.indexOf(o)!= -1;
}
Array.prototype.copy=function(){
	return this.concat();
}
Array.prototype.removeAt=function(i){
	return this.slice(0,i).concat(this.slice(i+1,this.length))
}
Array.prototype.remove=function(o){
	var i=this.indexOf(o);
	if(i!= -1) return this.removeAt(i)
	return this
}
if(!Array.prototype.push)
Array.prototype.push = Push;
function Push(){
	var ar=[];
	for (var i=0;i<arguments.length;i++) this[this.length]=arguments[i];
	return this.length;
}
if(!Array.prototype.pop)
Array.prototype.pop = Pop;
function Pop(){
	var rs=this[this.length-1];
	this.length = this.length-1;
	return rs;
}
/*********************************************
	common function
*********************************************/
/*
func:用于确定某个变量是否声明，变量是否声明还未付值，是否使用了一个并不存在的对象属性
*/
function isset(str){
	if(typeof(str)=='undefined'){
		return false;
	}
	return true;
}
/*
*/
function trimString(str) {
	if(!isset(str)){
		return "";
	}
	else {
		var retstr=new String(str);
		return retstr.trim();
	}
} 
/*
func:判断字符串是否为空
ret:empty string,return true
*/
function isEmptyString(s){
	if (typeof(s)!='undefined'){
		var s1 = new String(s);
		var s2 = s1.trim();
		if (s2.length>0){
			return false;
		}
		else{
			return true;
		}
	}
	return true;
}
/*
func:获取相隔天数的日期字符串
ret:yyyymmdd
*/
function getPreDay(year,month,day,n){
	    var d = new Date()
	    d.setUTCFullYear(year/1,(month-1)/1,(day-n));
	    nyear = d.getUTCFullYear();
	    nmonth = d.getUTCMonth()+1;
	    nday = d.getUTCDate();
	    if(nmonth<10){
	    	nmonth = '0'+nmonth;
	   	}
	    if(nday<10){
	    	nday = '0'+nday; 
	    }
	    return ''+nyear+nmonth+nday;
}
/*
func:得到当前日期(yyyymmdd)的字符串
*/
function getNowDate(){
		var dt = new Date();
		var y = dt.getFullYear();
		var m = dt.getMonth()+1;
		var d = dt.getDate();
		var dtValue = ''+y;
		if(m<10){
			dtValue += "0";
		}
		dtValue += m;
		if(d<10){
			dtValue += "0";
		}
		dtValue += d;
		return dtValue;
}
/*
func:得到当前日期(yyyy-mm-dd hh:mm:ss)的字符串
*/
function getNowDateTime(){
		var dt = new Date();
		var y = dt.getFullYear();
		var m = dt.getMonth()+1;
		var d = dt.getDate();
		var dtValue = ''+y;
		dtValue += "-";
		if(m<10){
			dtValue += "0";
		}
		dtValue += m;
		dtValue += "-";
		if(d<10){
			dtValue += "0";
		}
		dtValue += d;
		dtValue += " ";
		var h = dt.getHours();
		if(h<10){
			dtValue += "0"
		}
		dtValue += h;
		dtValue += ":";
		var m1 = dt.getMinutes();
		if(m1<10){
			dtValue += "0";
		}
		dtValue += m1;
		dtValue += ":";
		var s = dt.getSeconds();
		if(s<10){
			dtValue += "0";
		}
		dtValue += s;
		return dtValue;
}
/*
*/
function dw(str){
	document.write(str);
}
/*
	日历
*/

var gcToggle = "#ffff00";
var gcBG = "#CCCCFF";
function fPopUpCalendarDlg(ctrlobj)
{
	showx = event.screenX - event.offsetX ; // + deltaX;
	showy = event.screenY - event.offsetY +20; // + deltaY;
	newWINwidth = 210 + 4 + 18;
	retval = window.showModalDialog("/MSP/include/js/calendar/calendardlg.htm", "", "dialogWidth:178px; dialogHeight:196px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help=no "  );
	if( retval != null ){
		eval("document.all."+ctrlobj).value = retval;
		eval("document.all.dis"+ctrlobj).value = retval.substr(0,4)+"-"+retval.substr(4,2)+"-"+retval.substr(6,2);
	}else{
		//alert("canceled");
	}
}
function UI_calendarimg(id){
	var imgvar = '<img style="cursor: hand" onclick="fPopUpCalendarDlg(\'';
	    imgvar += id;
	    imgvar += '\');return false"' ;
	    imgvar += ' alt="日历" src="/MSP/include/js/calendar/calendar.gif"'; 
	    imgvar += ' align="absMiddle">';
	return imgvar;
}
function UI_calendar(id,value,readonly){
	var byear;
	var smonth;
	var sday;
	if(!isEmptyString(value)&&value.length==8){
		byear = value.substr(0,4);
		smonth = value.substr(4,2);
		sday = value.substr(6,2);
	}
	var jsvar = '';
	jsvar += '<input id="dis';
	jsvar += id;
	jsvar += '" readonly maxlength="10" size="10" name="dis';
	jsvar += id;
	if(isset(byear)){
		jsvar += '" value="'+byear+"-"+smonth+"-"+sday+'">';
	}
	else{
		jsvar += '" value="">';
	}
	jsvar += '<input type="hidden" id="';
	jsvar += id;
	jsvar += '" name="';
	jsvar += id;
	jsvar += '" value="'+value+'">';
	if(readonly!="true"){
		jsvar += UI_calendarimg(id);
	}
	
	return jsvar;
}

/*
	datagridpagination plus search,init()
*/
function initpage(formobj,searchid){
	var vobj;
	var tmpobj;
	vobj = document.createElement("INPUT");
	vobj.type="hidden";
	vobj.name=searchid;
	tmpobj = window.parent.document.getElementById(searchid);
	if(tmpobj){
			vobj.value = tmpobj.value;
	}
	formobj.appendChild(vobj);
	
	vobj = document.createElement("INPUT");
	vobj.type="hidden";
	vobj.name=searchid+"condition";
	tmpobj = window.parent.document.getElementById(searchid+"condition");
	if(tmpobj){
			vobj.value = tmpobj.value;
	}
	formobj.appendChild(vobj);
	
	vobj = document.createElement("INPUT");
	vobj.type="hidden";
	vobj.name=searchid+"conditionvalue";
	tmpobj = window.parent.document.getElementById(searchid+"conditionvalue");
	if(tmpobj){
			vobj.value = tmpobj.value;
	}
	formobj.appendChild(vobj);
	
}

function infoboxdis(){
	var vobj = window.event.srcElement;
	if(vobj){
		var vvobj = vobj.parentElement.nextSibling.firstChild.firstChild;
		if(vvobj.tagName=="DIV"){
			if(vobj.block=="1"){
				vobj.block="0";
				vobj.innerText = 4;
				vobj.title="展开";
				vvobj.style.display="none";
			}
			else{
				vobj.block="1";
				vobj.innerText = 6;
				vobj.title="折叠";
				vvobj.style.display="block";
			}
		}
	}
}