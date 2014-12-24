define(function (require, exports, module) {

    //需求确认
    return function setApp(app) {
        app.controller('TechnologicalProcessAchievementResultCtrl',
        	['$scope'  , 'Achievement'  , '$filter','Demand' ,'Processes','$q','NodeAnnex',  function ($scope ,Achievement, $filter,Demand ,Processes,$q,NodeAnnex) {
        	//按条件分页查询数据
        	$scope.pager=Demand;
        	
        	$scope.pageView = 0;
        	
			//跳转页面
			$scope.toPageView = function(view){
				$scope.pageView = view;
				if(view == '0'){
					$scope.refresh('first',true);
				}
			};
        	//分页传递的参数
        	//成果确认角色
        	//Z传递6 K传递e J传递h
        	$scope.params = {
        		//approState : 6,
        		fcode : 'fruit',
        		assignee : $scope.USER_INFO.logName
        	};
        	
       		$scope.toFollow = function(demand){
       			$scope.demand = demand;
   				//查询流程历史表
    			 $scope.reqStep = Demand.get({getHisTasks:true,params:{
            		id:demand.id,
            		userId:$scope.USER_INFO.logName
        		}} , function(result){});
       		}
       		//查看详情
       		$scope.toDetail = function(achievement){
       			$scope.achievement = achievement || new aemand({requType : 1});
    		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ id: achievement.id}}); 
       		}
            $scope.flowTypeList = [{"Name":"--请选择--"},
                                   {"flowType":"inside","Name":"专题、报表取数、统计"},
                                   {"flowType":"outside","Name":"跨机构取数"},
                                   {"flowType":"tech","Name":"技术需求"}
            						]
            
            //确认成果
            $scope.toSubmit = function(achievement){
            	
       			var defer = $q.defer();
       			var promise = defer.promise;
       			var flag1;
       			var flag2;
        		//判断是否有成果，并且所有成果都已经是确认过的了            	
        		//写查询的方法查询是否有成果
        		Processes.query({isArray:true,params:{id:achievement.id}},function (list){
        			flag1 = list.length;
   				});
        		//判断全部的成果是否都确认完事了
        		Processes.get({getFruitOfConfirm:true,params:{
        			id:achievement.id
        		}},function(results){
        			//全都确认过了返回的是true,如果有没有确认的则返回的事false
        			flag2 = results.message;
            		if(flag1 && 'true'==flag2){
            			defer.resolve();
            		}else{
            			defer.reject();
            		}
        		});
                promise.then(function(){//如果promise是resolve的话
                	if(achievement.approState == 'i'){
                    	Demand.query({doNextTask:true,params:{
                    		id:achievement.id,
                    		opPer:$scope.USER_INFO.logName,
                    		remarks:achievement.remarks,
                    		taskDefineCode:'fruitConfirm',
                    		nodeSeq:'6'
                		}} , function(){
    	            		$scope.refresh('first',true);
    	            	});
                	}else if(achievement.approState == 'i'){
                    	Demand.query({doNextTask:true,params:{
                    		id:achievement.id,
                    		opPer:$scope.USER_INFO.logName,
                    		remarks:achievement.remarks,
                    		taskDefineCode:'fruitConfirm',
                    		nodeSeq:'7'
    	            		}} , function(){
    	            		$scope.refresh('first',true);
    	            	});
                	}else if(achievement.approState == 'i'){
                    	Demand.query({doNextTask:true,params:{
                    		id:achievement.id,
                    		opPer:$scope.USER_INFO.logName,
                    		remarks:achievement.remarks,
                    		taskDefineCode:'fruitConfirm',
                    		nodeSeq:'4'
    	            		}} , function(){
    	            		$scope.refresh('first',true);
    	            	});
                	}
                }, function(){
              	alert("该需求还有未上传或未确认或被驳回的成果，不允许终止流程!");
     			});
            };
       		//驳回操作
//       		$scope.toReject =function(demand){
//   				Demand.query({doRejectTask:true,params:{
//                		id:demand.id,
//                		remarks:demand.remarks,
//                		per:$scope.USER_INFO.logName
//       				}}, function(){
//       					$scope.refresh('first',true);
//   				});
//       		}
       		
       		//成果确认按钮
       		$scope.getFruitInfo=function(achievement){
       			$scope.getFruitInfo.achievement = achievement;
       			Processes.query({
       				isArray:true,params:{id:achievement.id
   				}},function (list){
   					$scope.AllFruitList = list;
       			});
       			$scope.toPageView(1);
       		}
       		
            //确认成果
            $scope.modify = function(processes){
        		Processes.save({
        			 fruitCode:processes.fruitCode,
            		 oprId:'1'
            	},function(){
            		//判断全部的成果是否都确认完事了
            		var achievement = $scope.getFruitInfo.achievement;
            		Processes.get({getFruitOfConfirm:true,params:{
            			id:achievement.id
            		}}, function(results){
            			//全都确认过了返回的是true,如果有没有确认的则返回的事false
            			var flag = results.message;
            			if(flag == 'true'){
            				console.log('全部确认完事');
                    		Demand.save({
                    			id:processes.id,
                    			approState:'i'
                    		});
            			}
                	});
            		if($scope.getFruitInfo.achievement){
            			$scope.getFruitInfo($scope.getFruitInfo.achievement);
            		}
            	});
            };
            
            //驳回成果
            $scope.toDoBack = function(processes){
            	console.log('processes',processes);
            	$scope.processes = processes;
        		Processes.save({
        			 fruitCode:processes.fruitCode,
            		 oprId:'2',
            		 publicState:'0'
            	});
        		Demand.save({
        			id:processes.id,
        			approState:'6'
        		}, function(){
            		$scope.getFruitInfo($scope.getFruitInfo.achievement);
            	});
            };
            //下载方法
            $scope.download = function(file) { 
              var url=  '/nodeAnnex.shtml?method=download&params='+ angular.toJson(file);//                    
              var link = $('#downlink')[0];
              link.href = url;
              link.click();
           };
           $scope.getNodeAnnex = function(fruit){
	   		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ fruitCode : fruit.fruitCode}}); 
           }
        }]);
    }
});