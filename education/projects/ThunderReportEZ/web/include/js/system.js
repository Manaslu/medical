var System = {
  getPlatform : function(){
		var _platform = null;
		if(_platform!=null){
			return _platform;
		}
		if(typeof(navigator)=="undefined"){
			return "not-browser";
		}
		var ua=navigator.userAgent.toLowerCase();
		if(/msie/i.test(ua)){
			if(/msie 6/i.test(ua)){
				_platform="ie6";
				}
			else if(/msie 5\\.5/i.test(ua)){
				_platform="ie5.5";
				}
			else if(/msie 5\\.[^5]/i.test(ua)){
				_platform="ie5";
			}else{
				_platform="ie";
			}
		}else if(/gecko/i.test(ua)){
			_platform="moz";
		}else if(/opera/i.test(ua)){
			_platform="opera";
		}else{
			_platform="other";
		}
		return _platform;
	},
	isIeBrowser : function(){return(System.getPlatform().indexOf("ie")!=-1);},
	isMozBrowser : function(){return System.getPlatform()=="moz";}
};
