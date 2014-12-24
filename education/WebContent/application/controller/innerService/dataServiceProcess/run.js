define(function (require, exports, module) {

    //数据服务流程管理
    return function setApp(app) {
        app.controller('InnerServiceDataServiceProcessRunCtrl', 
        				['$scope' ,'RunProcess','FlowRuleCust', 'DataServiceProcess','DataSet','DataInteLog','TableInteRule','$filter' , 
                 function ($scope ,RunProcess,FlowRuleCust,DataServiceProcess,DataSet,DataInteLog,TableInteRule, $filter) {
            
        	//按条件分页查询数据
        	$scope.pager=RunProcess;
        	$scope.RunProcess = RunProcess;
        	$scope.params = {
            		runPer:$scope.USER_INFO.id
            };
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {
                		runPer:$scope.USER_INFO.id
                };
            };
        	
        	//初始新增/删除
       		$scope.toUpdate = function(runProcess){
       			$scope.runProcess = angular.copy(runProcess) || new RunProcess;
       		};
       		
       		//删除运行流程
            $scope.remove = function(runProcess){
            	var params = {runProcId:runProcess.runProcId};
            	RunProcess.remove({
            			params:angular.toJson(params)
            		},$scope.refresh.bind(null , 'current',true));
            };
            
            //跳转到配置运行流程界面
       		$scope.toSetRunPro = function(){
       			
       			//查询要应用的规则模板
       			FlowRuleCust.query({isArray:true,params:{
       				createPs:$scope.USER_INFO.id
       			}},function (list){
           			$scope.flowRuleCustList =list; 
                   });
       			
       			$scope.runProcess =  new RunProcess;
       			$scope.dataServiceList = [];
       			//查询要应用的数据服务名称
       			DataServiceProcess.query({isArray:true,params:{
       					createPer:$scope.USER_INFO.id
       				}},function (list){
       					list.forEach(function(item){
       						//判断数据服务流程是否已经正在使用，正在使用的就不显示了。
           					if(item.runProcId==null){
           						$scope.dataServiceList.push(item);
           					}
           				});
       			});
       		
       		};
       		
       		
       		//筛选要处理的数据集
            $scope.selectDataSets = function(runProcess){
            	
            	//查询要处理的数据集，分页用的
           		$scope.pager2=DataSet;
           		$scope.params2 = {
       				createUser : $scope.USER_INFO.id,
       				dataDefId  :runProcess.item.dataDefId
           		}
           		//清缓存的，保证每次点击规则模板都能查到相应的数据集
           		setTimeout(function(){
           			$scope.refresh2 && $scope.refresh2('first' , true); 	
           		},1);
            };
       		
       		
       		//选择要处理的数据集
            $scope.slectDataSet = function(dataSet){
            	 $scope.dataSet = dataSet;
            };
            
            //保存数据服务流程
            $scope.runPro = function(runProcess,dataSet){
            	RunProcess.put(
            			{
            				runProcName : runProcess.runProcName,
            				procRuleCustId : runProcess.item.flowRuleCustId,
            				dataServiceDesc : runProcess.dataServiceDesc,
            				sourceDataSet : dataSet.dataSetId,
            				resultDataSetName : runProcess.resultDataSetName,
            				dataServiceId:runProcess.dataServiceId,
            				resultDataSet:runProcess.resultDataSet,
            				runPer : $scope.USER_INFO.id,
            				runState:1
                        }, $scope.refresh.bind(null , 'first',true));
            	
            };
            
            
            //跳转到是否执行运行规则模板对话框
            $scope.toRunFlowRuleCsut = function(runProcess){
            	$scope.runPro=runProcess;
            
            };
            
            //运行规则模板，处理数据集
            $scope.runFlowRuleCsut = function(runProcess){
            	
            	//执行规则模板
            	RunProcess.query({runPro:true,params:{
            			runProcId:runProcess.runProcId
            		}},$scope.refresh.bind(null , 'current',true));
            	
            };
            
            //查看报告
            $scope.queryDetail = function(runProcess){
            	
            	//为了显示运行流程的一些基本信息
            	RunProcess.query({isArray:true,params:{
            			runProcId:runProcess.runProcId
            		}},function(list){
            		list.forEach(function(data){
            			$scope.runProVo = data;
            		});
            	});
            	
            	//显示执行运行流程后的详细信息
            	$scope.cleanList = [];
            	$scope.uniqueList = [];
            	$scope.rebuildList = [];
            	$scope.addressList = [];
            	$scope.postList = [];
            	
            	//查询整合日志表，显示整合后的详细信息
            	RunProcess.query({findReport:true,params:{
            			processId:runProcess.runProcId
            		}},function (list){
            			console.log(list);
            			for(var i = 0 ; i<list.length;i++){
	                		var data = list[i];
	                		var ruleType = data.ruleType;
	                		
	                		if("clean" == ruleType){
	                			$scope.cleanList.push(data);
							}else if ("unique" == ruleType){
								$scope.uniqueList.push(data);
							}else if ("rebuild" == ruleType){
								$scope.rebuildList.push(data);
							}else if ("address" == ruleType){
								$scope.addressList.push(data);
							}else if ("post" == ruleType){
								$scope.postList.push(data);
							}
                	}
                	
                });
            };
            
        }]);
    }

});

