define(function (require, exports, module) {

    //运行监控
    return function setApp(app) {
        app.controller('OmsMoveCtrl', ['$scope' ,'UserMonitor', function ($scope,UserMonitor) {
            $scope.pager=UserMonitor;
            $scope.params={
        			orderBy:'LOG_TIME',
        			order:'DESC'
        	}
        }]);
    }

});