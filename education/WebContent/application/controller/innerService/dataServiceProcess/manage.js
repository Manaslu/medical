define(function (require, exports, module) {

    //数据服务流程管理
    return function setApp(app) {
        app.controller('InnerServiceDataServiceProcessManageCtrl', ['$scope' ,'DataServiceProcess' ,'Customer','FlowRuleCust','RunProcess', '$filter' , 
                            function ($scope ,DataServiceProcess,Customer,FlowRuleCust,RunProcess, $filter) {
            
        	//按条件分页查询数据
        	$scope.pager=DataServiceProcess;
        	$scope.DataServiceProcess = DataServiceProcess;
        	$scope.params = {
                	createPer:$scope.USER_INFO.id
                };
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {
                	createPer:$scope.USER_INFO.id
                };
            };
        	
        	//选择客户
            $scope.toSlectCustomer = function(customer){
            	 $scope.cust = customer;
            };
        	
            
            //查询所有启用的客户
            $scope.pager2=Customer;
       		$scope.params2 = {
       				contacts:$scope.USER_INFO.id,
       				custStats: 1
       		}
       		
       		//重置按钮
            $scope.reset2 = function(params2){
                $scope.params2 = {
                		contacts:$scope.USER_INFO.id,
                		custStats: 1
                };
            };
            
        	//初始新增
       		$scope.toAdd = function(dataServiceProcess){
       			$scope.dataServiceProcess = angular.copy(dataServiceProcess) || new DataServiceProcess;
       			//初始化客户名称用的
       			$scope.cust = {};
       			
       		};
       		
       		//初始删除
       		$scope.$validate = "";
       		$scope.toDelete = function(dataServiceProcess){
       			$scope.dataServiceProcess = angular.copy(dataServiceProcess) || new DataServiceProcess;
       			//初始化客户名称用的
       			$scope.cust = {};
       			//判断该服务流程是否正在使用，如果正在使用则不可以删除
       			RunProcess.query({isArray:true,params:{
       				dataServiceId:dataServiceProcess.dataServiceId
            		}},function (list){
       				if(list.length){
               			 $scope.$validate = '运行流程正在使用该数据服务流程，不能删除。';
               		 }else{
               			$scope.$validate = "";
               		 }
                });
       			
       		};
       		
       		//初始化修改
       		$scope.toUpdate = function(dataServiceProcess){
       			
       			$scope.dataServiceProcess = angular.copy(dataServiceProcess);
       			//默认显示客户名称
       			Customer.query({isArray:true,params:{
                	custMamId:dataServiceProcess.custMamId
                }},function (list){
                	list.forEach(function(item){
                		$scope.cust = item;
       				});
                });
       		};
       		
            
       		//新增数据服务流程管理数据
            $scope.create = function(dataServiceProcess,customer){
            	DataServiceProcess.put(
            			{
            				flowRuleCustId : dataServiceProcess.flowRuleCustId,
            				runProcId : dataServiceProcess.runProcId,
            				dataServiceDesc : dataServiceProcess.dataServiceDesc,
            				dataServiceName : dataServiceProcess.dataServiceName,
            				createPer:$scope.USER_INFO.id,
            				custMamId : customer.custMamId,
            				id:	$scope.USER_INFO.id,
            				serviceState:'UNEXEC'
                        }, $scope.refresh.bind(null , 'first',true));
            };
            
            //修改数据服务流程管理数据
            $scope.modify = function(dataServiceProcess,customer){
            	DataServiceProcess.save({
            		flowRuleCustId : dataServiceProcess.flowRuleCustId,
    				runProcId : dataServiceProcess.runProcId,
            		dataServiceDesc : dataServiceProcess.dataServiceDesc,
    				dataServiceName : dataServiceProcess.dataServiceName,
    				createPer:$scope.USER_INFO.id,
    				custMamId : customer.custMamId,
    				id:	$scope.USER_INFO.id,
    				dataServiceId:dataServiceProcess.dataServiceId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
            //删除数据服务流程管理数据
            $scope.remove = function(dataServiceProcess){
            	var params={dataServiceId:dataServiceProcess.dataServiceId};
            	DataServiceProcess.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            };
       		
        }]);
    }

});

