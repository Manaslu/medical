define(function (require, exports, module) {

    //需求审批
    return function setApp(app) {
        app.controller('TechnologicalProcessDemandManageEapCtrl',            		
        		['$scope'  , 'Demand'  , '$filter','NodeAnnex' ,  function ($scope ,Demand, $filter,NodeAnnex) {
        			
        		var dateFormat = $filter('date');
            	//按条件分页查询数据
            	$scope.pager=Demand;
            	//分页传递的参数
            	//这里判断该用户是业务领导。。。。技术领导
            	//业务领导肯定传递2过后台
            	//Z技术领导  4  K技术领导9，c  J技术领导f
            	$scope.params = {
            		//approState : '2',
            		fcode : 'check',		
            		assignee : $scope.USER_INFO.logName
            	};
            	
            	$scope.reqStepType = {
            		"insideApply:1:18404" : {//专题、报表取数、统计
            			"需求阶段" : {
            				"1" : "业务需求申请人",
                			"2" : "业务领导审批",
                			"3" : "技术联系人",
                			"4" : "技术领导审批分配",
                			"5" : "技术员需求确认"  //到技术员确认完了，前面都显示为蓝的，中间显示红的
            			},
            			"执行阶段" : {
            				"6" : "成果确认"       //到全部确认完了，全部都是蓝的，最后那个是红的
            			},
            			"成果阶段" : {}            //全部确认完了，全部蓝的
            		},
            		"outsideApply:1:18408" : {//跨机构取数
            			"需求阶段" :{
            				"1" : "省技术员发起申请",
            				"2" : "省内领导审批",
            				"3" : "省内专门联系人",
            				"4" : "集团技术联系人",
            				"5" : "集团领导审批并分配任务",
            				"6" : "需求确认"
            			},
            			"执行阶段" : {
            				"7" : "成果确认"
            			},
            			"成果阶段" : {}
            		},
            		"techApply:1:18412" : {//技术需求
            			"需求阶段" : {
            				"1" : "技术人员发起申请",
            				"2" : "技术领导审批并分配任务",
            				"3" : "技术员需求确认"
            			},
            			"执行阶段" : {
            				"4" : "成果确认"
            			},
            			"成果阶段" : {}
            		}
            	};
            	
           		$scope.toDetail = function(demand){
           			$scope.demand = demand;
       				//查询流程历史表
        			 $scope.reqStep = Demand.get({getHisTasks:true,params:{
                		id:demand.id,
                		userId:$scope.USER_INFO.logName
            		}} , function(result){
            		});
           		}
           		//跳转到提交审核页面
           		$scope.toGoCheck = function(demand){
           			$scope.demand = demand;
           			//做判断，若状态为2则为业务领导审批,若状态为4则为技术领导审批
           			if(demand.approState == '2'){
           				//这里要调用方法，不用查询下一个，但是要查询需求管理表中的techContact字段
           				$scope.demand = Demand.get({getTectContact:true,params:{id:demand.id}});
           			}else if(demand.approState == '4' || demand.approState == 'c' || demand.approState == 'f'){
           				$scope.asUserList = Demand.query({getNextUser:true,params:{id : $scope.USER_INFO.id,inRoleId : 'JSXUQJ'}},function(list){
           					if(list.length == 1){
           						$scope.demand.userName = list[0].logName;
           					}
           				});
           			}else if(demand.approState == '9'){
           				$scope.demand = Demand.get({getProvContact:true,params:{id:demand.id}});
           			}
           		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}}); 
           		}
                $scope.flowTypeList = [{"Name":"--请选择--"},
                                       {"flowType":"inside","Name":"专题、报表取数、统计"},
                                       {"flowType":"outside","Name":"跨机构取数"},
                                       {"flowType":"tech","Name":"技术需求"}
                						]
            	//同意操作
           		$scope.toSubmit =function(demand){
           			if(demand.approState == '2'){
	           			Demand.query({doNextTask:true,params:{
	                		id:demand.id,
	                		opPer:$scope.USER_INFO.logName,
	                		remarks:demand.remarks,
	                		nextUserId:demand.techContacts,//传递从需求管理表中查询出的技术联系人
	                		taskDefineCode:'busCheck',
	                		nodeSeq:'2'
	            		}} , function(){
	            			$scope.refresh('first',true);
	           			});
           			}else if(demand.approState == '4'){
	           			Demand.query({doNextTask:true,params:{
	                		id:demand.id,
	                		opPer:$scope.USER_INFO.logName,
	                		remarks:demand.remarks,
	                		nextUserId:demand.userName,
	                		taskDefineCode:'techCheck',
	                		nodeSeq:'4'
	            		}} , function(){
	            			$scope.refresh('first',true);
	           			});
           			}else if(demand.approState == '9'){
	           			Demand.query({doNextTask:true,params:{
	                		id:demand.id,
	                		opPer:$scope.USER_INFO.logName,
	                		remarks:demand.remarks,
	                		nextUserId:demand.provContacts,
	                		taskDefineCode:'proTechCheck',
	                		nodeSeq:'2'
	            		}} , function(){
	            			$scope.refresh('first',true);
	           			});
           			}else if(demand.approState == 'c'){
	           			Demand.query({doNextTask:true,params:{
	                		id:demand.id,
	                		opPer:$scope.USER_INFO.logName,
	                		remarks:demand.remarks,
	                		nextUserId:demand.userName,
	                		taskDefineCode:'groupCheck',
	                		nodeSeq:'5'
	            		}} , function(){
	            			$scope.refresh('first',true);
	           			});
           			}else if(demand.approState == 'f'){
	           			Demand.query({doNextTask:true,params:{
	                		id:demand.id,
	                		opPer:$scope.USER_INFO.logName,
	                		remarks:demand.remarks,
	                		nextUserId:demand.userName,
	                		taskDefineCode:'techCheck',
	                		nodeSeq:'2'
	            		}} , function(){
	            			$scope.refresh('first',true);
	           			});
           			}
           		}
           		//驳回操作
           		$scope.toReject =function(demand){
       				Demand.query({doRejectTask:true,params:{
	                		id:demand.id,
	                		remarks:demand.remarks,
	                		per:$scope.USER_INFO.logName
           				}}, function(){
           					$scope.refresh('first',true);
       				});
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