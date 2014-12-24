define(function (require, exports, module) {

    //数据整合
    return function setApp(app) {
        app.controller('DataIntegrationIntegrationCtrl', ['$scope' , 'Merge', 'DataInteLog', 'DataSet', 'DataDefinitionAttr', 'RuleScript', 'TableInteRule',
            function ($scope, Merge, DataInteLog, DataSet, DataDefinitionAttr, RuleScript, TableInteRule) {

                $scope.view = 0;//页面局部跳转支持
                //0 表示整合记录列表
                //1 表示新增数据整合条件
                //2 表示设置整合规则级预览整合
                $scope.toView = function (view) {
                    $scope.view = view;
                };

                //预览方式切换
                $scope.viewMode = 0;
                $scope.toggleViewMode = function () {
                    $scope.viewMode = $scope.viewMode == 0 ? 1 : 0;
                };

                //设置规则类型参数
                $scope.params = {
                    ruleType: 'merge',
                    opUser: $scope.USER_INFO.id,
                    orderBy: 'CREATE_DATE',
                    order: 'DESC'
                };

                $scope.openDetail = function (item) {
                    $scope.detail({
                        columns: {
                            isArray: true,
                            params: angular.toJson({
                                dataSetId: item.resultSetId,
                                isPk:'N'
                            })
                        },
                        query: true,//默认false
                        params: {
                            dataSetId: item.resultSetId,
                            isPk:'N'
                        }
                    });
                };

                //整合记录查询
                $scope.pager = DataInteLog;

                var script = {};
                RuleScript.query({isArray: true, params: '{"ruleType":"merge"}'}, function (list) {
                    list.forEach(function (item) {
                        script[item.scriptCode] = item.scriptId
                    });
                });

                $scope.merge = {};
                $scope.rules = [
                    {}
                ];
                $scope.setRule = false;

                //1 创建新的合并
                $scope.createMerge = function () {
                    //整合数据
                    $scope.merge = {};

                    //整合依据
                    $scope.rules = [
                        {}
                    ];

                    $scope.setRule = false;

                    $scope.toView(1);
                };

                //2 查询数据集
                $scope.dbParams = {
                    createUser: $scope.USER_INFO.id
                };
                $scope.dbPager = DataSet;

                //3 设置整合规则
                $scope.setMergeRule = function () {
                    if (!$scope.merge.dbA || !$scope.merge.dbB) {
                        return alert('请选择数据集');
                    } else if (!$scope.merge.name) {
                        return alert('请填写合并后数据名称');
                    } else if (!$scope.merge.joinType) {
                        return alert('请选择连接方式');
                    }

                    //3.1 查询dbA的列信息
                    $scope.dbAColumns = getColumns($scope.merge._dbA);

                    //3.2 查询dbB的列信息
                    $scope.dbBColumns = getColumns($scope.merge._dbB);

                    //3.3 查询dbA的内容
                    $scope.dbAList = getDbContent($scope.merge._dbA);

                    //3.4 查询dbB的内容
                    $scope.dbBList = getDbContent($scope.merge._dbB);

                    $scope.setRule = true;//开始设置依据
                };

                //查询数据集列
                function getColumns(dbItem) {
                    return DataDefinitionAttr.query({
                        isArray: true,
                        params: angular.toJson({dataDefId: dbItem.dataDefId, isPk: 'N'})
                    })
                }

                //查询数据集内容
                function getDbContent(dbItem) {
                    return Merge.query({
                        mergeQuery: true,
                        params: angular.toJson({dataSetId: dbItem.dataSetId})
                    });
                }

                //整合状态
                $scope.stateType = [
                    {id: 'init', name: '待整合'},
                    {id: 'running', name: '整合中'},
                    {id: 'finished', name: '整合成功'},
                    {id: 'failure', name: '整合失败'}
                ];

                //选择数据集
                $scope.sel = function (db1, db2) {
                    $scope.selected._db1 = db1;
                    $scope.selected._db2 = db2;
                };

                //选中数据集
                $scope.selected = function (item) {
                    $scope.merge['_' + $scope.selected._db1] = item;
                    $scope.merge[$scope.selected._db1] = item.dataSetName;
                };

                //过滤器，选择数据集的时候，不要出现已选择过的数据集
                $scope.filterList = function (list) {
                    var db2 = $scope.merge['_' + $scope.selected._db2];
                    if (!list || !db2) {
                        return list || [];
                    } else if (list) {
                        return db2 ? list.filter(function (item) {
                            return item.dataSetId != db2.dataSetId;
                        }) : list;
                    }
                    return list || [];
                };

                //整合方式
                $scope.joinType = [
                    {id: 'left join', name: 'A为主数据'},
                    {id: 'right join', name: 'B为主数据'},
                    {id: 'inner join', name: 'AB交集'}
                ];

                //联动整合依据
                $scope.getBRoleKeys = function (a) {
                    if (!a)return [];
                    return $scope.dbBColumns.filter(function (b) {
                        return a.dataType == b.dataType;//只能出现类型一样的数据
                    });
                };

                //4 尝试开始整合
                $scope.startIntegration = function () {
                    var pass = !!$scope.rules.length,//整合规则必须1条以上
                        errs = [];

                    //整合依据不能为空
                    !pass && errs.push('整合依据是必须的');

                    //4.1 规则校验
                    $scope.rules.forEach(function (item, index) {
                        if (!item.a || !item.b) {//必须有的完整的规则
                            pass = false;//有任何一个不完整，则不许通过
                            errs.push(getMsg(item.a, item.b));
                        }
                    });

                    //4.2 校验未通过
                    if (!pass) {
                        errs.unshift('数据整合依据选择不完整 : ');
                        return alert(errs.join('\n'));
                    }

                    //4.3 整合配置
                    $scope.options = {
                        columns: [],
                        tr: $scope.dbAList,
                        dbA: $scope.dbAList,
                        dbB: $scope.dbBList,
                        dbAColumns: $scope.dbAColumns,
                        dbBColumns: $scope.dbBColumns
                    };

                    //4.4 初始化设置数据状态
                    $scope.resetData(true);

                    //4.5 初始化整合依据数据
                    $scope.rules.forEach(function (item) {
                        item.a._rule = item;
                        item.b._rule = item;
                        item.a._ruCls = item.b._ruCls = 'active';//整合依据样式
                    });

                    //可以开始整合
                    $scope.toView(2);
                };

                function getMsg(a, b) {
                    if (!a) {
                        return '  整合依据不能是空的'
                    }
                    if (!b) {
                        return '  ' + a.columnDesc + ' 没有选择对应整合依据'
                    }
                }

                //数据初始化
                function dataInit(selfDbName, selfCls, selfColumns, dbColumns2, selfDb, db, all) {
                    selfColumns.forEach(function (item) {
                        item._dbName = selfDbName;//标识是那个数据集
                        item._meDb = selfDb;//自己的数据集
                        item._pDb = db;//对应的数据集
                        item._key = dbColumns2;//标识对应的字段
                        item._self = selfColumns;//自己的字段
                        item._merge = null;//合并清空
                        item._mgCls = '';//清空样式
                        item._cls = selfCls;//自己默认的样式
                        item._availability = true;//是否有效整合
                        if (all) {
                            item._ruCls = '';
                            item._rule = null;//默认清空整合依据
                        }
                    });
                }

                //初始化数据
                $scope.resetData = function (all) {
                    dataInit('(B)', 'danger', $scope.dbBColumns, $scope.dbAColumns, $scope.dbBList, $scope.dbAList, all);
                    dataInit('(A)', 'warning', $scope.dbAColumns, $scope.dbBColumns, $scope.dbAList, $scope.dbBList, all);
                    $scope.options.change = !$scope.options.change;
                };

                //5 保存并且开始执行
                $scope.run = function () {
                    var columns = $scope.options.columns;

                    var postData = {
                        domerge: true,
                        ruleType: $scope.params.ruleType,
                        createUser: $scope.USER_INFO.id,
                        'dataSet1.dataSetId': $scope.merge._dbA.dataSetId,
                        'dataSet2.dataSetId': $scope.merge._dbB.dataSetId,
                        linkRule: $scope.merge.joinType,
                        resultDataSetName: $scope.merge.name
                    };

                    //整合依据
                    var columnDbARules = columns.filter(function (item) {
                        return item._rule && item._dbName === '(A)';
                    }).map(function (item) {
                        return item.columnName;
                    });

                    var columnDbBRules = columns.filter(function (item) {
                        return item._rule && item._dbName === '(B)';
                    }).map(function (item) {
                        return item.columnName;
                    });

                    postData.dataset1GroupColumn = columnDbARules.join(',');
                    postData.dataset2GroupColumn = columnDbBRules.join(',');


                    //合并和整合规则加入参数列表
                    var columnMerges = columns.filter(function (item) {
                        return item._merge || item._rule;
                    });
                    var columnRules = [];
                    for (var i = 0, ii = columnMerges.length, ia, ib; i < ii; i += 2) {
                        ia = columnMerges[i];
                        ib = columnMerges[i + 1];
                        columnRules.push({
                            ruleScript: {
                                scriptId: MathMap.scriptId(ia, ib)
                            },// 规则脚本库
                            dataSet1Col: ia.columnName,//数据A字段名
                            dataSet2Col: ib.columnName,//数据B字段名
                            resultColumnName: MathMap.columnName(ia, ib),//整合后的字段名
                            resultColumnType: MathMap.dataType(ia, ib),//整合后的字段类型
                            resultColumnLength: MathMap.length(ia, ib), //整合后的字段长度
                            resultColumnDesc: MathMap.columnDesc(ia, ib) // 结果集的字段描述
                        });
                    }

                    //不是合并字段，也不是整合依据，但是是有效的合并字段，也加入到合并列表
                    columns.filter(function (item) {
                        return !item._merge && !item._rule && item._availability;
                    }).forEach(function (item) {
                        var isdbA = item._dbName == '(A)';
                        columnRules.push({
                            ruleScript: {
                                scriptId: script[isdbA ? 'merge_a' : 'merge_b']
                            },// 规则脚本库
                            dataSet1Col: isdbA ? item.columnName : '',//数据A字段名
                            dataSet2Col: !isdbA ? item.columnName : '',//数据B字段名
                            resultColumnName: item.columnName,//整合后的字段名
                            resultColumnType: item.dataType,//整合后的字段类型
                            resultColumnLength: item.length, //整合后的字段长度
                            resultColumnDesc: item.columnDesc // 结果集的字段描述
                        });
                    });

                    var hasColumnName = {};
                    columnRules.forEach(function (column) {
                        if (hasColumnName[column.resultColumnName]) {
                            hasColumnName[column.resultColumnName] += 1;
                            column.resultColumnName += '_' + hasColumnName[column.resultColumnName];
                        } else {
                            hasColumnName[column.resultColumnName] = 1;
                        }
                    });

                    //非合并和整合规则加入参数列表
                    postData.rules = angular.toJson(columnRules);
                    Merge.save(postData, function (result) {
                        if (result.success == 'true') {
                            $scope.refresh && $scope.refresh('first', true);//刷新整合记录
                        }
                        $scope.toView(0);//返回整合记录
                    });
                };

                //整合后的属性计算
                var MathMap = {
                    scriptId: function (a, b) {
                        if (a._chk && b._chk) {
                            return script['merge_ab'];
                        }
                        return script[a._chk ? 'merge_a' : 'merge_b'];
                    },
                    dataType: function (a, b) {//获取字段类型
                        if (a._chk && b._chk) {
                            return 'VARCHAR2';
                        } else if (a._chk || a._rule) {
                            return a.dataType;
                        } else {
                            return b.dataType;
                        }
                    },
                    length: function (a, b) {//获取字段长度
                        if (a._chk && b._chk) {
                            if (a.dataType == 'NUMBER' && b.dataType == 'NUMBER') {
                                return '32';
                            } else if (a.dataType == 'VARCHAR2' && b.dataType == 'VARCHAR2') {
                                return (a.length || 0) + (b.length || 0) + 1;
                            } else if (a.dataType == 'VARCHAR2') {
                                return a.length + 32;
                            } else if (b.dataType == 'VARCHAR2') {
                                return b.length + 32;
                            }
                        } else if (a._chk || a._rule) {
                            return a.length;
                        } else {
                            return b.length;
                        }
                    },
                    columnDesc: function (a, b) {//获取字段描述
                        return (a._chk || a._rule) ? a.columnDesc : b.columnDesc;
                    },
                    columnName: function (a, b) {//获取字段名
                        return (a._chk || a._rule) ? a.columnName : b.columnName;
                    }
                };

                //删除整合日志
                $scope.removeInteLog = function (item) {
                    $scope._InteLog = item;
                };
                $scope.confirmRemove = function () {
                    TableInteRule.remove({ruleId: $scope._InteLog.ruleId}, $scope.refresh.bind(null, 'first', true));
                }
            }]);
    }

});