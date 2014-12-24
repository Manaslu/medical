define(function (require, exports, module) {

    //数据同步审核
    return function setApp(app) {
        app.controller('DataAnalyseSynchronizationExamineCtrl', ['$scope' ,'SynchronizationApply','SyncLog','SourceTables', function ($scope,SynchronizationApply,SyncLog,SourceTables) {


        	$scope.pager= SynchronizationApply;
        	
            $scope.previewAppl =function(indexObj){ //查看日志
             	$scope.viewAppl = angular.copy(indexObj);
             };
             
             $scope.save = function(key,applstatus,id) { //审批
                 	var audit = {};
                 	audit.approvalStats = applstatus;
                 	audit.approvalOpinion =  key.approvalOpinion;
                 	audit.id =  id;
                 	 
                     SynchronizationApply.save(audit,function(){
                     	$scope.refresh('current',true);
                     	$scope.key.approvalOpinion='';
                        
                     });
             };
             
             
             $scope.params1 = {};
             $scope.viewHistory = function(item) { //历史
            	 $scope.pager1 =  SyncLog;
            	 $scope.params1.tableNameen = item.tableNameen;
            	 $scope.refresh1 && $scope.refresh1('first' , true);
             };
        	
        }]);
    }

});