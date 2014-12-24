define(function(require, exports, module) {

    // 实验数据下载记录
    return function setApp(app) {
        app.controller('DataIntegrationDownloadLogCtrl', [
                '$scope',
                'DataUploadDownloadLog',
                'DataDefinition',
                function($scope, DataUploadDownloadLog, DataDefinition) {
                    $scope.defs = DataDefinition.query({
                        isArray : true,
                        params : angular.toJson({
                            "businessType" : "1",
                            "orderBy" : "CREATE_DATE",
                            "order" : "desc",
                            "createUser" : $scope.USER_INFO.id
                        })
                    });
                    $scope.paramsQuery = {
                        "orderBy" : "OP_START_DATE",
                        "order" : "desc",
                        "opUser" : $scope.USER_INFO.id,
                        "opType" : "1"
                    };
                    $scope.params = angular.copy($scope.paramsQuery);

                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
                    };
                    $scope.pager = DataUploadDownloadLog;
                    $scope.download = function(item) {
                        var currDate = new Date();
                        var url = '/dataUploadDownloadLog.shtml?method=download&fileType=1&id=' + item.id + '&time='
                                + currDate.getTime();// ;//
                        // $scope.filePath=url;
                        return linkDownload(url);
                    }

                    function linkDownload(link) {
                        $('#downloadLink').attr('href', link)[0].click();
                    }
                } ]);
    }

});