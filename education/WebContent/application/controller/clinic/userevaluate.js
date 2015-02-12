define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicUserevaluateCtrl', ['$scope','$http' ,'$filter','UserEvaluate',function ($scope,$http,$filter,UserEvaluate) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  UserEvaluate;
       	 
       	 if($scope.USER_INFO.id !='1'){
       		 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
         	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
         	 $scope.refresh && $scope.refresh('first' , true);	
       	 }
 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.pageitem="";
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(tobeedit){ //click on edit link
              	$scope.pageitem= tobeedit;
               };     
               
             $scope.create = function(key) {//add and edit
                  
            	 UserEvaluate.save(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                        $scope.clearForm();
                      	$('#addandedit').modal('hide');
                      	     
                      });
                
               }
 
            
    //-------------delete----------        
            $scope.todeletehealthfood = function(hid) {//click on DELETE link
                $scope.tobedeleteId = hid;
            };

            $scope.comfirmDelete = function() {//confirm to delete on dialog
	            var params = {
	            		departmentId : $scope.tobedeleteId
	            };
	            DepartmentManagement.remove({
	                params : angular.toJson(params)
	            }, function(jsonData) {
	                $scope.refresh('current', true);
	            });
            }; 
 
        }]);
    }

});