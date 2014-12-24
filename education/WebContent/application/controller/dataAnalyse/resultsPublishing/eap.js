define(function (require, exports, module) {

    //分析流程固化审核
    return function setApp(app) {
        app.controller('DataAnalyseResultsPublishingEapCtrl', ['$scope' , function ($scope) {
            $scope.list = [{},{},{},{},{},{},{},{},{}];
        }]);
    }

});