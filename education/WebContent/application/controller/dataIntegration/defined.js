define(function(require, exports, module) {

    return function setApp(app) {
        // 0 数据定义
        app.controller('DataIntegrationDefinedCtrl', [ '$scope', 'DataDefinition', 'DataDefinitionAttr', '$timeout',
                function($scope, DataDefinition, DataDefinitionAttr, timeout) {
                    $scope.paramsQuery={
                        'businessType' : '1',
                        'createUser':$scope.USER_INFO.id,
                        'orderBy':"CREATE_DATE",
                        'order':'desc'
                    };
                    $scope.params = angular.copy($scope.paramsQuery);
                    $scope.pager = DataDefinition;
                    $scope.list = [];
                    $scope.current = 0;
                    $scope.data = {};
                    var aNew;
                    $scope.addNew = function() {
                        $scope.$broadcast('edit', aNew = {}, true);// 处理编辑
                    };
                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
                    };
                    // 跳转到删除页
                    $scope.toRemove = function(itemKey) {
                        $scope.toRemoveItemKey = itemKey;
                    };
                    // 跳转到删除页
                    $scope.remove = function() {
                        var params = {
                            dataDefId : $scope.toRemoveItemKey
                        };
                        DataDefinition.remove({
                            params : angular.toJson(params)
                        }, function(jsonData) {
                            if (jsonData.megCode && jsonData.megCode == '1') {
                                alert("数据定义已被使用，请删除相应数据后删除！");
                            }
                            $scope.refresh('current', true);
                        });
                    };

                    // 编辑数据定义项
                    $scope.edit = function(item) {
                        DataDefinition.post({
                            dataDefId : item.dataDefId,
                            valid : 1
                        }, function(jsonData) {
                            if (jsonData.megCode && jsonData.megCode == '1') {
                                alert("数据定义已被使用，不可修改！");
                                return false;
                            }
                            $scope.toNextPage(1);
                            $scope.$broadcast('edit', item);// 处理编辑
                        });
                    };

                    $scope.preview = function(item) {
                        $scope.previewData = item;
                        var params = {
                            dataDefId : item.dataDefId
                        }
                        $scope.list = DataDefinitionAttr.query({
                            isArray : true,
                            params : angular.toJson(params)
                        },function(colms){
                            if(colms && colms.length){
                                colms.forEach(function(item){
                                    if(item.conceptModelId){
                                        item.conceptModelDesc=item.columnDesc;
                                    }
                                });
                            }
                      });
                    };

                    // 当数据项处理完成
                    $scope.$on('success', function(e, isNew) {
                        isNew && $scope.list.push(aNew);
                        $scope.refresh( 'current', true);
                    });

                    // 跳转页面
                    $scope.toNextPage = function(index) {
                        $scope.current = index;
                    }
                } ]);

        // 数据定义- 新增/修改 (2,3)
        app.controller(
                        'DataIntegrationDefinedEditCtrl',
                        [
                                '$scope',
                                'ConceptModelDic',
                                'DataTypeDic',
                                'DataDefinitionAttr',
                                'DataDefinition',
                                '$timeout',
                                function($scope, ConceptModelDic, DataTypeDic, DataDefinitionAttr, DataDefinition,
                                        timeout) {
                                    // ---------------------------初始化开始---------------------------
                                    $scope.$on('edit', function(e, data, isNew) {
                                        $scope.def_key = {
                                            length : null,
                                            columnName : null,
                                            columnDesc : null,
                                            dataType : 'VARCHAR2'
                                        };
                                        $scope.data = angular.copy(data);
                                        $scope.oldData = data;
                                        data.list = $scope.dataList = data.list || [];
                                        $scope.isNew = isNew;
                                        $scope.isShowLength = true;// 长度初始化显示
                                        $scope.isChange = false;
                                        $scope.clsMap = {};
                                        if (!isNew) {
                                            $scope.findAttr();
                                        }

                                        // 概念模式初始化
                                        $scope.cls = ConceptModelDic.query({
                                            isArray : true,
                                            params : {}
                                        }, function(jsonData) {
                                            for (var i = 0; i < jsonData.length; i++) {
                                                $scope.clsMap[jsonData[i].conceptModelId] = jsonData[i];
                                            }
                                        });

                                        // 字段类型初始化
                                        $scope.types = DataTypeDic.query({
                                            isArray : true,
                                            params : {}
                                        });

                                    });
                                    // ---------------------------初始化结束---------------------------

                                    // 根据数据类型 分别测试提示
                                    var configVarchar = {
                                        valid : 'numValid',
                                        name : 'numValid',
                                        reset : [ "floatValid" ]
                                    };
                                    var configNumber = {
                                        valid : 'floatValid',
                                        name : 'floatValid',
                                        reset : [ "numValid" ]
                                    };
                                    $scope.customeValid = function(val) {
                                        if (!$scope.key)
                                            $scope.key = {};
                                        if ($scope.key.dataType == 'VARCHAR2') {
                                            return configVarchar;
                                        } else {
                                            return configNumber;
                                        }
                                    }
                                    $scope.numValid = function(val) {
                                        if (/^[1-9][0-9]*$/.test(val)) {
//                                            var test4 = /^[1-2][0-9]{0,3}$/.test(val);
                                            var test3 = /^[1-9][0-9]{0,3}$/.test(val);
                                            if (test3) {
                                                var numVal = parseFloat(val);
                                                if (numVal <= 2000) {
                                                    return true;
                                                }
                                            }
                                            return false;
                                        }
                                        return true;
                                    }
                                    $scope.floatValid = function(val) {
                                        if (/^[1-9][0-9]*$/.test(val)) {
                                            var numVal = parseFloat(val);
                                            return (numVal <= 38);
                                        }
                                        return true;
                                    }

                                    $scope.reset = function() {
                                        if($scope.editItem){
                                            $scope.key = angular.copy($scope.editItem);
                                            $scope.s_key = $scope.key.conceptModelId;
                                        }else{
                                            $scope.key = angular.copy($scope.def_key);
                                            $scope.s_key = null;
                                        }
                                        $scope.initShowLength($scope.key);
                                    }

                                    // 数据定义的保存
                                    $scope.saveDef = function() {
                                        if (!$scope.data.dataDefName) {
                                            alert("请填写数据定义名称")
                                        } else {
                                            $scope.toNextPage(2);
                                        }
                                    }

                                    // 字段信息的保存
                                    $scope.save = function(key, element) {
                                        $scope.prveSkey = null;
                                        if (!$scope.validColName())
                                            return true;
                                        if (key.isEdit) {// 修改
                                            angular.extend($scope.editItem || {}, key);
                                        } else {// 新增
                                            key.dataDefId = $scope.data.dataDefId;
                                            $scope.dataList.push(key);
                                        }
                                        $scope.isChange = true;
                                        $scope.key = $scope.s_key = null;
                                        $('#push1').modal('hide');
                                        $scope.rebuildColName();
                                    };
                                    // 列名 自动生成
                                    $scope.rebuildColName = function() {
                                        for ( var i in $scope.dataList) {
                                            if (!$scope.dataList[i].conceptModelId) {// 不存在概念模型
                                                $scope.dataList[i].columnName = "COLUMN_" + (parseInt(i) + 1);
                                            }
                                        }
                                    }

                                    // 内部使用的公共方法：查看所有属性
                                    $scope.findAttr = function() {
                                        var params = {
                                            dataDefId : $scope.data.dataDefId
                                        }
                                        $scope.dataList = DataDefinitionAttr.query({
                                            isArray : true,
                                            params : angular.toJson(params)
                                        });
                                    };

                                    // 字段删除对话框
                                    $scope.setRemove = function(item, index) {
                                        $scope.index = index;
                                        $scope.removeItem = item;
                                        $scope.$parent.$emit('data', item);
                                    };

                                    // 字段删除
                                    $scope.remove = function() {
                                        $scope.isChange = true;
                                        $scope.dataList.splice($scope.index, 1);
                                        $scope.index = $scope.removeItem = null;
                                        $scope.rebuildColName();
                                    };

                                    // 级联保存
                                    $scope.saveAll = function() {
                                        $scope.data.businessType = '1';
                                        $scope.data.createUser = $scope.USER_INFO.id;
                                        $scope.data.columns = $scope.dataList;
                                        if (!$scope.dataList || $scope.dataList.length == 0) {
                                            alert("请添加字段信息！");
                                            return false;
                                        }
                                        for ( var i in $scope.data.columns) {// 去除是否编辑标志，解决后台json转换出错
                                            delete $scope.data.columns[i].isEdit;
                                        }
                                        if ($scope.data.id) {// 修改
                                            if (!$scope.isChange
                                                    && ($scope.data.dataDefName == $scope.oldData.dataDefName && $scope.data.dataDefDesc == $scope.oldData.dataDefDesc)) {
                                                $scope.ok();
                                                return false;
                                            }
                                            DataDefinition.post({
                                                optType : "updateAll",
                                                params : angular.toJson($scope.data)
                                            }, function(jsonData) {
                                                if (jsonData.success == 'true') {
                                                    $scope.data.id = jsonData.id;
                                                    $scope.data.dataDefId = jsonData.dataDefId;
//                                                    $scope.findAttr();
                                                    $scope.ok();
                                                } else {
                                                    if (jsonData.message) {
                                                        alert(jsonData.message);
                                                    } else {
                                                        alert("操作失败,请重试！");
                                                    }
                                                }
                                            });
                                        } else {// 新增
                                            DataDefinition.put({
                                                optType : "saveAll",
                                                params : angular.toJson($scope.data)
                                            }, function(jsonData) {
                                                if (jsonData.success == 'true') {
                                                    $scope.data.id = jsonData.id;
                                                    $scope.data.dataDefId = jsonData.dataDefId;
                                                    $scope.findAttr();
                                                    $scope.ok();
                                                } else {
                                                    alert("操作失败,请重试！");
                                                }
                                            });
                                        }
                                    };

                                    $scope.conceptModelDicInit = function(cmId) {
                                        if (!cmId) {
                                            cmd = {};
                                        } else {
                                            cmd = $scope.clsMap[cmId];
                                        }
                                        if (!$scope.key)
                                            $scope.key = {};

                                        for (var i = 0; i < $scope.dataList.length; i++) {// 新增时editItem＝undefined
                                            if ($scope.editItem && $scope.editItem.columnName) {// 当为修改时
                                                // 1.与editItem 相同说明没改类型
                                                if (cmd.conceptModelName != $scope.editItem.columnName && // 不是修改的记录
                                                cmd.conceptModelName == $scope.dataList[i].columnName) {// 没有修改概念类型
                                                    alert("此数据模型已被使用,请选择其它模型！");
                                                    if ($scope.prveSkey) {// 3.选择了其它模型 恢复为上次选择内容
                                                        timeout(function() {
                                                            $scope.s_key = $scope.prveSkey;
                                                        }, 1);
                                                    } else {// 第一次
                                                        timeout(
                                                                function() {
                                                                    $scope.s_key = angular
                                                                            .copy($scope.editItem.conceptModelId);
                                                                }, 1);
                                                    }
                                                    return false;
                                                }
                                            } else {
                                                if (cmd.conceptModelName == $scope.dataList[i].columnName) {// 新增时
                                                    alert("此数据模型已被使用,请选择其它模型！");
                                                    if ($scope.prveSkey) {// 3.选择了其它模型 恢复为上次选择内容
                                                        timeout(function() {
                                                            $scope.s_key = $scope.prveSkey;
                                                        }, 1);
                                                    } else {// 第一次
                                                        $scope.s_key = undefined;
                                                        timeout(function() {
                                                            $scope.s_key = undefined;
                                                        }, 1);
                                                    }
                                                    return false;
                                                }
                                            }
                                        }

                                        $scope.key.columnName = cmd.conceptModelName;
                                        $scope.key.columnDesc = cmd.conceptModelDesc;
                                        $scope.key.dataType = cmd.dataType;
                                        $scope.key.length = cmd.length;
                                        $scope.key.conceptModelId = cmd.conceptModelId;
                                        $scope.initShowLength($scope.key);
                                        $scope.prveSkey = angular.copy(cmd.conceptModelId);
                                    };

                                    // 修改属性
                                    $scope.editAttr = function(editObj) {
                                        editObj.isEdit = true;
                                        $scope.editItem = editObj;
                                        $scope.key = angular.copy(editObj);
                                        $scope.s_key = $scope.key.conceptModelId;
                                        $scope.initShowLength(editObj);
                                    };

                                    // 选择数据类型时的操作
                                    $scope.initLength = function(editObj) {
                                        editObj.length = null;
                                        $scope.initShowLength(editObj);
                                    };
                                    // 选择数据类型时的操作
                                    $scope.initShowLength = function(editObj) {
                                        if ($scope.key.dataType == 'VARCHAR2') {
                                            $scope.isShowLength = true;
                                        } else {
                                            $scope.isShowLength = false;
                                        }
                                    };

                                    // 字段名重复校验 还未使用
                                    $scope.validColName = function() {
                                        // 1.校验是否存在相同的字段名
                                        for ( var i in $scope.dataList) {
                                            if ($scope.editItem && $scope.editItem.columnName) {// 修改 校验概念类型
                                                // 与原始的修改记录比对 不是同一条记录
                                                // 与列表中的概念类型比较没有重复的
                                                if ($scope.key.columnName != $scope.editItem.columnName
                                                        && $scope.key.columnName == $scope.dataList[i].columnName) {// 没有修改概念类型
                                                    alert("存在相同的字段名称！");
                                                    return false;
                                                }
                                            }
                                        }
                                        // 2.分类型校验数据
                                        if ($scope.key.dataType == 'VARCHAR2' && !$scope.key.length) {
                                            alert("数据长度不可为空！");
                                            return false;
                                        }
                                        return true;
                                    };

                                    // 返回上一页
                                    $scope.ok = function() {
                                        $scope.toNextPage(0);
                                        $scope.$parent.$emit('success', $scope.isNew);
                                    };

                                } ]);
    }

});