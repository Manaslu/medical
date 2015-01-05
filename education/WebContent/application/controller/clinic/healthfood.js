define(function (require, exports, module) {

    //数据同步列表
    return function setApp(app) {
        app.controller('ClinicHealthfoodCtrl', ['$scope','$http' ,'$filter','HealthFood','$timeout',function ($scope,$http,$filter,HealthFood,$timeout) { 
        	
        	
        	$scope.pager=HealthFood;
        	
            $scope.clearForm = function(){//reset botton
                $scope.key="";
            }
            
            
            
            
        }]);
    }

});