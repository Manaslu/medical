define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicHealthfoodCtrl', ['$scope','$http' ,'$filter','HealthFood','$timeout',function ($scope,$http,$filter,HealthFood,$timeout) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  HealthFood;
       	 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
       	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
       	 $scope.refresh && $scope.refresh('first' , true);	
        	 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.key="";
//                $scope.tempeatPic=[];
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(healthfood){ //click on edit link
            	$scope.clearForm(); 
              	$scope.keye= angular.copy(healthfood);
 
//              	$scope.tempeatPic=[];
//              	$scope.tempeatPic[0]=healthfood.eatPic
               };     
               
             $scope.create = function(item) {//add and edit

            	 $scope.newentity = angular.copy(item);
                 if( $scope.newentity.eatPic &&  $scope.newentity.eatPic.length>0){
                	 var tt=  $scope.newentity.eatPic[0].filePath;
                	 $scope.newentity.eatPic =tt;
                 }
                   if($scope.newentity.eatId){
                	   $scope.newentity.eatDate=''; //update time now is number,it cause error of mismatch
                	   var t= $scope.newentity.eatPic[0]
                   	HealthFood.save($scope.newentity,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                       	$scope.newentity="";
                      	$('#editonly').modal('hide');
                      	     
                      });
               	}else{ 
               		$scope.newentity.clinicId =  $scope.USER_INFO.orgCd; 
               		HealthFood.put($scope.newentity,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                       	$scope.newentity="";
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