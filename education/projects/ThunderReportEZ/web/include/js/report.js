Ext.onReady(function(){
	var EDQ=Ext.DomQuery;
	var trarr=EDQ.select('table.thunderreport tbody tr');
	for(var i=0,length=trarr.length;i<length;i++){
		var tr=Ext.get(trarr[i]);
		tr.addClassOnOver('tr_mouseover',false);
		/*if(i==0)
			tr.addClass("tr_even");
			
	
		for(var j=0;j<tr.dom.childNodes.length;j++){
			var td = tr.dom.childNodes(j);
			//alert(td.innerHTML)
			if(parseInt(td.rowSpan)==1){
				if(i%2==0)
					td.className = "tr_even";
				else
					td.className= "tr_odd";
		
			}
			else if(parseInt(td.rowSpan)>1)
			   td.className= "tr_even";
		}
		//tr.addClassOnClick('tr_click',false)*/
	}
})

