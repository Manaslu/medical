define(function (require, exports, module) {

    //项目列表
    return function setApp(app) {
        app.controller('TechnologicalProcessProcessQuantizationCtrl', ['$scope' , function ($scope) {
            $scope.list = [{},{},{},{},{},{},{},{},{}];
        }]);
    }

});