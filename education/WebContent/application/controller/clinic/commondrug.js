define(function (require, exports, module) {

    //知识库管理
    return function setApp(app) {
        app.controller('ClinicCommondrugCtrl', ['$scope','$http' ,'$filter','CommonDrug','UploadFile','GeneratedKey','$timeout',function ($scope,$http,$filter,CommonDrug,UploadFile,GeneratedKey,$timeout) { 
            //------------------gridlist--------- 	 
          	 $scope.pager =  CommonDrug;
          	 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
          	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
          	 $scope.refresh && $scope.refresh('first' , true);	
           	 
 
               
        //------------------add/edit--------- ----------- 	
               $scope.edit =function(item){ //click on edit link
                  		$scope.keye= angular.copy(item);
             
                  };
               $scope.preview =function(item){ //click on edit link
                		$scope.keyv= angular.copy(item);
           
                }; 
                  
                $scope.create = function(item) {//add and edit
                	$scope.tempitem = angular.copy(item);
                  
                	GeneratedKey.get({ //单图片的思维                                        
         		   			method:'genKey'     
         			      },function(result){                     
         				     var newid = result.newId;  
         				     if($scope.tempitem.drugId){//edit
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
    				               	 var healthfood={};
    				               	 var uploadfile={};
    				               	 healthfood.drugId = newid;
    				               	 healthfood.drugName = $scope.tempitem.drugName;
    				               	 healthfood.drugContent = $scope.tempitem.drugContent;
    				               	 healthfood.drugPic = newid;
    				               	 healthfood.clinicId = $scope.USER_INFO.orgCd;
    				               	 uploadfile.id=	 newid;
    				               	 uploadfile.fileName = $scope.uploadPic[0].fileName;
    				               	 uploadfile.fileType = $scope.uploadPic[0].fileType;
    				               	 uploadfile.filePath = $scope.uploadPic[0].filePath;
    				               	 uploadfile.orgFileName = $scope.uploadPic[0].orgFileName;
    				               	UploadFile.put(uploadfile,function(){  });
    				               	CommonDrug.put(healthfood,function(){
    		                          	$scope.refresh('current',true);//refresh listgrid
    		                           $('#add').modal('hide');
    		                          });
                      		
                      		
                      	}
         				   
                          }
             	      );   
               		 
		               
                  };
    
               
       //-------------delete----------        
               $scope.todelete = function(hid,eid) {//click on DELETE link
                   $scope.tobedeleteId = hid;
                   $scope.tobedeleteFileId = eid;
               };

               $scope.comfirmDelete = function() {//confirm to delete on dialog
  	            var params = {
   	            		id : $scope.tobedeleteFileId
   	            };
  	          UploadFile.remove({
   	                params : angular.toJson(params)
   	            }, function(jsonData) {
   	            	
   	   	            var params = {
   	   	            		drugId : $scope.tobedeleteId
   	   	            };
   	   	         CommonDrug.remove({
   	   	                params : angular.toJson(params)
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
 