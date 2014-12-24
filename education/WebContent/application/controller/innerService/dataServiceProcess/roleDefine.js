define(function (require, exports, module) {

    //流程规则定制
    return function setApp(app) {
        app.controller('InnerServiceDataServiceProcessRoleDefineCtrl',['$scope' ,'FlowRuleCust' ,'RunProcess','DataDefinition', 'DataSet','DataInteLog','RuleScript','DataDefinitionAttr', '$filter' , function ($scope ,FlowRuleCust,RunProcess,DataDefinition,DataSet,DataInteLog,RuleScript, DataDefinitionAttr ,$filter) {
            
        	//按条件分页查询数据
        	$scope.pager=FlowRuleCust;
        	$scope.FlowRuleCust = FlowRuleCust;
        	 $scope.params = {
             		createPs:$scope.USER_INFO.id
             };
        	
        	//根据规则类型查询规则
       		$scope.selectRule = function(data){
       			$scope.dataRules = [];
       			$scope.commonTypeList=[];
       			RuleScript.query({isArray:true,params:{
	       				ruleType:data.colRuleType,
	       				isChd : "N",
	                    orderBy:"create_date",
	                    order:"asc"
       				}},function (list){
       				list.forEach(function(item){
       					$scope.dataRules.push(item);
       				});
	       			for (var i = 0; i < list.length; i++) {
	                     if (list[i].scriptId==6) {
	                         var chdRules=list[i].chdRuleScript;
	                         for(var j=0;j<chdRules.length;j++){
	                             $scope.commonTypeList.push({
	                                 "optType" : chdRules[j].scriptId,
	                                 "optName" : chdRules[j].scriptName
	                             });
	                         }
	                     }
	                 }
       				
                });
       		};
       		
       		
        	//根据数据定义dataDefId查询所有列名
       		$scope.selectCloums = function(data){
       			DataDefinitionAttr.query({isArray:true,params:{dataDefId:data.dataDefId}},function (list){
       				$scope.dataCloums =list; 
                });
       		};
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {
                		createPs:$scope.USER_INFO.id
                };
            };
            $scope.$validate = "";
            //初始化删除
       		$scope.toDelete = function(flowRuleCust){
       			$scope.flowRuleCust = angular.copy(flowRuleCust) || new FlowRuleCust;
       			//判断该规则模板是否正在使用，如果再使用不可以删除
            	RunProcess.query({isArray:true,params:{procRuleCustId:flowRuleCust.flowRuleCustId}},function (list){
       				if(list.length){
	           			 $scope.$validate = '该规则模板正在使用，不能删除';
	           		 }else{
	           			$scope.$validate = "";
	           		 }
                });
       			
       		};
       		
       		//初始化修改
       		$scope.toUpdate = function(flowRuleCust){
       			$scope.flowRuleCust = angular.copy(flowRuleCust);
       			//用于判断是新增页面还是修改页面，1是修改，2是新增
       			$scope.type = 1;
       			
       			DataSet.query({isArray:true,params:{dataDefId:flowRuleCust.dataDefId}},function (list){
            		$scope.dataSetList =list; 
       			});
       			//查询目标数据集
        		DataInteLog.query({goal:true,osetId:flowRuleCust.sourceDataSet},function (list){
        			$scope.dataList =list; 
                });
       			
       		};
       		
       		//修改流程规则定制
            $scope.modify = function(flowRuleCust){
            	
            	if(flowRuleCust.flowRuleCustType==0){
        			var s = flowRuleCust.dataSetId;
        		}else{
        			var s = '';
        		}
            	FlowRuleCust.save(
	            	{
	    				flowRuleCustName : flowRuleCust.flowRuleCustName,
	    				flowRuleCustType : flowRuleCust.flowRuleCustType,
	    				flowStats:0	,
	    				dataDefId : flowRuleCust.dataDefId,
	    				flowDesc : flowRuleCust.flowDesc,
	    				flowRuleCustId:flowRuleCust.flowRuleCustId,
	    				resultDataSet : flowRuleCust.ruleId,
	    				sourceDataSet : s
	                },$scope.refresh.bind(null , 'current',true));
            };
       		//规则模板设置页面，0的时候不显示，1的时候显示
       		$scope.current = 0;
            
       		//跳转页面
       		$scope.toPage = function(index) {
       			if(index == 1){
       				//查询数据定义表的数据定义名称
            		DataDefinition.query({isArray:true,params:{
	            			"businessType" : "1",
	                        "orderBy" : "CREATE_DATE",
	                        "order" : "desc",
	                        "createUser" : $scope.USER_INFO.id
            			}},function (list){
            				$scope.dataDefinitionList =list; 
                    });
       			}
        	    $scope.current = index;
        	    //用于判断是新增页面还是修改页面，1是修改，2是新增
        	    $scope.type = 2;
        	};
        	
        	//根据数据定义的dataDefId，查询数据集的源数据集名称sourceDataSet
            $scope.selectDataSet = function(flowRuleCust){
       			
            	DataSet.query({isArray:true,params:{dataDefId:flowRuleCust.dataDefId}},function (list){
            		$scope.dataSetList =list; 
               });
            };
            
            //根据数据集的dataSetId，查询数据集的目标数据集名称dataSetName
            $scope.selectDataInteLog = function(flowRuleCust){
                //查询目标数据集
        		DataInteLog.query({goal:true,osetId:flowRuleCust.sourceDataSet},function (list){
        			$scope.dataList =list; 
                });
             };
        	
             
           //规则列表
    		$scope.rules=[];
         	//添加规则
    		$scope.addRule = function(data,coloums,uniqueColumns){
    			var keyword = '';
    			var src = '';
    			var to = '';
    			var optType = '';
    			var optName = '';
    			var startIndex = '';
    			var strLength = '';
    			if(data.dataRule.scriptId == '12'){//剔除关键字及关键字以后字符
    				keyword = data.chdRule.keyword;
    			}else if (data.dataRule.scriptId == '6'){//自定义替换服务
    				src = data.chdRule.commonRules[0].src;
    				to = data.chdRule.commonRules[0].to;
    				optType = data.chdRule.commonRules[0].opt.optType;
    				optName = data.chdRule.commonRules[0].opt.optName;
    			}else if(data.dataRule.scriptId == '13'){//按照起始位置和长度进行字符截取
    				startIndex = data.chdRule.startIndex;
    				strLength = data.chdRule.strLength;
    			}
    			var keys = [] , desc = [];
    			coloums.forEach(function(column){
    				var arr = column.split('**');
    				keys.push(arr[0]);
    				desc.push(arr[1]);
    			});
    			//处理的字段
    			var s = keys.join(',');
    			//字段名
    			var n = desc.join(',');
    			//排重依据
    			var x = uniqueColumns.join(',');
    			$scope.rules.push({
       				colRuleType:data.colRuleType,
       				scriptId:data.dataRule.scriptId,
       				ruleDesc:data.dataRule.scriptName,
       				resultColumnName:s,
       				dataset1GroupColumn:x,
       				resultColumnDesc:n,
       				optType:optType,
       				src : src,
       				to: to,
       				keyword: keyword,
       				startIndex: startIndex ,
       				strLength : strLength,
       				optName :optName
    				});
    			//解决缓存问题
    			$scope.coloum_check=[];
    			$scope.all1=$scope.all2=null;
    		};
             
    		$scope.toSeeRule = function(flowRuleCust){
    			FlowRuleCust.query({findRules:true,params:{
    				ruleId:flowRuleCust.flowRuleCustId
       			}},function (list){
           			$scope.columnInteRuleList =list; 
                   });
        	};
    		
        	//保存自定义好的规则
        	$scope.saveRule = function(rule){
        		$scope.flowRule = angular.toJson(rule);
        		$scope.data.colRuleType='';
       			$scope.data.dataRule='';
        	};
             
        	//新增规则模板
        	$scope.save = function(flowRuleCust){
        		//手动定义定义好的规则
        		var flowRule = $scope.flowRule || "";
        		
        		//0代表数据抽取，得保存原数据集。1代表手工定义，没有源数据集，得虚拟生成，暂时保存为空
        		if(flowRuleCust.flowRuleCustType==0){
        			var s = flowRuleCust.sourceDataSet;
        		}else{
        			var s = '';
        		}
        		
        		FlowRuleCust.put(
            			{
            				flowRuleCustName : flowRuleCust.flowRuleCustName,
            				flowRuleCustType : flowRuleCust.flowRuleCustType,
            				flowStats:1	,
            				dataDefId : flowRuleCust.dataDefId,
            				flowDesc : flowRuleCust.flowDesc,
            				flowRuleCustId:flowRuleCust.flowRuleCustId,
            				resultDataSet : flowRuleCust.resultDataSet,
            				sourceDataSet : s,
            				createPs: $scope.USER_INFO.id,
            				rules:flowRule
                        }, $scope.refresh.bind(null , 'first',true));
        		
        		//解决缓存问题
       			$scope.coloum_check=[];
        		
        	}
        	
            //删除流程规则定制
            $scope.remove = function(flowRuleCust){
            	
            	var params = {flowRuleCustId:flowRuleCust.flowRuleCustId};
            	FlowRuleCust.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            };
        }]);
    }

});

