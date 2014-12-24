define(function(require, exports, module) {

    return function setApp(app) {
        // 清洗管理
        app.controller('DataIntegrationDataCleanManageCtrl',
                [
                        '$scope',
                        'DataSet',
                        'Rebuild',
                        'DataDefinition',
                        'DataInteLog',
                        'RuleScript',
                        'DataSetClean',
                        'TableInteRule',
                        function($scope, DataSet, Rebuild, DataDefinition, DataInteLog, RuleScript, DataSetClean,
                                TableInteRule) {
                            $scope.viewIndex = 0;
                            $scope.paramsQuery = {
                                "ruleType" : "clean",
                                "opUser" : $scope.USER_INFO.id,
                                "orderBy" : "CREATE_DATE",
                                "order" : "desc"
                            };
                            $scope.params = angular.copy($scope.paramsQuery);
                            $scope.resetQuery = function() {
                                $scope.params = angular.copy($scope.paramsQuery);
                            };
                            $scope.pager = DataInteLog;
                            $scope.toView = function(index) {
                                $scope.viewIndex = index;
                            };
                            $scope.defs = DataDefinition.query({
                                isArray : true,
                                params : angular.toJson({
                                    "businessType" : "1",
                                    "orderBy" : "CREATE_DATE",
                                    "order" : "desc",
                                    "createUser" : $scope.USER_INFO.id
                                })
                            });

                            // 新增
                            $scope.edit = function(item) {
                                $scope.$broadcast("edit", item || {}, !!item);
                                $scope.toView(1);
                            };

                            // 查看
                            $scope.preview = function(item) {
                                $scope.previewData = DataSetClean.post({
                                    optype : "preview",
                                    ruleId : item.ruleId,
                                    dataDefId : item.dataDefId1
                                });
                            };
                            // 清洗报告
                            $scope.report = function(item) {
                                $scope.$broadcast("report", item || {}, !!item);
                                $scope.toView(2);
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

        // 数据清洗
        app.controller('DataIntegrationDataCleanManageEditCtrl', [ '$scope', 'DataSet', 'DataDefinitionAttr',
                'RuleScript', 'DataSetClean', '$timeout','dialog',
                function($scope, DataSet, DataDefinitionAttr, RuleScript, DataSetClean, timeout,dialog) {
                    $scope.tableName = "数据表名称";
                    // 字段列表
                    $scope.tableKeys = [];
                    $scope.$on("edit", function(e, data, isNew) {
                        $scope.tableName = data.tableName || "";
                        $scope.data = data;
                        data.rules = $scope.rules = data.rules || [];
                        $scope.isNew = isNew;
                        $scope.currDs = null;
                        $scope.resultDataSetName = null;
                        $scope.dlPager = DataSet;

                        // 清理规则
                        $scope.cleanTypes = RuleScript.query({
                            isArray : true,
                            params : angular.toJson({
                                ruleType : "clean",
                                isChd : "N",
                                orderBy:"create_date",
                                order:"asc"
                            })
                        }, function(data) {
                            for (var i = 0; i < data.length; i++) {
                                if (data[i].scriptId==6) {
                                    var chdRules=data[i].chdRuleScript;
                                    $scope.commonTypeList=[];
                                    for(var j=0;j<chdRules.length;j++){
                                        $scope.commonTypeList.push({
                                            "optType" : chdRules[j].scriptId,
                                            "optName" : chdRules[j].scriptName
                                        });
                                    }
                                }
                            }
                        });
                    });

                    // ----------------------------数据集对话框 start-------------------------
                    $scope.openDialog = function() {
                        // 1.数据集分页查询
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
                        $scope.currDs = item;
                        $scope.tableName = item.dataDefName;

                        DataSet.post({
                            'optType' : 'count',
                            'originalDstId' : item.dataSetId,
                            'targetDsType' : 'clean'
                        }, function(jsonData) {
                            $scope.resultDataSetName = item.dataSetName + "_清洗" + (jsonData.count + 1);
                        });
                        var params = {
                            "dataDefId" : $scope.currDs.dataDefId
                        };
                        // ----------------------------数据集对话框 end-------------------------
                        // 字段列表
                        $scope.colinfo = DataDefinitionAttr.query({
                            isArray : true,
                            "params" : angular.toJson(params)
                        });
                        //$("#select1").modal("hide");
                        return true;
                    }

                    // $scope.cleanTypes = ['去除数字', '乱字符', '不合规姓名', '单位特征字', '行业特征字'];
                    // $scope.replaces = ['全部替换', '头替换', '尾替换'];

                    // 测试数据
                    // $scope.cleanTypes = ['去除数字', '乱字符', '不合规姓名', '单位特征字',
                    // '行业特征字','通用去除规则','规则参数设置','剔除关键字'].map(function(item , index){
                    // return {id : index , scriptName : item};
                    // });

                    $scope.initEdit = function() {
                        $scope.clean = {
                            "type" : null,
                            "columns" : [],
                            "columnNames" : []
                        };
                    }
                    $scope.validData = function(item) {
                        // 1.校验 清洗方式 字段不为空
                        if (!item || !item.type) {
                            alert("请选择清洗方式！");
                            return false;
                        }
                        // 2.校验 特殊清洗方式的子规则
                        var rule = item;
                        var chdRule = rule.chdRule;
                        if (chdRule) {
                            var strLength = chdRule.strLength;
                            var startIndex = chdRule.startIndex;
                            var keyword = chdRule.keyword;
                            var commonRules = chdRule.commonRules;
                            // 6 = 通用去除规则 clean.chdRule.commonRules
                            console.log(rule.type.scriptId);
                            if (rule.type.scriptId == 6) {
                                // 1.列表为空 暂进不会出现
                                if (!(commonRules && commonRules.length > 0)) {
                                    alert("请设置 自定义替换服务 内容项");
                                    return false;
                                }
                                // 2.通用去除规则 子项存在空列 item.optType item.src item.to
                                for (var i = 0; i < commonRules.length; i++) {
                                    var comRule = commonRules[i];
                                    if (!(comRule.src && comRule.src.length > 0)) {
                                        alert("请设置自定义替换服务第 " + (i + 1) + " 条记录的 原有字符 项");
                                        return false;
                                    }

                                    if (!(comRule.to && comRule.to.length > 0)) {
                                        alert("请设置自定义替换服务第 " + (i + 1) + " 条记录的 替换为 项");
                                        return false;
                                    }
                                }
                                // 3.初始化操作名称
                                for (var i = 0; i < commonRules.length; i++) {
                                    var comRule = commonRules[i];
                                    for (var j = 0; i < $scope.commonTypeList.length; j++) {
                                        var comType = $scope.commonTypeList[j];
                                        if (comType.optType == comRule.optType) {
                                            comRule.optName = comType.optName;
                                            break;
                                        }
                                    }
                                }
                            }
                            // 12 = 按起始位置和长度截取 clean.chdRule.startIndex clean.chdRule.strLength
                            if (rule.type.scriptId == 13) {
                                if (!(startIndex && startIndex.length > 0)) {
                                    alert("请输入起始位置");
                                    return false;
                                }
                                if (!(strLength && strLength.length > 0)) {
                                    alert("请输入截取长度");
                                    return false;
                                }
                            }
                            // 11= 剔除关键字 clean.chdRule.keyword
                            if (rule.type.scriptId == 12) {
                                if (!(keyword && keyword.length > 0)) {
                                    alert("请输入 关键字");
                                    return false;
                                }
                            }
                        }

                        if (!item || !item.type || !item.columns || item.columns.length == 0) {
                            alert("请选择清洗方式及字段！");
                            return false;
                        }
                        return true;
                    }
                    // 新增规则
                    $scope.addRule = function(item, obj) {
                        var isExistRule = false;
                        // 1.校验
                        if (!$scope.validData(item)) {
                            return false;
                        }
                        console.log(angular.toJson(item));
                        // 2.kv 分别存储
                        item.columnNames = [];
                        for (var i = 0; i < item.columns.length; i++) {
                            var key = item.columns[i];
                            for (var j = 0; j < $scope.colinfo.length; j++) {
                                if ($scope.colinfo[j].columnName == key) {
                                    item.columnNames.push($scope.colinfo[j].columnDesc);
                                    break;
                                }
                            }
                        }
                        for (var i = 0; i < $scope.rules.length; i++) {
                            if ($scope.rules[i].type.scriptName == item.type.scriptName&&!item.chdRule) {
                                if (confirm("已存在相同清洗方式设置，是否覆盖原清洗方式设置？")) {
                                    $scope.rules[i].columns = angular.copy(item.columns);
                                    $scope.rules[i].columnNames = angular.copy(item.columnNames);
                                    console.log(angular.copy(item.chdRule));
                                    $scope.rules[i].chdRule = angular.copy(item.chdRule);
                                    $("#put1").modal("hide");
                                }
                                isExistRule = true;
                                break;
                            }
                        }
                        if (!isExistRule) {
                            $scope.data.rules.push(angular.copy(item));
                            $("#put1").modal("hide");
                        }
                    };

                    $scope.setRemove = function(item, index) {
                        $scope.removeIndex = index;
                    };
                    $scope.removeAct = function() {
                        $scope.data.rules.splice($scope.removeIndex, 1)
                    };

                    // 保存 执行
                    $scope.save = function(isRun) {
                        // 校验
                        if (!$scope.resultDataSetName || $scope.resultDataSetName.trim().length == 0) {
                            alert("请输入'清洗后数据名称' ！");
                            return false;
                        } else {
                            if ($scope.resultDataSetName.trim().length > 50) {
                                alert("清洗后数据名称 最大长度为50个字符！");
                                return false;
                            }
                        }

                        if ($scope.data.rules.length == 0) {
                            alert("请设置清洗规则！");
                            return false;
                        }

                        console.log(angular.toJson($scope.data.rules));
                        // 规则－字段 对
                        var rules = [];
                        // 所有规则列
                        for (var i = 0; i < $scope.data.rules.length; i++) {
                            var ruleItem = $scope.data.rules[i];
                            // 规则内的所有列
                            for (var j = 0; j < ruleItem.columns.length; j++) {
                                var _columnRule = {
                                    ruleScript : {
                                        scriptId : ruleItem.type.scriptId
                                    },
                                    dataSet1Col : ruleItem.columns[j],
                                    subRuleScript : [],
                                    batchNo:"p_"+i,
                                    seqNo:i
                                };

                                if (ruleItem.chdRule) {
                                    // 1. 通用替换
                                    if (ruleItem.chdRule.commonRules) {
                                        var commonRules = ruleItem.chdRule.commonRules;
                                        // 遍历通用
                                        for (var k = 0; k < commonRules.length; k++) {
                                            var commonRule = commonRules[k];
                                            // 组织所有的子脚本及参数值
                                            var _subRuleScript = {
                                                scriptId : commonRule.optType,
                                                colName : ruleItem.columns[j],
                                                seqNo : k,
                                                subScriptParams : [ {
                                                    paraCd : "src",
                                                    paraVal : commonRule.src,
                                                    seqNo : 1
                                                }, {
                                                    paraCd : "to",
                                                    paraVal : commonRule.to,
                                                    seqNo : 2
                                                } ]
                                            }
                                            _columnRule.subRuleScript.push(_subRuleScript);
                                        }
                                    }
                                    // 2.截取
                                    if (ruleItem.chdRule.startIndex) {
                                        _columnRule.scriptParams = [ {
                                            paraCd : "startIndex",
                                            paraVal : ruleItem.chdRule.startIndex,
                                            seqNo : 1
                                        }, {
                                            paraCd : "strLength",
                                            paraVal : ruleItem.chdRule.strLength,
                                            seqNo : 2
                                        } ];
                                    }
                                    // 3.关键字
                                    if (ruleItem.chdRule.keyword) {
                                        _columnRule.scriptParams = [ {
                                            paraCd : "keyword",
                                            paraVal : ruleItem.chdRule.keyword,
                                            seqNo : 1
                                        } ];
                                    }
                                }
                                rules.push(_columnRule);
                            }
                        }
                        // 数据集id
                        // 当前用户
                        // 规则内容 －对应的字段数组
                        var params = {
                            dataSet1 : {
                                "dataSetId" : $scope.currDs.dataSetId
                            },
                            createUser : $scope.USER_INFO.id,
                            resultDataSetName : $scope.resultDataSetName,
                            columnRules : rules
                        }
                        console.log(angular.toJson(params));
                        DataSetClean.post({
                            optype : "clean",
                            params : angular.toJson(params)
                        }, function() {
//                            alert("程序后台执行中");
                            timeout(function() {
                                $scope.$parent.$emit("editSuccess", $scope.data, $scope.isNew, isRun);
                                $scope.toView(0);
                                dialog("地址清洗匹配后台执行中，请稍后点击“查询”查看数据匹配结果。" , '友情提示' );
                            }, 300);
                        });
                    };
                } ]);

        // 数据报告
        app.controller('DataIntegrationDataCleanManageReportCtrl', [ '$scope', 'DataSetClean','$log',
                function($scope, DataSetClean , $log) {
                    $scope.$on('report', function(e, data) {
                        $scope.data = data;
                        $scope.reportData = DataSetClean.post({
                            optype : "report",
                            ruleId : data.ruleId,
                            dataDefId : data.dataDefId1
                        }, function(jsonData) {
                            $scope.rules = jsonData.titleList;
                            $scope.cleanState = jsonData.result;
                            $log.log('rules',jsonData);
                        });
                    });

                    // $scope.rules = [ '头尾空格去除', '称谓去除', '数字去除', '敏感词去除' ];
                    $scope.checkNull = function(item) {
                        if ($.isPlainObject(item)) {
                            for ( var key in item) {
                                if (key != '$$hashKey') {
                                    return false;
                                }
                            }
                            return true;
                        }
                        return !item;
                    };
                } ]);
    }

});