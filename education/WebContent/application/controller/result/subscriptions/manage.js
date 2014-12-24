define(function (require, exports, module) {

    //订阅通知--订阅管理
    return function setApp(app) {
    	
        app.controller('ResultSubscriptionsManageCtrl',['$scope' ,'Subscribe' ,'Theme','ThemeRep','User','Institution','Empower','EmailPush', '$filter' , function ($scope ,Subscribe ,Theme,ThemeRep,User,Institution,Empower,EmailPush,$filter) {
            
        	var dateFormat = $filter('date');
        	
        	//按条件分页查询订阅管理信息数据
        	$scope.pager=Subscribe;
        	$scope.Subscribe = Subscribe;
        	
        	$scope.params = {};
        	
        	//初始查询主题名称
        	ThemeRep.query({isArray:true,params:{}},function (list){
        		$scope.themeNameList =list; 
            });
        	
        	Theme.query({isArray:true,params:{}},function (list){
        		$scope.ThemeList =list; 
           });
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {};
                $scope.params2 = {byOrgCd:1};
            };
                
            
        	//初始化修改/删除/查看详情
       		$scope.toUpdate = function(subscribe){
       			$scope.params = {};
       			$scope.params2 = {byOrgCd:1};//初始化查询参数
       			$scope.subscribe = angular.copy(subscribe) || new Subscribe;
       			ThemeRep.query({isArray:true,params:{anaThemeId:subscribe.anaThemeId}},function (list){
            		$scope.themeSetList =list; 
       			});
       			if(subscribe){
        			$scope.subscribe.staerDate = dateFormat(subscribe.staerDate , 'yyyy-MM-dd');
        			$scope.subscribe.endDate = dateFormat(subscribe.endDate , 'yyyy-MM-dd');
        		}
       		};
        	
       	    //初始化新增为创建人赋值
       		$scope.toAdd = function(){
       			$scope.subscribe = new Subscribe;
       			$scope.subscribe.createUser=$scope.USER_INFO.id;
       		};
       		
       	    //根据主题库的anaThemeId，查询themeSetList
            $scope.selectTheme = function(subscribe){
            	ThemeRep.query({isArray:true,params:{anaThemeId:subscribe.anaThemeId}},function (list){
            		$scope.themeSetList =list; 
               });
            };
       		
       		//新增订阅信息
            $scope.create = function(subscribe){
            	//console.log(subscribe);//打印日志
            	subscribe.$put($scope.refresh.bind(null , 'first',true));
            };
       		
            //修改订阅管理信息
            $scope.modify = function(subscribe){
            	Subscribe.save({
            		subscribeName:subscribe.subscribeName,
            		subscribeMethod:subscribe.subscribeMethod,
            		pushMethod:subscribe.pushMethod,
            		subscribeDesc:subscribe.subscribeDesc,
            		staerDate:subscribe.staerDate,
            		endDate:subscribe.endDate,
            		subscribeId:subscribe.subscribeId,
            		Id:subscribe.id,
            		anaThemeId:subscribe.anaThemeId,
            		anaThemeRepId:subscribe.anaThemeRepId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
           //修改订阅管理发布状态
            $scope.modifyStats = function(subscribe){
            	var stats=subscribe.subscribeStats;
            	if(stats==0){
            		stats=1;
            	}else{
            		stats=2;
            	}
            	Subscribe.save({
            		subscribeStats:stats,
            		subscribeId:subscribe.subscribeId
            	},$scope.refresh.bind(null , 'current',true));
            };
            
            //邮件推送
            $scope.emailpush=function(subscribe){
            	console.log(subscribe);
            	EmailPush.put({
            		subscribeId:subscribe.subscribeId,
            		subsType:subscribe.pushMethod,
            		subsName:subscribe.subscribeName,
            		reportName:subscribe.repName
            	},$scope.refresh.bind(null , 'current',true));
            };
            
            var link = $('#toReport')[0];
            //邮件预览
            $scope.emaillook=function(subscribe){
            	//templates/result/subscriptions/preview.html
            	//report.html?id=1
            	link.href = 'report.shtml?method=report';
    			link.click();
            };
            
           
            //删除订阅管理信息
            $scope.remove = function(subscribe){
            	//var params={subscribeId:subscribe.subscribeId,Id:subscribe.Id};
            	//Subscribe.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            	Subscribe.save({
            		subscribeId:subscribe.subscribeId,
            		isDel:0
            	},$scope.refresh.bind(null , 'current',true));
            	
            };
            
            //跳转到权限配置页面
       		$scope.toConfig = function(subscribe){
       		    //按条件分页查询客户数据
            	$scope.pager2=User;
            	$scope.User = User;
             	$scope.subscribe = angular.copy(subscribe) || new Subscribe;
            	
            	 //查询机构树
            	$scope.data = Institution.query({
                    list: true,
                    params: {
                    	//id: $scope.USER_INFO.orgCd=='AA0000000000' ? '' : $scope.USER_INFO.orgCd,
                    	parentOrgCd:$scope.USER_INFO.orgCd=='AA0000000000' ? '99999' : ''
                    }
                } , function(result){
                	var node = find(result , 'orgCd' , $scope.params.orgCd);
       				if(node){
       					$scope.params.orgName = node.name;
       				}
                });
            	
            	$scope.params2 = {byOrgCd:1};
            	
            	$scope.showTree = function(el , obj){
                	$scope.showTree._obj = obj;
            		treeBox = $('#ztree-box2');
            		$(document).off('click' , click)
            		var pos = $(el).position();
            		
            		treeBox.css({//定位选择器
            			width : $(el).parents('.input-group').width() + 'px',
            			left : pos.left + 'px',
            			top : pos.top + 30 + 'px'
            		}).slideDown();//打开选择器
            		
            		setTimeout(function(){
            			$(document).on('click' , click);//绑定事件，点击树之外的区域，关闭选择器
            		},1);
            		
            	}

                $scope.settings = {
                		callback : {
                			onClick : function(e , id , node , level){
                				$scope.$apply(function(){
                					var params = $scope.showTree._obj;
                					if(params){
                						params.orgName = node.name;
                						params.orgCd = node.orgCd;
                					}
                					treeBox.slideUp();//关闭选择器
                					$(document).off('click' , click);//取消事件
                				});
                			}
                		}
                    };
            	
   				//查询权限表，然后把以前设置的默认选中
            	Empower.query({list:true,params:{subscribeId:subscribe.subscribeId,subscribeType:1}},function (list){
   					$scope.subscribe.selected = list.map(function(item){
   						return item.userId;
   					});
   				});
       		};
	     	
       		//保存权限
       		$scope.saveEmpower=function(empower){
       			var uid=$scope.subscribe.selected.join(',');
       			//var users=[];
       			//console.log(pager2);
       			//$scope.subscribe.selected.forEach(function(userId){
       			//	$scope.limitArr2.forEach(function(item){
       					
       			//		if(item.id===userId){
       			//			users.push(item);
       			//		}
       			//	});
       			//});
       			
       			//var rightUser=users.map(function(user){
       			//	return {
       			//		userId:user.id,
       			//		userName:user.userName,
       			//		orgCd:user.orgCd
       			//	};
       			//});
       			Empower.put(
            			{
            				create:true,
            				users : angular.toJson(""),
            				
            				userId : uid,
                    		subscribeId:$scope.subscribe.subscribeId
                    		//userName :$scope.USER_INFO.userName,
                    		//departmentId : $scope.USER_INFO.orgCd
                        }, $scope.refresh.bind(null , 'first',true));
       		};
            
	       	var treeBox;
	     	function click(e){
	 			if(!$.contains(treeBox[0] , e.target)){//判断是否选择器内区域
	 				treeBox.slideUp();
	 				$(document).off('click',click);
	 			}
	 		}
       		
        	//校验是否为空
        	$scope.validName = function(val){
            	if(val){
            		return true;
            	}
            };
        	
            
        }]);
    }

});

