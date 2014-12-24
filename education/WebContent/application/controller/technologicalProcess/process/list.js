define(function (require, exports, module) {

    //项目列表
    return function setApp(app) {
        app.controller('TechnologicalProcessProcessListCtrl', ['$scope' ,'Processes','Demand' , '$filter' , '$q' ,'NodeAnnex' , function ($scope,Processes,Demand,$filter, $q,NodeAnnex) {
        	//按条件分页查询数据
        	$scope.pager=Processes;
        	
        	$scope.params = {
        			contact : $scope.USER_INFO.logName
            	};
       		//查看详情
       		$scope.toDetail = function(processes){
       			$scope.processes = processes || new Processes;
       			if(processes){
       				processes.demand = processes.demand || {};
       				processes.demand.requName = processes.requName; 
           		 	$scope.fileList=NodeAnnex.query({isArray:true,params:{ fruitCode: processes.fruitCode}}); 
       			}
       		}
            //跳转到删除对话框
       		$scope.toDelete=function(processes){
       			$scope.processes=processes;
       		}
            //删除需求
            $scope.remove = function(processes){
            	var params={fruitCode:processes.fruitCode};
            	Processes.remove({
            			params:angular.toJson(params)
            		},$scope.refresh.bind(null , 'current',true))
            };
            $scope.flowTypeList = [{"Name":"--请选择--"},
                                   {"requCode":"Z","Name":"专题、报表取数、统计"},
                                   {"requCode":"K","Name":"跨机构取数"},
                                   {"requCode":"J","Name":"技术需求"}
            						]
            //新增需求
            $scope.create = function(processes){
            	processes.id = processes.demand.id,
            	processes.creator = $scope.USER_INFO.logName
        		Processes.put(processes,function(result){
        			Demand.query({doSaveAnnex:true,params:{
                		//id:result.demandId,
                		//fruitCode:result.fruitCode,
            			fruitCode:result.fruitCode,
                		files:processes.fileResponseList
            		}} , function(){
            			$('#fruitUpload').modal('hide');
            			$scope.refresh('first',true);
            		});
        		});
            }
            
            $scope.publishIt = function(processes){
        		Processes.save({
        		 publicState:"1",
        		 fruitCode:processes.fruitCode
        		});
        		Demand.save({
        			id:processes.id,
        			approState:'6'
        		},$scope.refresh.bind(null , 'current',true))
            }
            //修改需求
            $scope.modify = function(processes){
        		Processes.save({
        			 fruitCode:processes.fruitCode,
            		 fruitCont:processes.fruitCont,
            		 oprId:'0',
        			 files:processes.fileResponseList
            	},function(){
            		$('#updateFruit').modal('hide');
            		$scope.refresh.bind(null , 'current',true)
            	});
            };
            
            //查询数据
            $scope.searchData = function(searchParams){
                //根据类型不同，选择资源查询
            	$scope.searchList = Processes.query({getByContact:true,params:{
                		contact : $scope.USER_INFO.logName,
                		requName : searchParams.requName
            	}});
            };
            //查询所有数据
            $scope.searchData_ = function(){
            	$scope.searchList = Processes.query({getByContact:true,params:{
                		contact : $scope.USER_INFO.logName
            	}});
            };
            
            $scope.processes = {};
            
            $scope.selected = function(data){//普通表格选中后使用
        			$scope.processes['demand'] = data;
            };
            
            //下载方法
            $scope.download = function(file) { 
              var url=  '/nodeAnnex.shtml?method=download&params='+ angular.toJson(file);//                    
              var link = $('#downlink')[0];
              link.href = url;
              link.click();
           };
           
           $scope.delAttach = function(){
 				 var params={fileId:$scope.fid};
 				 var processes = $scope.processes;
 				 NodeAnnex.remove({params:angular.toJson(params)}, function(){
 					$scope.fileList=NodeAnnex.query({isArray:true,params:{ fruitCode: processes.fruitCode}});
 				} );  
	         }    
	         $scope.toDeleteAnnex = function(fid,processes){
	      	   $scope.fid = fid;
	      	   $scope.processes = processes;
	         }
             $scope.fn = function(file , type , ext){
          	   alert("只支持上传"+ext+"类型的文件");
             }
        }]);
    }
});