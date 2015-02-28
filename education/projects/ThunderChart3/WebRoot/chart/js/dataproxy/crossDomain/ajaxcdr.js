

 
AjaxCrossDomainRequest = function(url, method,id,para){
	this.url = url;
	this.id = id;
	this.para = para;
	this.DATA_LOADED_SUBJECT = "dataproxy" + this.id + "." + "dataLoaded";
	this.DATA_REQUEST_SUBJECT = "dataproxy" + this.id + "." + "dataRequest";
}
AjaxCrossDomainRequest.prototype={
	request:function(sub,postData,id){
	var proxy =  Ext.StoreMgr.lookup(id);		
		Ext.Ajax.request({
            url : proxy.url, 
            method: 'POST',
            params :{"para" : proxy.para},
            success: function ( result, request ) {
            		var responsetext = result.responseText ;
            		var pro =  Ext.StoreMgr.lookup(id);
            		window.PageBus.publish(pro.DATA_LOADED_SUBJECT,responsetext);
            },
            failure: function ( result, request) { 
                Ext.MessageBox.alert("系统消息", result.responseText); 
            } 
		});
		
		
		
	
	}
}