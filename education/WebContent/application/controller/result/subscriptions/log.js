define(function(require, exports, module) {
 
    //订阅通知--订阅日志
    return function setApp(app) {
        app.controller('ResultSubscriptionsLogCtrl',['$scope' ,'EmailPush','ThemeRep', '$filter' , function ($scope ,EmailPush ,ThemeRep,$filter) {
            
        	//按条件分页查询订阅日志信息数据
        	$scope.pager=EmailPush;
        	$scope.EmailPush = EmailPush;
        	
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
