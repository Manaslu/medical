if (typeof getElementById!="function") {
  var getElementById = function (id) {
    if   (typeof(id)=="object") return id;
    if   (document.getElementById(id)) { return document.getElementById(id); } 
    else { throw new Error(id +" argument error, can not find \"" +id+ "\" element"); }
  }
}

function getElCoordinate (e) {
  var t = e.offsetTop;
  var l = e.offsetLeft;
  var w = e.offsetWidth;
  var h = e.offsetHeight;
  while (e=e.offsetParent) {
    t += e.offsetTop;
    l += e.offsetLeft;
  }; return {
    top: t,
    left: l,
    width: w,
    height: h,
    bottom: t+h,
    right: l+w
  }
}

var neverModules = window.neverModules || {};
neverModules.modules = neverModules.modules || {}; 

neverModules.modules.slider = function (cfg) {
  if ((typeof cfg)!="object") 
  throw new Error("config argument is not a object, err raise from slider constructor");

  this.targetId  = cfg.targetId;
  this.hints     = cfg.hints?cfg.hints:"";
  this.sliderCss = cfg.sliderCss?cfg.sliderCss:"";
   
  this.barCss    = cfg.barCss?cfg.barCss:"";
  this.min       = cfg.min?cfg.min:0;
  this.max       = cfg.max?cfg.max:100;
  this.onstart   = null;
  this.onchange  = function (){Thunder.zoomchart(this.cur);}
  this.onfinish  = null;
  //sjj
  this.slider_w  = cfg.slider_w?cfg.slider_w:"10"; 
  this.slider_h  = cfg.slider_h?cfg.slider_h:"200"; 
  this.slider_bc = cfg.slider_bc?cfg.slider_bc:"#eee"; 
  this.bar_w = cfg.bar_w?cfg.bar_w:"10"; 
  this.bar_h = cfg.bar_h?cfg.bar_h:"10"; 
  this.bar_bc = cfg.bar_bc?cfg.bar_bc:"#eee"; 
  
  
  this._defaultInitializer.apply(this);
}

neverModules.modules.slider.prototype = {
  _defaultInitializer: function () {
    this._bar     = null;
    this._slider  = null;
    this._wrapper = null;
    this._target  = getElementById(this.targetId);
    if (this.min>this.max){var x=this.min;this.min=this.max;this.max=x;}
    this._value   = this.min;
  },

  create: function () {
    this._createSlider();
  },

  dispose: function () {
    //virtual function
  },

  createBar: function () { with(this) {
    //0.10 can not create mutilple bar
    //this interface is for next version
    //by never-online
    var _self = this;
    _bar = document.createElement("DIV");
    _wrapper.appendChild(_bar);
    _bar.title = hints;
    _bar.id = targetId + "_bar";
    _bar.className = barCss;
    _bar.style.position  = "absolute";

		//SJJ
		_bar.style.width = _self.bar_w + "px"; 
		_bar.style.height = _self.bar_h + "px"; 
		_bar.style.backgroundColor = _self.bar_bc; 
	
		
    _bar.onmousedown = function (event) {
      _self._initHandle(event);
    }
  }},

  setValue: function (value) { with(this) {
    if (!_bar) return;
    value = Number(value);
    value = value>max?max:value<min?min:value;
    _bar.style.left = Math.round((value-min)*((_slider.offsetWidth-_bar.offsetWidth)/(max-min)))+"px";
    _value = value;
    onchange.call(this);
  }},

  getValue: function () {
    return this._value;
  },

  _createSlider: function () { with(this) {
     var _self = this;
    _wrapper = document.createElement("DIV");
    _target.appendChild(_wrapper);
    _wrapper.id = targetId + "_wrapper";
    _wrapper.style.position = "relative";
   
    _slider = document.createElement("DIV");
    _wrapper.appendChild(_slider);
    _slider.id = targetId + "_slider";
    _slider.className = sliderCss;
    _slider.style.position  = "absolute";
     
    //sjj
    _slider.style.width = _self.slider_w + "px"; 
    _slider.style.height = _self.slider_h + "px"; 
    _slider.style.backgroundColor = _self.slider_bc; 
    
    //sjj  
    _slider_b = document.createElement("DIV");
    document.getElementById(targetId + "_slider").appendChild(_slider_b);
    _slider_b.id = targetId + "_slider_b";
   // _slider_b.className = winSliderb;
    _slider_b.style.position  = "absolute";
    _slider_b.style.height = parseInt(_self.slider_h-4) + "px"; 
    _slider_b.style.backgroundColor = _self.slider_bc; 
    
    this.createBar();
    _slider.onclick = function (event) { _self._moveTo(event); }
  }},

  _moveTo: function (evt) { with(this) {
    evt = evt?evt:window.event; 
    var x = evt.clientX-getElCoordinate(_slider).left-Math.round(_bar.offsetWidth/2);
    x = x<=_slider.offsetLeft?_slider.offsetLeft:x>=_slider.offsetLeft+_slider.offsetWidth-_bar.offsetWidth?_slider.offsetLeft+_slider.offsetWidth-_bar.offsetWidth:x;
    _bar.style.left = x+"px"; _value = Math.round(x/((_slider.offsetWidth-_bar.offsetWidth)/(max-min))+min);
    onchange.call(this);
  }},

  _initHandle: function (evt) { with(this) {
    evt  = evt?evt:window.event; var _self = this;
    _bar.slider_x = evt.clientX-_bar.offsetLeft;
    document.onmousemove = function (event) { _self._changeHandle(event); }
    document.onmouseup   = function (event) { _self._stopHandle(event);   }
  }},

  _changeHandle: function (evt) { with(this) {
    evt = evt?evt:window.event; var x = evt.clientX-_bar.slider_x;
    x = x<=_slider.offsetLeft?_slider.offsetLeft:x>=_slider.offsetLeft+_slider.offsetWidth-_bar.offsetWidth?_slider.offsetLeft+_slider.offsetWidth-_bar.offsetWidth:x;
    _bar.style.left = x+"px"; _value = Math.round(x/((_slider.offsetWidth-_bar.offsetWidth)/(max-min))+min);
    onchange.call(this);
  }},

  _stopHandle: function (evt) { with(this) {
    //Release event
    document.onmousemove = null;
    document.onmouseup   = null;
  }}
  
 
}



 //sjj
  MySlider = function(id,caption,s_w,s_h,s_c,b_w,b_h,b_c,arrange,index,tip)
 { 
 	 this.setInitValue = function(min,max,value,thres,bq,sq)
 	 {
 	 	 this.winSlider.min = min; 
 	 	 this.winSlider.max = max; 
 	 	 this.cur = value; 
 	 	 this.thres = thres; 
 	 	 this.bq = bq; 
 	 	 this.sq = sq; 
 	 	 this.winSlider.onchange = function(){
 	 	 	Thunder.zoomchart(this.cur);
 	 	 	};	
 	 	 this.setValue(this.cur); 
 	 }
 	 
 	 this.setValue = function(cur)
   {
   	 this.winSlider.setValue(cur); 
   	 document.getElementById(this.id+"_d3").innerText = cur; 
   }
   
   this.getValue = function()
   {
   	 return this.winSlider.getValue(); 
   }
            
 	 {
	  var d1 =  document.createElement('div') ;
    var d2 =  document.createElement('div') ;
    var d3 =  document.createElement('div') ;
    var font_style = "normal small-caps normal 8pt Courier";
    
    d1.id = id + "_d1";
    d2.id = id + "_d2"; 
    d3.id = id + "_d3"; 
    
    document.getElementById(id).appendChild(d1);
    document.getElementById(id).appendChild(d2);
    document.getElementById(id).appendChild(d3); 
   
    this.id = id;  
    this.winSlider = new neverModules.modules.slider({targetId: id + "_d2",
                                                          sliderCss: "winSlider",
                                                          barCss: "winBar",
                                                          slider_w:s_w,
                                                          slider_h:s_h,
                                                          slider_bc:s_c,
                                                          bar_w:b_w,
                                                          bar_h:b_h,
                                                          bar_bc:b_c
                                                          });
    
    if (arrange == 'h')
    {
      document.getElementById(id+"_d1").style.cssText = "float:left;clear:right;font:"+font_style+";color:blue;"; 
			document.getElementById(id+"_d2").style.cssText = "float:left;clear:right;position:relative;"; 
			var l = s_w+5;
			document.getElementById(id+"_d3").style.cssText = "float:left;padding-left:"+ l +"px;clear:right;font:"+font_style+";color:blue;"; 
    }                                                     
    else
    {
     	document.getElementById(id+"_d1").style.cssText = "text-align:center; width:"+s_w+"px;font:"+font_style+";color:blue;"; 
    	var h = s_h + 7; 		
			document.getElementById(id+"_d3").style.cssText = "text-align:center;padding-top:"+h+"px; width:"+s_w+"px;font:"+font_style+";color:blue;"; 
    } 
   
  	document.getElementById(id+"_d1").innerHTML = caption;  
  	
  	//add tip 
  	var tipname = "'"+id+"tip'"; 
    document.getElementById(id+"_d1").innerHTML = "<a onmouseover=\"showtip("+tipname+")\" onmouseout=\"hidtip("+tipname+")\">"+caption+"</a>"; 
    var d_tip = document.createElement('div'); 
    d_tip.id = id+"tip"; 
    d_tip.className = "tipdiv"; 
    document.getElementById(id+"_d1").appendChild(d_tip);
    var d_p = document.createElement('p'); 
    d_p.innerHTML = tip ; 
    document.getElementById(id+"tip").appendChild(d_p);
    
    this.winSlider.create();
   }
 }
