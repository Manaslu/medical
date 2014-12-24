define(function (require, exports, module) {
    //省中心上传监控
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbProvinceUploadCtrl',  ['$scope' ,'EsbSend' ,'Common',function ($scope , EsbSend , Common) {
        	$scope.busiList = [{"portcode":"KHG","name":"客管系统"},{"portcode":"TDX","name":"投递系统"},{"portcode":"YEX","name":"营业系统"}]//Common.query({code:true});
    		var defaultParams = {
    			orderBy:'BUSINESS_DATE',
				order:'DESC'
    		};
        	
        	if($scope.USER_INFO.provCode != '99'){
        		defaultParams.logProvinceNo =[$scope.USER_INFO.provCode];
        		$scope.pager = EsbSend;
        	}else{
        		Common.query({isArray:true,params:"{}"} , function(results){
        			var tmpList=angular.copy(results);
        			tmpList.splice(0,1);
        			logProvinceNo = tmpList.map(function(item){
        				return item.id
        			});
        			defaultParams.logProvinceNo = logProvinceNo;
        			$scope.provList = results;	
        			$scope.pager = EsbSend;
        		});
        	}
        	$scope.params = defaultParams;
        }]);
    }

});