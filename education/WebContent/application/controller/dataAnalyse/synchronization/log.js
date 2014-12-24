define(function (require, exports, module) {

    //指标维护
    return function setApp(app) {
        app.controller('DataAnalyseSynchronizationLogCtrl', ['$scope' ,'SyncLog', function ($scope,SyncLog) {
        	$scope.pager=SyncLog;
        }]);
    }

});