define(function (require, exports, module) {

    //报刊订阅总体情况分析
    return function setApp(app) {
        app.controller('ResultNewspaperResultPZCtrl', ['$scope' , '$routeParams','AnalysisTheme',function ($scope, $routeParams,AnalysisTheme ) {
        	$scope.availableThemeList= AnalysisTheme.query({isArray : true, params : {finishedAnalysis:'32',loguserId: $scope.USER_INFO.id}});
            angular.forEach($routeParams , function(val , key){
                $scope[key] = val;
            });
            $scope.chart = $routeParams.chart !== 'false';//是否有图表
            $scope.table = $routeParams.table !== 'false';//是否有图表
        }]);
    }

});