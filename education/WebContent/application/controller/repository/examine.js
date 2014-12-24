 define(function (require, exports, module) {

    //知识库审核
    return function setApp(app) {
        app.controller('RepositoryExamineCtrl', ['$scope' ,'Share','CommonLabel','CommonAttachment' , '$filter', 
                                                 function ($scope,Share,CommonLabel,CommonAttachment, $filter) {
            //$scope.list = [{},{},{},{},{},{},{},{},{}];                    
             	//按条件分页查询数据
        	$scope.params={                       
        			'orderBy':'APPLY_DATE',  //"orderBy        
        			'userId':  $scope.USER_INFO.id,
        			'approvalStats':'待审核'
        	};                               
            	$scope.pager=Share;                
            /*	findByPager(params:{     'orderBy':'APPLY_DATE',  	'userId': $scope.USER_INFO.id
            	       }  ); */ 
            	$scope.Share = Share;
            	$scope.labelList=CommonLabel.query({isArray:true,params:"{}"});         
            	  
            	$scope.examine= function(share){
            		$scope.share=share;         
            		//attachmentNameList
            		  console.log( "-  share.knowledgeId="+share.knowledgeId);
                	$scope.attachmentNameList=CommonAttachment.query(
                			{isArray:true,
                			  params:{ knowledgeId: share.knowledgeId} 
                			} 
                	);                            
                	 console.log( $scope.attachmentNameList);  
                	
                	
            	}; 
            	var  currentDay;
            	var  fmtCurrentDay;
            	              
            	var dateFIlter = $filter('date'); 
            	fmtCurrentDay = dateFIlter(+new Date , 'yyyy-MM-dd');//+new Date                 
            	     
                var   remoteAddr="";  	 	
                $scope.remoteAddr =Share.get({                                         
    			 			        		method:'getRemoteAddr' 
    				               },function(result){                     
    				            	   console.log( "   result.serverAddr ="+    result.serverAddr );                                                        
    				            	   $scope.remoteAddr=  result.serverAddr ; 
    				            	    return  result.serverAddr;
    				               }
    					 );          
            console.log( "-   $scope.remoteAddr="+$scope.remoteAddr );  
	                                 
              
                
            $scope.imgSrc = function( fileDir, fileType,fileId){ 
         	   return '/commonAttachment.shtml?method=download&fileDir='+fileDir+'&fileType='+fileType+'&fileId='+ fileId  
  		     //  return '/commonAttachment.shtml?method=download&oldFileName='+oldFileName+'&fileDir='+fileDir+'&fileType='+fileType 
         	}
                       
           /* $scope.download = function(fileDir,fileName,fileType,oldFileName) { 
             	console.log( "fileDir  fileDir["+fileDir       );    
             	 console.log( "fileDir  fileName["+fileName       );   
             	 var downSet={
             			 'fileDir' : '',
             			 'fileName': '',
             			 'fileType': '',
             			 'oldFileName': '' 
             	 };     
             	 
           	   downSet.fileDir = fileDir;
           	   downSet.fileName = fileName;
           	   downSet.fileType = fileType;
           	   downSet.oldFileName= oldFileName;     
           	   console.log( 'downSet', downSet       );        
              var currDate=new Date();             
              var url=  '/commonAttachment.shtml?method=download&params='+ angular.toJson(downSet)+'&time='+currDate.getTime();//                    
              console.log(  "sftest  turl["+url       );                 
              return url;        
           };*/
               
            var downLink = $('#download-link');
             
            $scope.download = function( fileDir, fileType,fileId ){                                                         
			     var currDate=new Date();      //附件为空， 不能下载          
      	     CommonAttachment.get( {                                                                                                                                                                      
				      method :'verifyFile',
				      fileDir:  fileDir                       
			       }, function(result){
		        	if( result.fileDownloadFlag=='yes'    ){               
		        		console.log( "3------params=" );                           
		        		download('/commonAttachment.shtml?method=download&fileDir='+fileDir+'&fileType='+fileType+'&fileId='+ fileId   );
		        	}else if ( result.fileDownloadFlag=='no'   ){     
             			alert('附件为空， 不能下载 ! ');
             		}
             	}); 
            };              
              
            function download(url){
             	downLink[0].href = url;
             	downLink[0].click();                    
            };
 	    			 
           
          
            $scope.reset = function( ){ 
            	console.log("--ttttttrrrrrr");         
            	$scope.params.knowledgeName=""; 
            	$scope.params.labelName=""; 
            	$scope.params.typelist=""; 
            	$scope.params.sonTypelist=""; 
            	$scope.params.professionallist="";   
            	$scope.params.busiDirectionlist=""; 
            	$scope.params.startTime="";
            	$scope.params.endTime="";  
            	              
            	$scope.params.labellibLabelName="";     
            	$scope.params.authors="";      
            	//"params.labellibLabelName"             
            };
                        
           
            	$scope.agree= function(share){
            		$scope.getTime;                                    
            		$scope.share=share; 
            		share.approvalStats='发布';                        
            		share.approvalUser=$scope.USER_INFO.userName;
            		//	id : $scope.USER_INFO.provCode,name : $scope.USER_INFO.provName
            		            
            		share.approvalFlag="true";  
            //		share.approvalOpin
            		console.log("--tttttttttttaaashare.approvalDate["+share.approvalDate  );                                     
            		console.log("----     share.approvalFlag["+share.approvalFlag  );                                     
                                         
            	    $scope.update(share);
            	}; 
            	//驳回
            	$scope.refuse= function(share){
            		$scope.getTime;  
            		$scope.share=share; 
            		share.approvalStats='驳回';   
            		share.approvalUser=$scope.USER_INFO.userName;
            		
            		share.approvalFlag="true";  
               	    $scope.update(share); 
            	}  
                                                                                                                     
                //修改知识                                   
                $scope.update = function(share){ 
                	//  Share.put调的是叉入一条数据。
                	 Share.save({    //save  是UPDATE                       
    		               knowledgeId:share.knowledgeId, 
    		               approvalFlag: share.approvalFlag, 
    	              	   //knowledgeName:share.knowledgeName,
    	              	  // labelName:share.labelName,            
               	           	  // knowledgeDesc:share.knowledgeDesc,   
    		              // approvalDate:share.approvalDate,
    		               approvalUser:share.approvalUser,          
                	       approvalOpin:share.approvalOpin, 
    	              	   approvalStats:share.approvalStats   
    	             	},$scope.refresh.bind(null , 'current',true))
                };
            //examine.html
        }]);
    }

});