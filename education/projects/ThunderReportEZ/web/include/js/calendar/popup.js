var gcToggle = "#ffff00";
var gcBG = "#CCCCFF";
//webapp??
var  appPath=parent.gblData.appPath;

function fPopUpCalendarDlg(ctrlobj)
{
	showx = event.screenX - event.offsetX ; // + deltaX;
	showy = event.screenY - event.offsetY +20; // + deltaY;
	newWINwidth = 210 + 4 + 18;
	retval = window.showModalDialog(appPath+"include/bi/js/calendar/calendardlg.htm", "", "dialogWidth:178px; dialogHeight:196px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;help=no "  );
	if( retval != null ){
		ctrlobj.value = retval;
	}else{
		//alert("canceled");
	}
}
