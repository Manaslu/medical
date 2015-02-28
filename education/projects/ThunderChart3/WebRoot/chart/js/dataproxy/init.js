function getTopWindow(){
	var win = window;
	for(;;){
			if(win.opener && win.opener !== win && win.opener.name!=="teamsun/MDSS"){
				win = win.opener.top;
			}
			else
				break;
	
	}
	return win;
} 

function loadXMLStr(xmlString){
	var moz = (typeof document.implementation.createDocument != 'undefined');
	var ie = (typeof window.ActiveXObject != 'undefined');
	var xmlDoc;
	if (moz) {
		domParser = new DOMParser();
		xmlDoc = domParser.parseFromString(xmlString, 'text/xml')
	} else if (ie) {
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = false;
		xmlDoc.loadXML(xmlString);
	}
	return xmlDoc;

}
