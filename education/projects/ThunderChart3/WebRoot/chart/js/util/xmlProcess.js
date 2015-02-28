Ext.namespace("Thunder");
Ext.namespace("Thunder.XML");
Thunder.XML.loadXMLStr = function(xmlString){
	var xmlDoc;
    try {
        if (typeof (XSLTProcessor) != "undefined") { // FF, Safari, Chrome etc
    		domParser = new DOMParser();
    		xmlDoc = domParser.parseFromString(xmlString, 'text/xml')
        }else{
    		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
    		xmlDoc.async = false;
    		xmlDoc.loadXML(xmlString);
        }
    }
    catch (e) {
        alert("xmlDoc load string error!");
        return null;
    }
    return xmlDoc;
}


Thunder.XML.loadXMLFile = function(file){
	var xmlDoc;
    if (typeof (XSLTProcessor) != "undefined") { // FF, Safari, Chrome etc
    	xmlDoc = document.implementation.createDocument("", "", null);
    }
    if (typeof (xmlDoc.transformNode) != "undefined") { // IE6, IE7, IE8
    	xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
    }
    else {
    	xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
    }
	xmlDoc.async = false
    try {
    	xmlDoc.load(file);
    }
    catch (e) {
        alert("xmlDoc load file error!");
        return null;
    }
    return xmlDoc;
}
 
Thunder.XML.transformNode =function(xmlDoc, xsltDoc) {
    if (typeof (XSLTProcessor) != "undefined") { // FF, Safari, Chrome etc
        var xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsltDoc);
        var xmlFragment = xsltProcessor.transformToFragment(xmlDoc, document);
         
        if (typeof(GetXmlStringFromXmlDoc)!= "undefined"){       
            return GetXmlStringFromXmlDoc(xmlFragment);
        }
        else{
            var xmls = new XMLSerializer();
            var sResult = xmls.serializeToString(xmlFragment);
            if (sResult.indexOf("<transformiix:result") > -1){
                sResult = sResult.substring(sResult.indexOf(">") + 1, sResult.lastIndexOf("<"));
            }       
            return sResult;
        }
    }

    if (typeof (xmlDoc.transformNode) != "undefined") { // IE6, IE7, IE8
        return xmlDoc.transformNode(xsltDoc);
    }
    else {
        try { // IE9 and grater
            if (window.ActiveXObject) {
                var xslt = new ActiveXObject("Msxml2.XSLTemplate");
                var xslDoc = new ActiveXObject("Msxml2.FreeThreadedDOMDocument");
                xslDoc.loadXML(xsltDoc.xml);
                xslt.stylesheet = xslDoc;
                var xslProc = xslt.createProcessor();
                xslProc.input = xmlDoc;
                xslProc.transform();
                return xslProc.output;
            }
        }
        catch (e) {
            alert("the function [XmlDocument.transformNode] is not supported by this browser, can't transform XML document to HTML string!");
            return null;
        }

    }
}

Thunder.getChildByNodeName = function(parNode, nodeName){
	var node = null;
	for (var i = 0; i < parNode.childNodes.length; i++) {
		if (parNode.childNodes[i].nodeName == nodeName)
			node = parNode.childNodes[i];
	}
	return node;
}

