define(function(require, exports, module) {

	// 数据重构
	return function setApp(app) {
		app.controller('DataIntegrationRebuildCtrl', [
				'$scope',
				'Rebuild',
				'DataInteLog',
				'DataSet',
				'DataDefinitionAttr',
				'RuleScript',
				'TableInteRule',
				'DataDefinition',
				function($scope, Rebuild, DataInteLog, DataSet,
						DataDefinitionAttr, RuleScript, TableInteRule,
						DataDefinition) {
					// 1.页面切换
					$scope.view = 0;
					$scope.toPage = function(index) {
						$scope.view = index;
					};
					$scope.edit = function() {
						$scope.toPage(1);
						// 1.1加载重构的规则脚本
						$scope.ruleScriptList = $scope.ruleScriptList
								|| RuleScript.query({
									isArray : true,
									params : {
										ruleType : "rebuild"
									}
								});
						// 1.2
						$scope.param1 = {
							createUser : $scope.USER_INFO.id
						};
						
				                                     
						      
						$scope.DataSetPager = DataSet;
						$scope.DataSetParams = {
			                    createUser: $scope.USER_INFO.id,    
			                	order:  'desc',                
			                	orderBy:'CREATE_DATE'
			            };               
					}

					$scope.stateType = [ {
						id : 'init',
						name : '待重构'
					}, {
						id : 'running',
						name : '重构中'
					}, {
						id : 'finished',
						name : '重构成功'
					}, {
						id : 'failure',
						name : '重构失败'
					} ];

					// 2.初始化数据定义列表
					$scope.dataDefList = DataDefinition.query({
						isArray : true,
						_box : false,
						params : {
							createUser : $scope.USER_INFO.id
						}
					});

					// 3. 查询重构的整合日志
					$scope.params = {
						ruleType : 'rebuild',
						opUser : $scope.USER_INFO.id,
						orderBy : 'CREATE_DATE',
						order : 'DESC'
					};
					$scope.pager = DataInteLog;

					// 5.删除整合日志
					$scope.removeInteLog = function(item) {
						$scope._InteLog = item;
					}
					$scope.confirmRemove = function() {
						TableInteRule.remove({
							ruleId : $scope._InteLog.ruleId
						}, $scope.refresh.bind(null, 'first', true));
					}

					
					// 6.查询数据集的属性定义，查询
					$scope.rebuild = {};
					$scope.selectDA = function(item) {
						$scope.rebuild.dataSet = item;
						$scope.rebuild.dataSetName = item.dataSetName;
						DataSet.post({'optType':'count','originalDstId':item.dataSetId,'targetDsType':'rebuild'},function(jsonData){
	                        $scope.rebuild.resultSetName = item.dataSetName + "_重构"+ (jsonData.count+1);
	                    });
						$scope.dbAColumns = getColumns(item);
						$("#select1").modal("hide");
					}

					// 查询数据集的属性定义
					function getColumns(dbItem) {
						return DataDefinitionAttr.query({
							isArray : true,
							params : angular.toJson({
								dataDefId : dbItem.dataDefId,
								isDisplay:'Y'
							})
						})
					}
					
					$scope.rules=[];
					$scope.addColumnRule=function(){
						$scope.rules.push({
							ruleScript:$scope.rebuild.ruleScript,
							column:$scope.rebuild.column
						});
					}
					//8.执行重构
					$scope.executeRun=function(){
						var postData = {
							dorebuild : true,
							ruleType : $scope.params.ruleType,
							createUser : $scope.USER_INFO.id,
							'dataSet1.dataSetId' : $scope.rebuild.dataSet.dataSetId,
							resultDataSetName : $scope.rebuild.resultSetName
						};
						var columnRules=$scope.rules.map(function(item){
								return {
									ruleScript: {
		                                scriptId: item.ruleScript.scriptId
		                            },
		                            dataSet1Col: item.column.columnName//数据A字段名
								 };
							});
						postData.rules = angular.toJson(columnRules);
						
						Rebuild.save(postData , function(result){
	                        if(result.success == 'true'){
	                            $scope.refresh && $scope.refresh('first' , true);//刷新整合记录
	                        }
	                        $scope.toPage(0);//返回整合记录
	                    });
					}//end executeRun
					
					$scope.openDetail = function (item) {
	                    $scope.detail({
	                        columns: {
	                            isArray: true,
	                            params: angular.toJson({
	                                dataSetId: item.resultSetId
	                            })
	                        },
	                        query: true,//默认false
	                        params: {
	                            dataSetId: item.resultSetId
	                        }
	                    });
	                };   
					//9.重置所有设置
					$scope.resetObj=function(){
						$scope.rebuild = {};
						$scope.dbAColumns=[];
						$scope.rules=[];
					}
				} ]);
	}
});