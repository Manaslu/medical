define(function(require, exports, module) {

	// 数据加载总体情况
	return function setApp(app) {
		app.controller('ReceiveDownloadEsbLoadStateCtrl', [ '$scope',
				'EsbDownload','Common', function($scope, EsbDownload,Common) {
					$scope.pager = EsbDownload;
					$scope.busiList=Common.query({code:true});
					Common.query({isArray:true,params:"{}"} , function(results){
	        			$scope.provList = results;
	        		});
					var defaultParams = {orderBy : 'BUSINESS_DATE'};
					
					if($scope.USER_INFO.provCode != '99'){
		        		defaultParams.provinceName =$scope.USER_INFO.provName;
					}
					
					$scope.params=defaultParams;
				}]);
	}

});