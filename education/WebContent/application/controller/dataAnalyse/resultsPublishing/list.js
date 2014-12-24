define(function (require, exports, module) {

    //分析流程固化列表
    return function setApp(app) {	

	app.controller('DataAnalyseResultsPublishingListCtrl', [ '$scope', 'AnalysisProcess', '$routeParams','RuleScript','$log','$filter','Menu' , 'menuMap','ExportTables','dialog',
	    function($scope, AnalysisProcess, $routeParams , RuleScript , $log,	$filter, Menu, menuMap,ExportTables,dialog ) {
		$scope.pager = AnalysisProcess;
		$scope.params = {
			status_ : $routeParams.status,
//			solidUser:$scope.USER_INFO.userName,
			orderBy: 'SOLID_DATE',
			order  : 'desc'
		};
		
		$scope.saveBtn = $routeParams.status;
		
		
		//新增 / 修改
		$scope.toCreate = function(item){
			$log.log('当前对象',item);
			$scope.obj = item || new AnalysisProcess;
			
			if(item){
				$scope.scriptList=[
						{OBJECT_NAME : $scope.obj.solidScript}
					];
			}else{
				$scope.scriptList = AnalysisProcess.query({
					findTable :true,
					params :{
						tableName : 'v_proc_list'
					}
				});
			}
			
		};
		
		//新增修改的保存方法
		$scope.save = function(){
			var dateFormat = $filter('date');
			if($scope.obj.id){//是修改
				$log.log('当前对象',angular.toJson($scope.obj));
				
				$scope.obj.solidDate = dateFormat($scope.obj.solidDate,'yyyy-MM-dd');
				delete $scope.obj.startDT;
				delete $scope.obj.endDT;
				AnalysisProcess.save($scope.obj,function(){
					$scope.refresh('current',true);
				});
			}else{
				$log.log('当前对象',$scope.obj);
//				$scope.obj.id='123';
//				$scope.obj.anaThemeId='201';
				$scope.obj.solidUser= $scope.USER_INFO.userName;
				delete $scope.obj.startDT;
				delete $scope.obj.endDT;
//				$scope.obj.solidDate='2014-6-24';
//				$scope.obj.anaDataSet='暂无'; 用作存储menu_id
				$scope.obj.status_='1';
				$scope.obj.runStatus_='0';
//				$scope.obj.approvalUser = $scope.USER_INFO.userName;
				AnalysisProcess.query({
					copyScript :true,
					params :{
						scriptName : $scope.obj.solidScript
					}
				});
				$scope.obj.$put(function(){
					$scope.refresh('first' , true);
				});
			}
		};
		//启动
		$scope.exeScript = function(item){
			$scope.tableName = AnalysisProcess.query({
				executeScript :true,
				params :{
					id : item.id,
					procedure : item.solidScript
				}
			},function(list){
				if(list && list.length) dialog(list[0].error,"提示");
				$scope.refresh('current',true);
			});
		}
		//提交审核
		$scope.toSend = function(item,status){
			$scope.status=status;
			$scope.obj = item;
			
			if(status==4){
	            $scope.subMenu = {
	            	menuTitle : item.procName,
	            	menuDesc  : item.procDesc,
	            	params    : 'id='+item.id
	            };
			}
			$log.log('当前提交对象',angular.toJson($scope.obj));
		};
		
		//发送提交
		$scope.send = function(type){
			$scope.obj.status_ = $scope.status;
			if(type) {
				$scope.obj.status_ = type;
	            var params = {
	            		menuId : $scope.obj.anaDataSet
	                 };
	            Menu.remove({
	                 params : angular.toJson(params)
	             })
	             
	            /**
	             * 删除菜单权限中间表
	        	AnalysisProcess.remove({
	        		removeRolePermission : true,
			    	params : angular.toJson({
						roleId: 1,
						menuId  : $scope.obj.anaDataSet,
			    	})
	        	});
	           */ 
			}
			var dateFormat = $filter('date');
			$scope.obj.solidDate = dateFormat($scope.obj.solidDate,'yyyy-MM-dd');
			$scope.obj.startDT = dateFormat($scope.obj.startDT,'yyyy-MM-dd');
			$scope.obj.endDT = dateFormat($scope.obj.endDT,'yyyy-MM-dd');
			if($scope.status == 3) $scope.obj.approvalUser = $scope.USER_INFO.userName;
			$log.log('当前对象',angular.toJson($scope.obj));
			AnalysisProcess.save($scope.obj,function(){
				$scope.refresh('current',true);
			});
		}
		
		//驳回
		$scope.back = function(type){
			var dateFormat = $filter('date');
			$scope.obj.solidDate = dateFormat($scope.obj.solidDate,'yyyy-MM-dd');
			$scope.obj.startDT = dateFormat($scope.obj.startDT,'yyyy-MM-dd');
			$scope.obj.endDT = dateFormat($scope.obj.endDT,'yyyy-MM-dd');
			$scope.obj.approvalUser = $scope.USER_INFO.userName;
			$scope.obj.status_= '1';
			$scope.obj.runStatus = '0';
			AnalysisProcess.save($scope.obj,function(){
				$scope.refresh('current',true);
			});
		}
		
		//删除
		$scope.toRemove = function(item){
			$scope.obj = item;
		};
		$scope.remove = function(){
            var params = {
                   id : $scope.obj.id,
                   procedure : $scope.obj.solidScript
                };
			AnalysisProcess.remove({
                params : angular.toJson(params)
            }, function(){
            	$scope.refresh('current',true);
            });
		};
		
		//查看
		$scope.toSee = function(item){
			$scope.obj = item;
			$scope.tableName = AnalysisProcess.query({
				exportTables :true,
				params :{
					id : item.id
				}
			},function(list){
				var tableName = list[0].OUTPUTTABLENAME;
				$scope.dataMap = AnalysisProcess.query({
					findTable :true,
					params :{
						tableName : tableName
					}
				});
				
				$scope.columnMap = AnalysisProcess.query({
					findColumns :true,
					params :{
						tableName : tableName
					}
				});
				
			});
		};
		
		/**菜单加载开始**/
    	$scope.data = Menu.query({
	    	isArray : true ,
	    	params : angular.toJson({
	    		parMenuId : 10608,
	    		menuId :10608
	    	})
	    });
    	
	    var api;
	    $scope.settings = {
	        view : {
	            autoCancelSelected : false,
	            selectedMulti : false
	        },
	        callback: {
	            beforeClick : function(_ , obj){
	                return obj.menuTitle === "分析成果展示";
	            },
	            onClick: function(e , _ , obj){
	                $scope.$apply(function(){
	                    $scope.subMenu.parMenuId = obj.menuId;
	                });
	            }
	        },
	        data : {
	        	key : {
	        		name : "menuTitle"
	        	},
	        	simpleData : {
	        		enable : true,
	        		idKey : "menuId",
	        		pIdKey : "parMenuId"
	        	}
	        }
	    };
	    /**菜单加载结束**/

	    
	    //成果展示

	    //成果展示>分析结果展示.split('>')

//	    $scope.data = [
//	      {name : "成果展示" , id:106 , children : [
//	          {name : "分析结果展示" , id:10610 , children : [
//	                {name : "成果展示1" , id:1061001 , children : []},        
//	                {name : "成果展示2" , id:1061002 , children : []},  
//	                {name : "成果展示3" , id:1061003 , children : []}
//	     ]}
//	     ]}
//	     ];

//	    menuMap = findMenu(menuMap , '成果展示');
//	    menuMap = findMenu(menuMap.children , '分析成果展示');

//	    function findMenu(menu , name){
//	        for (var i = 0; i < menu.length; i++) {
//	            var obj = menu[i];
//	            if(obj.name == name){
//	                return obj;
//	            }
//	        }
//	    }
	    
	    
//
	    
	    
	    
	    $scope.put = function(){
	        
	        Menu.query({
	        	isArray : true ,
		    	params : angular.toJson({
					orderBy: 'MENU_ID',
					order  : 'desc',
		    		parMenuId : 10608
		    	})
	        } , function(list){
	        	var id;
	        	if(list && list.length){
	        		id = list[0].menuId += 1;
	        	}else{
	        		id = 1060801;
	        	}
	        	$scope.subMenu.menuId = id;
	        	$scope.subMenu.menuName = $scope.subMenu.menuTitle;
	        	$scope.subMenu.action = 'main'+id;
	        	$scope.subMenu.template = 'templates/result/resultsShow/main.html';
	        	$log.log('menu对象',angular.toJson($scope.subMenu));
	        	
        		/**更新审核状态为已发布**/
        		$scope.obj.status_ = $scope.status;
    			var dateFormat = $filter('date');
    			$scope.obj.solidDate = dateFormat($scope.obj.solidDate,'yyyy-MM-dd');
    			$scope.obj.startDT = dateFormat($scope.obj.startDT,'yyyy-MM-dd');
    			$scope.obj.endDT = dateFormat($scope.obj.endDT,'yyyy-MM-dd');
    			$scope.obj.anaDataSet = id;
    			$log.log('AnalysisProcess对象',angular.toJson($scope.obj));
    			AnalysisProcess.save($scope.obj);
	        	
	        	Menu.put($scope.subMenu , function(){
//		            /**
//		             * menu = {
//		             *      parentId,
//		             *      id,
//		             *      name,
//		             *      action,
//		             *      template,
//		             *      params
//		             * }
//		             */
//		            menu.controllerName = 'ResultResultsShowMainCtrl';
//		            menu.controllerUrl = '~/result/resultsShow/main';
//		            menu.route = '/at/result/resultsShow/' + menu.action;
//		            menuMap.push(menu);
	        		
		            $scope.subMenu = null;
		            $scope.refresh('current',true);
		        });
	        	
	        	/**
	        	 * 添加菜单权限中间表
	        	AnalysisProcess.put({
	        		addRolePermission : true,
			    	params : angular.toJson({
						roleId: 1,
						menuId  : id,
			    		state : '1'
			    	})
	        	});
	        	*/
	        });
	        
	        
	        
	    };

	    //ztree控制api
	    $scope.ready = function(ztreeApi){
	        api = ztreeApi;
	        api.expandAll(true);
	    }
		
		
	}]);
   
}});