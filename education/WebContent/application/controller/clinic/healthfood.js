define(function (require, exports, module) {

    //知识库管理
    return function setApp(app) {
        app.controller('ClinicHealthfoodCtrl', ['$scope','$http' ,'$filter','HealthFood','UploadFile','GeneratedKey','$timeout',function ($scope,$http,$filter,HealthFood,UploadFile,GeneratedKey,$timeout) { 
            //------------------gridlist--------- 	 
          	 $scope.pager =  HealthFood;
          	 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
          	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
          	 $scope.refresh && $scope.refresh('first' , true);	
           	 
        //------------reset botton---------------------
               $scope.clearForm = function(){//reset botton

                   //$scope.key="";
//                   $scope.tempeatPic=[];
               };
               
        //------------------add/edit--------- ----------- 	
               $scope.edit =function(healthfood){ //click on edit link
                  		$scope.keye= angular.copy(healthfood);
             
                  };
               $scope.preview =function(healthfood){ //click on edit link
                		$scope.keyv= angular.copy(healthfood);
           
                }; 
                  
                $scope.create = function(item) {//add and edit
                	$scope.tempitem = angular.copy(item);
                  
                	GeneratedKey.get({ //单图片的思维                                        
         		   			method:'genKey'     
         			      },function(result){                     
         				     var newid = result.newId;  
         				     if($scope.tempitem.eatId){//edit
	  		                   var params = {
	   		       	            		id : $scope.tempitem.eatPic
	   		       	            };
	   		                       UploadFile.remove({
	   		       	                params : angular.toJson(params)
	   		       	            }, function(jsonData) {
	   		       	             
				   		       	            var healthfood={};
					                     	 var uploadfile={};
					                     	 healthfood.eatId = $scope.tempitem.eatId;
					                     	 healthfood.eatTitle = $scope.tempitem.eatTitle;
					                     	 healthfood.eatContent = $scope.tempitem.eatContent;
					                     	 healthfood.eatPic = newid;
					                     	 healthfood.clinicId = $scope.USER_INFO.orgCd;
					                     	 
					                     	 uploadfile.id=	 newid;
					                     	 uploadfile.fileName = $scope.uploadPic[0].fileName;
					                     	 uploadfile.fileType = $scope.uploadPic[0].fileType;
					                     	 uploadfile.filePath = $scope.uploadPic[0].filePath;
					                     	 uploadfile.orgFileName = $scope.uploadPic[0].orgFileName;
					                   	   
					                  		UploadFile.put(uploadfile,function(){
				                     		 
					                       });
					                      	HealthFood.save(healthfood,function(){
					                      		$scope.refresh('current',true);//refresh listgrid
					                         	$('#editonly').modal('hide');
					                         	     
					                         });
	   		       	                 
	   		       	            });
                      	}else{// add 
    				               	 var healthfood={};
    				               	 var uploadfile={};
    				               	 healthfood.eatId = newid;
    				               	 healthfood.eatTitle = $scope.tempitem.eatTitle;
    				               	 healthfood.eatContent = $scope.tempitem.eatContent;
    				               	 healthfood.eatPic = newid;
    				               	 healthfood.clinicId = $scope.USER_INFO.orgCd;
    				               	 uploadfile.id=	 newid;
    				               	 uploadfile.fileName = $scope.uploadPic[0].fileName;
    				               	 uploadfile.fileType = $scope.uploadPic[0].fileType;
    				               	 uploadfile.filePath = $scope.uploadPic[0].filePath;
    				               	 uploadfile.orgFileName = $scope.uploadPic[0].orgFileName;
    		                  		HealthFood.put(healthfood,function(){
    		                          	$scope.refresh('current',true);//refresh listgrid
    		                           $('#pushDialog').modal('hide');
    		                          });
                      		
                      		UploadFile.put(uploadfile,function(){
                      		 
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
   	   	            		eatId : $scope.tobedeleteId
   	   	            };
   	   	            HealthFood.remove({
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
 


