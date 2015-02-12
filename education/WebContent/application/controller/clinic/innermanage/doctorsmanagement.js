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
                $scope.clearForm = function(){//reset botton
                    $scope.key="";
                    $scope.keye="";
                    $scope.uploadProfilePic="";
                    $scope.uploadCertificatePic="";
                   
                };
                $scope.create = function(item) {//add and edit
                	$scope.tempitem = angular.copy(item);
                  
                	$scope.keys = GeneratedKey.post({
                        optype : "genKeyN",
                        n : 3
                    },function(result){                    
         				     var newids = result.newIds.split(",");  
         				     if($scope.tempitem.doctorId){//edit
	  		                   var params = {
	   		       	            		id : "'"+$scope.tempitem.doctorPic+"','"+$scope.tempitem.doctorCertificate+"'"
	   		       	            };
	   		                       UploadFile.remove({
	   		       	                params : angular.toJson(params)
	   		       	            }, function(jsonData) {
	   		       	             
				   		       	         var doctor={};
				   		       	         var uploadProfilefile={};
	    				               	 var uploadcertificatefile={};
	    				               	    doctor.doctorId = $scope.tempitem.doctorId;
		    				               	doctor.doctorName = $scope.tempitem.doctorName;
		    				               	doctor.doctorDesc = $scope.tempitem.doctorDesc;
		    				               	doctor.doctorDegree = $scope.tempitem.doctorDegree;
		    				               	doctor.doctorTel = $scope.tempitem.doctorTel;
		    				               	doctor.doctorSpeciality = $scope.tempitem.doctorSpeciality;
		    				               	doctor.doctorDepartment = $scope.tempitem.doctorDepartment;
		    				               	doctor.clinicId = $scope.USER_INFO.orgCd;
		    				               	doctor.doctorGender = $scope.tempitem.doctorGender;
		    				               	doctor.doctorIdcard = $scope.tempitem.doctorIdcard;
		    				               	
		    				               	doctor.doctorPic = "blank";
		    				               	doctor.doctorCertificate = "blank";
		    				               	
		    				               	
		    				               	
		    				               	
		    				               	if($scope.uploadProfilePic.length>0){
		    				               		doctor.doctorPic = newids[0];
			    				  	             
			    				               	uploadProfilefile.id=	 newids[0];
			    				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
			    				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
			    				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
			    				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
			    				               	UploadFile.put(uploadProfilefile,function(){  });
		    				               	}
		    				               	
		    				               	if($scope.uploadCertificatePic.length>0){
		    				               		doctor.doctorCertificate = newids[1];
		    				               	  
			    				               	uploadcertificatefile.id=	 newids[1];
			    				               	uploadcertificatefile.fileName = $scope.uploadCertificatePic[0].fileName;
			    				               	uploadcertificatefile.fileType = $scope.uploadCertificatePic[0].fileType;
			    				               	uploadcertificatefile.filePath = $scope.uploadCertificatePic[0].filePath;
			    				               	uploadcertificatefile.orgFileName = $scope.uploadCertificatePic[0].orgFileName;
			    				               	UploadFile.put(uploadcertificatefile,function(){  });
		    				               		
		    				               	}
		    				               	
		    				               	DoctorsManagement.save(doctor,function(){
					                      		$scope.refresh('current',true);//refresh listgrid
					                      		$scope.clearForm();
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
    				               	doctor.doctorPic = "blank";
    				               	doctor.doctorCertificate = "blank";
    				               	
    				               	doctor.doctorDegree = $scope.tempitem.doctorDegree;
    				               	doctor.doctorTel = $scope.tempitem.doctorTel;
    				               	doctor.doctorSpeciality = $scope.tempitem.doctorSpeciality;
    				               	doctor.doctorDepartment = $scope.tempitem.doctorDepartment;
    				               	doctor.clinicId = $scope.USER_INFO.orgCd;
    				               	doctor.doctorGender = $scope.tempitem.doctorGender;
    				               	doctor.doctorIdcard = $scope.tempitem.doctorIdcard;
    				               	
    				               	
    				               	
    				           
    				              
    				               	if($scope.uploadProfilePic.length>0){
    				               		doctor.doctorPic = newids[0];
    				               		uploadProfilefile.id=	 newids[0];
        				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
        				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
        				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
        				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
        				               	UploadFile.put(uploadProfilefile,function(){  });
    				               	} 
    				               	
    				               	if($scope.uploadCertificatePic.length>0){
    				               		doctor.doctorCertificate = newids[1];
    				               		uploadcertificatefile.id=	 newids[1];
        				               	uploadcertificatefile.fileName = $scope.uploadCertificatePic[0].fileName;
        				               	uploadcertificatefile.fileType = $scope.uploadCertificatePic[0].fileType;
        				               	uploadcertificatefile.filePath = $scope.uploadCertificatePic[0].filePath;
        				               	uploadcertificatefile.orgFileName = $scope.uploadCertificatePic[0].orgFileName;
        				               	UploadFile.put(uploadcertificatefile,function(){  });
    				               	}
    				               	
    				               	
    				               	DoctorsManagement.put(doctor,function(){
    		                          	$scope.refresh('current',true);//refresh listgrid
    		                          	$scope.clearForm();
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
   	            		id : "'"+$scope.tobedeleteProfileId+"','"+$scope.tobedeleteCertificateId+"'"
   	            };
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
 


