define(function (require, exports, module) {

    //需求分配
    return function setApp(app) {
        app.controller('TechnologicalProcessDemandManageListCtrl',
        		['$scope'  , 'Demand'  , '$filter','NodeAnnex' ,  function ($scope ,Demand, $filter,NodeAnnex) {
                	//按条件分页查询数据
                	$scope.pager=Demand;
                	//分页传递的参数
                	//技术联系人Z传递3 K传递a,b
                	$scope.params = {
                		//approState : 3,
                		fcode : 'list',
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
                	
                    //跳转到终止流程对话框
               		$scope.toDelete=function(demand){
               			$scope.demand=demand;
               		}
                    //终止流程
                    $scope.remove = function(demand){
                    	var params={id:demand.id};
                    	Demand.remove({
                    			params:angular.toJson(params)
                    		},$scope.refresh.bind(null , 'current',true))
                    };
               		//跳转到提交审核页面
               		$scope.toGoCheck = function(demand){
               			$scope.demand = demand;
               			if(demand.approState == '3' || demand.approState == 'b'){
                       		$scope.asUserList = Demand.query({getNextUser:true,params:{id : $scope.USER_INFO.id,inRoleId : 'JSLDSP'}},function(list){
               					if(list.length == 1){
               						$scope.demand.userName = list[0].logName;
               					}
                       		});
               			}else if(demand.approState == 'a'){
               				$scope.demand = Demand.get({getGroupTechContact:true,params:{id:demand.id}});
               			}
               		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}}); 
               		}
                    $scope.flowTypeList = [{"Name":"--请选择--"},
                                           {"flowType":"inside","Name":"专题、报表取数、统计"},
                                           {"flowType":"outside","Name":"跨机构取数"},
                                           {"flowType":"tech","Name":"技术需求"}
                    						]
               		//回退到初始发起人
               		$scope.toReject = function(demand){
               			Demand.query({rejectTechContactTask:true,params:{
           					id:demand.id,
           					opPer:$scope.USER_INFO.logName,
           					remarks:demand.remarks
           				}}, function(){
                    		$scope.refresh('first',true);
               			});               				
               		}
                	//提交操作
               		$scope.toSubmit = function(demand){
               			if(demand.approState == '3'){
                   			Demand.query({doNextTask:true,params:{
                        		id:demand.id,
                        		opPer:$scope.USER_INFO.logName,
                        		remarks:demand.remarks,
                        		message:demand.message,
                        		nextUserId:demand.userName,
    	                		taskDefineCode:'techContact',
    	                		nodeSeq:'3'
                    		}} , function(){
                    			$scope.refresh('first',true);
                   			});               				
               			}else if(demand.approState == 'a'){
    	           			Demand.query({doNextTask:true,params:{
    	                		id:demand.id,
    	                		opPer:$scope.USER_INFO.logName,
    	                		remarks:demand.remarks,
    	                		message:demand.message,
    	                		nextUserId:demand.groupTechContacts,
    	                		taskDefineCode:'proContact',
    	                		nodeSeq:'3'
    	            		}} , function(){
    	            			$scope.refresh('first',true);
    	           			});
               			}else if(demand.approState == 'b'){
    	           			Demand.query({doNextTask:true,params:{
    	                		id:demand.id,
    	                		opPer:$scope.USER_INFO.logName,
    	                		remarks:demand.remarks,
    	                		message:demand.message,
    	                		nextUserId:demand.userName,
    	                		taskDefineCode:'groupContact',
    	                		nodeSeq:'4'
    	            		}} , function(){
    	            			$scope.refresh('first',true);
    	           			});
               			}
               		}
                    //终止流程
                    $scope.doTerminateTask = function(demand){
                    	Demand.query({doTerminateTask:true,params:{
                    		id:demand.id,
                			opPer:$scope.USER_INFO.logName
                		}} , function(){
                			$scope.refresh('first',true);
                		});
                    };
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