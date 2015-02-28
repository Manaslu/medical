Ext.ns("Ext.ux");

Ext.ux.XmlReaderBuilder = function(meta, recordType){
    meta = meta || {};
    Ext.ux.XmlReaderBuilder.superclass.constructor.call(this, meta, recordType || meta.fields);
};

Ext.extend(Ext.ux.XmlReaderBuilder, Ext.data.DataReader, {
    readRecords : function(o){
        var doc = loadXMLFromString(o);
        var root = doc.documentElement || doc;
    	var q = Ext.DomQuery;
    	var recordType = this.recordType, fields = recordType.prototype.fields;
    	var sid = this.meta.id;
    	var totalRecords = 0, success = true;
    	if(this.meta.totalRecords){
    	    totalRecords = q.selectNumber(this.meta.totalRecords, root, 0);
    	}

        if(this.meta.success){
            var sv = q.selectValue(this.meta.success, root, true);
            success = sv !== false && sv !== 'false';
    	}
    	var records = [];
    	var ns = q.select(this.meta.record, root);
        for(var i = 0, len = ns.length; i < len; i++) {
	        var n = ns[i];
	        var values = {};
	        var id = sid ? q.selectValue(sid, n) : undefined;
	        for(var j = 0, jlen = fields.length; j < jlen; j++){
	            var f = fields.items[j];
                var v = q.selectValue(f.mapping || f.name, n, f.defaultValue);
	            v = f.convert(v, n);
	            values[f.name] = v;
	        }
	        var record = new recordType(values, id);
	        record.node = n;
	        records[records.length] = record;
	    }

	    return {
	        success : success,
	        records : records,
	        totalRecords : totalRecords || records.length
	    };
    }
});

/**
 * 从文件中读取XML文件
 * @param {} xmlFile
 * @return {}
 */
function loadXMLFromFile(xmlFile){
	var xmlDoc = null;
	if(window.ActiveXObject){
		xmlDoc = new ActiveXObject("Msxml2.DOMDocument");
		xmlDoc.async = false;
		xmlDoc.load(xmlFile);
	} else if(document.implementation && document.implementation.createDocument){
		xmlDoc = document.implementation.createDocument("","",null);
		xmlDoc.async = false;
		xmlDoc.load(xmlFile);
	}
	return xmlDoc;
}

/**
 * 
 * @param {} xmlStr
 * @return {}
 */
function loadXMLFromString(xmlStr){
	var xmlDoc = null;
	if (document.implementation && document.implementation.createDocument) {
		var domParser = new DOMParser();
		xmlDoc = domParser.parseFromString(xmlStr, 'text/xml')
	} else if (window.ActiveXObject) {
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = false;
		xmlDoc.loadXML(xmlStr);
	}
	return xmlDoc;
}


