define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicInnermanageDoctorsmanagementCtrl', ['$scope','$http' ,'$filter','Doctorsmanagement','$timeout',function ($scope,$http,$filter,Doctorsmanagement,$timeout) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  Doctorsmanagement;
       	 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
       	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
       	 $scope.refresh && $scope.refresh('first' , true);	
        	 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.key="";
                $scope.tempeatPic=[];
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(healthfood){ //click on edit link
              	$scope.key= healthfood;
              	$scope.tempeatPic=[];
              	$scope.tempeatPic[0]=healthfood.eatPic
               };     
               
             $scope.create = function(key) {//add and edit
               if($scope.tempeatPic && $scope.tempeatPic.length>0){
            	   key.eatPic=$scope.tempeatPic[0].filePath; //
               }
                   if(key.eatId){
                   	key.eatDate=''; //update time now is number,it cause error of mismatch
                   	HealthFood.save(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                        $scope.clearForm();
                      	$('#addandedit').modal('hide');
                      	     
                      });
               	}else{ 
               		key.clinicId =  $scope.USER_INFO.orgCd; 
               		HealthFood.put(key,function(){
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
	            		eatId : $scope.tobedeleteId
	            };
	            HealthFood.remove({
	                params : angular.toJson(params)
	            }, function(jsonData) {
	                $scope.refresh('current', true);
	            });
            }; 
 
        }]);
    }

});