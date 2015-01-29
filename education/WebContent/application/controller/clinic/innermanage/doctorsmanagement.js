define(function (require, exports, module) {

    //知识库管理
    return function setApp(app) {
        app.controller('ClinicInnermanageDoctorsmanagementCtrl', ['$scope','$http' ,'$filter','DoctorsManagement','DepartmentManagement','UploadFile','GeneratedKey','$timeout',function ($scope,$http,$filter,DoctorsManagement,DepartmentManagement,UploadFile,GeneratedKey,$timeout) { 
            //------------------gridlist--------- 	 
          	 $scope.pager =  DoctorsManagement;
          	 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
          	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
          	 $scope.refresh && $scope.refresh('first' , true);	
           	 
 
             DepartmentManagement.query({isArray:true,params:{}},function (list){//for combo
          		$scope.departments =list; 
              });
        //------------------add/edit--------- ----------- 	
               $scope.edit =function(item){ //click on edit link
                  		$scope.keye= angular.copy(item);
             
                  };
               $scope.preview =function(item){ //click on edit link
                		$scope.keyv= angular.copy(item);
           
                }; 
                
                
                 
               
//                $scope.keys = GeneratedKey.post({
//                    optype : "genKeyN",
//                    n : 2
//                },function(result){
//                });
                
                  
                $scope.create = function(item) {//add and edit
                	$scope.tempitem = angular.copy(item);
                  
                	$scope.keys = GeneratedKey.post({
                        optype : "genKeyN",
                        n : 3
                    },function(result){                    
         				     var newids = result.newIds.split(",");  
         				     if($scope.tempitem.doctorId){//edit
	  		                   var params = {
	   		       	            		id : $scope.tempitem.drugPic
	   		       	            };
	   		                       UploadFile.remove({
	   		       	                params : angular.toJson(params)
	   		       	            }, function(jsonData) {
	   		       	             
				   		       	            var healthfood={};
					                     	 var uploadfile={};
					                     	 healthfood.drugId = $scope.tempitem.drugId;
					                     	 healthfood.drugName = $scope.tempitem.drugName;
					                     	 healthfood.drugContent = $scope.tempitem.drugContent;
					                     	 healthfood.drugPic = newid;
					                     	 healthfood.clinicId = $scope.USER_INFO.orgCd;
					                     	 
					                     	 uploadfile.id=	 newid;
					                     	 uploadfile.fileName = $scope.uploadPic[0].fileName;
					                     	 uploadfile.fileType = $scope.uploadPic[0].fileType;
					                     	 uploadfile.filePath = $scope.uploadPic[0].filePath;
					                     	 uploadfile.orgFileName = $scope.uploadPic[0].orgFileName;
					                   	   
					                  		UploadFile.put(uploadfile,function(){
				                     		 
					                       });
					                  		CommonDrug.save(healthfood,function(){
					                      		$scope.refresh('current',true);//refresh listgrid
					                         	$('#edit').modal('hide');
					                         	     
					                         });
	   		       	                 
	   		       	            });
                      	}else{// add 
    				               	 var doctor={};
    				               	 var uploadProfilefile={};
    				               	 var uploadcertificatefile={};
    				               	 
    				               	doctor.doctorId = newids[2];
    				               	doctor.doctorName = $scope.tempitem.doctorName;
    				               	doctor.doctorDesc = $scope.tempitem.doctorDesc;
    				               	doctor.doctorPic = newids[0];
    				               	doctor.doctorDegree = $scope.tempitem.doctorDegree;
    				               	doctor.doctorTel = $scope.tempitem.doctorTel;
    				               	doctor.doctorSpeciality = $scope.tempitem.doctorSpeciality;
    				               	doctor.doctorDepartment = $scope.tempitem.doctorDepartment;
    				               	doctor.clinicId = $scope.USER_INFO.orgCd;
    				               	doctor.doctorGender = $scope.tempitem.doctorGender;
    				               	doctor.doctorIdcard = $scope.tempitem.doctorIdcard;
    				               	doctor.doctorCertificate = newids[1];
    				           
    				              
    				               	 
    				               	uploadProfilefile.id=	 newids[0];
    				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
    				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
    				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
    				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
    				               	UploadFile.put(uploadProfilefile,function(){  });
 
    				               	uploadcertificatefile.id=	 newids[1];
    				               	uploadcertificatefile.fileName = $scope.uploadCertificatePic[0].fileName;
    				               	uploadcertificatefile.fileType = $scope.uploadCertificatePic[0].fileType;
    				               	uploadcertificatefile.filePath = $scope.uploadCertificatePic[0].filePath;
    				               	uploadcertificatefile.orgFileName = $scope.uploadCertificatePic[0].orgFileName;
    				               	UploadFile.put(uploadcertificatefile,function(){  });
    				               	
    				               	DoctorsManagement.put(doctor,function(){
    		                          	$scope.refresh('current',true);//refresh listgrid
    		                           $('#add').modal('hide');
    		                          });
                      		
                      		
                      	}
         				   
                          }
             	      );   
               		 
		               
                  };
    
               
       //-------------delete----------        
               $scope.todelete = function(hid,eid,cid) {//click on DELETE link
                   $scope.tobedeleteId = hid;
                   $scope.tobedeleteProfileId = eid;
                   $scope.tobedeleteCertificateId = cid;
               };

               $scope.comfirmDelete = function() {//confirm to delete on dialog
  	            var params1 = {
   	            		id : $scope.tobedeleteProfileId
   	            };
  	            var params2 = {
   	            		id : $scope.tobedeleteCertificateId
   	            };
  	          UploadFile.remove({
 	                params : angular.toJson(params2)
 	            }, function(jsonData) {});
  	          
  	          UploadFile.remove({
   	                params : angular.toJson(params1)
   	            }, function(jsonData) {
   	            	
   	   	            var params3 = {
   	   	            	doctorId : $scope.tobedeleteId
   	   	            };
   	   	      DoctorsManagement.remove({
   	   	                params : angular.toJson(params3)
   	   	            }, function(jsonData) {
   	   	                $scope.refresh('current', true);
   	   	            });
   	            });
               }; 
               
               $scope.imgShow = function(fileId){ 
            	   if(!fileId){
            		   return;
            	   }
             	   return '/education/uploadFile.shtml?method=download&fileId='+fileId; 
             	}
               
           }]);
    };
              
}); 
 


