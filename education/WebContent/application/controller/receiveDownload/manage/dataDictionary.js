define(function (require, exports, module) {
    // 数据字典管理
     function setApp(app) {
        app.controller('ReceiveDownloadManageDataDictionaryCtrl', ['$scope' ,'DataDic','Common', function ($scope,DataDic,Common) {
            $scope.pager = DataDic;
            $scope.queryColumn=function(table){
            	$scope.tableName=table.tableName;
            	$scope.columnList=DataDic.query({queryDic:true , params : angular.toJson({
            		tableName:table.tableName
            	})});
            }
            $scope.busiList=Common.query({code:true});
            
        }]);
    }
    return setApp;

});