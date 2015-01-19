define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicCommondrugCtrl', ['$scope','$http' ,'$filter','CommonDrug','$timeout',function ($scope,$http,$filter,CommonDrug,$timeout) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  CommonDrug;
       	 $scope.params = {};  
       	 $scope.params.clinicId = $scope.USER_INFO.orgCd;
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
                 if( $scope.newentity.drugPic &&  $scope.newentity.drugPic.length>0){
                	 var tt=  $scope.newentity.drugPic[0].filePath;
                	 $scope.newentity.drugPic =tt;
                 }
                   if($scope.newentity.drugId){
                	    
                	    
                	   CommonDrug.save($scope.newentity,function(){
                       	$scope.refresh('current',true); 
                       	$scope.newentity="";
                      	$('#editonly').modal('hide');
                      	     
                      });
               	}else{ 
               		$scope.newentity.clinicId =  $scope.USER_INFO.orgCd; 
               		CommonDrug.put($scope.newentity,function(){
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
	            		drugId : $scope.tobedeleteId
	            };
	            CommonDrug.remove({
	                params : angular.toJson(params)
	            }, function(jsonData) {
	                $scope.refresh('current', true);
	            });
            }; 
 
        }]);
    }

});