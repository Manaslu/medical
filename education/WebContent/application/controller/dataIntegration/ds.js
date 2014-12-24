define(function(require, exports, module) {

    return function setApp(app) {
        // 0:数据集管理
        app.controller('DataIntegrationDsCtrl', [ '$scope', 'DataSet', 'DataDefinition', 'DataDefinitionAttr',
                function($scope, DataSet, DataDefinition, DataDefinitionAttr) {
                    $scope.paramsQuery = {
                        'createUser' : $scope.USER_INFO.id,
                        "orderBy" : "CREATE_DATE",
                        "order" : "desc"
                    };
                    $scope.params = angular.copy($scope.paramsQuery);

                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
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
                    $scope.current = 0;// 默认显示第一视图
                    $scope.toPage = function(index) {
                        $scope.current = index;
                    };
                    $scope.pager = DataSet;

                    // 跳转到删除页
                    $scope.toRemove = function(item) {
                        $scope.removeKey = item.dataSetId;
                    };
                    // 跳转到删除页
                    $scope.remove = function() {
                        var params = {
                            'dataSetId' : $scope.removeKey
                        };
                        DataSet.remove({
                            'params' : angular.toJson(params)
                        }, $scope.refresh.bind(null, 'current', true));
                    };

                    $scope.append = function(item) {
                        $scope.$broadcast('append', item);
                        $scope.toPage(2);
                    };

                    $scope.detail = function(item) {
                        $scope.$broadcast('detail', item);
                        $scope.toPage(1);
                    };
                    $scope.$on('detailSuccess', function(e, data) {
                        $scope.refresh('current', true);
                    });
                    // 当数据项处理完成
                    $scope.$on('appendSuccess', function(e, isNew) {
                        $scope.refresh('current', true);
                    });
                } ]);

        // 1:数据集管理- 详情
        app.controller('DataIntegrationDsDetailsCtrl', [ '$scope', '$timeout', 'DataDefinitionAttr', 'DataDefinition',
                'DataSetContent', function($scope, $timeout, DataDefinitionAttr, DataDefinition, DataSetContent) {
                    $scope.$on('detail', function(e, data) {
                        $scope.data = data;
                        $scope.initColumns();
                        $scope.initTitle();
                        $timeout(function() {
                            data.list = $scope.list = data.list || [];
                        });
                        // 查询参数
                        $scope.initPager(data.tableName);
                    });

                    // 初始化分页查询内容 每次进入时的清空处理
                    $scope.initPager = function(tableName) {
                        if (!$scope.params1)
                            $scope.params1 = {};
                        $scope.params1.tableName = tableName;// 当前表名
                        if (!$scope.detailPager) {
                            $scope.detailPager = DataSetContent;
                        }
                    };

                    // 初始化字段查询列表
                    $scope.initColumns = function() {
                        var params = {
                            'dataDefId' : $scope.data.dataDefId,
                            'dataType' : "VARCHAR2",
                            'hasId' : '1'
                        };
                        $scope.colinfo = DataDefinitionAttr.query({
                            isArray : true,
                            'params' : angular.toJson(params)
                        });
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

                    // 字段选择改变后 对应参数的初始化
                    $scope.colChg = function(item) {
                        var columnName = "";
                        if ($scope.colItemInfo) {
                            columnName = item.columnName;
                        }
                        $scope.params1.likeColumn = columnName;
                        // alert(angular.toJson($scope.params.likeColumn));
                    };

                    // 新增
                    $scope.edit = function(item, isNew) {
                        $scope.editItem = item;
                        for (var i = 0; i < $scope.titleList.length; i++) {
                            $scope.titleList[i].editVal = item[i];
                            $scope.titleList[i].inputType = "text";
                        }
                        $scope.modColumnsMap = $scope.titleList;

                        $scope.isNew = isNew;
                    };

                    $scope.saveEdit = function() {
                        var isNewStr = '0';
                        var isEmpty = true;
                        if ($scope.isNew) {
                            $scope.list.push($scope.editItem);
                            isNewStr = '1';
                        }
                        for (var i = 0; i < $scope.titleList.length; i++) {
                            if ($scope.titleList[i].editVal) {
                                isEmpty = false;
                            }
                        }
                        if (isEmpty) {
                            alert("请填写数据后提交！");
                            return false;
                        }
                        console.log($scope.titleList[1].editVal);
                        DataSetContent.save({
                            update : true,
                            params : angular.toJson({
                                columns : $scope.titleList,
                                dataSetId : $scope.data.dataSetId,
                                isNew : isNewStr,
                                user : $scope.USER_INFO,
                                tableName : $scope.data.tableName
                            })
                        }, function(resultData) {
                            if (resultData.success == 'true') {
                                $("#edit1").modal('hide');
                                $scope.refresh1(null, 'current', true)
                            }
                        });

                        $scope.cancelEdit();
                    };

                    $scope.cancelEdit = function() {
                        $scope.isNew = $scope.editItem = null;
                    };

                    $scope.ok = function() {
                        $scope.toPage(0);
                        $scope.detailPager = undefined;
                        $scope.$parent.$emit('detailSuccess');
                    };

                    $scope.toRemoveItem = function(item) {
                        $scope.removeRecordId = item[0];
                    };

                    $scope.removeItemDS = function() {
                        var params = {
                            dataSetId : $scope.data.dataSetId,
                            id : $scope.removeRecordId,
                            tableName : $scope.data.tableName
                        };
                        DataSetContent.remove({
                            params : angular.toJson(params)
                        }, $scope.refresh1(null, 'current', true));
                    };

                } ]);

        // 2:数据集管理- 追加
        app.controller('DataIntegrationDsAppendCtrl', [ '$scope', 'DataSet', 'dialog',
                function($scope, DataSet, dialog) {
                    $scope.$on('append', function(e, data) {
                        $scope.data = data;
                        $scope.dataSet = data;
                        $scope.data.dataDefId = data.dataDefId;
                        $scope.data.dataSetId = data.dataSetId;
                        $scope.data.separate = "tab";
                        $scope.data.firstIsTitle = "1";
                        $scope.data.userId = $scope.USER_INFO.id;
                        $scope.data.userName = $scope.USER_INFO.userName;
                        $scope.showOtherSeq = false;
//                        $scope.uploader.reset();
                        $scope.sepShow = false;
                    });

                    $scope.ok = function() {
                        $scope.toPage(0);
                        $scope.$parent.$emit('appendSuccess');
                    };

                    // 选择文件时的回调
                    $scope.changeFile = function(file) {
                        $scope.file = file;
                        $scope.sepShow = $scope.checkShow();
                        $scope.$watch();
                    }
                    // 显示其它后面的文本框
                    $scope.checkShow = function() {
                        if ($scope.file) {
                            return $scope.file.ext == 'txt' || $scope.file.ext == 'TXT';
                        } else {
                            return false;
                        }
                    };
                    // 单个文件上传错误
                    $scope.uperror = function(file, type, ext) {
                        if (type == 'extensions') {
                            alert("上传文件类型错误，请选择txt,xls,xlsx,et文件上传。");
                        } else if (type == 'fileSingleSizeLimit') {
                            alert("上传文件超过最大限制，请选择其它文件。");
                        } else if (type == 'emptyFile') {
                            alert("上传文件为空文件，请选择其它文件。");
                        }
                    }
                    // 上传成功回调
                    $scope.singleupsuccess = function() {
                        setTimeout(function(){
                            var files = $scope.data.uploaderFile;
                            $scope.upsuccess(files[0]);
                        } , 10);
                    }
                    // 上传成功回调
                    $scope.upsuccess = function(data, file) {
                        $scope.data.filePath = data.filePath;
                        $scope.data.orgFileName = data.orgFileName;
                        var data = {
                            method : "saveup",
                            params : angular.toJson($scope.data)
                        }

                        DataSet.put(data, function(jsonData) {
                            if (jsonData.success == 'true') {
                                if (location.href.indexOf('#/at/dataIntegration/testData/ds') > 0) {
                                    dialog('数据后台追加中，请稍后在“实验数据管理”中查看数据。上传过程中的错误数据可以在“实验数据下载”中进行下载。', '友情提示');
                                } else {
                                    dialog('数据后台追加中，请稍后在“数据管理”中查看数据。上传过程中的错误数据可以在“数据下载”中进行下载。', '友情提示');
                                }
                                $scope.toPage(0);
                                $scope.refresh("current", true);
                            } else {
                                alert("操作失败,请检查上传文件数据格式！");
                            }
                        });
//                        $scope.uploader.reset();
                    }
                    // 开启追加
                    $scope.toUpAddData = function() {
                        if ($scope.data.separate == 'other') {
                            if ($scope.data.otherSeparate == undefined || $scope.data.otherSeparate.length == 0) {
                                alert("请输入分隔符");
                                return false;
                            }
                        }
                        $scope.uploader.upload();
                    }

                } ]);
    }

});