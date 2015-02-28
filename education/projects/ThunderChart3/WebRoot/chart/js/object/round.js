  var roundDiv= function(divname,width,height,displaystr){
  	
  	  	//divname=document.getElementById(divname);
  	var rbroundbox=document.createElement("div");
  	    rbroundbox.setAttribute("id","rbroundbox");
  	    
  	var rbtop=document.createElement("div");
  	    rbtop.setAttribute("id","rbtop");
  	    
  	var a=document.createElement("div");
  	    a.setAttribute("id","rbtopdiv");
  	
  	var rbcontent=document.createElement("div");
  	    rbcontent.setAttribute("id","rbcontent");  	
  	    
  	var rbbot=document.createElement("div");
  	    rbbot.setAttribute("id","rbbot");
  	    
  	var b=document.createElement("div");
  	    b.setAttribute("id","rbbotdiv");
  	
  	rbtop.appendChild(a);
  	rbbot.appendChild(b);		
  	
  	rbroundbox.appendChild(rbtop);
  	rbroundbox.appendChild(rbcontent);
  	rbroundbox.appendChild(rbbot);
  	divname.appendChild(rbroundbox);
  	
  	rbroundbox.style.height=(height-14)+"px";
  	rbroundbox.style.width=width+"px";
  	
  	rbcontent.style.height=(height-28)+"px";
  	rbcontent.innerHTML=displaystr;
  	
  	}