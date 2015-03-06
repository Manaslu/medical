define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicClinicadminCtrl', ['$scope','$http' ,'$filter','ClinicInformation','GeneratedKey','UploadFile',function ($scope,$http,$filter,ClinicInformation,GeneratedKey,UploadFile) { 
     //------------------gridlist--------- 	 

       	 
       	 
         var params = {
        		 clinicId : $scope.USER_INFO.orgCd    
         };
         ClinicInformation.query({  
       		                    isArray:true,
       		                    params: angular.toJson(params)
       	                      },  function (list){
     		$scope.clinicinfo =list[0];
     		if(!$scope.clinicinfo.clinicName || !$scope.clinicinfo.clinicLongitude  || !$scope.clinicinfo.clinicLatitude){// if clinic name is null then it is a new user
     			$('#newuserdia').modal('show');
     		} 
         });
 
            
     //------------------add/edit--------- ----------- 	
            $scope.edit =function(tobeedit){ //click on edit link
              	$scope.key= tobeedit;
               };     
               
             $scope.create = function(key) {//add and edit
            	 $scope.tempitem = angular.copy(key);
                   if(key.clinicId){
                		   $scope.keys = GeneratedKey.post({
                               optype : "genKeyN",
                               n : 3
                           },function(result){                    
                				 var newids = result.newIds.split(",");  
                				
                				 var ids= "";
                				 if($scope.tempitem.clinicPic && $scope.tempitem.clinicLicense){
                					 ids = "'"+$scope.tempitem.clinicPic+"','"+$scope.tempitem.clinicLicense+"'";
                				 }else if($scope.tempitem.clinicPic){
                					 ids="'"+$scope.tempitem.clinicPic+"'";
                				 }else if($scope.tempitem.clinicLicense){
                					 ids="'"+$scope.tempitem.clinicLicense+"'";
                				 }
                				 if(ids){
                					 var params = {
        	   		       	            		id : ids
        	   		       	            };
        	   		                       UploadFile.remove({
        	   		       	                params : angular.toJson(params)
        	   		       	            }, function(jsonData) {
        	   		       	             
        				   		       	         var clinicadmin={};
        				   		       	        
 	       	    				               	clinicadmin.clinicId = $scope.tempitem.clinicId;
 	       	    				              	clinicadmin.clinicName = $scope.tempitem.clinicName;
 	       	    				           		clinicadmin.clinicAddress = $scope.tempitem.clinicAddress;
 	       	    				        		clinicadmin.clinicDesc = $scope.tempitem.clinicDesc;
 	       	    				     			clinicadmin.clinicTel = $scope.tempitem.clinicTel;		
 					       	    				clinicadmin.clinicLongitude = $scope.tempitem.clinicLongitude;
 					       	    				clinicadmin.clinicLatitude = $scope.tempitem.clinicLatitude;
 					       	    				clinicadmin.clinicSpeciality = $scope.tempitem.clinicSpeciality;
 					       	    				clinicadmin.clinicPic = "blank";
 					       	    				clinicadmin.clinicLicense = "blank";
        		    				               	
        		    				               	
        		    				               	
        		    				               	
        		    				               	if($scope.uploadProfilePic.length>0){
        		    				               	   clinicadmin.clinicPic = newids[0];
        		    				               	    var uploadProfilefile={};
        			    				               	uploadProfilefile.id=	 newids[0];
        			    				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
        			    				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
        			    				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
        			    				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
        			    				               	UploadFile.put(uploadProfilefile,function(){  });
        		    				               	}
        		    				               	
        		    				               	if($scope.uploadCertificatePic.length>0){
        		    				               		clinicadmin.clinicLicense = newids[1];
        		    				               	   var uploadcertificatefile=[];
        			    				               	uploadcertificatefile.id=	 newids[1];
        			    				               	uploadcertificatefile.fileName = $scope.uploadCertificatePic[0].fileName;
        			    				               	uploadcertificatefile.fileType = $scope.uploadCertificatePic[0].fileType;
        			    				               	uploadcertificatefile.filePath = $scope.uploadCertificatePic[0].filePath;
        			    				               	uploadcertificatefile.orgFileName = $scope.uploadCertificatePic[0].orgFileName;
        			    				               	UploadFile.put(uploadcertificatefile,function(){  });
        		    				               		
        		    				               	}
        		    				               	
        		    				             ClinicInformation.save(clinicadmin,function(){
        					                         	$('#add').modal('hide');
        					                         });
        	   		       	                 
        	   		       	            });
                				 }else{
                					    var clinicadmin={};
				   		       	       
   	    				               	clinicadmin.clinicId = $scope.tempitem.clinicId;
   	    				              	clinicadmin.clinicName = $scope.tempitem.clinicName;
   	    				           		clinicadmin.clinicAddress = $scope.tempitem.clinicAddress;
   	    				        		clinicadmin.clinicDesc = $scope.tempitem.clinicDesc;
   	    				     			clinicadmin.clinicTel = $scope.tempitem.clinicTel;		
			       	    				clinicadmin.clinicLongitude = $scope.tempitem.clinicLongitude;
			       	    				clinicadmin.clinicLatitude = $scope.tempitem.clinicLatitude;
			       	    				clinicadmin.clinicSpeciality = $scope.tempitem.clinicSpeciality;
			       	    				clinicadmin.clinicPic = "blank";
			       	    				clinicadmin.clinicLicense = "blank";
		    				               	
		    				               	
		    				               	
		    				               	
		    				               	if($scope.uploadProfilePic.length>0){
		    				               	   clinicadmin.clinicPic = newids[0];
			    				  	             var uploadProfilefile={};
			    				               	uploadProfilefile.id=	 newids[0];
			    				               	uploadProfilefile.fileName = $scope.uploadProfilePic[0].fileName;
			    				               	uploadProfilefile.fileType = $scope.uploadProfilePic[0].fileType;
			    				               	uploadProfilefile.filePath = $scope.uploadProfilePic[0].filePath;
			    				               	uploadProfilefile.orgFileName = $scope.uploadProfilePic[0].orgFileName;
			    				               	UploadFile.put(uploadProfilefile,function(){  });
		    				               	}
		    				               	
		    				               	if($scope.uploadCertificatePic.length>0){
		    				               		clinicadmin.clinicLicense = newids[1];
		    				               	   var  uploadcertificatefile={};
			    				               	uploadcertificatefile.id=	 newids[1];
			    				               	uploadcertificatefile.fileName = $scope.uploadCertificatePic[0].fileName;
			    				               	uploadcertificatefile.fileType = $scope.uploadCertificatePic[0].fileType;
			    				               	uploadcertificatefile.filePath = $scope.uploadCertificatePic[0].filePath;
			    				               	uploadcertificatefile.orgFileName = $scope.uploadCertificatePic[0].orgFileName;
			    				               	UploadFile.put(uploadcertificatefile,function(){  });
		    				               		
		    				               	}
		    				               	
		    				             ClinicInformation.save(clinicadmin,function(){
					                         	$('#add').modal('hide');
					                         	alert("你已经成功添加了诊所的信息,现在需要您添加医生的信息,请点击菜单栏上的'医生'");
					                         });
                				 }
       	  		                   
                               
                				   
                                 }
                    	      ); 
                		   
               	} 
               }
 
            
 

            $scope.comfirmNewClinic = function() { 
            	$('#add').modal('show');
            	$scope.key= $scope.clinicinfo;
            }; 
 
        }]);
    }

});