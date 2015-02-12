define(function (require, exports, module) {

    //知识库管理
    return function setApp(app) {
        app.controller('ClinicCommonillnessCtrl', ['$scope','$http' ,'$filter','CommonIllness','GeneratedKey','UploadFile','$timeout',function ($scope,$http,$filter,CommonIllness,GeneratedKey,UploadFile,$timeout) { 
            //------------------gridlist--------- 	 
          	 $scope.pager =  CommonIllness;
          	 $scope.params = {};  
          	 $scope.params.clinicId = $scope.USER_INFO.orgCd; 
          	 $scope.refresh && $scope.refresh('first' , true);	
           	 
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
            
                   
                };
                $scope.create = function(item) {//add and edit
                	$scope.tempitem = angular.copy(item);
                  
                	$scope.keys = GeneratedKey.post({
                        optype : "genKeyN",
                        n : 1
                    },function(result){                    
         				     var newids = result.newIds.split(",");  
         				     if($scope.tempitem.diseaseId){//edit
	  		                   var params = {
	   		       	            		id : "'"+$scope.tempitem.diseasePic+"'" 
	   		       	            };
	   		                       UploadFile.remove({
	   		       	                params : angular.toJson(params)
	   		       	            }, function(jsonData) {
	   		       	             
	   		       	             var commonillness={};
	                             var uploadProfilefile={};
	                      
		                         commonillness.diseaseId = $scope.tempitem.diseaseId;
		                         commonillness.diseaseName = $scope.tempitem.diseaseName;
		                         commonillness.diseaseContent = $scope.tempitem.diseaseContent;
		                         commonillness.clinicId = $scope.USER_INFO.orgCd;
		                         commonillness.diseasePic = "blank";
		                         
				               	if($scope.uploadProfilePic.length>0){
				               		commonillness.diseasePic = newids[0];
    				  	             
    				               	uploadProfilefile.id=	 newids[0];
    				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
    				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
    				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
    				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
    				               	UploadFile.put(uploadProfilefile,function(){  });
				               	}
				               	CommonIllness.save(commonillness,function(){
		                      		$scope.refresh('current',true); 
		                      		$scope.clearForm();
		                         	$('#edit').modal('hide');
		                         });
	   		       	                 
	   		       	            });
                      	}else{// add 
    				               	 var commonillness={};
    				               	 var uploadProfilefile={};
    				               	 
    				               	commonillness.diseaseId = newids[0];
    				               	commonillness.diseaseName = $scope.tempitem.diseaseName;
    				               	commonillness.diseaseContent = $scope.tempitem.diseaseContent;
    				               	commonillness.clinicId = $scope.USER_INFO.orgCd;
    				               	commonillness.diseasePic = 'blank';
    				                
    				           
    				              
    				            	if($scope.uploadProfilePic.length>0){
    				            		commonillness.diseasePic = newids[0];
    				            		
    				            		uploadProfilefile.id=	 newids[0];
        				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
        				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
        				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
        				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
        				               	UploadFile.put(uploadProfilefile,function(){  });
    				            	}
    				               	
 
    				                
    				               	
    				               	CommonIllness.put(commonillness,function(){
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
               };

               $scope.comfirmDelete = function() {//confirm to delete on dialog
  	            var params1 = {
   	            		id : "'"+$scope.tobedeleteProfileId+"'" 
   	            };
  	          UploadFile.remove({
   	                params : angular.toJson(params1)
   	            }, function(jsonData) {
   	            	
   	   	            var params3 = {
   	   	            	diseaseId : $scope.tobedeleteId
   	   	            };
   	   	      CommonIllness.remove({
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
 


