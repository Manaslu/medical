define(function (require, exports, module) {

    //客户管理
    return function setApp(app) {
        app.controller('InnerServiceClientCtrl', ['$scope' ,'Customer' , 'DataServiceProcess','$filter' , 
                       function ($scope ,Customer,DataServiceProcess, $filter) {
            
        	//按条件分页查询数据
        	$scope.pager=Customer;
        	$scope.Customer = Customer;
        	$scope.params={
        			contacts:$scope.USER_INFO.id
        	}
        	
        	//校验
        	$scope.validName = function(val){
            	if(val){
            		return true;
            	}
            };
            
            //校验邮箱
        	$scope.validEmail = function(val){
            	if(val){
            		//不是正确的邮箱
            		return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val);
            	}
            	return true;
            };
        	
            //校验邮编
        	$scope.validPost = function(val){
            	if(val){
            		return /^[0-9]+$/.test(val);//数字
            	}
            	return true;
            };
            
            //校验手机号的
        	$scope.validPhone = function(val){
            	if(val){
            		//不是完整的11位手机号或者正确的手机号前七位
            		return /^1[3|4|5|8][0-9]\d{8}$/.test(val);
            	}
            	return true;
            };
            
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {
                		contacts:$scope.USER_INFO.id
                };
            };
            //重置新增页面
            $scope.resetCustomer = function(customer){
                $scope.customer = {};
            };
        	
        	 //初始化修改/新增/删除/查看详情
       		$scope.toUpdate = function(customer){
       			$scope.customer = angular.copy(customer) || new Customer;
       		};
       		
       		$scope.$validate = "";
       		$scope.toDelete = function(customer){
       			$scope.customer = angular.copy(customer);
       			//判断该客户是否正在使用，如果正在使用则不可以删除
       			DataServiceProcess.query({isArray:true,params:{
       				custMamId:customer.custMamId
            		}},function (list){
       				if(list.length){
               			 $scope.$validate = '该客户正在受理业务中...，不能删除';
               		 }else{
               			$scope.$validate = "";
               		 }
                });
       		};
       		
       	
        	
       		//新增客户
            $scope.create = function(customer){
            	Customer.put({
            		custName:customer.custName,
            		custType:customer.custType,
            		custAddr:customer.custAddr,
            		contacts:$scope.USER_INFO.id,
            		tel:customer.tel,
            		post:customer.post,
            		eMail:customer.eMail,
            		custStats:1,
            		custMamId:customer.custMamId
            	},$scope.refresh.bind(null , 'first',true));
            };
       		
            //修改客户
            $scope.modify = function(customer){
            	Customer.save({
            		custName:customer.custName,
            		custType:customer.custType,
            		custAddr:customer.custAddr,
            		contacts:$scope.USER_INFO.id,
            		tel:customer.tel,
            		post:customer.post,
            		eMail:customer.eMail,
            		custStats:customer.custStats,
            		custMamId:customer.custMamId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
          //注销用户
            $scope.logout = function(customer){
            	Customer.save({
            		custStats:"2",
            		custMamId:customer.custMamId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
            //激活用户
            $scope.focus = function(customer){
            	Customer.save({
            		custStats:"1",
            		custMamId:customer.custMamId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
            //删除客户
            $scope.remove = function(customer){
            	var params={custMamId:customer.custMamId};
            	Customer.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            };
            
        }]);
    }

});

