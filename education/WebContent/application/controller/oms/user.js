define(function (require, exports, module) {

    	//用户管理
    	return function setApp(app) {
            app.controller('OmsUserCtrl', ['$scope'  , 'User' , 'UserRoleRela','Institution', 'Role' ,'Common','Journal', 
                                           function ($scope  , User ,UserRoleRela, Institution,Role ,Common,Journal) {
            	
            	//按条件分页查询数据
            	$scope.pager=User;
            	$scope.User = User;
            	$scope.UserRoleRela = UserRoleRela;
            	$scope.params = {
        			orgCd:$scope.USER_INFO.orgCd,
        			orderBy:'u.CREATE_TIME',
                    order:'desc'
            	};
            	
            	var treeBox , _type;
            	function click(e){
        			if(!$.contains(treeBox[_type ? 0 : 1] , e.target)){
        				treeBox.slideUp();
        				$(document).off('click',click);
        			}
        		}
            	$scope.showTree = function(el , type){
            		treeBox = $('#ztree-box,#ztree-box2');
            		_type = type;
            		$(document).off('click' , click)
            		var pos = $(el).position();
            		var box = treeBox.eq(type ? 0 : 1);
            		var api = type ? $scope.zTreeApi2 : $scope.zTreeApi;
            		
            		box.css({
            			width : $(el).parents('.input-group').width() + 'px',
            			left : pos.left + 'px',
            			top : pos.top + 30 + 'px'
            		}).slideDown();
            		
            		setTimeout(function(){
            			$(document).on('click' , click);
            		},1);
            		
            		if(api){//取消全部选中
            			var nodes = api.getSelectedNodes();
            			nodes.forEach(function(node){
            				api.cancelSelectedNode(node);	
            			});
            		}
            		
            	}
            	
            	$scope.settings = {
            		callback : {
            			onClick : function(e , id , node , level){
            				$scope.$apply(function(){
            					if(_type){
            						$scope.params.orgName = node.name;
            						$scope.params.orgCd = node.orgCd;
            					}else if($scope.user){
            						$scope.user.orgName = node.name;
            						$scope.user.orgCd = node.orgCd;
            						$scope.user.provCode = node.provCode+'';
            					}
            					treeBox.slideUp();
            					$(document).off('click' , click);
            				});
            			}
            		}
                };
            	
            	//查询机构树
            	$scope.data = Institution.query({
                    list: true,
                    params: {
                    	//查询本级及下级机构
                    	id: $scope.USER_INFO.orgCd=='AA0000000000' ? '' : $scope.USER_INFO.orgCd,
                    	parentOrgCd:$scope.USER_INFO.orgCd=='AA0000000000' ? '99999' : ''
                    }
                } , function(result){
                	var node = find(result , 'orgCd' , $scope.params.orgCd);
       				if(node){
       					$scope.params.orgName = node.name;
       				}
                });
            	
              //动态校验登陆名不能重复
                $scope.getParams = function(logName){
                	if(logName){
                		return   {
                            isArrays: true,
                            params: angular.toJson({
                          	  logName: logName
                            })
                        };
                	}
                };
            	
            	//校验
            	$scope.myValid = function(val){
                	if(val){
                		return true;
                	}else{ 
                		return false;
                	}
                };
            	
            	//校验邮箱
            	$scope.validEmail = function(val){
                	if(val){
                		return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val);//不是正确的邮箱
                	}
                	return true;
                };
                //验证
                $scope.valid = function(data){
                	if(data && data.length){
                		return false;
                	}else{
                		return true;
                	}
                };
              
                
            	//校验手机号的
            	$scope.validPhone = function(val){
                	if(val){
                		return /^1[3|4|5|8][0-9]\d{4,8}$/.test(val);//不是完整的11位手机号或者正确的手机号前七位
                	}
                	return true;
                };
            	//校验用户名的
            	$scope.myLogName = function(val){
                	if(val){
                		return /^[a-zA-Z0-9]+$/.test(val);//只能输入英文字母和数字
                	}
                };
            	
                //校验用户名密码输入是否一致的
                $scope.validPassword = function(val){
                	$scope.passWord = val;
                	if(val){
                		return true;
                	}else{
                		return false;
                	}
                };
                //校验用户名密码输入是否一致的
                $scope.validConfirmPassword = function(val){
                	var password = $scope.passWord;
                	if(val == password){
                		return true;
                	}else{
                		return false;
                	}
                };
            	
                //查询用户角色，用于给用户分配角色
            	Role.query({list:true,params:{
            			inroleId:$scope.USER_INFO.inRoleId,
            			userId:$scope.USER_INFO.id
	            	}},function(list){
	            		//1代表系统角色，2代表内置角色
	            		$scope.roles1 = list.filter(function(items){
	       					if(items.roleType =="1"){
	       						return items;
	       					}
	       				});
	       				$scope.roles2 = list.filter(function(items){
	       					if(items.roleType =="2"){
	       						return items;
	       					}
	       				});
            	}); 
            	
            	//跳转到配置用户角色页面，并默认选中已配置的角色
           		$scope.toSetUserRole = function(user){
           			$scope.user = angular.copy(user) || new User;
           			
           			UserRoleRela.query({list : true,params:{userId:user.id}}, function(list){
           				$scope.role_check1 = list.filter(function(items){
           					if(items.roleType =="1"){
           						return items;
           					}
           				}).map(function(item){
       						return item.roleId;
       					});
           				$scope.role_check2 = list.filter(function(items){
           					if(items.roleType =="2"){
           						return items;
           					}
           				}).map(function(item){
       						return item.roleId;
       					});
           				//设置全选框显示不显示
           				if($scope.roles1.length==$scope.role_check1.length){
           					$scope.all1=true;
               			}else{
               				$scope.all1=null;
               			}
           				if($scope.roles2.length==$scope.role_check2.length){
           					$scope.all2=true;
               			}else{
               				$scope.all2=null;
               			}
       				});
           			
           		}
           		
           		//查看详情
           		$scope.toDetail = function(user){
           			$scope.user = angular.copy(user) || new User;
           			UserRoleRela.query({list : true,params:{userId:user.id}}, function(list){
           				$scope.role_check = list.map(function(item){
       						return item;
       					});
       				});
           		}
            	
            	//设置用户角色
                $scope.setRole = function(list ,list2){
                	//把数组转化成字符串
                	UserRoleRela.put(
                			{
                        		roleIds : [].concat(list , list2).join(","),
                        		isMain : 1,
                            	userId:$scope.user.id
                            }, $scope.refresh.bind(null , 'current',true));
                	
                	//设置完角色查角色，为了全选框的显示不显示问题
                	Role.query({list:true,params:{
                			userId:$scope.USER_INFO.id,
                			inroleId:$scope.USER_INFO.inRoleId
    	            	}},function(list){
    	            		//1代表系统角色，2代表内置角色
    	            		$scope.roles1 = list.filter(function(items){
    	       					if(items.roleType =="1"){
    	       						return items;
    	       					}
    	       				});
    	       				$scope.roles2 = list.filter(function(items){
    	       					if(items.roleType =="2"){
    	       						return items;
    	       					}
    	       				});
                	}); 
                	
                };
            	
            	//所属省份
            	$scope.model =Common.query({isArray:true,params:{
            		roleAll:1
            	}});
            	
            	
            	//重置按钮
                $scope.reset = function(params){
                    $scope.params ={
                    		orgCd:$scope.USER_INFO.orgCd,
                			orderBy:'u.CREATE_TIME',
                            order:'desc'};
                };
                //重置新增页面的信息
                $scope.resetUser = function(user){
                    $scope.user ={};
                }
                //初始化修改/新增
           		$scope.toAdd = function(user){
           			$scope.user = new User;
           		}
                
           		//如果修改密码的时候清空重复密码的内容，重新填写后才能保存
           		$scope.clearPassword = function(){
           			$scope.user.confirmPassword = "";
           		}
           		
            	//初始化修改/新增
           		$scope.toUpdate = function(user){
           			$scope.user = angular.copy(user);
           			$scope.user.password="";
           			$scope.user.confirmPassword = "";
           			if(user && $scope.data && user.orgCd){
           				var node = find($scope.data , 'orgCd' , user.orgCd);
           				if(node){
           					$scope.user.orgName = user.orgName = node.name;
           				}
           			}
           		}
            	//修改时反显机构用的
           		function find(arr , key , val){
           			var node;
                	for(var i=0,ii=arr.length; i<ii ; i++){
                		if(arr[i][key] == val){
                			return arr[i];
                		}else if(arr[i].children){
                			node = find(arr[i].children , key , val);
                			if(node) return node;
                		}
                	}
                }
           		
           		//新增用户
                $scope.create = function(user){
                	if($scope.USER_INFO.orgCd =="AA0000000000"){
                		var code = user.provCode;
                	}else{
                		var code = $scope.USER_INFO.provCode;
                	}
                	User.put({
                		logName:user.logName,
                		userName:user.userName,
                		provCode:code,
                		password:user.password,
                		remark:user.remark,
                		email:user.email,
                		state:user.state,
                		phone:user.phone,
                		orgCd:user.orgCd,
                		id:user.id
                	},$scope.refresh.bind(null , 'first',true));
                };
                
                //修改用户
                $scope.modify = function(user){
                	if($scope.USER_INFO.orgCd =="AA0000000000"){
                		var code = user.provCode;
                	}else{
                		var code = $scope.USER_INFO.provCode;
                	}
           			User.save({
                		logName:user.logName,
                		userName:user.userName,
                		provCode:code,
                		password:user.password,
                		remark:user.remark,
                		email:user.email,
                		state:user.state,
                		phone:user.phone,
                		orgCd:user.orgCd,
                		id:user.id
                	},$scope.refresh.bind(null , 'current',true))
                };
                
                //注销用户，password不传的话不能注销和激活。
                $scope.logout = function(user){
                	User.save({
                		password:"",
                		state:"2",
                		id:user.id
                	},$scope.refresh.bind(null , 'current',true))
                };
                
                //激活用户,password不传的话不能注销和激活。
                $scope.focus = function(user){
                	User.save({
                		password:"",
                		state:"1",
                		id:user.id
                	},$scope.refresh.bind(null , 'current',true))
                };
                
                //密码重置
                $scope.resetPWD = function(user){
                	User.save({
                		password:"123456",
                		id:user.id
                	},$scope.refresh.bind(null , 'current',true))
                };
                
                //跳转到删除对话框
                $scope.$validate = "";
           		$scope.toDelete=function(user){
           			$scope.user=angular.copy(user);
           			//判断该用户是否正在使用，如果正在使用则不可以删除
           			Journal.query({isArray:true,params:{
           					id:user.id
                		}},function (list){
           				if(list.length){
                   			 $scope.$validate = '该用户正在使用中...，不能删除';
                   		 }else{
                   			$scope.$validate = "";
                   		 }
                    });
           		}
           		
                
                //删除用户
                $scope.remove = function(user){
                	//id是用户实体类的id，userId是用户角色实体类的id
                	var params={id:user.id , userId:user.id};
                	//删除用户前，先把给该用户赋的角色权限删掉，避免垃圾数据
                	UserRoleRela.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
                	User.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
                };
        }]);
    }

});