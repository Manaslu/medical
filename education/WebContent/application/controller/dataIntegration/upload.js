define(function(require, exports, module) {

    return function setApp(app) {
        // 实验数据上传
        app.controller('DataIntegrationUploadCtrl', [
                '$scope',
                '$http',
                'DataDefinition',
                'ConceptModelDic',
                'DataTypeDic',
                'DataDefinitionAttr',
                'DataSet',
                '$timeout',
                'dialog',
                '$location',
                function($scope, $http, DataDefinition, ConceptModelDic, DataTypeDic, DataDefinitionAttr, DataSet,
                        timeout, dialog, $location) {
                    $scope.current = 0;
                    $scope.showOtherSeq = false;
                    $scope.editItem = {};
                    $scope.conceptModelId = "";
                    $scope.fileIsNull = true;
                    $scope.userCase = {};

                    $scope.data = {
                        "method" : "upload",
                        'separate' : 'tab',
                        'firstIsTitle' : '1'
                    };
                    $scope.toPage = function(index) {
                        $scope.current = index;
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

                    // 选择文件时的回调
                    $scope.changeFile = function(file) {
                        $scope.data.filePath = null;
                        $scope.file = file;
                        $scope.data.dataSetName = file.name.substring(0, file.name.lastIndexOf("."));
                        $scope.sepShow = $scope.checkShow();
                        $scope.$watch();
                    }
                    // 测试是否是数据整合内的上传
                    $scope.IntegrationUserCaseTest = function() {
                        return location.href.indexOf('#/at/dataIntegration/testData/upload') > 0;
                    }
                    // 上传不同入口数据初始化
                    if ($scope.IntegrationUserCaseTest()) {// 数据上传
                        $scope.userCase.userCaseName = '实验数据上传';
                        $scope.userCase.redirectUrl = '/at/dataIntegration/testData/ds';
                        $scope.userCase.redirectTipContent = '数据后台导入中，请稍后在“实验数据管理”中查看数据。上传过程中的错误数据可以在“实验数据下载”中进行下载。';
                    } else {
                        $scope.userCase.userCaseName = '数据上传';
                        $scope.userCase.redirectUrl = '/at/innerService/ds';
                        $scope.userCase.redirectTipContent = '数据后台导入中，请稍后在“数据管理”中查看数据。上传过程中的错误数据可以在“数据下载”中进行下载。';
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
                        setTimeout(function() {
                            var files = $scope.data.uploaderFile;
                            $scope.upsuccess(files[0]);
                        }, 10);
                    }
                    $scope.upsuccess = function(data) {
                        // {"fileType":"txt","filePath":"","fileName":"1403331632090.txt","orgFileName":"tttt.txt","success":"true"}
                        $scope.data.filePath = data.filePath;
                        $scope.data.orgFileName = data.orgFileName;
                        console.log(angular.toJson(data));
                        DataSet.save($scope.data, function(data) {
                            if (data.columns.length == 0) {
                                alert("上传文件数据为空，请选择其它文件！");
                                return false;
                            }
                            $scope.findAttr();
                            $scope.list = data.content.content;
                            $scope.titleList = data.content.title;
                            $scope.data.filePath = data.filePath;

                            if (!$scope.data.dataDefId) {//
                                $scope.columns = data.columns;
                            }
                            if (data.erroFilePath) {
                                // $scope.errorFilePath = data.erroFilePath;
                                alert("上传文件中存在错误文件，请到实验数据下载中下载");
                            }
                            $scope.toPage(1);
                        });
                    }
                    // 文件上传按钮
                    $scope.uploadFile = function() {
                        if ($scope.data.separate == 'other') {
                            if ($scope.data.otherSeparate == undefined || $scope.data.otherSeparate.length == 0) {
                                alert("请输入分隔符");
                                return false;
                            }
                        }
                        $scope.uploader.upload();
                    }

                    // 概念模式初始化
                    $scope.cls = ConceptModelDic.query({
                        isArray : true,
                        params : {}
                    });

                    // 字段类型初始化
                    $scope.types = DataTypeDic.query({
                        isArray : true,
                        params : {}
                    });
                    // 数据模型 初始化
                    $scope.conceptModelDicInit = function(gnmx) {
                        if (!$scope.editItem)
                            $scope.editItem = {};
                        if (!gnmx)
                            gnmx = {};
                        // 与自己相同不提示
                        // 除与自己相同还存在相同时出错
                        for (var i = 0; i < $scope.columns.length; i++) {
                            if ($scope.editItemOrg.columnName != gnmx.conceptModelName// 与自己不是同一条记录
                                    && $scope.columns[i].columnName == gnmx.conceptModelName) {
                                alert("此数据模型已被使用,请选择其它模型！");

                                if ($scope.prveEditItem) {// 3.选择了其它模型 恢复为上次选择内容
                                    timeout(function() {
                                        $scope.conceptModelSel($scope.prveEditItem.conceptModelId);
                                    }, 1);
                                } else {// 第一次
                                    timeout(function() {
                                        $scope.conceptModelSel($scope.editItemOrg.conceptModelId);
                                    }, 1);
                                }
                                return false;
                            }
                        }
                        $scope.editItem.columnName = gnmx.conceptModelName;
                        $scope.editItem.columnDesc = gnmx.conceptModelDesc;
                        $scope.editItem.dataType = gnmx.dataType;
                        $scope.editItem.length = gnmx.length;
                        $scope.editItem.conceptModelId = gnmx.conceptModelId;
                        $scope.editItem.conceptModelDesc = gnmx.conceptModelDesc;
                        $scope.initShowLength($scope.editItem);
                        $scope.prveEditItem = angular.copy(gnmx);
                    };

                    // 准备编辑字段
                    $scope.edit = function(editObj) {
                        var cmd = editObj.conceptModelId;
                        $scope.editItemOrg = editObj;
                        $scope.gnmx = null;
                        if (!$scope.data.dataDefId) {
                            $scope.conceptModelSel(cmd);
                            $scope.editItem = angular.copy(editObj);
                        }
                        $scope.initShowLength(editObj);
                        $scope.prveEditItem = null;
                    };

                    $scope.conceptModelSel = function(cmd) {
                        if (!cmd) {
                            $scope.gnmx = null;
                        } else {
                            for (var i = 0; i < $scope.cls.length; i++) {// 类型初始化
                                var item = $scope.cls[i];
                                if (item.conceptModelId == cmd) {
                                    $scope.gnmx = item;
                                    break;
                                }
                            }
                        }
                    };
                    // 根据数据类型 分别测试提示
                    var configVarchar = {
                        valid : 'numValid',
                        name : 'numValid'
                    };

                    $scope.customeValid = function(val) {
                        if (!$scope.editItem)
                            $scope.editItem = {};
                        return configVarchar;
                    }
                    $scope.numValid = function(val) {
                        if (/^[1-9][0-9]*$/.test(val)) {
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

                    // 保存字段编辑
                    $scope.saveEdit = function(ele) {
                        if ($scope.validColName()) {
                            delete $scope.editItem._name;
                            delete $scope.editItem.ignore;
                            angular.extend($scope.editItemOrg, $scope.editItem);
                            $('#edit1').modal('hide');
                        }
                    };
                    // 字段名重复校验 还未使用
                    $scope.validColName = function() {
                        for ( var i in $scope.columns) {
                            if ($scope.editItemOrg) {
                                if ($scope.editItem.columnName != $scope.editItemOrg.columnName
                                        && $scope.columns[i].columnName == $scope.editItem.columnName) {
                                    alert("存在相同的字段名称！");
                                    return false;
                                }
                            }
                        }
                        // 2.分类型校验数据
                        if ($scope.editItem.dataType == 'VARCHAR2' && !$scope.editItem.length) {
                            alert("数据长度不可为空！");
                            return false;
                        }
                        return true;
                    };

                    // 忽略字段
                    $scope.ignoreEdit = function() {
                        $scope.editItem.ignore = true;
                        $scope.editItem._name = '忽略';
                        $scope.editItem.name = '--';
                        $scope.editItem.type = '--';
                        $scope.editItem.length = '--';
                        $scope.editItem.desc = '--';
                    };

                    // 列名 查询所有列
                    $scope.findAttr = function() {
                        $scope.columns = [];
                        if ($scope.data.dataDefId) {
                            var params = {
                                dataDefId : $scope.data.dataDefId
                            }
                            $scope.columns = DataDefinitionAttr.query({
                                isArray : true,
                                params : angular.toJson(params)
                            }, function(colms) {
                                if (colms && colms.length) {
                                    colms.forEach(function(item) {
                                        if (item.conceptModelId) {
                                            item.conceptModelDesc = item.columnDesc;
                                        }
                                    });
                                }
                            });
                        }
                    };

                    // 数据定义保存 数据上传
                    $scope.saveAll = function() {
                        if ($scope.columns.length != $scope.list[0].length) {
                            alert("数据定义与上传数据不符");
                            $scope.isCanSave = false;
                            return false;
                        }

                        $scope.data.columns = $scope.columns;
                        $scope.data.userId = $scope.USER_INFO.id;
                        $scope.data.userName = $scope.USER_INFO.userName;
                        var data = {
                            method : "saveup",
                            params : angular.toJson($scope.data)
                        }
                        DataSet.put(data, function(jsonData) {
                            if (jsonData.success == 'true') {
                                dialog($scope.userCase.redirectTipContent, '友情提示');
                                $location.path($scope.userCase.redirectUrl);
                                $location.replace();
                            } else {
                                alert("操作失败,请重试！");
                            }
                        });
                    };
                    // 选择数据类型时的操作
                    $scope.initLength = function(editObj) {
                        editObj.length = null;
                        $scope.initShowLength(editObj);
                    };
                    // 选择数据类型时的操作
                    $scope.initShowLength = function(editObj) {
                        if (editObj.dataType == 'VARCHAR2') {
                            $scope.isShowLength = true;
                        } else {
                            $scope.isShowLength = false;
                        }
                    };

                    $scope.reset = function() {
                        if ($scope.editItemOrg) {
                            $scope.editItem = angular.copy($scope.editItemOrg);
                            $scope.conceptModelSel($scope.editItem.conceptModelId);
                            $scope.initShowLength($scope.editItem);
                        }
                    };

                } ]);
    }

});