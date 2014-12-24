define(function(require, exports, module) {
 
    //订阅通知--订阅日志
    return function setApp(app) {
        app.controller('ResultSubscriptionsRecordCtrl',['$scope' ,'SubsTail','ThemeRep', '$filter' , function ($scope ,SubsTail ,ThemeRep, $filter) {
            
        	//按条件分页查询订阅日志信息数据
        	$scope.pager=SubsTail;
        	$scope.SubsTail = SubsTail;
        	
        	$scope.params = {};
        	
        	//初始查询主题名称
        	ThemeRep.query({isArray:true,params:{}},function (list){
        		$scope.themeNameList =list; 
            });
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {};
            };
            
        }]);
    }
	
	
});
