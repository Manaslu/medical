define(function (require, exports, module) {
    //数据下载总体情况
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbDataDownloadSituationCtrl', ['$scope' , 'DataDownload','Common',function ($scope,DataDownload,Common) {
            $scope.pager = DataDownload;
            $scope.provList=Common.query({isArray:true,params:"{}"});
            $scope.params = {
    				orderBy:'INIT_DATE',
    				order:'DESC'
        	};
        	$scope.busiList=Common.query({code:true});
        		//[{name:"营业系统"},{name:"投递系统"},{name:"农资分销系统"},{name:"客管系统"},{name:"集邮系统"},{name:"网运系统"},{name:"报刊系统"},{name:"邮资封片卡系统"},{name:"贺卡兑奖平台"},{name:"短信平台"},{name:"电商平台(机票)"}];
        }]);
    }
});