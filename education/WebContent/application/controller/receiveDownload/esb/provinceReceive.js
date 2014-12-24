define(function (require, exports, module) {
    //省中心接收监控
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbProvinceReceiveCtrl',  ['$scope' ,'EsbRecv','Common', function ($scope,EsbRecv,Common) {
        	//查询业务系统列表
        	$scope.busiList=Common.query({code:true});
        	var defaultParams = {
        			orderBy:'LOADING_DATE',
    				order:'DESC'
            };	
        	//不是全国中心用户，默认参数值
    		if($scope.USER_INFO.provCode != '99'){
        		defaultParams.logProvinceNo =[$scope.USER_INFO.provCode];
        		$scope.pager=EsbRecv;
        	}else{
        		Common.query({isArray:true,params:"{}"} , function(results){
        			$scope.provList = results;
        			var tmpList=angular.copy(results);
        			//tmpList.splice(0,1);
        			logProvinceNo = tmpList.map(function(item){
        				return item.id
        			});
        			defaultParams.logProvinceNo =logProvinceNo;
        			$scope.pager=EsbRecv;
        		});
        	}
        	
        	$scope.params = defaultParams;
        }]);
    }
});