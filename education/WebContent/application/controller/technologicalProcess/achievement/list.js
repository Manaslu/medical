define(function (require, exports, module) {

    //成果查看
    return function setApp(app) {
        app.controller('TechnologicalProcessAchievementListCtrl', 
        		['$scope'  , 'Achievement' ,'Demand' , '$filter','NodeAnnex','Processes' ,  function ($scope ,Achievement, Demand,$filter ,NodeAnnex,Processes) {
        	//按条件分页查询数据
        	$scope.pager=Achievement;
        	//分页传递的参数
        	$scope.params = {
        		approState : '7',
        		applPer : $scope.USER_INFO.logName
        	};
        	
       		$scope.toFollow = function(demand){
       			$scope.demand = demand;
   				//查询流程历史表
    			 $scope.reqStep = Demand.get({getHisTasks:true,params:{
            		id:demand.id,
            		userId:$scope.USER_INFO.logName
        		}} , function(result){});
       		}
        	
       		//查看详情
       		$scope.toDetail = function(achievement){
       			$scope.achievement = achievement || new aemand({requType : 1});
    		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: achievement.id}}); 
       		}
            $scope.flowTypeList = [{"Name":"--请选择--"},
                                   {"flowType":"inside","Name":"专题、报表取数、统计"},
                                   {"flowType":"outside","Name":"跨机构取数"},
                                   {"flowType":"tech","Name":"技术需求"}
            						]
            //下载方法
            $scope.download = function(file) { 
              var url=  '/nodeAnnex.shtml?method=download&params='+ angular.toJson(file);//                    
              var link = $('#downlink')[0];
              link.href = url;
              link.click();
           };
      		//成果确认按钮
      		$scope.getFruitInfo=function(achievement){
      			$scope.getFruitInfo.achievement = achievement;
      			Processes.query({
      				isArray:true,params:{id:achievement.id
  				}},function (list){
  					$scope.AllFruitList = list;
      			});
      		}
            $scope.getNodeAnnex = function(fruit){
	   		 	$scope.fruitFileList=NodeAnnex.query({isArray:true,params:{ fruitCode : fruit.fruitCode}}); 
           }
        }]);
    }
});