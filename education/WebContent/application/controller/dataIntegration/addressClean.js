define(function(require, exports, module) {

    return function setApp(app) {
        // 地址清洗匹配
        app.controller('DataIntegrationAddressCleanCtrl', [
                '$scope',
                'DataDefinition',
                'DataInteLog',
                'DataSet',
                'DataDefinitionAttr',
                'AddressClean',
                '$timeout',
                'TableInteRule',
                'dialog',
                function($scope, DataDefinition, DataInteLog, DataSet, DataDefinitionAttr, AddressClean, timeout,
                        TableInteRule,dialog) {
                    $scope.data = {};
                    $scope.viewIndex = 0;
                    $scope.toView = function(index) {
                        $scope.viewIndex = index;
                    };
                    $scope.paramsQuery = {
                        "ruleType" : "address",
                        "opUser" : $scope.USER_INFO.id,
                        "orderBy" : "CREATE_DATE",
                        "order" : "desc"
                    };
                    $scope.params = angular.copy($scope.paramsQuery);

                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
                    };

                    // ------------------------第一页 查询列表内容-------------------------
                    $scope.defs = DataDefinition.query({
                        isArray : true,
                        params : angular.toJson({
                            "businessType" : "1",
                            "orderBy" : "CREATE_DATE",
                            "order" : "desc",
                            "createUser" : $scope.USER_INFO.id
                        })
                    });
                    $scope.pager = DataInteLog;

                    $scope.edit = function(item) {
                        $scope.$broadcast('edit', item || {}, !!item);
                        $scope.toView(1);
                        $scope.currDs = null;
                        $scope.data = {};
                        $scope.colinfo = [];
                    };

                    // ------------------------第一页 查询列表内容-------------------------
                    $scope.dlPager = DataSet;
                    $scope.openDialog = function(item) {
                        // 1.数据集分页查询
                        $scope.DsSelector(function(item){
                            return $scope.selectDs(item);
                        },{'excludeTypes':['post','address']});
                        
                    };

                    // 数据集选择
                    $scope.selectDs = function(item) {
                        if(item.dataAmount==0){
                            alert("所选择数据无记录，请选择其它数据进行操作！");
                            return false;
                        }
                        // 清空其它设置项
                        $scope.data = {};
                        $scope.currDs = item;
                        $scope.tableName = item.dataDefName;
                        var params = {
                            "dataDefId" : $scope.currDs.dataDefId
                        };
                        DataSet.post({
                            'optType' : 'count',
                            'originalDstId' : item.dataSetId,
                            'targetDsType' : 'address'
                        }, function(jsonData) {
                            $scope.data.resultDataSetName = item.dataSetName + "_地址匹配" + (jsonData.count + 1);
                        });
                        // 字段列表
                        $scope.colinfo = DataDefinitionAttr.query({
                            isArray : true,
                            "params" : angular.toJson(params)
                        }, function(columnList) {
                            // 地址、邮编列 默认选中规则
                        });
                        //$("#select1").modal("hide");
                        return true;
                    }

                    // 保存 执行
                    $scope.save = function() {
                        // 校验
                        if (!$scope.data.resultDataSetName || $scope.data.resultDataSetName.trim().length == 0) {
                            alert("请输入'清洗后数据名称' ！");
                            return false;
                        }
                        if (!$scope.data.addr) {
                            alert("请选择地址所有列！");
                            return false;
                        }

                        var colRule = [ {
                            dataSet1Col : $scope.data.addr,
                            colType : "address"
                        }, {
                            dataSet1Col : $scope.data.post,
                            colType : "post"
                        } ]
                        // 规则内容 －对应的字段数组
                        var params = {
                            dataSet1 : {
                                "dataSetId" : $scope.currDs.dataSetId
                            },
                            createUser : $scope.USER_INFO.id,
                            resultDataSetName : $scope.data.resultDataSetName,
                            columnRules : colRule
                        }
                        console.log(angular.toJson(params));
                        AddressClean.post({
                            optType : "execute",
                            params : angular.toJson(params)
                        }, function() {
//                            alert("程序后台执行中");
                            timeout(function() {
                                $scope.toView(0);
                                $scope.refresh && $scope.refresh('first', true);// 刷新整合记录
                                dialog("地址清洗匹配后台执行中，请稍后点击“查询”查看数据匹配结果。" , '友情提示' );
                            }, 300);
                        });
                    };
                    // -------------------------end----------------------------

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

//                    $scope.reExecute = function(ruleId) {
//                        TableInteRule.post({
//                            optType : "reExecute",
//                            "ruleId" : ruleId
//                        }, function() {
////                            alert("程序后台执行中");
//                            dialog("地址清洗匹配后台执行中，请稍后点击“查询”查看数据匹配结果。" , '友情提示' );
//                            $scope.refresh && $scope.refresh('first', true);// 刷新整合记录
//                            $scope.toView(0);
//                        });
//                    };

                    $scope.toBack = function() {
                        $scope.toView(0);
                    };

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

                    $scope.toRePort = function(item) {
                    	$scope.obj = item;
                        DataSet.get({
                			getData : true,
                            'params' : angular.toJson({
                    			'groupColumn' : 'MATCHED_DEPTH',
                    			'resultSetId' : item.resultSetId
                            })
                		}, function(result) {
                			$scope.data = result.data;
                			setReportData(result.data);
                        });
                        
                        DataSet.get({
                			address : true,
                            'params' : angular.toJson({
                    			'type' : 'post',
                    			'resultSetId' : item.resultSetId,
                    			'ruleId'      : item.ruleId
                            })
                		}, function(result) {
                			$scope.postColumn = result.data;
                        });      
                        
                        DataSet.get({
                			address : true,
                            'params' : angular.toJson({
                    			'type' : 'address',
                    			'resultSetId' : item.resultSetId,
                    			'ruleId'      : item.ruleId
                            })
                		}, function(result) {
                			$scope.addressColumn = result.data;
                        });  
                    };

                    function setReportData(data) {
                        var config = {
                            chart : {
                                plotBackgroundColor : null,
                                plotBorderWidth : null,
                                plotShadow : false
                            },
                            title : {
                                text : null
                            },
                            tooltip : {
                                pointFormat : '{series.name}: <b>{point.percentage:.2f}%</b>'
                            },
                            plotOptions : {
                                pie : {
                                    allowPointSelect : true,
                                    cursor : 'pointer',
                                    dataLabels : {
                                        enabled : true,
                                        format : '<b>{point.name}</b>: {point.percentage:.2f} %',
                                        style : {
                                            color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                        }
                                    }
                                }
                            },
                            series : [ {
                                type : 'pie',
                                name : '比重',
                                data : data
                            } ]
                        };
                        
                        $scope.chartConfig = config;
                    }

                } ]);

        // 1:数据集管理- 详情
        app.controller('DataIntegrationDataCleanContentCtrl', [ '$scope', '$timeout', 'DataDefinitionAttr',
                'DataDefinition', 'DataSetContent',
                function($scope, $timeout, DataDefinitionAttr, DataDefinition, DataSetContent) {
                    $scope.$on('detail', function(e, data) {// 两个参数 tableName dataDefId
                        $scope.data = data;
                        $scope.initTitle();
                        $scope.initPager(data.tableName);
                    });

                    // 初始化分页查询内容 每次进入时的清空处理
                    $scope.initPager = function(tableName) {
                        if (!$scope.contentParams)
                            $scope.contentParams = {};
                        $scope.contentParams.tableName = tableName;// 当前表名
                        if (!$scope.detailPager) {
                            $scope.detailPager = DataSetContent;
                        }
                    };

                    // 初始化字段查询列表
                    $scope.initTitle = function() {
                        var params = {
                            'dataDefId' : $scope.data.dataDefId,
                            'hasId' : '1'
                        };
                        $scope.titleList = DataDefinitionAttr.query({
                            isArray : true,
                            'params' : angular.toJson(params)
                        });
                    };

                    $scope.toBack = function() {
                        $scope.toView(0);
                    };

                } ]);
    }

});