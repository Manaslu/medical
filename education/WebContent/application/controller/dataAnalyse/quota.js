define(function (require, exports, module) {

    //指标维护
    return function setApp(app) {
        app.controller('DataAnalyseQuotaCtrl', ['$scope' ,'Kpi','IndexTheme','SourceIndexs', function ($scope,Kpi,IndexTheme,SourceIndexs) {
            $scope.pager=Kpi;
            
            $scope.methNameString="";
            $scope.methIdString="";
            
            $scope.themes = IndexTheme.query({ //主题数据
                isArray : true,
                params : "{}"
            });
            
 
            
            $scope.save = function(key) { //新增
                if (key.indexId) {//修改
                    
                    key.calMethod = $scope.methIdString;
                    key.createTime ="";
                    Kpi.save(key,function(){
                    	$scope.refresh('current',true);
                    	$scope.clearForm();
                   });
                } else {
                    key.createUser = $scope.USER_INFO.id;
                    key.userId = $scope.USER_INFO.id;
                    key.calMethod = $scope.methIdString;
                    key.calMethodDesc = $scope.methNameString;
                    Kpi.put(key,function(){
                    	 $scope.refresh('current',true);
                    	 $scope.clearForm();
                    });
                   
                }
               
                
            };
            
            $scope.clearForm = function() { 
                $scope.key="";
                $scope.clearMeth();
            	$scope.methNameString = "";
            	$scope.methIdString  = "";
            	
            };
            
            $scope.toDelete = function(obj) {
            	var todeleteInd= {};
            	todeleteInd.indexId=obj.indexId;
            	todeleteInd.failFlag='0';
                Kpi.save(todeleteInd,$scope.refresh.bind(null , 'current',true));
            };
            
            $scope.setIndexList = function(themeid) { //历史
           	 $scope.indexListArray =  SourceIndexs.query({ //主题数据
                 isArray : true,
                 params : angular.toJson({"indexThemeId":themeid})
             });
           	$scope.clearMeth();
            };
            

            
            
            $scope.direction=[
                              {indexDirectionId:'36',indexDirectionName:'决策支持'},
                              {indexDirectionId:'37',indexDirectionName:'服务支撑'},
                              {indexDirectionId:'38',indexDirectionName:'风险管控'},
                              {indexDirectionId:'39',indexDirectionName:'流程优化'},
                              {indexDirectionId:'40',indexDirectionName:'交叉营销'},
                              {indexDirectionId:'41',indexDirectionName:'产品创新'},
                              {indexDirectionId:'42',indexDirectionName:'其他'}
                              ]
            
            $scope.addMeth = function(){
                $scope.methNameString = $scope.methNameString + " " + $scope.key.selectedIndex.indexName ;
                $scope.methIdString= $scope.methIdString +" " + $scope.key.selectedIndex.sourceColumn;
                $scope.key.selectedIndex='';
            	
            };
            
            $scope.operMeth = function(oper){
                $scope.methNameString = $scope.methNameString + " " + oper ;
                $scope.methIdString= $scope.methIdString +" " + oper;
            	
            };
            
            $scope.clearMeth = function(){
                $scope.methNameString = "" ;
                $scope.methIdString= "";
            	
            }
            
            $scope.preview =function(indexObj){ //点击查看/维护
            	$scope.viewindex = angular.copy(indexObj);
            };
            
            $scope.findDirectionId = function (list , key){
           	 var obj;
           	 for(var i=0;i<list.length;i++){
           		 if(list[i].indexDirectionName == key)
           			 return list[i].indexDirectionId;
           	 }
            }
            
            $scope.showEditView =function(indexObj){ //点击编辑
            	$scope.key = angular.copy(indexObj) ;
            	$scope.key.indexDirectionId = $scope.findDirectionId($scope.direction,indexObj.indexDirectionId)
            	$scope.setIndexList($scope.key.indexTheme_id);
            	$scope.methNameString = $scope.key.calMethodDesc;
            	$scope.methIdString  = $scope.key.calMethod;
            	 
            };
            
            $scope.beforeAdd =function(){ //点击编辑
            	$scope.key = null ;
            };
            
            
        }]);
    }

});