define(function (require, exports, module) {

    //成果查看
    return function setApp(app) {
        app.controller('TechnologicalProcessAchievementPublishCtrl', 
        		['$scope'  , 'Achievement' ,'Demand' , '$filter','NodeAnnex','Processes' ,'Fllow',  function ($scope ,Achievement, Demand,$filter ,NodeAnnex,Processes,Fllow) {
        	//按条件分页查询数据
        	$scope.pager=Fllow;
        	//分页传递的参数
        	$scope.params = {
        		applPer : $scope.USER_INFO.logName
        	};
            $scope.flowTypeList = [{"Name":"--请选择--"},
                                   {"flowType":"inside","Name":"专题、报表取数、统计"},
                                   {"flowType":"outside","Name":"跨机构取数"},
                                   {"flowType":"tech","Name":"技术需求"}
            						]
       		$scope.toFollow = function(demand){
       			$scope.demand = demand;
   				//查询流程历史表
    			 $scope.reqStep = Demand.get({getHisTasks:true,params:{
            		id:demand.id,
            		userId:$scope.USER_INFO.logName
        		}} , function(result){});
       		}
        }]);
    }

});
