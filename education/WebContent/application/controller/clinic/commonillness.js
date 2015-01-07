define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicCommonillnessCtrl', ['$scope','$http' ,'$filter','CommonIllness','IllnessType','$timeout',function ($scope,$http,$filter,CommonIllness,IllnessType,$timeout) { 
       	 $scope.pager =  CommonIllness;
       	 
       	IllnessType.query({isArray:true,params:{}},function (list){//for combo
    		$scope.illnessType =list; 
        });
        	 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.key="";
                $scope.tempeatPic=[];
            }
            
     //------------------add/edit--------- ----------- 	
            $scope.edithealthfood =function(healthfood){ //click on edit link
              	$scope.key= healthfood;
              	$scope.tempeatPic=[];
              	$scope.tempeatPic[0]=healthfood.eatPic
               };     
               
             $scope.create = function(key) {//add and edit
                
                   if(key.illnessId){
                	   CommonIllness.save(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                        $scope.clearForm();
                      	$('#addandedit').modal('hide');
                      	     
                      });
               	}else{ 
               		CommonIllness.put(key,function(){
                       	$scope.refresh('current',true);//refresh listgrid
                       	$scope.clearForm();
                        $('#addandedit').modal('hide');
                       });
               	}
               }
 
            
    //-------------delete----------        
            $scope.todelete = function(hid) {//click on DELETE link
                $scope.tobedeleteId = hid;
            };

            $scope.comfirmDelete = function() {//confirm to delete on dialog
	            var params = {
	            		illnessId : $scope.tobedeleteId
	            };
	            CommonIllness.remove({
	                params : angular.toJson(params)
	            }, function(jsonData) {
	                $scope.refresh('current', true);
	            });
            }; 
 
        }]);
    }

});