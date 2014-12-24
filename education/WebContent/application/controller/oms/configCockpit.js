define(function (require, exports, module) {

    //配置驾驶舱
    return function setApp(app) {
        app.controller('OmsConfigCockpitCtrl', ['$scope' , function ($scope) {
            $scope.list = [{},{},{},{},{},{},{},{},{}];
        }]);
    }

});