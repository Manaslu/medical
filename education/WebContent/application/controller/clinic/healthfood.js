define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicHealthfoodCtrl', ['$scope','$http' ,'$filter','HealthFood','UploadFile','$timeout',function ($scope,$http,$filter,HealthFood,UploadFile,$timeout) { 
     //------------------gridlist--------- 	 
       	 $scope.pager =  HealthFood;
       	 $scope.params = {};// normally "$scope.pager =  HealthFood" is already enough for listgrid  ,but here we need to use clinicId to identify user's authority  
       	 $scope.params.clinicId = $scope.USER_INFO.orgCd;//default clinic id for current user
       	 $scope.refresh && $scope.refresh('first' , true);	
        	 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton

                //$scope.key="";
//                $scope.tempeatPic=[];
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(healthfood){ //click on edit link
            	
                var paramsPic = {
                  		 id : healthfood.eatPic 
                   };
                UploadFile.query({ //初始化用户列表
                 		                    isArray:true,
                 		                    params: angular.toJson(paramsPic)
                 	                      },  function (list){
               		$scope.uploadPic =list[0]; 
               		$scope.keye= angular.copy(healthfood);
                   }); 
            	
            	$scope.clearForm(); 
              	
 
//              	$scope.tempeatPic=[];
//              	$scope.tempeatPic[0]=healthfood.eatPic
               };     
               
             $scope.create = function(item) {//add and edit
 
            	  
                
                   if(item.eatId){
                	 var newid = Math.floor(Math.random() * 10000000)+'';//for both
                	   
                  	 var healthfood={};
                  	 var uploadfile={};
                	   
                  
                  	 healthfood.eatTitle = item.eatTitle;
                  	 healthfood.eatContent = item.eatContent;
                  	 healthfood.eatPic = newid;
                  	 healthfood.clinicId = $scope.USER_INFO.orgCd;
                  	 
                  	 uploadfile.id=	 newid;
                  	 uploadfile.fileName = $scope.uploadPic[0].fileName;
                  	 uploadfile.fileType = $scope.uploadPic[0].fileType;
                  	 uploadfile.filePath = $scope.uploadPic[0].filePath;
                  	 uploadfile.orgFileName = $scope.uploadPic[0].orgFileName;
                  	 uploadfile.success = $scope.uploadPic[0].success;
                	   
                	   
                   	HealthFood.save($scope.newentity,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                      	$('#editonly').modal('hide');
                      	     
                      });
                   	
                    var params = {
    	            		id : item.eatPic
    	            };
                    UploadFile.remove({
    	                params : angular.toJson(params)
    	            }, function(jsonData) {
    	                 
    	            });
               		UploadFile.put(uploadfile,function(){
                  		 
                    });
               	}else{// add 
               		
               	 var newid = Math.floor(Math.random() * 10000000)+'';//for both
            	 var healthfood={};
            	 var uploadfile={};
            	 
            	 healthfood.eatId = newid;
            	 healthfood.eatTitle = item.eatTitle;
            	 healthfood.eatContent = item.eatContent;
            	 healthfood.eatPic = newid;
            	 healthfood.clinicId = $scope.USER_INFO.orgCd;
            	 
            	 uploadfile.id=	 newid;
            	 uploadfile.fileName = $scope.uploadPic[0].fileName;
            	 uploadfile.fileType = $scope.uploadPic[0].fileType;
            	 uploadfile.filePath = $scope.uploadPic[0].filePath;
            	 uploadfile.orgFileName = $scope.uploadPic[0].orgFileName;
            	 uploadfile.success = $scope.uploadPic[0].success;
            	 
 
               		HealthFood.put(healthfood,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                        $('#addandedit').modal('hide');
                       });
               		
               		UploadFile.put(uploadfile,function(){
               		 
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