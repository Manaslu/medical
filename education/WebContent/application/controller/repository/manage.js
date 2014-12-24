define(function (require, exports, module) {

    //知识库管理
    return function setApp(app) {
        app.controller('RepositoryManageCtrl', ['$scope','Share','CommonLabel','CommonAttachment','Upload','$filter' ,'Manage','Institution','RelateLabel','LabelLib', '$q', function ($scope,Share,CommonLabel,CommonAttachment,Upload, $filter, Manage,Institution,RelateLabel,LabelLib,$q) {
        	var dateFormat = $filter('date');
        	var  upFileCnt=0;  //init                 
         	//按条件分页查询数据
        	$scope.params={                                                                        
        			'orderBy':'APPLY_DATE',
        			'userId': $scope.USER_INFO.id
        	};     
        	var  attachment_atts=[];//每个附件的三个属性存入       
            var  addAttachmentEndFlag="false";//清空附件标志     
            var  manyFileUploadFlag ;
            var  singleFileUploadFlag;       
            var  singleFileUploadStrartFlag  ;  
                                                                             
            var uploadFlag;                                                                     
                      
        	$scope.pager=Share;  
        	$scope.Share = Share;
       
        	//$scope.CommonLabel = CommonLabel;         
          	$scope.labelList=CommonLabel.query({isArray:true,params:{'orderBy':'CREATE_DATE'} });                            
          	                                
          	console.log('$scope.labelList', $scope.labelList );
          	          
         	//$scope.attachmentNamelList=CommonAttachment.query({isArray:true,params:"{}"});                            
         	     
           var  busiDirectionList=['决策支持','服务支撑','风险管控','流程优化','交叉营销','产品创新','其他'];
         
          	      
       	// generateKey() {
       	var  genKey=function(  ){ 
        	   Share.get({                                         
		   			method:'genKey'     
			      },function(result){                     
				   	  console.log( "  ----uid ="+    result.uuid ); 
				      $scope.addsuccKnowledgeId = result.uuid;    
                 }
    	      );   
      		};
           
                     
           //$scope.uuid =$scope.genKey( ); 
    	   console.log( " genKey( )-------- 1="+    genKey( )  );                                                        
    	   console.log( " genKey( )2 ="+   genKey( )  );                                                        
  		                            
	         /*  $scope.CascadeLabelNames =Share.get({                                         
			        			method:'getLabel' 
						      },function(result){                     
							   	   console.log( " labelNames="+    result.labelNames );    //("labelNames", names );                                                    
							   	   
							   	   return   result.labelNames ;
				             }
				); */   
            //跳转到删除对话框
       		$scope.toDelete=function(share){                
       			$scope.share=share;
       		}
         	//跳转到删除附件对话框 
       		$scope.toDeleteAttachment=function(elId, share){                
       			$scope.elId=elId;   			
       			$scope.share=share;
       			console.log(   "  $scope.elId="+$scope.elId    );
       		}
         /*   $scope.download = function(fileDir,fileName,fileType,oldFileName) { 
            	console.log(   "  manage.js     fileDir  fileDir["+fileDir       );    
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
             console.log(  "  turl["+url       );                 
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
        	 
            
            $scope.imgSrc = function( fileDir, fileType,fileId){ 
         	   return '/commonAttachment.shtml?method=download&fileDir='+fileDir+'&fileType='+fileType+'&fileId='+ fileId  
  		     //  return '/commonAttachment.shtml?method=download&oldFileName='+oldFileName+'&fileDir='+fileDir+'&fileType='+fileType 
         	}
                    
                      
             
       		
            //删除角色
            $scope.remove = function(share){
        	 console.log(share);       	    
		   	 var params={knowledgeId:share.knowledgeId};   
			 RelateLabel.remove( {params:angular.toJson(params)}
								 ,function(result){                                                        
									 console.log("-----params--"+params);    	
						
						            Share.remove({params:angular.toJson(params)}//知识库主表
						                    ,function(result){       
						                        //var params={knowledgeId:share.knowledgeId};        
								   			 CommonAttachment.remove({params:angular.toJson(params)
								   				                },
								   				                //删除KNOWSBASEID做外键的附件表后，再删BASE主表
								   				              function(result){    
								   				                 Share.remove({params:angular.toJson(params)}//知识库主表
								   				                     , $scope.refresh.bind(null , 'current',true) 
								   				                 );
								   				              console.log("---     --remove--全部三个表---------"); 
								   				              }
								   				    	   						         
								   			 ); 			       
						                    } 
						                  );   
				                }
				            	 
			       );  
				  
				  console.log("-----remove--    -    --------");   
            };
               
            $scope.delAttach = function(fid, share ){
            	 console.log("delAttach---="+fid);                               
				 var params={id:fid};  
			 
				         
				 CommonAttachment.remove( {
					 params:angular.toJson(params)
				 } , function(result){    
						$scope.attachmentNamelList = CommonAttachment.query({
							isArray:true,
							params :{ knowledgeId:share.knowledgeId} 
						} , function(fileList){
							console.log('new file list', fileList.length  );	
						});     
						
				  });  
            }                                                                                
                                                                                
         //重
            $scope.reset = function(params ){    //reset(params);     
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
            	$scope.params.approvalStats=""; 
            	
            	$scope.params.approvalUser="";//               
            	$scope.params.approvalStartTime="";
            	$scope.params.approvalEndTime="";  
            	               
            };
                                	                      
            // 回显标签ID数组 
            $scope.getLabelIdArray=function(labellibLabelName, callback  ){ 
            	LabelLib.get({                                                                     
 		   			method:'getLabelId' ,
 		   		    names:labellibLabelName                 
 		   		                
 			      },function(result){                           
 				   	  console.log( "  labelIdArray ="+    result.labelIdArray ); 
 				      $scope.labelIdArray =  result.labelIdArray; 
 				 	  callback && callback(result); 
                  }
     	      );  
            	
         	};
                                 
            var defer1 , defer2;    
            var originalFileCnt= 0;                                                            
            $scope.openBox = function(share,addFlag,modifyFlag){
            	       	         
          		//$scope.uploader && $scope.uploader.reset();
          		//$scope.singleUploader && $scope.singleUploader.reset();
          	                       
          		 console.log(  '[', share,']');                       
            	shareDefer=null , newShareData=null;     
            	//$scope.share = share || {};       
            	//设修改的回显                    
                if(!share){
                	$scope.share ={};     
                	  //console.log(  '==[', $scope.share );  
                }
                if(share){
                	 $scope.share= angular.copy(share); 
                }
                //lablel 回显示
               
                console.log( "===$scope.share.labellibLabelName	["+ $scope.share.labellibLabelName);
        		                                                   
            	//新增                  	                                                                 
            	if(addFlag =='true')  {                       
	            	 $scope.share.knowledgeName="";          
	            	 $scope.share.labelNameList="" ; 
	            	 $scope.share.knowledgeType="" ;    
	            	 $scope.share.knowledgeSonType="";               
	            	            
	            	 $scope.share.professional="";    
	            	 $scope.share.busiDirection="";    
	            	 $scope.share.authors="";                
	            	 $scope.share.authorsCompany=""; 
	            	 $scope.share.knowledgeDesc="";  
	            	 $scope.share.content="";
	            	    
	            	 $scope.attachmentNamelList="";  //初始为空  
	            	                                                        
	               	 $scope.addFlag="true";    
	            	 $scope.modifyFlag="false";	    
	            	           
	            	// console.log( '$scope.USER_INFO.institution',$scope.USER_INFO.institution);
	               	 //console.log("]op----1  ="+angular.fromJson($scope.USER_INFO.institution)     );    
	               	// var institution = angular.fromJson($scope.USER_INFO.orgName);
	               	 
	               	 $scope.share.authors = $scope.USER_INFO.userName;           
	               	 console.log( "inRoleId["+$scope.USER_INFO.inRoleId  +"	$scope.addFlag ["+	 $scope.addFlag+"  "  + "=op---  ["  );   
	               	// if(institution){     
	            		$scope.share.authorsCompany = $scope.USER_INFO.orgName;
	               		$scope.share.orgCd =$scope.USER_INFO.orgCd;
	               		                                      
	               	 console.log( "$$$$$$$$$ $scope.share.authorsCompany   ["+ $scope.share.authorsCompany  );   
	               	 console.log( " $scope.share.orgCd   ["+ $scope.share.orgCd   );   
		   	              
	               	                 
	               //每次新增时清空  
	               //	share=null;                   
	    	   	   // $scope.share=null;            
	    	   	 console.log(  " 每次新增时清空   ");
               	}              
            	//修改                                              
            	if(modifyFlag =='true'){     
                  
            		 console.log( "openfox  1----------knowledgeType["+ share.knowledgeType); 
            		 var  type=share.knowledgeType;
            		 //init                      
            		 share.knowledgeSonType="";  
            		 $scope.share.knowledgeSonType="";  
            		 
                 // console.log( "openfox 2 -------$share.knowledgeSonType["+share.knowledgeSonType); 
              		
            		if(type=='需求'){    
            			$scope.share.knowledgeType='案例';
            			       share.knowledgeSonType='需求';
            			$scope.share.knowledgeSonType='需求';
            		} else if(  type=='报告'){   //''){
            			$scope.share.knowledgeType='案例';
            			       share.knowledgeSonType='报告';
            			$scope.share.knowledgeSonType='报告';
            		} else if(  type=='脚本'){   //''){
            			$scope.share.knowledgeType='案例';
            			       share.knowledgeSonType='脚本';
            			$scope.share.knowledgeSonType='脚本';
            			 console.log( "------foot");
            		}   else if (  type=='应用报告'){   //''){
            			$scope.share.knowledgeType='案例';
            			        share.knowledgeSonType='应用报告';
            			$scope.share.knowledgeSonType='应用报告';
            			 console.log( "-----------55");
            		}                                                           
            		 console.log( "====openfox 33  share.share.knowledgeSonType["+ share.knowledgeSonType); 
            		 console.log( "===	["+share.labellibLabelName);
            		
              	  $scope.getLabelIdArray(share.labellibLabelName,function(result){                           
              		share.labelNameList =  result.labelIdArray;  
              		$scope.share.labelNameList=   result.labelIdArray;             
              		 console.log( "==       $scope.share.labelNameList["+ $scope.share.labelNameList); 
                   	
	             });                      
              	 
            	 $scope.attachmentNamelList=CommonAttachment.query({isArray:true,params:{ knowledgeId:share.knowledgeId} }  );      
            	 attachmentNamelList=CommonAttachment.query({isArray:true,params:{ knowledgeId:share.knowledgeId} }  );      
            	 console.log("----whx----" +attachmentNamelList);   
            	 $scope.modifyFlag="true";	
            	 $scope.addFlag="false";          
            	 Manage.getFileCnt({'id':share.knowledgeId},function(result){
            		 if(result){   
                		 originalFileCnt=result.filecnt;      
                		 console.log( originalFileCnt+"]originalFileCnt --------------------"  );
                	 } 
            	 });  
            	 //回显标签    
            	//var  labellibLabelNameArray= share.labellibLabelName.split(',');
            	//console.log('labellibLabelNameArray',labellibLabelNameArray);
            	                  
            	}               
            	                              
            	if(share.approvalStats=='驳回'){
            		 $scope.attachmentNamelList=CommonAttachment.query({isArray:true,params:{ knowledgeId:share.knowledgeId} }  );      
	                           
	               	 $scope.addFlag="false";          
	               	 Manage.getFileCnt({'id':share.knowledgeId},function(result){
	               		 if(result){   
	                   		 originalFileCnt=result.filecnt;      
	                   		 console.log( originalFileCnt+"]originalFileCnt --------------------"  );
	                   	 } 
	               	 });  
            	} 
            	 
            	console.log( "openBox     attachmentNamelList ====["+   share.knowledgeId);  
            	//每次新增时清空   
            };                              
            var shareDefer , newShareData;                
            $scope.fileQueued = function(file){
            	if(!shareDefer){//只保存一次
            		shareDefer = true;
            		if($scope.addFlag == 'true'){ //上传附件，新增                
            			console.log( '$scope.share', $scope.share);  
            			$scope.share.approvalStats="未审核";    		                          
    	   			} 
            	}  
            };
             //取消时                        
            $scope.delAddObj = function(){    
                                                                                                   
            };     
            //取机构树
        	var treeBox;
        	function click(e){
    			if(!$.contains(treeBox[0] , e.target)){
    				treeBox.slideUp();
    				$(document).off('click',click);
    			}
    		}
        	$scope.showTree = function(el , share){
        		$scope.showTree.share = share;
        		treeBox = $('#ztree-box2');
        		$(document).off('click' , click)
        		var pos = $(el).position();
        		var api = $scope.zTreeApi2;
        		
        		treeBox.css({
        			width : $(el).parents('.input-group').width() + 'px',
        			left : pos.left + 'px',
        			top : pos.top + 30 + 'px'
        		}).slideDown();
        		
        		setTimeout(function(){
        			$(document).on('click' , click);
        		},1);
        		
        		if(api){//取消全部选中
        			var nodes = api.getSelectedNodes();
        			nodes.forEach(function(node){
        				api.cancelSelectedNode(node);	
        			});
        		} 
        	} 
        	$scope.settings = {
        		callback : {
        			onClick : function(e , id , node , level){
        				$scope.$apply(function(){
        					if($scope.showTree.share){
        						$scope.showTree.share.authorsCompany = node.name;
        						$scope.showTree.share.orgCd = node.orgCd;     
        					}
        					treeBox.slideUp();
        					$(document).off('click' , click);
        				});
        			}
        		}
            }; 
        	//查询机构树
        	$scope.data = Institution.query({
                list: true,
                params: {
                	id: $scope.USER_INFO.orgCd=='AA0000000000' ? '' : $scope.USER_INFO.orgCd,
                	parentOrgCd:$scope.USER_INFO.orgCd=='AA0000000000' ? '99999' : ''
                }
            } , function(result){
            	var node = find(result , 'orgCd' , $scope.params.orgCd);
   				if(node){
   					$scope.params.authorsCompany = node.name;
   				}
            });       
        	
        	function find(list , key , val){
        		for (var i = 0; i < list.length; i++) {
					if(list[i][key] == val){
						return list[i];
					}
				}
        	}
            
           function   beforeSend (data){ 
            	$scope.share.applyUser =  $scope.USER_INFO.userName;	                                               
            	if(newShareData){          
            		$scope.share.knowledgeId= newShareData.KnowledgeId;
            	}  
            	console.log(   "  ===========  BeforeSend send-");
            	$scope.share.applyUser =  $scope.USER_INFO.userName;	                                               
            };          
                      
            /*function  send(btn,share,formDataMap) {              
            	 console.log('新增时且 上传test      附件'，formDataMap   );   
              	            
            }*/      
            //(1上传完附件,2不上传),点保存时    
        	$scope.send = function(btn, share,  formDataMap ){            		         
        		// console.log("!$scope.singleUploader.isComplete()["+!$scope.singleUploader.isComplete());    
        		// singleFileUploadFlag = !$scope.singleUploader.isComplete();
        	   var  multipleFileList= formDataMap.multipleFileList;
   	    	   var  singleFile= formDataMap.singleFile;
   	    	                                                   
   	    	  console.log(' singleFile',singleFile   );   
   	    	  console.log('新增时且 上传duo[',multipleFileList   );                                                                                        
        		if(share.knowledgeType=='案例'){ 
        			if(share.knowledgeSonType=='需求'){
        				share.knowledgeType='需求';
        			}  
        			if(share.knowledgeSonType=='报告'){
        				share.knowledgeType='报告';
        			}  
        			if(share.knowledgeSonType=='脚本'){
        				share.knowledgeType='脚本';
        			}  
        			if(share.knowledgeSonType=='应用报告'){
        				share.knowledgeType='应用报告';
        			}  
        		}    
        
	        	//新增时且上传附件                    
        	    if($scope.addFlag == 'true' ){ //含，第二、三、四种，      //第 二种情况 有多附件上传，无单附件上传时
        	    	 //如有单附件开始时  	 //第四种只有单附件上传时
        	         //  多附件，单直接从   .multipleFileList，            singleFile         	    	 
        	    	 if( multipleFileList ){
        	    		//  console.log('新增时且 上传附件'，multipleFileList   );   
              	       //  console.log("多附件 legng[" +  multipleFileList.length); 
        	    	   }                               
        	          share.fileCnt =0;  
        	      	 $scope.share.approvalStats="未审核";    
	        		 $scope.share.authorsCompany=  share.authorsCompany;  //
	        		  // RelateLabel              
					 var  callbackKnowledgeId=""; 
					 var  reLabelIdList=[];  //
					// save share 刷新页面   
 	   				 genKey( );                
         			              	               
        	    	 share.knowledgeId=  $scope.addsuccKnowledgeId;               
        	    	 var   attachmentAndLabelId= share.knowledgeId; 
        	    	 share.fileCnt=upFileCnt;   
        	    	                              
        	    	 //都传完时 
        	     	 //var result1 = results[0];	//var result2 = results[1];  
	        	     $scope.addCommit(share,function(result) {       
      	            		 /*for(var k=0;k<attachment_atts.length;k++){ //一个数组记录一个附件信息 
     	    		    	     addAttachment(  share.knowledgeId,attachment_atts[k].fileName,attachment_atts[k].fileType,attachment_atts[k].filePath, attachment_atts[k].oldFileName ,  attachment_atts[k].picFlag   );
     	    		    	 }*/  
	        	    	 for(var k=0;k<multipleFileList.length;k++){ //一个数组记录    多附件信息 
	    		    	  addAttachment(share.knowledgeId,multipleFileList[k].fileName,multipleFileList[k].fileType,multipleFileList[k].filePath, multipleFileList[k].orgFileName ,  multipleFileList[k].picFlag   );
	    		    	 }
        	    	     for(var k=0;k<singleFile.length;k++){ //单附件信息 
				    	  addAttachment( share.knowledgeId,singleFile[k].fileName,singleFile[k].fileType,singleFile[k].filePath,singleFile[k].orgFileName,  "true"   );
				         }    
      	            		//叉关联标签入库
  		       	    	 var reLabelIdList=[];      
	  		   	   			   if($scope.share.labelNameList){
	  	     	   					$scope.share.labelNameList.forEach(function(item){  	  
	  	   	  					    	  reLabelIdList.push(item); 
	  	   	  				        });  
	  	     	   				  console.log(  $scope.addsuccKnowledgeId+  "]id==  reLabelIdList--------["+reLabelIdList); //  
	  	     					    for( var p in  reLabelIdList){ 
	  	     							   if(typeof(reLabelIdList[p])=="function"){   
	  	     						       }else{	    	   //入关联表
	       						    	   console.log(   "-入关联表--reLabelIdList[p]-"+reLabelIdList[p]); 
	       						    	   addKnowledgebaseRelate( share.knowledgeId ,reLabelIdList[p]);// save
	       						         }   
	  	     						}  
	  		   	   			   }   
  		   	   		        //attachment_atts=[];  //上传完，清空附件数组     
        	    		 });                         
    	    	                      
        	    	    console.log('end新增      时    且上传附件    ' ); 
        	    	    //清空附件标志 
    	             $('#pushDialog').modal('hide');// 关掉对话框    
	        	 }                          
        	                                                                                    
        	  //只新增时    
        	     //修改且上传附件时，
        	     if( $scope.modifyFlag =='true'){     
        	    	     if(share.approvalStats=='驳回'){
            			   share.approvalStats='未审核';           
            			 } 
            			 upFileCnt=   originalFileCnt+upFileCnt;
            			 //多附件，单 直接从   .multipleFileList，    singleFile 	 
            			 if(singleFile){
            				// console.log(   'singleFile',singleFile );    
            			 } 
	               	 //都传完时                                            
	        	    	 share.fileCnt =upFileCnt;
	        	    	 $scope.update(share);//save  是UPDATE   
	        	    	 
	        	    	 var doMultipleFile = function ( shareKnowledgeId,fileName, fileType, filePath,orgFileName	){
	        	    		 CommonAttachment.query({  	//先查询是否有原 重名的多附件，如有则删掉原重名的多附件
		        	    		 isArray:true,
		        	    		 params :{ 
		        	    			 knowledgeId: shareKnowledgeId, 
		        	    			 oldFileName: orgFileName 
		        	    		 }                              
		        	    	 }, function(result){       
						   	     console.log(   'muliti  attache modify',result);
						     	 console.log('  --kno wledgeId',shareKnowledgeId);
						     	 if(result){  //如有原 重名的多附件，则删掉原重名的多附件
						     		 var params={knowledgeId:shareKnowledgeId,oldFileName:orgFileName };        
						   	         CommonAttachment.remove( {
						   	        	 		 	  params:angular.toJson(params)
						   	        	 		 	  },function(result){ 
						   	    	                 addAttachment(shareKnowledgeId,fileName,fileType,filePath,orgFileName, ""  );													    	   	    	 
										   	            }
						   	    		              );
						     	  }else{ //  无重名的多附件，直接叉入
						     		 console.log( "	 mul   orgFileName["+orgFileName);
						     		addAttachment(shareKnowledgeId,fileName,fileType,filePath,orgFileName, ""  );													    	   	    	   	        
						     	  }  
		 					    } 
					        );  
	        	    	 }        
		        	    	  //有上传附件，则去重并入库                            	    		                
	        	    	 for(var k=0;k<multipleFileList.length;k++){ //多附件信息  
	        	    		 doMultipleFile(share.knowledgeId,multipleFileList[k].fileName,
	        	    				 multipleFileList[k].fileType,multipleFileList[k].filePath,
	        	    				 multipleFileList[k].orgFileName);
				    	 }  	                                               
	        	    	 
	        	      	                                    
	        	    	 console.log(  	"singleFile.length "+singleFile.length );
		    	         if (singleFile.length ){ //单附件 
		        	    	 CommonAttachment.query(  //先查询是否有原单附件，如有则删掉原附件
													{isArray:true,				      
													 params :{ knowledgeId: share.knowledgeId,
														       picFlag:'true'
														     }
													}, 
													function(result){       
											   	     console.log(   'sin attache modify',result);
											     	 console.log(' share-  --knowledgeId',share.knowledgeId); 
											     	 if(result){  //如有原重名的单附件，则删掉原重名的单附件
											     	   var params={ knowledgeId:share.knowledgeId,
											   	    		        picFlag:'true'   }; 			     	
											   	       CommonAttachment.remove( {params:angular.toJson(params)},  
											   	    		            function(result){     	    		
														     	  	       addAttachment(share.knowledgeId,singleFile[0].fileName,singleFile[0].fileType,
														     				             singleFile[0].filePath,singleFile[0].orgFileName, "true" );          		   	            
											   	                           }
											   	       					);         		
											     	 }else{
											     		                                    
											     	  	console.log(  " singleFile remove  2"+ singleFile[0].fileName +" ingleFile[k].orgFileName="+  singleFile[0].orgFileName );	 								     	    
											     		addAttachment(share.knowledgeId,singleFile[0].fileName,singleFile[0].fileType,
											     				             singleFile[0].filePath,singleFile[0].orgFileName, "true" );           			  
								   	                      
											     	 }
							 					    } 
							  );    
					     }                                                                                                                                                                                    
		    	    	    //upload afere ,clear
		    	    	 // attachment_atts=[];  //上传完，清空附件数组      
	        	    	 //  公用改VO和标签                                   	   
		    	         var doRelateLabel  = function (shareKnowledgeId, reLabelId){
		    	             addKnowledgebaseRelate( shareKnowledgeId  ,  reLabelId);// save   
		    	         } 
		    	         var  reLabelIdList=[];                            
	        	    	 $scope.update(share);//save  是UPDATE  
	     	   				if($scope.share.labelNameList!=undefined ){
	     						 $scope.share.labelNameList.forEach(function(item){  // map              
	     					    	  reLabelIdList.push(item); 
	     				    	 });  
	                       
	     						//删掉原关联标签，叉入新选中的标签
	     						 var params={knowledgeId:share.knowledgeId};   
	     						 RelateLabel.remove( {params:angular.toJson(params)} 
	     									); 
	     						
	     					     console.log( "修改时$$$   reLabelIdList----------["+reLabelIdList); //  
	     						 for( var p in  reLabelIdList){ 
	     							   if(typeof(reLabelIdList[p])=="function"){   
	     						       }else{                  
	     						    	   //modify关联表                    
	     						    	  console.log("  reLabelIdList.length[ "+ reLabelIdList.length);   
	     						    	  console.log(   "-m-reLabelIdList[p]-"+reLabelIdList[p]); 
	     						    	    // knowledgeId:share.knowledgeId,  
	     						    	  doRelateLabel( share.knowledgeId  , reLabelIdList[p] );// save  	
	     						    	  		                                           				    	   
	     						         }   
	     						 }  
	     					 }   
	        	    	 console.log(   "attachent 修改----end" ); 
        	      }      
	   			//清空附件数
	        	upFileCnt=0;   //  $scope.uploadFlag='false';      //init   false         
	   		    // $scope.addsuccKnowledgeId="ff";    
	   		    //清空附件标志 
	    	    //uploadFlag=null;
	   		    $('#pushDialog').modal('hide');//close box 
        	}; 
         	/*     */       
         	var attachmentPut = function(fileName,fileType,filePath, oldFileName, picFlag){   //put(role,demandRebuildColumn  	
         		console.log("添加 附件" , arguments);
                attachment_atts.push({
                	fileName:fileName , 
                	fileType: fileType,
                	filePath:filePath,
                	oldFileName:oldFileName ,
                	picFlag:picFlag          
                	
                });                 
            }; 
            //处理新增知识     ,刷新页面                          
            $scope.addCommit = function(share , callback){                    
            	console.log( "addCommit--------- share.k nowledgeId=" , share.knowledgeId);  	                            
            	//  Share.put调的是叉入一条数据。
            	 Share.put({                        
		               knowledgeId:share.knowledgeId, 
	              	   knowledgeName:share.knowledgeName,
	              	   labelName:"" ,
	              	   knowledgeType:share.knowledgeType,
	               	   knowledgeDesc:share.knowledgeDesc,             
	                   content: share.content,                    
	                   applyUser :  $scope.USER_INFO.userName,        	
	              	   approvalStats: $scope.share.approvalStats, 
	              	   fileCnt:  share.fileCnt,             
	              	   
	              	   busiDirection:  share.busiDirection,
	                   authors:    share.authors,
	              	   authorsCompany: share.authorsCompany,
	              	   professional:   share.professional,
	              	   userId:   $scope.USER_INFO.id         
	             	},	function(result) {                 
	             		$scope.refresh('current',true);
	             		callback && callback(result);
	             	});
            };  
            //处理新增知识 ,不刷新页面                          
          
            //加附件                 
            var  addAttachment=function(knowledgeId,fileName,knowledgeType,filePath,oldFileName ,picFlag  ){
            	console.log("加附件= addAttachment  =   " );
            	console.log("addAttachment" , arguments);
            	CommonAttachment.put({      
        		  //  id:null,                  
        		    knowledgeId :knowledgeId,          
        		    fileName:     fileName,
        		    fileType:knowledgeType,
        		    uploadStats:'已上传',
        		    fileDir: filePath,         //data.KnowledgeId  
        		    oldFileName: oldFileName,     
        			picFlag: picFlag       
    				// 存附件实体 
    				/*//KnowledgeBaseAtt   entity = new  KnowledgeBaseAtt();  
    				entity.setFileName();         
    				entity.setFileType( knowledgeType);//      
    				entity.setUploadDate(new Date()); // from back server
    				entity.setUploadStats("已上传");v*/
    				//entity.setFileDir(path);    
        	     },$scope.refresh.bind(null , 'current',true)       ); 
            };
            //ADD关联知识                                  
            
          //加附件                 
            var addKnowledgebaseRelate = function(  callknowledgeId,reLabelId ){ 
            	//RelateLabel.put调的是叉入一条数据。
            	console.log( "  叉入--------- addKnowledgebaseRelate   ");  	            
            	RelateLabel.put({      
            		    id:null,      
            		    knowledgeId :callknowledgeId, 
            		    labelId:     reLabelId 
            	 }); 
            }   
             ///修改关联知识表                   
             function updateKnowledgebaseRelate  ( callknowledgeId,reLabelId ){ 
            	// update一条数据。                                                                                                             
            	console.log(   "----修改关联    ---"+callknowledgeId);         
            	RelateLabel.save({      
            		    id:null,      
            		    knowledgeId :callknowledgeId, 
            		    labelId:     reLabelId 
            	 }); 
            };
             
            //修改知识                                              
            $scope.update = function(share){ 
            	console.log(   "----    ----修改知识     :share.knowledgeId["+share.knowledgeId);   
            	console.log( "share.authorsCompany["+share.authorsCompany+  "]-- share.knowledgeDesc["+ share.knowledgeDesc    );
            	if(share.approvalStats=='驳回'){       
    			   share.approvalStats="未审核";                       
    			}                                                  
            	console.log( "------修改知识    share.knowledgeType["+   share.knowledgeType); 
               	console.log( "---share.knowledgeSonType["+ share.knowledgeSonType);
                                        
                Share.save({    //save  是UPDATE                       
		               knowledgeId:share.knowledgeId, 
	              	   knowledgeName:share.knowledgeName,
	              	   labelName:share.labelName,
	              	   knowledgeType:share.knowledgeType,
	               	   knowledgeDesc:share.knowledgeDesc, 
	                   content:      share.content, 
	              	   approvalStats: share.approvalStats,
	              	   
	              	   busiDirection: share.busiDirection,                  
	                   authors: share.authors,
	              	   authorsCompany: share.authorsCompany,   //"share.orgName"
	              	   professional:   share.professional,
	              	   fileCnt:  share.fileCnt 
	             	},$scope.refresh.bind(null , 'current',true))
            };     	                                
            //新增角色
            $scope.create = function(share){
            	share.$put($scope.refresh.bind(null , 'first',true));
            };                                       
            // 审核角色
            $scope.examinePage = function(share){ 
            	console.log(share);
            	
       			$scope.share=share; 
       			
            	$scope.attachmentNamelList=CommonAttachment.query({isArray:true,params:{ knowledgeId: share.knowledgeId} }  );                            
            	console.log(   " 审核角色     attachmentNamelList       =====["+   share.knowledgeId);  
            }; 
            
            //发布
            $scope.publish = function(share){ 
            	console.log(share);
       			$scope.share=share; 
       		 	$scope.attachmentNamelList=CommonAttachment.query({isArray:true,params:{ knowledgeId: share.knowledgeId} }  );                
            };               
            //预览                             
            $scope.preview = function(share){ 
            	console.log(share);
       			$scope.share=share; 
       		 	$scope.attachmentNamelList=CommonAttachment.query({isArray:true,params:{ knowledgeId: share.knowledgeId} }  );                
       	      	console.log(   " 预览    =====["+   share.knowledgeId);  
       	 	                            
            };    
                        
            //发布提交 
            $scope.publishSubmit = function(share){ 
            	$scope.share=share;  
            	  share.approvalStats='发布';   
            	Share.save({
                	   knowledgeId:share.knowledgeId, 
                	   approvalStats: share.approvalStats   
               	},$scope.refresh.bind(null , 'current',true))
                   
            }
            
             
        	var appDate;
            // 审核角色
            $scope.examine = function(share){ 
            	//console.log(share);
       			$scope.share=share;
       			share.applyDate=""; // 
       			//share.approvalDateStr="2014-05-09 00:00"; //
       			delete share.approvalDate;
       			     
               share.approvalStats='待审核';   
           ///    Share.save({approvalDate:share.approvalDate});                
               Share.save({
            	   knowledgeName:share.knowledgeName,
            	   knowledgeType:share.knowledgeType,
            	   knowledgeId:share.knowledgeId, 
            	   approvalStats: share.approvalStats,
            	   remarks:  share.remarks                                             
           	},$scope.refresh.bind(null , 'current',true))
               
           //  share.$put($scope.refresh.bind(null , 'first',true));
            };
                                                       
              
        }]);
    }
              
}); 
/**
 * var defer1 , defer2;
 * $scope.send = function(){
 * 		$scope.upload1.start();
 * 		$scope.upload2.start();
 * 	defer1 = $q.defer();
 * 	defer2 = $q.defer(); 
 * 		if(upload1 and !upload2){
 * 			defer2.resolve();
 * 		}
 * 		if(!upload1 and !upload2){
 * 			defer1.resolve();
 * 			defer2.resolve();
 * 		}
 * 
 * 		$q.all([defer1.promise , defer2.promise]).then(function(results){
 * 			var result1 = results[0];
 * 			var result2 = results[1];
 * 		});
 * 
 * }
 * 
 * var uploadfilelist1 = [];
 * $scope.uploadSuccess1 = function(file , response){
 * 	uploadfilelist1.push(response);
 * 	if($scope.upload1.isComplete()){
 * 		defer1.resolve(uploadfileList1);
 * 	}
 * }
 * 
 * $scope.uploadSuccess2 = function(file , response){
 * 		defer2.resolve(response);
 * }
 * 
 */


