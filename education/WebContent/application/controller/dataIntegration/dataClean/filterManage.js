define(function(require, exports, module) {

    // 排重管理
    return function setApp(app) {
        //主查询页
        app.controller('DataIntegrationDataCleanFilterManageCtrl', [
                '$scope',
                'DataSet',
                'Rebuild',
                'DataDefinition',
                'DataInteLog',
                'RuleScript',
                'DataSetUnique',
                'TableInteRule',
                function($scope, DataSet, Rebuild, DataDefinition, DataInteLog, RuleScript, DataSetUnique,
                        TableInteRule) {
                    $scope.viewIndex = 0;
                    $scope.toView = function(index) {
                        $scope.viewIndex = index;
                    };
                    $scope.paramsQuery = {
                        "ruleType" : "unique",
                        "opUser" : $scope.USER_INFO.id,
                        "orderBy" : "CREATE_DATE",
                        "order" : "desc"
                    };
                    $scope.params = angular.copy($scope.paramsQuery);

                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
                    };
                    $scope.pager = DataInteLog;

                    $scope.defs = DataDefinition.query({
                        isArray : true,
                        params : angular.toJson({
                            "businessType" : "1",
                            "orderBy" : "CREATE_DATE",
                            "order" : "desc",
                            "createUser" : $scope.USER_INFO.id
                        })
                    });

                    // 查看
                    $scope.preview = function(item) {
                        $scope.previewData = DataSetUnique.post({
                            optype : "preview",
                            ruleId : item.ruleId,
                            dataDefId : item.dataDefId1
                        });
                    };

                    $scope.edit = function(item) {
                        $scope.$broadcast('edit', item || {}, !!item);
                        $scope.toView(1);
                    };
                    $scope.$on("editSuccess", function(e, data, isNew, autoRun) {
                        $scope.refresh("current", true);
                    });

                    // 跳向删除对话框
                    $scope.toRemove = function(item) {
                        $scope.rmObject = item;
                    };
                    // 跳向删除对话框
                    $scope.remove = function(item) {
                        TableInteRule.remove({
                            ruleId : $scope.rmObject.ruleId
                        }, $scope.refresh.bind(null, 'first', true));
                    };
                    // 重新执行
                    $scope.reExecute = function(ruleId) {
                        TableInteRule.post({
                            optType : "reExecute",
                            "ruleId" : ruleId
                        }, function() {
                            alert("程序后台执行中");
                            $scope.toView(0);
                        });
                    };
                    $scope.detail = function(item) {
                        // $scope.$broadcast('detail', item.resultDataSet);
                        // $scope.toView(100);
                        var config = {};
                        config.query = true;
                        config.columns = {
                            'isArray' : true,
                            'params' : angular.toJson({
                                'dataSetId' : item.resultSetId
                            })
                        };
                        config.params = {
                            'dataSetId' : item.resultSetId
                        };
                        $scope.showDataSetInfo(config);
                    };
                    
                } ]);
        
        //排重二级页
        app.controller('DataIntegrationDataCleanFilterManageEditCtrl', [ '$scope', 'DataSet', 'DataDefinitionAttr',
                'RuleScript', 'DataSetUnique', '$timeout','dialog',
                function($scope, DataSet, DataDefinitionAttr, RuleScript, DataSetUnique, timeout,dialog) {
                    $scope.$on('edit', function(e, data) {
                        $scope.data = data;
                        data.rules = $scope.rules = data.rules || [];
                        // 清理规则
                        $scope.uniqueTypes = RuleScript.query({
                            isArray : true,
                            params : angular.toJson({
                                'ruleType' : 'unique'
                            })
                        });
                        $scope.currDs = null;
                        $scope.resultDataSetName = null;
                        $scope.dlPager = DataSet;
                        $scope.isCheckedAll=false;
                    });
                    
                    // 过滤掉源数据信息中的排重依据字段
                    $scope.listFilter = function(e) {
                        // 返回布尔值：true 显示 false 不显示
                        console.log($scope.colinfo.length+"  "+$scope.uniqueColumns.length);
                        if($scope.colinfo.length==$scope.uniqueColumns.length){
                            $scope.isCheckedAll=true;
                        }else{
                            $scope.isCheckedAll=false;
                        }
                        if ($scope.uniqueColumns && $scope.uniqueColumns.length > 0) {
                            for (var i = 0; i < $scope.uniqueColumns.length; i++) {
                                if (e.columnName == $scope.uniqueColumns[i]) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    // ----------------------------数据集对话框 start-------------------------
                    $scope.openDialog = function() {
                        // 1.数据集分页查询
//                        $scope.dlParams = {
//                            "createUser" : $scope.USER_INFO.id,
//                            "orderBy" : "CREATE_DATE",
//                            "order" : "desc"
//                        }
//                        timeout(function() {
//                            $scope.dlRefresh('first', true);
//                        }, 1);
                        $scope.DsSelector(function(item){
                            return $scope.selectDs(item);
                        });
                    }
                    // 数据集选择
                    $scope.selectDs = function(item) {
                        if(item.dataAmount==0){
                            alert("所选择数据无记录，请选择其它数据进行操作！");
                            return false;
                        }
                        $scope.uniqueColumns = [];
                        $scope.currDs = item;
                        $scope.tableName = item.dataDefName;
                        // $scope.resultDataSetName = item.dataSetName + "_排重_" + $scope.dataStr();
                        DataSet.post({
                            'optType' : 'count',
                            'originalDstId' : item.dataSetId,
                            'targetDsType' : 'unique'
                        }, function(jsonData) {
                            $scope.resultDataSetName = item.dataSetName + "_排重" + (jsonData.count + 1);
                        });
                        var params = {
                            'dataDefId' : $scope.currDs.dataDefId
                        };
                        // 字段列表
                        $scope.colinfo = DataDefinitionAttr.query({
                            isArray : true,
                            'params' : angular.toJson(params)
                        }, function(in_colinfo) {
                            for ( var i in in_colinfo) {
                                in_colinfo[i].scriptId = $scope.uniqueTypes[0].scriptId;
                            }
                        });
//                        $("#select1").modal("hide");
                        return true;
                    }

                    // ----------------------------数据集对话框 end-------------------------
                    // 保存
                    $scope.save = function(isRun) {
                        // 1.校验排重后数据集
                        if (!$scope.resultDataSetName || $scope.resultDataSetName.trim().length == 0) {
                            alert("请输入'排重后数据名称' ！");
                            return false;
                        }
                        // 2.校验 排重依据
                        if ($scope.uniqueColumns.length == 0) {
                            alert("请设置排重依据！");
                            return false;
                        }
                        // 源数据信息:被过滤后的列表
                        var uniqueColumnRules = [];
                        for (var i = 0; i < $scope.colinfo.length; i++) {
                            var col = $scope.colinfo[i];
                            if ($scope.listFilter(col)) {
                                uniqueColumnRules.push(col);
                            }
                        }
                        // 规则－字段 键值对
                        var rules = [];
                        for (var i = 0; i < uniqueColumnRules.length; i++) {
                            var colItem = uniqueColumnRules[i];
                            rules.push({
                                ruleScript : {
                                    scriptId : colItem.scriptId
                                },
                                dataSet1Col : colItem.columnName
                            });
                        }
                        // 规则内容 －对应的字段数组
                        var params = {
                            dataSet1 : {
                                "dataSetId" : $scope.currDs.dataSetId
                            },
                            createUser : $scope.USER_INFO.id,
                            resultDataSetName : $scope.resultDataSetName,
                            dataset1GroupColumn : $scope.uniqueColumns.join(","),
                            columnRules : rules
                        }
                        console.log(angular.toJson(params));

                        DataSetUnique.post({
                            optype : "unique",
                            params : angular.toJson(params)
                        }, function() {
//                            alert("程序后台执行中");
                            timeout(function() {
                                $scope.$parent.$emit("editSuccess", $scope.data, $scope.isNew, isRun);
                                $scope.toView(0);
                                dialog("数据排重后台执行中，请稍后点击“查询”查看数据排重结果。", '友情提示' );
                            }, 300);
                        });
                    };

                } ]);

    }

});