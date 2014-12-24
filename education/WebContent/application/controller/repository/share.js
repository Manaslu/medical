define(function (require, exports, module) {

    //共享知识库 
	//('share.shtml')
    return function setApp(app) {
        app.controller('RepositoryShareCtrl', ['$scope','Share','CommonLabel','CommonAttachment','$q' , 
                                               function ($scope,Share,CommonLabel,CommonAttachment,$q ) {
        	   
        	     //所有人可见共享知识                      
        	$scope.params={
        			approvalStats:'发布',
        			'orderBy':'APPLY_DATE'
        		 //	'userId': $scope.USER_INFO.id
        	};             
        	             
         	//按条件分页查询数据
        	$scope.pager=Share;  
        	$scope.Share = Share;
        	$scope.labelList=CommonLabel.query({isArray:true,params:"{}"});                            
        	   
        	var type1=  ['案例','经验'] ;
        	var type2=  ['案例','基础信息']  ;
        	var type3=  ['案例','经验','基础信息']  ;    
            var   remoteAddr="";  	 	
            $scope.remoteAddr =Share.get({                                         
		        		method:'getRemoteAddr' 
           },function(result){                        
        	   $scope.remoteAddr=  result.serverAddr ;
           });             	
            
       		
            //新增角色
            $scope.create = function(share){
            	share.$put($scope.refresh.bind(null , 'first',true));
            };  
            
            	       
           $scope.imgSrc = function( fileDir, fileType,fileId){
        	        
        	   return '/commonAttachment.shtml?method=download&fileDir='+fileDir+'&fileType='+fileType+'&fileId='+ fileId  
 		     //  return '/commonAttachment.shtml?method=download&oldFileName='+oldFileName+'&fileDir='+fileDir+'&fileType='+fileType 
        	}
                                                  
           var downLink = $('#download-link');
           //download(  fileDir, fileType,oldFileName ) 
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
            	console.log( "5-----url="+ url );
        		                              
            	downLink[0].click();                    
           };
	    			 
             //重置
             $scope.reset = function( ){          
             	$scope.params = {}; 
             };	    
	    			 
	    			
            //查看详情
       		$scope.toDetail = function( share){         
       			$scope.share=share; 
       		 	$scope.attachmentNameList=
       		 		CommonAttachment.query({isArray:true,params:{ knowledgeId: share.knowledgeId} } );                
    	                                           
            };    
            
            
        }]);
    }
     
});