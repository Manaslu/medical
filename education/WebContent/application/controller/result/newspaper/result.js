define(function (require, exports, module) {

    //报刊订阅总体情况分析
    return function setApp(app) {
        app.controller('ResultNewspaperResultCtrl', ['$scope' , '$routeParams' , 'Common' , '$filter',function ($scope, $routeParams , Common ,$filter) {
            angular.forEach($routeParams, function (val, key) {
                //key = type 是否需要选择图表类型
                //key = file 图标对应数据文件
                //key = height 对应高度
                //key = any 自定义属性，在页面可以随意访问
                $scope[key] = val;
            });
            $scope.chart = $routeParams.chart !== 'false';//是否有图表
            $scope.table = $routeParams.table !== 'false';//是否有图表

          
            if($scope.USER_INFO.provCode!='99'){            	
            	$scope.provCode=$scope.USER_INFO.provCode;
            	$scope.prov=false;
            }
            
            if ($scope.prov) {
                $scope.provList = Common.query({isArray: true, params: "{}"});//省份列表
            }
            
            $scope.years = $filter('date')(Date.now , 'yyyy-MM-dd');
            
        }]);
    }

});