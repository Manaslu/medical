define(function (require, exports, module) {

    //分析过程参数化
    return function setApp(app) {
        app.controller('DataAnalyseProcessParametricCtrl', 
        		 ['$scope','AnalysisTheme','AnalysisType','ParaYearList','ParaPapers','ParaIndustry',
        		  'ParaImportantClient','ParaProvince','ParaDefinitionBigClient','ParameterList','$timeout'
        		  ,function ($scope,AnalysisTheme,AnalysisType,ParaYearList,ParaPapers,ParaIndustry,ParaImportantClient,ParaProvince,ParaDefinitionBigClient,ParameterList,$timeout) {
 
        			 
                	 $scope.params = {};
                   	 $scope.pager =  AnalysisTheme;
                   	 $scope.params.loguserId = $scope.USER_INFO.id;//限制条件为,需求确认人为当前登录人自己
                   	 $scope.refresh && $scope.refresh('first' , true);	
        			 
        		       	//$scope.pager =AnalysisTheme;
        		       	$scope.pager3 = ParaIndustry;
        		        $scope.targetPapersId=[];//目标报刊id
        		        $scope.targetPapersName=[];//目标报刊名字
        		        $scope.targetPapersFl=[];//目标报刊分类
        		        $scope.targetPapersNames="";
        		        
        		        $scope.competetorId=[];//竞争报刊id
        		        $scope.competetorName=[];//竞争报刊名字
        		        $scope.competetorIds="";
 
        		        $scope.industryId=[];//行业id
        		        $scope.industryName=[];//行业名
        		        
        		        $scope.samePaperId=[];//同类市场id
        		        $scope.samePaperName=[];//同类市场名字	
        		        
        		        //$scope.importantClientFilePath="";//上传文件后返回的文件地址
        		        $scope.paraBigClientArray=[];
        		        $scope.paraBigClientDescArray=[]; //大客户的描述
        		        
        		    	$scope.createErrorMessage="";
        		        
        		        
        	        	$scope.analysisType = AnalysisType.query({isArray : true,params : "{}"});
        	        	$scope.paraYearList = ParaYearList.query({isArray : true, params : "{}"});
        	        	$scope.industryList = ParaIndustry.query({isArray : true,params : "{}"});
        	        	$scope.definitionBigClientList = ParaDefinitionBigClient.query({isArray : true,params : "{}"});
        	        	$scope.provinceList = ParaProvince.query({isArray : true,params : {userProvCode:$scope.USER_INFO.provCode}});    
        	        	
  // --------------------------选择年度     	        	
        	            $scope.getPaperByYear = function(year) { //选择年度之后,获得某年的所有报刊,目标报刊选择框的内容
        	            	$scope.clearForm();
        	            	$scope.limitArr1 = [];
        	            	$scope.limitArr2 = [];
        	            	$scope.clearIndustry();
        	            	$scope.limitArr4 = [];
	       	                 $scope.params1 = {};
	    	               	 $scope.pager1 =  ParaPapers;
	    	               	 $scope.params1.paraYear = $scope.formparams.paraYear;
	    	               	 setTimeout(function(){
	    	               		$scope.refresh1 && $scope.refresh1('first' , true);	 
	    	             	 },1);
	    	               	 
	    	               	 
	    	               	 
    	               };
 //---------------------------点击目标报刊界面的查询       	               
           	            $scope.getPaperByYearAndKeyword = function() { //点击查询,获得按关键字查询的报刊
 		    	               	 $scope.pager1 =  ParaPapers;
		    	               	 $scope.params1.paraYear = $scope.formparams.paraYear;
		    	               	 $scope.params1.codeorname= $scope.params1.codeorname;
		    	               	 setTimeout(function(){
		    	               		$scope.refresh1 && $scope.refresh1('first' , true);	 
			    	             	 },1);
		    	               	 
    	               };       	            	
 
//-----------------------------目标报刊界面的确定       	           
        	           $scope.confirmTargetPapers = function(){
      	              	 //竞争报刊数据
	    	               	 $scope.pager2 =  ParaPapers;
	    	               	 $scope.params2={};
	    	               	 $scope.params2.paraYear = $scope.formparams.paraYear;
	    	               	 $scope.params2.competetor = "'"+$scope.targetPapersId.join("','")+"'";
	    	               	 setTimeout(function(){
	    	               		$scope.refresh2 && $scope.refresh2('first' , true);//竞争对手信息 
			    	          },1);
	    	               	 
	    	               	 
//-----------------------------同类市场数据  	 
    	               	     $scope.pager4 =  ParaPapers;
	    	               	 $scope.params4={};
	    	               	 $scope.params4.paraYear = $scope.formparams.paraYear;
	    	               	 $scope.params4.cpfl = "'"+$scope.targetPapersFl.join("','")+"'";
	    	               	 setTimeout(function(){
	    	               		$scope.refresh4 && $scope.refresh4('first' , true);//同类市场
				    	          },1);
	    	               	 
	    	               	 $scope.creatAnalysisThemeName();//产生默认的分析主题名称
	    	               	 
	    	               	 //显示在已选的区的内容
	    	               	$scope.formparams.targetPapersNames= $scope.targetPapersName.join(",");
	    	         
        	           };  
//-----------------------------目标报刊界面的清除       	           
    	           $scope.cancelTargetPapers = function(){
			              $scope.clearTargetPaper();//目标报刊
			              $scope.clearCompetetors();//竞争报刊
			              $scope.clearSameMarkets();//同类市场
  	               };      	           
	           
//-------------------竞争对手界面的查询        	           
          	            $scope.getPaperByYearAndKeywordm = function() { //点击查询,获得按关键字查询的报刊
	    	               	 $scope.pager2 =  ParaPapers;
	    	               	 $scope.params2.paraYear = $scope.formparams.paraYear;
	    	               	 $scope.params2.codeorname= $scope.params2.codeorname;
	    	               	 setTimeout(function(){
	    	               		$scope.refresh2 && $scope.refresh2('first' , true);
					    	 },1);
	    	               	 
   	                   };   
 
//-------------------竞争对手界面的清除         	              
		              $scope.clearCompetetors = function(){ //清理竞争对手报刊
		    		     
		    		        $scope.competetorId=[];//竞争报刊id
		    		        $scope.competetorName=[];//竞争报刊名字	
		    		        $scope.formparams.competetorNames="";
//		    		        $scope.competetorIds="";
		              }   		        
		              $scope.confirmCompetetors  =function(){
		            	  $scope.formparams.competetorNames = $scope.competetorName.join(",");
		            	  
		              }  
  
//-------------------行业类型界面查询按钮
       	           $scope.getIndustryByName = function(){
	               	 $scope.pager3 =  ParaIndustry;
	               	 $scope.params3.industryName= $scope.params3.industryName;
	               	 setTimeout(function(){
	               		$scope.refresh3 && $scope.refresh3('first' , true);
				    	 },1);
	               	 
   	               };  
    
//---------------------行业类型界面清除
       	           $scope.clearIndustry = function(){
    	        	   $scope.industryId=[];
    	        	   $scope.industryName=[];
    	        	   $scope.formparams.industryNames="";
   	               };      	               
   	            $scope.comfirmIndustry =function(){
   	            	$scope.formparams.industryNames =$scope.industryName.join(",");
   	            	
   	            }           
 
//-------------------同类市场界面的查询        	           
     	            $scope.getSamePaperByYearAndKeywordm = function() { //点击查询,获得按关键字查询的报刊
   	               	 $scope.pager4 =  ParaPapers;
   	               	 $scope.params4.paraYear = $scope.formparams.paraYear;
   	               	 $scope.params4.codeorname= $scope.params4.codeorname;
	               	 setTimeout(function(){
	               		$scope.refresh4 && $scope.refresh4('first' , true);
					 },1);
   	               	 
	                   };   
//-------------------同类市场界面的清除         	              
		              $scope.clearSameMarkets = function(){ //清理同类市场
		    		        $scope.samePaperId=[];//同类市场id
		    		        $scope.samePaperName=[];//同类市场名字	
		    		        $scope.formparams.samePaperNames="";
		    		        
		              }   
		              $scope.comfirmSameMarkets = function(){
		            	  $scope.formparams.samePaperNames=$scope.samePaperName.join(",");
		            	  
		              }
//--------------------------------------形成报刊名		              
       	            $scope.creatAnalysisThemeName = function() { 
       	            	$scope.formparams.anaThemeName="";
       	            	$scope.formparams.anaThemeName +=  $scope.formparams.paraYear+"年度";
       	            	if($scope.targetPapersName.length>1){
       	            		$scope.formparams.anaThemeName+=$scope.targetPapersName.slice(0,2).join('，') + '等';
       	            	}else{
       	            		$scope.formparams.anaThemeName+=$scope.targetPapersName[0];
       	            	}
       	            	$scope.formparams.anaThemeName +="报刊分析";
    	            	 
    	            	 
       	            	}; 
//--------------------------------------形成大客户参数	       	            	
           	        $scope.createBigClientArray = function() {  
           	        	for(var i=0;i<$scope.definitionBigClientList.length;i++){
           	        		if($scope.definitionBigClientList[i].value){
               	        		$scope.paraBigClientArray.push($scope.definitionBigClientList[i].definitionId + "_"+ $scope.definitionBigClientList[i].value);
               	        		$scope.paraBigClientDescArray.push($scope.definitionBigClientList[i].definitionName.replace("X",$scope.definitionBigClientList[i].value));
               	        	}
           	        	}
           	        	$scope.formparams.paraBigClientArrays =  $scope.paraBigClientArray.join(",") ;//传给后台
           	        	$scope.formparams.bigclientsDesc=$scope.paraBigClientDescArray.join(",");//展示用
           	        	
          	        }; 
          	        
           	 
          	        
          	      
       	            	
//--------------------------------- 清理目标报刊
	          	      $scope.clearTargetPaper = function(){
	    		        $scope.targetPapersId=[];//目标报刊id
	    		        $scope.targetPapersName=[];//目标报刊名字
	    		        $scope.targetPapersNames="";
	    		        $scope.targetPapersFl=[];//目标报刊分类
	    		        
	          	    	  
	          	      }
    	              
//-------------------------------清理重点省		        
		              $scope.clearImportantProvince = function(){
		            	  $scope.formparams.importantProvince = "";
		              }	
		              
		         
 //-----------------------------清理所有      	           
	    	           $scope.clearForm = function(){
				              $scope.clearTargetPaper();//目标报刊
				              $scope.clearCompetetors();//竞争报刊
				              $scope.clearSameMarkets();//同类市场
				              $scope.clearIndustry();//清理行业类型
				              $scope.clearParaBigClientArray()//大客户 
				              $scope.clearImportantProvince();//重点省
				              //$scope.uploader.reset();//清除上传控件里的内容
				              $scope.formparams.anaThemeName="";//清除默认的主题名
		                      $scope.formparams.anaThemeDesc="";//主题描述
		                      $scope.formparams.anaScriptName="";
		                      $scope.formparams.targetPapersNames="";//目标报刊
		                      $scope.USER_INFO.userName="";//创建人
		                      $scope.formparams.version="";
	  	               }; 
	  	             $scope.resetForm = function(){
	  	            	$scope.clearForm();
	  	            	 formparams.paraYear="";
	  	             }
	  	               
	  	             
		           
             
//-------------------大客户定义的清除		             
		             $scope.clearParaBigClientArray = function(){
		            	  $scope.formparams.ParaBigClientArrays="";
		            	  $scope.formparams.bigclientsDesc="";
		            	  $scope.paraBigClientArray=[];
		            	  $scope.paraBigClientDescArray=[];
		             }
		             
//------------------------------------------提交表单        	              
		              $scope.saveParameters = function(){ 
		            	  
//		            	  if($scope.uploader.isComplete()){//没有文件在控件中,用户没有选择文件上传
//		            		  $scope.saveForm();
//		            	  }else{//有文件在控件中并且还未上传
//		            		  $scope.uploader.start();
//		            	  }
		            	  $scope.saveForm();
		              } 		              
 //------------------------------上传回调函数,提交表单的其他内容--------------------------------------------
 
	                    $scope.upsuccess = function(data, file) {
	                    	//$scope.importantClientFilePath = data.filePath ;
	                    	$scope.saveForm();
	                    	 
	                    }
//------------------------------保存表单的内容	                    
	                    $scope.saveForm = function() {
	                    	
	                    	if($scope.competetorId.length>5){
	                    		$scope.createErrorMessage = "竞争品种超过了5个";
	                    		$timeout(function(){
	                    			$scope.createErrorMessage ="";
	                    			
	                       	    } , 2000);
	                    			return;
	                    	} 
	                    	if($scope.formparams.importantProvince.length>3){
	                    		$scope.createErrorMessage = "重点省超过了3个";
	                    		$timeout(function(){
	                    			$scope.createErrorMessage ="";
	                    			
	                       	    } , 2000);
	                    			return;
	                    	} 
	                    	if($scope.formparams.paraYear !='2014'){
	                    		$scope.createErrorMessage = "年份暂只支持'2014'";
	                    		$timeout(function(){
	                    			$scope.createErrorMessage ="";
	                    			
	                       	    } , 2000);
	                    			return;
	                    	}
	                    	
	                    	var parameterList={};
	                    	
	                    	parameterList.anaThemeName = $scope.formparams.anaThemeName;
	                    	parameterList.anaMethodId = $scope.formparams.anaMethodId;//分析方法id,暂时只有一个值
	                    	parameterList.anaThemeDesc = $scope.formparams.anaThemeDesc;//主题描述
	                    	parameterList.createUser = $scope.USER_INFO.id;//创建人
	                    	parameterList.version = $scope.formparams.version;
	                    	parameterList.year = $scope.formparams.paraYear;
	                    	parameterList.objectPaper = $scope.targetPapersId.join(",");
	                    	parameterList.userProvince = $scope.USER_INFO.provCode;
	                    	
	                    	
	                    	parameterList.competetorIds = $scope.competetorId.join(",");
	                    	parameterList.samePaperIds = $scope.samePaperId.join(",");
	                    	parameterList.industryIds = $scope.industryId.join(",");
	                    	parameterList.paraBigClientArrays = $scope.formparams.paraBigClientArrays;
	                    	//parameterList.importantClientFilePath = $scope.importantClientFilePath;
	                    	parameterList.importantProvince = $scope.formparams.importantProvince.join(",");
	                    	ParameterList.put(parameterList, function(data) {
	                    		$scope.refresh('current',true);
	                    		 $scope.clearForm(); 
	                    		 $('#pushDialog').modal('hide');
	                        });
	                    	 
	                    } 
                        //收集删除的主键 
	                    $scope.toRemove = function(themeid) {
	                        $scope.removalThemeid = themeid;
	                    };
                        //收集删除的主键 
	                    $scope.toExcute = function(themeid) {
	                        $scope.excuteThemeid = themeid;
	                    };

	                    
	                    // 确认启动存储过程
	                    $scope.comfirmExcute = function(){
	                    	ParameterList.post({
	                            execute:1,
	                    		excuteThemeid:$scope.excuteThemeid 
	                        },function(){
	                            $scope.refresh(null,'current' , true);
	                        });
	                        $scope.refresh(null,'current' , true);
	                    }
	                    //确认删除,参数为主题id
//	                    $scope.comfirmDelete = function() {
//	                        var params = {
//	                        		removalThemeid : $scope.removalThemeid
//	                        };
//	                        ParameterList.remove({
//	                            params : angular.toJson(params)
//	                        }, function(jsonData) {
//	                            $scope.refresh('current', true);
//	                        });
//	                    }; 
	                    
	                    //需要修改状态的
	                    $scope.comfirmDelete = function() {
	                    	var theme = {
                    			themeValid : '0',
                    			anaThemeId : $scope.removalThemeid
	                    	};
	                    	AnalysisTheme.save(theme,function(){
	                        	$scope.refresh('current',true);
	                        });
	                    };
	                    //需要修改状态的
	                    
	                    $scope.commitToSystem = function(id) {
	                    	var theme = {
                    			anaStatusId : '31',
                    			anaThemeId : id
	                    	};
	                    	AnalysisTheme.save(theme,function(){
	                        	$scope.refresh('current',true);
	                        });
	                    };
	                    
	                    
//	                    $scope.commitToSystem = function(id) {
//	                    	var theme = {};
//	                    	theme.anaStatusId = '31';
//	                    	theme.anaThemeId =  id;
//	                    	AnalysisTheme.save(theme,function(){
//	                        	$scope.refresh('current',true);
//	                        });
//	                    };
	                    
        }]) 
    }

});