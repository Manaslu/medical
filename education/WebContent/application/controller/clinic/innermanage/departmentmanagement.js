define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicInnermanageDepartmentmanagementCtrl', ['$scope','$http' ,'$filter','DepartmentManagement',function ($scope,$http,$filter,DepartmentManagement) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  DepartmentManagement;
 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.pageitem="";
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(tobeedit){ //click on edit link
              	$scope.pageitem= tobeedit;
               };     
               
             $scope.create = function(key) {//add and edit
                   if(key.departmentId){
                	   DepartmentManagement.save(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                        $scope.clearForm();
                      	$('#addandedit').modal('hide');
                      	     
                      });
               	}else{ 
               		key.clinicId =  $scope.USER_INFO.orgCd; 
               		DepartmentManagement.put(k,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                       	$scope.clearForm();
                        $('#addandedit').modal('hide');
                       });
               	}
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