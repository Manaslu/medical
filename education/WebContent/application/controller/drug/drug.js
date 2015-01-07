define(function (require, exports, module) {

    //数据同步列表
    return function setApp(app) {
        app.controller('ClinicDrugCtrl', ['$scope','$http' ,'$filter','Drug','$timeout',function ($scope,$http,$filter,Drug,$timeout) { 
        	
        	
        	$scope.pager=Drug;
        	
            $scope.clearForm = function(){//reset botton
                $scope.key="";
            }
            
            
            
            
        }]);
    }

});