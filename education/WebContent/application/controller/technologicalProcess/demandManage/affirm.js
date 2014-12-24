define(function (require, exports, module) {

    //需求确认
    return function setApp(app) {
        app.controller('TechnologicalProcessDemandManageAffirmCtrl',         		
        	['$scope'  , 'Demand'  , '$filter' ,'NodeAnnex',  function ($scope ,Demand, $filter,NodeAnnex) {
        	//按条件分页查询数据
        	$scope.pager=Demand;
        	//分页传递的参数
        	//技术确认角色
        	//Z传递5 K传递d J传递g
        	$scope.params = {
        		//approState : 5,
        		fcode : 'confirm',
        		assignee : $scope.USER_INFO.logName
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
       		$scope.toDetail = function(demand){
       			$scope.demand = demand || new Demand({requType : 1});
    		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}}); 
       		}
            $scope.flowTypeList = [{"Name":"--请选择--"},
                                   {"flowType":"inside","Name":"专题、报表取数、统计"},
                                   {"flowType":"outside","Name":"跨机构取数"},
                                   {"flowType":"tech","Name":"技术需求"}
            						]
        	//提交操作
       		$scope.toSubmit = function(demand){
       			if(demand.approState == '5'){
           			Demand.query({doNextTask:true,params:{
                		id:demand.id,
                		opPer:$scope.USER_INFO.logName,
                		remarks:demand.remarks,
                		taskDefineCode:'techConfirm',
                		dataPassType:demand.dataPassType,
                		nodeSeq:'5'
            		}} , function(){
            			$scope.refresh('first',true);
           			});
       			}else if(demand.approState == 'd'){
           			Demand.query({doNextTask:true,params:{
                		id:demand.id,
                		opPer:$scope.USER_INFO.logName,
                		remarks:demand.remarks,
                		taskDefineCode:'groupConfirm',
                		dataPassType:demand.dataPassType,
                		nodeSeq:'6'
            		}} , function(){
            			$scope.refresh('first',true);
           			});
       			}else if(demand.approState == 'g'){
           			Demand.query({doNextTask:true,params:{
                		id:demand.id,
                		opPer:$scope.USER_INFO.logName,
                		remarks:demand.remarks,
                		taskDefineCode:'techConfirm',
                		dataPassType:demand.dataPassType,
                		nodeSeq:'3'
            		}} , function(){
            			$scope.refresh('first',true);
           			});
       			}
       		}
            //下载方法
            $scope.download = function(file) { 
              var url=  '/nodeAnnex.shtml?method=download&params='+ angular.toJson(file);//                    
              var link = $('#downlink')[0];
              link.href = url;
              link.click();
           };
        }]);
    }

});