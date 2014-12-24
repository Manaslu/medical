define(function (require, exports, module) {

    //项目结题
    return function setApp(app) {
        app.controller('TechnologicalProcessProcessEndCtrl', ['$scope' , function ($scope) {
            $scope.list = [{},{},{},{},{},{},{},{},{}];
        }]);
    }

});