define(function (require, exports, module) {

    //全国中心下载监控
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbNationwideDownloadCtrl', ['$scope' ,'EsbSend' ,'Common',function ($scope,EsbSend,Common) {
        	   $scope.pager=EsbSend;
        	   $scope.params = {
        			logProvinceNo:[$scope.USER_INFO.provCode],
       				orderBy:'BUSINESS_DATE',
       				order:'DESC'
           		};
        	   $scope.busiList=[{"portcode":"NPS","name":"报刊系统"},{"portcode":"FJP","name":"电商机票"},{"portcode":"CXD","name":"短信平台"},{"portcode":"HKD","name":"贺卡兑奖"},{"portcode":"POS","name":"集邮系统"},{"portcode":"KHG","name":"客管系统"},{"portcode":"DMS","name":"农资分销"},{"portcode":"WYN","name":"网运系统"},{"portcode":"YZF","name":"邮资封片"}];//Common.query({code:true});
        	   $scope.provList=Common.query({isArray:true,params:"{}"});
        }]);
    }

});