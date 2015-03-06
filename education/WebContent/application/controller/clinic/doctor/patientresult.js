define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicDoctorPatientresultCtrl', ['$scope','$http' ,'$filter','DiagnoseResult','UserAppointment','UserInformation','$timeout',
                                                         function ($scope,$http,$filter,DiagnoseResult,UserAppointment,UserInformation,$timeout) { 
 
  
        	var dateFIlter = $filter('date'); 
        	 
 
         var paramsBefore = {
        		 clinicId : $scope.USER_INFO.orgCd,
//        		 doctorId : $scope.USER_INFO.id,
        		 beforeNoon:    1
         };
         var paramsAfter = {
        		 clinicId : $scope.USER_INFO.orgCd,
//        		 doctorId : $scope.USER_INFO.id,
        		 afterNoon:    1
         };
       	UserAppointment.query({ //初始化用户列表
       		                    isArray:true,
       		                    params: angular.toJson(paramsBefore)
       	                      },  function (list){
     		$scope.userAppointmentBeforeNoon =list; 
         });
       	UserAppointment.query({ //初始化用户列表
				               isArray:true,
				               params: angular.toJson(paramsAfter)
				             },  function (list){
		   $scope.userAppointmentAfterNoon =list; 
		});
       	
       	
        $scope.clickOnUser =  function(orderid,userid){  //点击用户名
        	$scope.currentUserOrderId=orderid;
        	$scope.currentUserId = userid;
                var params = {
                   orderId : orderid
                };
                var params1 = {
                		mUserId : userid
                     };
                
                UserInformation.query({
                    isArray:true,
                    params: angular.toJson(params1)
                    },
                    function (list){
				     $scope.clickUser = list[0];
				});
                
                DiagnoseResult.query({
                                      isArray:true,
                                      params: angular.toJson(params)
                                      },
                                      function (list){
                $scope.currentUserResult =list; 
                 $scope.key = list[0];
                
                });
          }
        	 
     //------------reset botton---------------------
            $scope.clearForm = function(){//reset botton
                $scope.key="";
            }
            
             $scope.create = function(key) { 
             if(key.seeDoctorId){
                   	key.seeDoctorDate=''; //update time now is number,it cause error of mismatch
                   	DiagnoseResult.save(key,function(){
                        $scope.clearForm();
                        
                        var params = {
                       		 clinicId : $scope.USER_INFO.orgCd,
                       		 doctorId : $scope.USER_INFO.id 
                        };
                      	UserAppointment.query({ //初始化用户列表
                      		                    isArray:true,
                      		                    params: angular.toJson(params)
                      	                      },  function (list){
                    		$scope.userAppointment =list; 
                        });
                      	     
                      });
               	}else{ //add
               		key.clinicId =    $scope.USER_INFO.orgCd; 
               		key.doctorId =    $scope.USER_INFO.id;
               		key.mUserId =     $scope.currentUserId;
               		key.userOrderId = $scope.currentUserOrderId;
               		DiagnoseResult.put(key,function(){
                       	$scope.clearForm();
                       	var params = {
                       		 clinicId : $scope.USER_INFO.orgCd,
                       		 doctorId : $scope.USER_INFO.id 
                        };
                      	UserAppointment.query({ //初始化用户列表
                      		                    isArray:true,
                      		                    params: angular.toJson(params)
                      	                      },  function (list){
                    		$scope.userAppointment =list; 
                        });
                       });
               	}
               }
 
        }]);
    }

});