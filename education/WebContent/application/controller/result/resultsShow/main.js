define(function (require, exports, module) {
    //公告栏
    return function setApp(app) {
        app.controller('ResultResultsShowMainCtrl', ['$scope' , '$routeParams' , 'AnalysisProcess',
        function ($scope , $routeParams , AnalysisProcess) {
			$scope.tableName = AnalysisProcess.query({
				exportTables :true,
				params :{
					id : $routeParams.id
				}
			},function(list){
				var tableName = list[0].OUTPUTTABLENAME;
				$scope.dataMap = AnalysisProcess.query({
					findTable :true,
					params :{
						tableName : tableName
					}
				});
				
				$scope.columnMap = AnalysisProcess.query({
					findColumns :true,
					params :{
						tableName : tableName
					}
				});
				
			});
			
			
			
        }]);
    }

});