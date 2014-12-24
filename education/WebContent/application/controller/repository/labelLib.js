define(function (require, exports, module) {

    //标签库管理
    return function setApp(app) {
        app.controller('RepositoryLabelLibCtrl', ['$scope','LabelLib','CommonLabel','Institution','RelateLabel', function ($scope,LabelLib,CommonLabel,Institution, RelateLabel) {
        
        	   /* $scope.params={  
    	        	'id':$scope.USER_INFO.id
    	        }; */          
        	   
        	     //	function TestCtrl($scope , UserResource){
          $scope.LabelLib = LabelLib; 
          $scope.valid = function(response){//response = {valid : true} 
            return response.listSize == 0;
          };
          $scope.getParams = function(value){
        	   console.log( 'value',value );           
        	  if(!value)return; //value为空不调校验
        	  return {                   
        	   	method : 'ifRepetitionName',
                labelName : value
             }
          };
        	    
       var inRoleId= $scope.USER_INFO.inRoleId;
        	
    	                      
        /* *   server	{Resource}	指定的访问服务器的server
         * 		valid 	{Function}	指定的校验方法
         * 		params	{Function}	指定的发送服务器的数据
         * 		name	{String}	校验程序名称
         * 		fn		{String}	发送服务器的方法(query,get,put,remove)
         */
        
            console.log("-666666 666666--      $scope.USER_INFO.id- -[)--"+$scope.USER_INFO.id);            
            /* */          
          	//按条件分页查询数据                 
        	/* if(currentRoleId==1){  // 系统  管理员=1
        		 $scope.params={  
     	        		'orderBy':'CREATE_DATE'  
     	         };  
        	 }else{
        		 $scope.params={  
     	        		'orderBy':'CREATE_DATE' ,
     	        		'userId': $scope.USER_INFO.id
     	         };  
        	 } */                                                                  
    		$scope.params={  
 	        		'orderBy':'CREATE_DATE',  
 	        	     startTime:"",
        	         endTime: ""    
        	
 	         };                                    
         	$scope.pager=LabelLib;    
         	$scope.LabelLib = LabelLib;
         	 
         	//$scope.CommonLabel = CommonLabel;         
           	$scope.labelList=CommonLabel.query({isArray:true,params:"{}"});                  
            console.log( $scope.USER_INFO.orgCd+ "- 666666--      $scope.USER_INFO.id- -[)--"+ $scope.USER_INFO.id);            
            var orgList  ;
            var  orgArray=[];   
        	var  modifyFlag;
        	var addFlag;
        	
        	  
                                                      
               	
            $scope.modify=function (item,  flag   ) {      //modify(item,'true')
            	
            	$scope.labelLib= angular.copy(item);   ;  
                                                                                            
            	modifyFlag=flag;           
            	console.log(  'item', item);                            
            	console.log(  " item. labelId["+item.labelId );  
            };      
            $scope.toAdd=function (label,aFlag) {    
            	//toAdd(labelLib,'true')"            
            	console.log(  'f', $scope.labelLib);
            	console.log(  'labelLib', label);
            	addFlag='true';
            	     
	            if(aFlag=='true'){
	            	modifyFlag='ts';                           
	            	if  ($scope.label!=undefined){
	            	    labelLib.labelName=""; 
		                labelLib.labelDesc=" ";    
		            	console.log(  "-777test  true");
	            	}          
	            	  $scope.clear(label );                                  
	             	console.log(  "----999-------addFlag------"  );              
	            }   
	                                                        
            	if(label!=null){                           
                 //	$scope.labelLib =labelLib;  
                	label.labelId= undefined; 
                	console.log(  "---------------------");        
            	}
            	                     
            };                                               
            $scope.keep=function (labelLib) {                                                                        
            	console.log(      "     ke    ep---labelLib.labelId ["+   labelLib.labelId); 
            	                          
            	if ( (labelLib.labelId== undefined)&&(addFlag=='true')   ){   //新增                                                                           
            		//consol.keymap[0] +"==" );//+    keyArr[0]+" [[[[[["+ keyArr[0].key   );  
            	//	labelLib.labelId= $scope.keymap[0];                          
            		labelLib.createUser=$scope.USER_INFO.userName;  
            		labelLib.userId=   $scope.USER_INFO.id;    	//用户ID	 
            		labelLib.addFlag="true";     
            		           
            		console.log(" labelLib.labbelLib.labelName.length  ["+labelLib.labelName.length );	
            		console.log("  labelLib.labelDesc.length  ["+ labelLib.labelDesc.length );	
        		
            		   //无重名时，才新增
            		console.log(   "新        增   labelLib.labelId [" ); 
    				$scope.addCommit(labelLib);//save   
            		
            		                
            	/*var	verificationNamelist  = LabelLib.get( { method:'ifRepetitionName',
                 									      'orderBy':'CREATE_DATE'  ,
                 			         	        		  'labelName': labelLib.labelName
                 	                            },function(result){             //("labelNames", names );                                                    
                							   	  if(  result.listSize>=1){
                							   	       alert("有已存在的标签名，不能再次新增");
                							   	  }
                							   	  if(  result.listSize<1 ){
                							   		   //无重名时，才新增
                							   		console.log(   "新        增   labelLib.labelId [" ); 
                					    				$scope.addCommit(labelLib);//save           
              							   	      }                      
                				                 }          
                 	                         );*/   
    		                               	              
            	  // $scope.clear(labelLib);                                          
            		 
    			}             
            	if( (modifyFlag=='true')   ){    //修改        
            		console.log(  " ---修改 ---------- labelId    :labelLib.labelId["+    labelLib.labelId );	   
            		labelLib.addFlag=null;         
            		labelLib.createUser=$scope.USER_INFO.userName;   
            		  
            		console.log( '修改   labelLib', labelLib); 
	   				$scope.update(labelLib);//save  
	   				 console.log( '$scope.labelLib', $scope.labelLib); 
	   				$scope.labelLib= angular.copy(labelLib);         
	   			    console.log( '$scope.labelLib', $scope.labelLib);   
	   			    
	   			 console.log( 'labelLib', labelLib);   
	   			 console.log(   "修改---===========99999999999======-end" );                        
            	}
            	
            	
            };  
            
            $scope.clear = function(labelLib ){ 
            	 labelLib=null;                      
	   				 //save ,清空labelLib                
	   			 $scope.labelLib=null;     
            };               
            $scope.reset = function( ){ 
            	$scope.params.labelName="";                      
            	$scope.params.labelDesc  ="";
            	$scope.params.createUser  ="";
            	$scope.params.createDate="";      
            	$scope.params.startTime="";
            	$scope.params.endTime ="";
            	                                  
            	console.log(   "新9999999	$scope.params.startTime ["+	$scope.params.startTime	);             
            	console.log(   "新--"+	 $scope.params.labelDesc  );
            };
            
            //处理新增                                                                                                                                                                                                      
            $scope.addCommit = function(labelLib){                  
               	console.log(   "addCommit-----vv-- ---  LabelLib.LabelLib.labelId[" );  
            	//  labelLib.put调的是叉入一条数据。
                LabelLib.put({    //c                      
                                                                       
		               labelName:labelLib.labelName, 
	    			   labelDesc:labelLib.labelDesc, 
	    			   addFlag: labelLib.addFlag,                               
	    			   userId : labelLib.userId      ,
	                   createUser:labelLib.createUser 
	             	},$scope.refresh.bind(null , 'current',true))
            };
            //修改                       
            $scope.update = function(labelLib){ 
            	//  Share.put调的是叉入一条数据。
              LabelLib.save({    //save  是UPDATE                       
            		 labelId:labelLib.labelId,                  
	                 labelName:labelLib.labelName, 
  			         labelDesc:labelLib.labelDesc                                                    
	             	},$scope.refresh.bind(null , 'current',true))
            };                                                                                                                     
            $scope.toRemoveDialog=function ( labelLib ) {
             	$scope.labelLib = labelLib;
            };                                                                         
            //删除标签                                                    
            $scope.remove = function(labelLib){
             	console.log(" 先删标签关联表 ------   ");              
            	//先删标签关联表                                                                              
            	 var params={labelId:  labelLib.labelId   };
				 RelateLabel.remove( {params:angular.toJson(params)}, 
						             function(result){                     
					 					 console.log(' result',result);
					 					 
					 					 LabelLib.remove({params:angular.toJson(params)}, 
					 							      $scope.refresh.bind(null , 'current',true));
					 			        
				 						}
					            	  );  
				      
            	console.log(labelLib);                    
            	//var params={labelId:labelLib.labelId};
            	                    
            
            };
                                                                              
        }]);
    } 
} );