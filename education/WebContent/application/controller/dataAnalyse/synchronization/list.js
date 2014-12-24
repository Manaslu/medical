define(function (require, exports, module) {

    //数据同步列表
    return function setApp(app) {
        app.controller('DataAnalyseSynchronizationListCtrl', ['$scope' ,'Synchronization', 'SourceTables',
                                                               'DemandAvailable','SyncAuditor',
                                                               'SynchronizationApply','$filter',
                                                               'SyncLog','$timeout',function ($scope,Synchronization,SourceTables,DemandAvailable,SyncAuditor,SynchronizationApply,$filter,SyncLog,$timeout) {
        	 //$scope.pager=Synchronization;
        	 $scope.tempdemand = "";
        	 $scope.selectedDemand="";
        	 $scope.DemandAvailable = DemandAvailable;
        	 $scope.createErrorMessage = "";
        	 $scope.sourceSystem =[];//填充系统名
        	 $scope.sourceTableBysystem=[];//动态的数组,表列表
        	 
        	 
        	 $scope.params = {};
           	 $scope.pager =  Synchronization;
           	 $scope.params.loguserId = $scope.USER_INFO.id;//限制条件为,需求确认人为当前登录人自己
           	 $scope.refresh && $scope.refresh('first' , true);	
        	 
        	 $scope.getNewDemand = function(){//得到需求选择器上的数据
            	 $scope.params1 = {};
               	 $scope.pager1 =  DemandAvailable;
               	 $scope.params1.logname = $scope.USER_INFO.logName;//限制条件为,需求确认人为当前登录人自己
               	 
               	 if($scope.getNewDemand.init){
               		setTimeout(function(){
                   		$scope.refresh1 && $scope.refresh1('first' , true);	 
                 	 },1);	 
               	 }
               	$scope.getNewDemand.init = true;
        	 }
        	 

        	 
        	 
        	 $scope.allDemandList = DemandAvailable.query({ //为了修改的时候回写
                 isArray : true,
                 params : {queryforall:1,logname:$scope.USER_INFO.logName}
             });
        	 
             $scope.previewLog =function(indexObj){ //查看日志
            	 $scope.params2 = {};
            	 $scope.pager2 =  SyncLog;
            	 $scope.params2.applyid = indexObj.id;
        	  	 setTimeout(function(){
        	  		$scope.refresh2 && $scope.refresh2('first' , true);	 
            	 },1);
             };
             $scope.editApply =function(applyobj){ //修改
            	 var dateFIlter = $filter('date'); 
            	 $scope.key= applyobj;
            	 $scope.key.dataStartTime=dateFIlter(applyobj.dataStartTime , 'yyyy-MM-dd');//毫秒类型的转成日期型   
            	 $scope.key.dataEndTime=dateFIlter(applyobj.dataEndTime , 'yyyy-MM-dd');//毫秒类型的转成日期型
            	 $scope.key.failureDate=dateFIlter(applyobj.failureDate , 'yyyy-MM-dd');//毫秒类型的转成日期型
            	 $scope.findTable($scope.sourceTables,applyobj.tablenameen);
            	 $scope.selectedDemandtemp = $scope.findDemand( $scope.allDemandList,applyobj.requId);
            	 $scope.key.selectedsystemname = applyobj.sourceSystem;
            	 $scope.getTablesBySystem(applyobj.sourceSystem);
            	 $scope.toSlectDemand($scope.selectedDemandtemp);
             };
             
             //修改的时候,源表回写
             $scope.findTable = function (list , key){
            	 for(var i=0;i<list.length;i++){
            		 if(list[i].tableNameen == key){
            			 $scope.key.selectedsource =  list[i];
                		 return;
            			 
            		 }
            			 
            	 }
             }
             //修改的时候,需求回写
             $scope.findDemand = function (list , key){
            	 var obj;
            	 for(var i=0;i<list.length;i++){
            		 if(list[i].id == key)
            			 return list[i];
            	 }
             }
             
             $scope.sourceTables = SourceTables.query({ //主题数据
                 isArray : true,
                 params : "{}"
             } , function(list){
            	 var sourceSystems= list.map(function(item){
            		 return item.sourceSystem;
            	 });
            	 
            	 $scope.sourceSystem = _.uniq( sourceSystems,true);//数组去重
            	 
             });
             
             $scope.getTablesBySystem = function(systemname){
            	 $scope.sourceTableBysystem=[];
            	 for(var i=0;i<$scope.sourceTables.length;i++){
            		 if($scope.sourceTables[i].sourceSystem == systemname){
            			 $scope.sourceTableBysystem.push($scope.sourceTables[i]);
            		 }  
            	 } 
             }
             
             $scope.clearForm = function(){
            	 
                 $scope.key="";
                  
                 $scope.selectedDemand="";
                 $scope.demand="";
            	 
             }
             
             
        	 $scope.syncauditors = SyncAuditor.query({ //所有有审核权限的用户
                 isArray : true,
                 params : "{}"
             });
             
             $scope.toSlectDemand = function(item){
            	 $scope.selectedDemand = angular.copy(item);
            	 $scope.demand = item.requName;
            };
            
            $scope.save = function(key) { 
            	if(key.dataEndTime){
            		if(key.failureDate <= key.dataEndTime){
            			 
            			$scope.createErrorMessage = "失效时间必须大于数据时间段之数据结束时间";
                		$timeout(function(){
                			$scope.createErrorMessage ="";

                   	    } , 2000);
            			return;
            		}
            	}else{
            		if(key.failureDate <= key.dataStartTime){
            			$scope.createErrorMessage = "失效时间必须大于数据时间段之数据开始时间";
                		$timeout(function(){
                			$scope.createErrorMessage ="";
                   	    } , 2000);
            			return;
            		}
            		
            		if(key.saveCycle=='14'){//周期为一次性,结束时间为空,靠
            			$scope.createErrorMessage = "数据时间段之结束时间不能为空";
                		$timeout(function(){
                			$scope.createErrorMessage ="";
                   	    } , 2000);
            			return;
            		}
            	}
            	
            	
            	var newsyncappl = {};
            	newsyncappl.requId = $scope.selectedDemand.id;//需求id
            	newsyncappl.dataStartTimes=key.dataStartTime;//开始时间
            	newsyncappl.dataEndTimes=key.dataEndTime;//结束时间
            	newsyncappl.cheanMethod=key.cheanMethod;//清理方式
            	newsyncappl.applyUser = $scope.USER_INFO.id;//申请人
            	newsyncappl.theme = $scope.selectedDemand.spcOrjName;//主题
            	newsyncappl.sourceTableName = key.selectedsource.tableName;//表名
            	newsyncappl.tableNameen = key.selectedsource.tableNameen;//表名
            	newsyncappl.tableComment = key.tableComment;
            	newsyncappl.sourceSystem = key.selectedsource.sourceSystem;
            	newsyncappl.saveCycle = key.saveCycle;

            	 
            	var dateFIlter = $filter('date'); 
            	fmtCurrentDay = dateFIlter($scope.selectedDemand.createTime , 'yyyy-MM-dd');//毫秒类型的转成日期型   
            	
            	newsyncappl.demandDates =fmtCurrentDay;
            	newsyncappl.applyUni = $scope.selectedDemand.applOrg;
            	newsyncappl.applyOrg = $scope.selectedDemand.applDept;
            	newsyncappl.receiveUser = $scope.selectedDemand.recvWorker;
            	newsyncappl.applyPhone = $scope.selectedDemand.dataApplTel;
            	newsyncappl.approvalUser = key.approvalUser;
            	newsyncappl.apprRoleId = "SJSH"; //59 为审批角色id,初始化已经固定好
            	newsyncappl.failureDates  = key.failureDate;
            	newsyncappl.autoManu = key.autoManu;
            	
            	if(key.id){//修改
            		newsyncappl.id = key.id;
                    
            		SynchronizationApply.save(newsyncappl,function(){
                    	$scope.refresh('current',true);
                   	     $('#pushDialog').modal('hide');
                   });
            		
            	}else{
                    SynchronizationApply.put(newsyncappl,function(){
                    	$scope.refresh('current',true);
                        $scope.key="";
                        $scope.selectedDemand="";
                        $scope.demand="";
                        $('#pushDialog').modal('hide');
                        
                    });
            		
            	}
            	

                
            };
            $scope.todeleteapply = function(applyid) {
                $scope.tobedeleteId = applyid;
            };
            //提交
            $scope.handinItem = function(id) {
            	var audit = {};
            	audit.approvalStats = '22';
            	audit.id =  id;
            	 SynchronizationApply.save(audit,function(){
                	$scope.refresh('current',true);
                    
                });
            }; 
            //删除
            $scope.comfirmDelete = function() {
            	var audit = {};
            	audit.approvalStats = '45';//删除状态
            	audit.id =  $scope.tobedeleteId;
            	 SynchronizationApply.save(audit,function(){
                	$scope.refresh('current',true);
                    
                });
            }; 
            $scope.setRejectItem = function(item) {
            	$scope.toviewOpionobj = angular.copy(item);
                
            };            
            
             
        }]);
    }

});