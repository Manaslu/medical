define(function(require, exports, module) {

    // 实验数据下载
    return function setApp(app) {
        app.controller('DataIntegrationDownloadDownloadCtrl', [
                '$scope',
                'DataSet',
                'DataDefinition',
                'DataDefinitionAttr',
                '$timeout',
                'DataUploadDownloadLog',
                function($scope, DataSet, DataDefinition, DataDefinitionAttr, timeout, DataUploadDownloadLog) {
                    $scope.paramsQuery = {
                        'createUser' : $scope.USER_INFO.id,
                        'orderBy' : "CREATE_DATE",
                        'order' : 'desc'
                    };
                    $scope.params = angular.copy($scope.paramsQuery);
                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
                    };
                    $scope.isToSave = true;
                    $scope.data = {};
                    $scope.filePath = "";
                    $scope.down = {
                        'fileType' : 'txt'
                    };
                    $scope.list = [ {}, {}, {}, {}, {}, {}, {}, {}, {} ];
                    $scope.defs = DataDefinition.query({
                        isArray : true,
                        params : angular.toJson({
                            "businessType" : "1",
                            "orderBy" : "CREATE_DATE",
                            "order" : "desc",
                            "createUser" : $scope.USER_INFO.id
                        })
                    });
                    $scope.pager = DataSet;

                    $scope.toDownload = function(item) {
                        $scope.down.dataSetId = item.dataSetId;
                        $scope.data.dataDefId = item.dataDefId;
                        $scope.downDsName = item.dataSetName;
                        $scope.down.columns = [];
                        $scope.findAttr();
                    }

                    // 内部使用的公共方法：查看所有属性
                    $scope.findAttr = function() {
                        var params = {
                            dataDefId : $scope.data.dataDefId
                        }
                        $scope.columns = DataDefinitionAttr.query({
                            isArray : true,
                            params : angular.toJson(params)
                        });
                    }
                    $scope.isChecked = function() {
                        if (!$scope.down.columns || $scope.down.columns.length == 0) {
                            $scope.isToSave = true;
                        } else {
                            $scope.isToSave = false;
                        }
                    }
                    // 全选
                    $scope.checkedAll = function() {
                        if ($scope.down.columns && $scope.down.columns.length == $scope.columns.length) {
                            $scope.down.columns = [];
                        } else {
                            $scope.down.columns = [];
                            for (var i = 0; i < $scope.columns.length; i++) {
                                $scope.down.columns.push($scope.columns[i].id);
                            }
                        }
                    }
                    $scope.errDownload = function(item) {
                        var currDate = new Date();
                        console.log(item.errorLogCount);
                        if (item.errorLogCount) {// 是否是列表上的数据下载
                            if (item.errorLogCount == 1) {// 当错误文件为1个时直接下载
                                var url = '/dataUploadDownloadLog.shtml?method=errordown&fileType=2&dataSetId='
                                        + item.dataSetId + '&time=' + currDate.getTime();//
                                linkDownload(url);
                            } else {// 当错误文件为多个时 让用户去选择下载错误文件
                                $scope.erroFiles = DataUploadDownloadLog.query({
                                    isArray : true,
                                    params : angular.toJson({
                                        "dataSetId" : item.dataSetId,
                                        "isError" : "Y",
                                        "opType" : "2",
                                        "opUser" : $scope.USER_INFO.id,
                                        "orderBy" : "OP_START_DATE",
                                        "order" : "desc"
                                    })
                                }, function(jsonData) {
                                    for (var i = 0; i < jsonData.length; i++) {

                                        jsonData[i].sourFileNameNoType = jsonData[i].sourFileName.substring(0,
                                                jsonData[i].sourFileName.lastIndexOf("."));
                                        console.log(jsonData[i].sourFileNameNoType);
                                    }
                                });
                                $("#errdownload").modal("show");
                            }
                        } else {// 下载列表内指定的单个下载文件
                            var url = '/dataUploadDownloadLog.shtml?fileType=2&method=download&id=' + item.id
                                    + '&time=' + currDate.getTime();//
                            linkDownload(url);
                            // $("#errdownload").modal("hide");
                        }
                    }
                    $scope.dsDownload = function() {
                        $scope.down.userId = $scope.USER_INFO.id;
                        $scope.down.userName = $scope.USER_INFO.userName;
                        $scope.down.orderBy = "OP_START_DATE";
                        $scope.down.order = "desc";
                        $scope.down.isToEmpty = "Y";
                        var currDate = new Date();
                        var url = '/dataSet.shtml?method=download&params=' + angular.toJson($scope.down) + '&time='
                                + currDate.getTime();//
                        if ($scope.down.columns && $scope.down.columns.length > 0) {
                            linkDownload(url);
                            $('#download').modal('hide');
                        } else {
                            alert("请选择下载字段！");
                        }
                    };

                    function linkDownload(link) {
                        $('#downloadLink').attr('href', link)[0].click();
                    }

                } ]);
    }

});