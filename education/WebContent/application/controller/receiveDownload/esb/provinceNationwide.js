define(function (require, exports, module) {

    //省至全国中心数据核对
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbProvinceNationwideCtrl', ['$scope' , 'DataCheck','Common',function ($scope,DataCheck,Common) {
        	 $scope.pager = DataCheck;
        	 var defaultParams = {
         			orderBy:'BUSINESS_DATE',
     				order:'DESC',
     				logProvinceNo:$scope.USER_INFO.provCode
             };
         	$scope.params = defaultParams;
         	if($scope.USER_INFO.provCode === '99'){
        		Common.query({isArray:true,params:"{}"} , function(results){
        			$scope.provList = results;
        		});
        	}
         	$scope.busiList=Common.query({code:true});
         	//[{name:"营业系统"},{name:"投递系统"},{name:"农资分销系统"},{name:"客管系统"},{name:"集邮系统"},{name:"网运系统"},{name:"报刊系统"},{name:"邮资封片卡系统"},{name:"贺卡兑奖平台"},{name:"短信平台"},{name:"电商平台(机票)"}];
        }]);
    }

});