define(function (require, exports, module) {

    //用户操作日志
    return function setApp(app) {
    	app.controller('OmsJournalCtrl', ['$scope' , 'Journal', function ($scope ,Journal) {
            
    		//按条件分页查询数据
        	$scope.pager=Journal;
        	$scope.params={
        			orderBy:'LOG_TIME',
        			order:'DESC'
        	}
        	
        }]);
    }

});