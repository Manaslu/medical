	define(function (require, exports, module) {

    //角色管理
    return function setApp(app) {
        app.controller('OmsRoleCtrl', ['$scope' , 'Role' ,'RolePermissions' ,'Menu','UserRoleRela' , '$filter' , 
            function ($scope , Role ,RolePermissions, Menu,UserRoleRela, $filter) {
            
        	//按条件分页查询数据
        	$scope.pager=Role;
        	$scope.Role = Role;
        	$scope.RolePermissions = RolePermissions;
        	
        	$scope.params={
        			roleType : '1',
        			inroleId:$scope.USER_INFO.inRoleId,
        			userId:$scope.USER_INFO.id
            	}
        	
        	//校验输入框的
        	$scope.myValid = function(val){
            	if(val){
            		return true;
            	}else{
            		return false;
            	}
            };
        	
            //验证
            $scope.valid = function(data){
            	if(data && data.length){
            		return false;
            	}
            	return true;
            };
            
            $scope.getParams = function(roleName){
            	if(roleName){
            		return   {
                        isArrays: true,
                        params: {
                        	roleName: roleName,
                        	inRoleId:1
                        }
                    };
            	}
            };
            
          //配置权限
        	$scope.settings = {
                check: {
                    enable: true
                },
                callback:{
                	beforeCheck:function(treeId,treeNode){
                		/*console.log("treeNode:",treeNode);
                		if(treeNode.action==='index'){                			
                			$scope.zTreeApi.getNodesByParam("action","index").forEach(function(node){
                				$scope.zTreeApi.checkNode(node,false);
                			});
                		}*/
                		return true;
                	}
                }
            };
        	//查看配置权限
        	$scope.settings2 = {
                check: {
                }
            };
        	
            //重置按钮
            $scope.reset = function(params){
                $scope.params = {
                		roleType : '1',
                		inroleId:$scope.USER_INFO.inRoleId,
            			userId:$scope.USER_INFO.id
                };
            };
            
            //新增页面点击取消时，清除新增页面的值
            $scope.resets = function(){
                $scope.role =  new Role;
            };
            
            //初始化修改/新增
       		$scope.toUpdate = function(role){
       			$scope.role = angular.copy(role) || new Role;
       		};
       		
            //跳转到权限配置页面
       		$scope.toConfig = function(role,detail){
       			//权限配置的下拉列表
                $scope.data = Menu.query({
                		list : true,
                		params: {
                			menuId:role.roleId,
                			dealId:$scope.USER_INFO.inRoleId,
                			userId:$scope.USER_INFO.id
            			}
                } , function(){
                	setTimeout(function(){
                		$scope.seeDetail = detail;
               			$scope.role = role;
               			var api = $scope.zTreeApi;
               			if(api){
               				//清除所有选中的，然后再设置默认的
               				api.checkAllNodes(false);
//               			api.expandAll(true);//打开所有节点
               				//查询角色权限表，然后把以前设置的默认选中
               				RolePermissions.query({list : true,params:{roleId:role.roleId}} , function(list){
               					list.map(function(item){
               						return item.menuId;
               					}).forEach(function(id){
                   					var nodes = api.getNodesByParam("menuId", id, null);
                   					if(nodes.length){
                   						api.checkNode(nodes[0]);
                   					}	
                   					
                   				});
               				});
               			}
                	} , 1);
                });
       			
       		};
       		
       		//设置权限
            $scope.setAuthority = function(){
            	var api = $scope.zTreeApi,
            		role = $scope.role;
            	if(api){
            		var nodes = api.getCheckedNodes(true),
            		menuIds = nodes.map(function(node){
            				return node.menuId;
            			});
            		
            		var s= menuIds.join(",");
            		RolePermissions.put(
            			{
                    		menuIds : s,
                        	roleId:role.roleId
                        }, $scope.refresh.bind(null , 'current',true));
            	}
            };
            
            //查看配置权限
       		$scope.toConfig1 = function(role,detail){
       			//权限配置的下拉列表
                $scope.data2 = Menu.query({
                		list : true,
                		params: {
                			menuId:role.roleId,
                			dealId:'2',
                			userId:$scope.USER_INFO.id
            			}
                } , function(){
                	setTimeout(function(){
                		$scope.seeDetail = detail;
               			$scope.role = role;
               			var api = $scope.zTreeApi2;
               			if(api){
               				//清除所有选中的，然后再设置默认的
               				api.checkAllNodes(false);
//               			api.expandAll(true);//打开所有节点
               				//查询角色权限表，然后把以前设置的默认选中
               				RolePermissions.query({list : true,params:{roleId:role.roleId}} , function(list){
               					list.map(function(item){
               						return item.menuId;
               					}).forEach(function(id){
                   					var nodes = api.getNodesByParam("menuId", id, null);
                   					if(nodes.length){
                   						api.checkNode(nodes[0]);
                   					}	
                   					
                   				});
               				});
               			}
                	} , 1);
                });
       			
       		};
       		
       		
       		
       		//查看详情
       		$scope.toDetail = function(role){
       			$scope.role = angular.copy(role);
       		}
       		
            //修改角色
            $scope.modify = function(role){
            	Role.save({
            		roleName:role.roleName,
            		roleDefault:role.roleDefault,
            		roleDesc:role.roleDesc,
            		state:role.state,
            		inRoleId:role.inRoleId,
            		roleId:role.roleId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
            
            //新增角色
            $scope.create = function(role){
            	role.$put($scope.refresh.bind(null , 'first',true));
            };
            
            //跳转到删除对话框
            $scope.$validate = "";
       		$scope.toDelete=function(role){
       			$scope.role=role;
       			//判断该角色是否被莫个用户使用，如使用不能删除
       			UserRoleRela.query({isArray:true,params:{
       					roleId:role.roleId
            		}},function (list){
       				if(list.length){
               			 $scope.$validate = '用户正在使用该角色...，不能删除';
               		 }else{
               			$scope.$validate = "";
               		 }
                });
       		}
            
            //删除角色
            $scope.remove = function(role){
            	var params={roleId: parseInt(role.roleId)};
            	//删除角色前，先把给该角色赋的菜单权限和用户赋的角色删掉，避免垃圾数据
            	UserRoleRela.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            	RolePermissions.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            	Role.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            };
        }]);
    }

});

