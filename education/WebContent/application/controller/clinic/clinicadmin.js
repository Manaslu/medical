define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicClinicadminCtrl', ['$scope','$http' ,'$filter','ClinicInformation',function ($scope,$http,$filter,ClinicInformation) { 
     //------------------gridlist--------- 	 

       	 
       	 
         var params = {
        		 clinicId : $scope.USER_INFO.orgCd    
         };
         ClinicInformation.query({  
       		                    isArray:true,
       		                    params: angular.toJson(params)
       	                      },  function (list){
     		$scope.clinicinfo =list[0];
     		if(!$scope.clinicinfo.clinicName){// if clinic name is null then it is a new user
     			$scope.newuserflag=true;
     			$('#newuserdia').modal('show');
     		}else{
     			$scope.newuserflag=false;
     		}
         });
 
            
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
               		DepartmentManagement.put(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                       	$scope.clearForm();
                        $('#addandedit').modal('hide');
                       });
               	}
               }
 
            
 

            $scope.comfirmNewClinic = function() { 
            	$('#add').modal('show');
            }; 
 
        }]);
    }

});