define(function (require, exports, module) {
    var config = require('data/test-result.json');
    //成果展示-国际小包
    function setApp(app) {
        app.controller('ResultInternationalProjectResultsCtrl', ['$scope' , '$routeParams' ,'$filter' ,
            function ($scope, $routeParams , $filter) {

                var type = $routeParams.type;

                $scope.type = type;

                var dateFilter = $filter('date');

                $scope[type] = dateFilter(new Date , type == 'month' ? 'yyyyMM' : 'yyyyMMdd');

                $scope.filename  = $routeParams.filename;
                $scope.index  = $routeParams.index;

        }]);
    }


    return setApp;

});