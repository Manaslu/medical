define(function (require, exports, module) {
    //全国中心接收监控
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbNationwideReceiveCtrl', ['$scope' ,'EsbRecv','Common', function ($scope,EsbRecv,Common) {
        	$scope.pager=EsbRecv;
        	$scope.provList=Common.query({isArray:true,params:"{}"});
        	$scope.params = {
        		logProvinceNo:[$scope.USER_INFO.provCode],
   				orderBy:'LOADING_DATE',
   				order:'DESC'
       		};
        	$scope.busiList=Common.query({code:true});
        }]);
    }

});