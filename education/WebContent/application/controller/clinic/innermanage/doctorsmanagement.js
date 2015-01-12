define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicInnermanageDoctorsmanagementCtrl', ['$scope','$http' ,'$filter','DoctorsManagement','DepartmentManagement','$timeout',function ($scope,$http,$filter,DoctorsManagement,DepartmentManagement,$timeout) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  DoctorsManagement;
       	 $scope.params = {};
       	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
       	 $scope.refresh && $scope.refresh('first' , true);	
       	 
 
         
         DepartmentManagement.query({isArray:true,params:{}},function (list){//for combo
     		$scope.departments =list; 
         });
        	 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.key="";
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(healthfood){ //click on edit link
              	$scope.key= healthfood;
               };     
               
             $scope.create = function(key) { 
               if( key.doctorPic &&  key.doctorPic.length>0){
              	 var tt=  key.doctorPic[0].filePath;
              	 key.doctorPic =tt;
               }
               if( key.doctorCertificate &&  key.doctorCertificate.length>0){
                	 var tt=  key.doctorCertificate[0].filePath;
                	 key.doctorCertificate =tt;
               }
             if(key.doctorId){
                   	key.doctorDate=''; //update time now is number,it cause error of mismatch
                   	DoctorsManagement.save(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                        $scope.clearForm();
                      	$('#addandedit').modal('hide');
                      	     
                      });
               	}else{ //add
               		key.clinicId =  $scope.USER_INFO.orgCd; 
               		DoctorsManagement.put(key,function(){
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
	            		doctorId : $scope.tobedeleteId
	            };
	            DoctorsManagement.remove({
	                params : angular.toJson(params)
	            }, function(jsonData) {
	                $scope.refresh('current', true);
	            });
            }; 
 
        }]);
    }

});