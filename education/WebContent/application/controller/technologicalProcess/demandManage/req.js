define(function (require, exports, module) {


    //需求列表
    return function setApp(app) {
        app.controller('TechnologicalProcessDemandManageReqCtrl',
        		['$scope'  , 'Demand' , '$q' ,'User','Institution' ,'$filter','NodeAnnex',function ($scope ,Demand , $q , User,Institution ,$filter,NodeAnnex) {
        			
        			$scope.pageView = 0;//默认显示 ，0 = 列表页面，1 = 新增 和 修改页面
        			
               		$scope.toFollow = function(demand){
               			$scope.demand = demand;
           				//查询流程历史表
            			 $scope.reqStep = Demand.get({getHisTasks:true,params:{
                    		id:demand.id,
                    		userId:$scope.USER_INFO.logName
                		}});
               		}
        			
        			var dateFormat = $filter('date');
        			
                    $scope.flowTypeList = [{"Name":"--请选择--"},
                                           {"flowType":"inside","Name":"专题、报表取数、统计"},
                                           {"flowType":"outside","Name":"跨机构取数"},
                                           {"flowType":"tech","Name":"技术需求"}
                    						]
        			//跳转页面
        			$scope.toPageView = function(view){
        				$scope.pageView = view;
        			};
                	//按条件分页查询数据
                	$scope.pager=Demand;
                	//分页传递的参数
                	$scope.params = {
                		fcode : 'apply',
                		assignee : $scope.USER_INFO.logName
                		//taskDefKey : 'proTechBegin' 
                	};
               		//查看详情
               		$scope.toSeeDetail = function(demand){
           				$scope.see_demand = Demand.get({getAllContacts:true , demandId :demand.id} , function(){
           					$scope.see_demand.analCyclStart = dateFormat(demand.analCyclStart , 'yyyy-MM-dd');
           				});
               		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}}); 
               		}

               		//跳转到提交审核页面
               		$scope.toGoCheck = function(demand){
           				$scope.see_demand = Demand.get({getAllContacts:true , demandId :demand.id} , function(){
           					$scope.see_demand.analCyclStart = dateFormat(demand.analCyclStart , 'yyyy-MM-dd');
           				});
               			$scope.reqTypeCd = {}
               			//根据流程类型判断走哪个流程
               			if(demand.flowType == 'inside'){
               				$scope.asUserList = Demand.query({getNextUser:true,params:{id : $scope.USER_INFO.id,inRoleId : 'YWLDSP'}},function(list){
               					if(list.length == 1){
               						$scope.see_demand.userName = list[0].logName;
               					}
               				});
               			}else if(demand.flowType == 'outside' || demand.flowType == 'tech'){
               				$scope.asUserList = Demand.query({getNextUser:true,params:{id : $scope.USER_INFO.id,inRoleId : 'JSLDSP'}} , function(list){
               					if(list.length == 1){
               						$scope.see_demand.userName = list[0].logName;
               					}
               				})
               			}
               		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}}); 
               		}
               		//监听flowType发生变化，给页面上设定值
               		$scope.$watch('demand.flowType' , function(flowType){
               			if($scope.isCreate && flowType){
               				$scope.demand.requCode = $scope.reqTypeCd[flowType];
               			}
               		});
               		
               		var reqList = $scope.reqList = [
	                  {key : 'inside' , name : '专题、报表取数、统计'},
	                  {key : 'outside' , name : '跨机构取数'},
	                  {key : 'tech' , name : '技术需求'}
                  	];
               		
               		var dataApplContacts;
               		
               		//跳转到新增修改页面
               		$scope.toDetail = function(demand){
               			
               			var defer = $q.defer();
               			var promise = defer.promise;
               			//查询当前用户的角色是否有发起申请的权利
               			/**
               			 * 第一种：如果是业务申请角色，则只给该用户选择  专题、报表取数、统计 
               			 * 第二种：如果是集团的技术申请角色，则只给 技术需求  
               			 * 第三种：如果是省内，技术申请角色，则给   跨机构取数，技术需求 
               			 */
               			$scope.reqFlowType = Demand.get({getFlowType:true , userId : $scope.USER_INFO.id} , function(result){
               				$scope.reqList = reqList.filter(function(reqType){
               					return result.reqType.indexOf(reqType.key) > -1; 
               				});
               				if(!$scope.reqList.length){
               					defer.reject();
               				}else{
               					defer.resolve();
               				}
               			});
               			
               			promise.then(function(){
               			//查询需求编号
                   			$scope.reqTypeCd = Demand.get({getRequCode:true , demandId : demand ? demand.id : ''} , function(){
                   				$scope.demand.requCode = $scope.reqTypeCd[$scope.demand.flowType];//默认赋值
                   			});//{'inside':'T2014333333' ,'outside':'K2014333344','tech' : 'J2014333344'  }
                   			//赋默认值
                   			$scope.demand = demand || new Demand({
                   				requType : 1 ,
                   				applPer : $scope.USER_INFO.userName,
                   				objApplyOrg : $scope.USER_INFO.orgName,
                   				applOrg : $scope.USER_INFO.orgName
                   			});
                   			if(demand){//是修改
                       		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}}); 
                   				//查询各种要表关联的数据
                   				$scope.demand = Demand.get({getAllContacts:true , demandId :demand.id} , function(){
                   					$scope.demand.analCyclStart = dateFormat(demand.analCyclStart , 'yyyy-MM-dd');
                   					
                   					$scope.demand._dataApplContacts = {
                   						userName : $scope.demand.dataApplContacts_,
                   						logName : $scope.demand.dataApplContacts
                   					};
                   					
                   					$scope.demand._groupTechContacts = {
                   						userName : $scope.demand.groupTechContacts_,
                   						logName : $scope.demand.groupTechContacts
                   					};
                   					
                   					$scope.demand._provContacts = {
                   						userName : $scope.demand.provContacts_,
                   						logName : $scope.demand.provContacts
                   					};  
                   					
                   					$scope.demand._techContact = {
                   						userName : $scope.demand.techContacts_,
                   						logName : $scope.demand.techContacts
                   					};               				               						
                   				});
                   			}else{
                   				$scope.fileList = [];
                   				autoRefresh('0' , 'techContact' ,'JSLXR', function(list , key , key2){
                   					if(list.length == 1){
                   						$scope.demand._techContact = list[0];
                   					}
                   				});//技术联系人
                   				autoRefresh('0' , 'provContacts','JSLXR', function(list , key , key2){
	               					if(list.length == 1){
	               						$scope.demand._provContacts = list[0];
	               					}
                   				});//省内专门联系人
                   				autoRefresh('0' , 'groupTechContacts' ,'99', function(list , key , key2){
	               					if(list.length == 1){
	               						$scope.demand._groupTechContacts = list[0]; 
	               					}
               					});//集团技术联系人
                   			}
                   			$scope.toPageView(1);
                   			if($scope.api){
                   				$scope.api.reset();
                   			}
                   			$scope.isCreate = !demand;
               			} , function(){
               				alert("很抱歉，您没有权限发起需求申请!");
               			});
               		};
               		
               		
               		
               		function autoRefresh(type , key , key2 , callback){
               			var search  = searchMap[type];
               			if(search){
               				var params = {provCode : '99'};
               				if(key=='groupTechContacts'){
               					params.inRoleId = 'JSLXR';
                        	}else if(key=='techConfirm' && key2 =='99'){
                        		params.inRoleId = 'JSXUQJ';
                        	}else if (key=='groupBoss'){
                        		params.inRoleId = 'JSLDSP';
                        	}else{
                        		params.provCode = $scope.USER_INFO.provCode;
                        		params.inRoleId = key2;
                        	}
               				search.query(angular.extend({} , paramsMap[type],  {
               					params : angular.toJson(params)
               				}) , function(result){
               					callback(result , key , key2);
               				})
               			}
               		}
               		
               		/**
               		 * //查询数据
                    $scope.searchData = function(){
                    	//根据类型不同，选择资源查询
                    	$scope.searchList = searchMap[$scope.searchType].query( getParams() );
                    };
                    function getParams(){
                    	return angular.extend({} , paramsMap[$scope.searchType] , {
                    		params : angular.toJson($scope.searchParams)
                    	});
                    }
               		 */
               		
               		
                    //按流程新增需求
                    $scope.create = function(demand,userId){
//                    	demand.dataApplTel = demand._dataApplContacts.phone;
//                    	demand.dataApplEmail = demand._dataApplContacts.email;
                    	demand.userId = $scope.USER_INFO.logName;
                    	if(demand.flowType == 'inside'){
                    		demand.techContacts = demand._techContact.logName;
                    	}else if(demand.flowType == 'outside'){
                      		demand.provContacts = demand._provContacts.logName;//省内专门联系人
                    		demand.groupTechContacts = demand._groupTechContacts.logName;//集团技术联系人
                    	}
                    	
                    	Demand.put(angular.extend({}  , demand , {
                    		applPer : $scope.USER_INFO.logName
                    	}),function(result){
                    		Demand.query({doSaveAnnex:true,params:{
                        		id:result.demandId,
                        		files:demand.fileResponseList
                    		}} , function(){
                    			$scope.refresh('first',true);
                    		});
                			$scope.refresh('first',true);
                			$scope.toPageView(0);
                		});
                    };
                    


                    //补录新增需求
                    $scope.createIt = function(demand,userId){
                    	demand.userId = $scope.USER_INFO.logName;
                    	demand.techConfirmPsn = demand._techConfirm.logName;
                    	if(demand.flowType == 'inside'){
                    		demand.techContacts = demand._techContact.logName;
                    		demand.bussExamPsn = demand._busBoss.logName;
                    		demand.techExamPsn = demand._techBoss.logName;
                    	}else if(demand.flowType == 'outside'){
                      		demand.provContacts = demand._provContacts.logName;//省内专门联系人
                    		demand.groupTechContacts = demand._groupTechContacts.logName;//集团技术联系人
                    		demand.provLeader = demand._proBoss.logName;//省内技术领导
                    		demand.groupLeader = demand._groupBoss.logName;//集团技术领导
                    	}else if(demand.flowType == 'tech'){
                    		demand.techExamPsn = demand._techBoss.logName;
                    	}	
                    	Demand.put(angular.extend({}  , demand , {
                    		applPer : $scope.USER_INFO.logName
                    	}),function(result){
                    		Demand.query({doSaveAnnex:true,params:{
	                    		id:result.demandId,
	                    		files:demand.fileResponseList
                    		}} , function(){
                    			$scope.refresh('first',true);
                    		});
                			$scope.toPageView(0);
                		});
                    };
                    //跳转到删除对话框
               		$scope.toDelete=function(demand){
               			$scope.demand=demand;
               		}
                    //删除需求
                    $scope.remove = function(demand){
                    	var params={id:demand.id};
                    	Demand.remove({
                    			params:angular.toJson(params)
                    		},$scope.refresh.bind(null , 'current',true))
                    };
                    //修改需求
                    $scope.modify = function(demand){
                    	console.log('ddddd',demand.fileResponseList);
                		Demand.save({
                			 id:demand.id,
                			 requCode:demand.requCode,
                			 requName:demand.requName,
                			 requCont:demand.requCont,
                			 requType:demand.requType,
                			 applPer:demand.applPer,
                			 applOrg:demand.applOrg,
                			 applDept:demand.applDept,
//                			 dataApplContacts:demand.dataApplContacts,
//                			 dataApplTel:demand.dataApplTel,
//                			 dataApplEmail:demand.dataApplEmail,
                			 dataPassType:demand.dataPassType,
                			 dataExtrType:demand.dataExtrType,
                			 analCyclStart:demand.analCyclStart,
                			 requDesc:demand.requDesc,
                			 requUrl:demand.requUrl,
                			 spcOrjName:demand.spcOrjName,
                			 objApplyOrg : demand.objApplOrg,
                			 securityLev : demand.securityLev,
                			 emergencyLev : demand.emergencyLev,
                			 dataSources : demand.dataSources,
                			 contact : demand.contact,
                			 remarks : demand.remarks,
                			 addRecord : demand.addRecord,
                			 flowType : demand.flowType,
                			 provContacts : demand._provContacts.logName,
                			 groupTechContacts : demand._groupTechContacts.logName,
                			 techContacts : demand._techContact.logName,
                			 files:demand.fileResponseList
                    	},$scope.refresh.bind(null , 'current',true))
                    	$scope.toPageView(0);
                    };
                    //项目申请单位的数据源
                    $scope.treeData = Institution.query({list : true,params:{
                    	id:$scope.USER_INFO.orgCd=='AA0000000000' ? '' : $scope.USER_INFO.orgCd,
                        parentOrgCd:$scope.USER_INFO.orgCd=='AA0000000000' ? '99999' : ''
                    }});
                    
                    //选中后返回值给defer状态
                    $scope.selected = function(data){//普通表格选中后使用
                    	defer.resolve(data);//把defer状态修改为完成
                    };
                    var defer;
                    //打开选中对话框，保存选择信息
                    //type 选择器类型
                    //key  demand 保存的关键字key
                    //key2 selected 目标关键字key
                    $scope.openSearch = function(type , key , key2){
                    	defer = $q.defer();//这是一个空的对象
                    	$scope.searchType = type;
                    	if(key=='groupTechContacts'){
                    		$scope.searchParams = {
                    				provCode : '99',
                    				inRoleId : 'JSLXR'};
                    	}else if(key=='techConfirm' && key2 =='99'){
                    		$scope.searchParams = {
                    				provCode : '99',
                    				inRoleId : 'JSXUQJ'};
                    	}else if (key=='groupBoss'){
                    		$scope.searchParams = {
                    				provCode : '99',
                    				inRoleId : 'JSLDSP'};                    		
                    	}else{
                    		$scope.searchParams = {
                    				provCode : $scope.USER_INFO.provCode,
                    				inRoleId : key2};	
                    	}
                    	defer.promise.then(function(data){
                    		//success
                    		if(type == '0'){//数据申请联系人
                    			$scope.demand['_' + key] = data;
                    			data = data['id'];
                    		}else{
                    			data = data[key2 || key];
                    		}
                    		$scope.demand[key] = data;
                    	});
                    	if(type != 'tree'){
                    		$scope.searchData();
                    	}
                    };
                    //表格查询数据源
                    var searchMap = {//Resource 查询器
                    	//0 : Req,//查询项目申请单位
                    	0 : User//数据申请联系人
                    };
                    var paramsMap = {
                    	0 : {queryTechContact : true},
                    	provCode : $scope.USER_INFO.provCode,
                    	inRoleId : 'JSLXR'
                    }
                    //查询数据
                    $scope.searchData = function(){
                    	//根据类型不同，选择资源查询
                    	$scope.searchList = searchMap[$scope.searchType].query( getParams() );
                    };
                    function getParams(){
                    	return angular.extend({} , paramsMap[$scope.searchType] , {
                    		params : angular.toJson($scope.searchParams)
                    	});
                    }
                    $scope.treeSetting = {
                        callback : {
                        	beforeClick : function(treeId , node){
                        		//如果有不让选择的节点，在这里做判断，返回false阻止触发onClick
                        	},
                        	onClick : function(event , treeId , nodeData , level){
                        		$scope.selected(nodeData);//把点击的节点数据作为选中值
                        		$scope.$apply();
                        		$('#select').modal('hide');//关闭对话框
                        	}
                        }
                    };
                    //提交审核
                    $scope.toSubmit = function(demand){
                    	if(demand.flowType == 'inside'){
                        	Demand.query({doNextTask:true,params:{
	                    		id:demand.id,
	                    		opPer:$scope.USER_INFO.logName,
	                    		remarks:demand.remarks,
	                    		nextUserId:demand.userName,
                    			taskDefineCode:'busBegin',
                    			nodeSeq:'1'
                    		}} , function(){
                    			$scope.refresh('first',true);
                    		});
                    	}else if(demand.flowType == 'outside'){
                        	Demand.query({doNextTask:true,params:{
	                    		id:demand.id,
	                    		opPer:$scope.USER_INFO.logName,
	                    		remarks:demand.remarks,
	                    		nextUserId:demand.userName,
                    			taskDefineCode:'proTechBegin',
                    			nodeSeq:'1'
                    		}} , function(){
                    			$scope.refresh('first',true);
                    		});
                    	}else if(demand.flowType == 'tech'){
                        	Demand.query({doNextTask:true,params:{
	                    		id:demand.id,
	                    		opPer:$scope.USER_INFO.logName,
	                    		remarks:demand.remarks,
	                    		nextUserId:demand.userName,
                    			taskDefineCode:'techBegin',
                    			nodeSeq:'1'
                    		}} , function(){
                    			$scope.refresh('first',true);
                    		});
                    	}
                    };
                    //跳转到终止对话框
               		$scope.toTerminateTask=function(demand){
               			$scope.demand=demand;
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
                    
                    //下载方法
                    $scope.download = function(file) { 
                      var url=  '/nodeAnnex.shtml?method=download&params='+ angular.toJson(file);//                    
                      var link = $('#downlink')[0];
                      link.href = url;
                      link.click();
                   };
              		//跳转到补录页面
              		$scope.record = function(demand){
              			
              			var defer = $q.defer();
              			var promise = defer.promise;
              			$scope.reqFlowType = Demand.get({getFlowType:true , userId : $scope.USER_INFO.id} , function(result){
              				$scope.reqList = reqList.filter(function(reqType){
              					return result.reqType.indexOf(reqType.key) > -1; 
              				});
              				if(!$scope.reqList.length){
              					defer.reject();
              				}else{
              					defer.resolve();
              				}
              			});
              			promise.then(function(){
                   			//查询需求编号
                       			$scope.reqTypeCd = Demand.get({getRequCode:true , demandId : demand ? demand.id : ''} , function(){
                       				$scope.demand.requCode = $scope.reqTypeCd[$scope.demand.flowType];//默认赋值
                       			});//{'inside':'T2014333333' ,'outside':'K2014333344','tech' : 'J2014333344'  }
                       			
                       			//赋默认值
                       			$scope.demand = demand || new Demand({
                       				requType : 1 ,
                       				applPer : $scope.USER_INFO.userName,
                       				objApplyOrg : $scope.USER_INFO.orgName,
                       				applOrg : $scope.USER_INFO.orgName
                       			});
	               				autoRefresh('0' , 'busBoss','YWLDSP', function(list , key , key2){
		               					if(list.length == 1){
		               						$scope.demand._busBoss = list[0];
		               					}
	           					});//业务领导审批人
	               				autoRefresh('0' , 'techBoss' ,'JSLDSP', function(list , key , key2){
		               					if(list.length == 1){
		               						$scope.demand._techBoss = list[0];
		               					}
	           					});//技术领导审批人
	               				autoRefresh('0' , 'proBoss','JSLDSP', function(list , key , key2){
	               					if(list.length == 1){
	               						$scope.demand._proBoss = list[0];
	               					}
	           					});//省内领导
	               				autoRefresh('0' , 'groupBoss' ,'99', function(list , key , key2){
	               						if(list.length == 1){
	               							$scope.demand._groupBoss = list[0];
		               					}
		           				});//集团领导
                   				autoRefresh('0' , 'techContact' ,'JSLXR', function(list , key , key2){
                   					if(list.length == 1){
                   						$scope.demand._techContact = list[0];
                   					}
                   				});//技术联系人
                   				autoRefresh('0' , 'provContacts','JSLXR', function(list , key , key2){
	               					if(list.length == 1){
	               						$scope.demand._provContacts = list[0];
	               					}
                   				});//省内专门联系人
                   				autoRefresh('0' , 'groupTechContacts' ,'99', function(list , key , key2){
	               					if(list.length == 1){
	               						$scope.demand._groupTechContacts = list[0]; 
	               					}
               					});//集团技术联系人
	               				autoRefresh('0' , 'techConfirm','JSXUQJ', function(list , key , key2){
		           					if(list.length == 1){
		           						$scope.demand._techConfirm = list[0];
		           					}
		       					});//技术需求确认人
	               				autoRefresh('0' , 'techConfirm','99', function(list , key , key2){
		           					if(list.length == 1){
		           						$scope.demand._techConfirm = list[0];
		           					}
		       					});//集团技术操作员
                       			$scope.toPageView(2);
                       			$scope.isCreate = !demand;
                   			} , function(){
                   				alert("很抱歉，您没有权限发起补录申请!");
                   			});
                   		}
	                    $scope.delAttach = function(){
		       				 var params={fileId:$scope.fid};
		       				 var demand = $scope.demand;
		       				 NodeAnnex.remove({params:angular.toJson(params)}, function(){
		       					$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: demand.id}});
		       				} );  
	                   }    
	                   $scope.toDeleteAnnex = function(fid,demand){
	                	   $scope.fid = fid;
	                	   $scope.demand = demand;
	                   }
	                   $scope.fn = function(file , type , ext){
	                	   alert("只支持上传"+ext+"类型的文件");
    	               }
        }]);
    }
});
