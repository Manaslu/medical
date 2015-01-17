define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicDoctorCrewscheduleCtrl', ['$scope','$http' ,'$filter','FullCalendar','$timeout',function ($scope,$http,$filter,FullCalendar,$timeout) { 
 
   
 
//         var params = {
//        		 clinicId : $scope.USER_INFO.orgCd 
//         };
//         DoctorsManagement.query({ //初始化用户列表
//       		                    isArray:true,
//       		                    params: angular.toJson(params)
//       	                      },  function (list){
//     		$scope.doctors =list; 
//         });
        	
        	FullCalendar.query({ //初始化用户列表
                         isArray:true,
                         params: {}
            },  function (list){
               $scope.calendars =list; 
               
       		$('#calendar').fullCalendar({
       			id: $scope.USER_INFO.orgCd ,//Function.view.calendar.options.id
    			header: {
    				left: 'prev,next',
    				center: 'title',
    				right: 'month,agendaWeek'
    			},
    			lang: 'zh-cn',
    			eventColor:'#333333',
    			eventLimit: true, 
    			buttonIcons: false,
    			allDaySlot : true,
    			slotMinutes :'60',
    			firstHour : '8',
    			slotEventOverlap:'true',
    			weekMode : 'liquid',
    			editable: true,
    			droppable: true, // this allows things to be dropped onto the calendar
    			drop: function(date, all_day) {
    				console.log('drop'+this.id);//div 的id
    			},
    			dayClick: function() { 
    				console.log('a day has been clicked!'); 
    			},
    			eventClick: function(event, jsEvent, view) {
    				 
    				$(this).css('border-color', 'red');
    			},
    			eventAfterRender:function( event, element, view ) { //add
    				if(!event.id){//new
    					event._id=view.calendar.options.id+event._id;
    				}
    				console.log('eventAfterRender:'+event._id);
    			},
    			 eventResize : function(event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view) {//update
    			      

    			      updatetime(event,dayDelta,minuteDelta);
    			  },
    			  eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {//update
    			      
    			      if(allDay){
    			          update_date(event,dayDelta);
    			      }else{
    			          
    			          updatetime_date(event,dayDelta,minuteDelta);
    			      }
    			  },
    			  eventMouseover: function(event, jsEvent, view){ //监听不止一个元素！.fc-event及初始加载两个子元素均可能
    			      
    			      var $h = $(jsEvent.target);
    			      
    			      $h.attr("_id",event.id);
    			      
    			  },
    			events:list
    		});
               
               
            });
       	
       	$scope.clearSchedule = function(){//重置整个日历
       		$('#calendar').fullCalendar('destroy');
//    		$('#calendar').fullCalendar({
//    			header: {
//    				left: 'prev,next',
//    				center: 'title',
//    				right: 'agendaWeek,month'
//    			},
//    			lang: 'zh-cn',
//    			eventColor:'#333333',
//    			 
//    			eventLimit: true, 
//    			buttonIcons: false,
//    			allDaySlot : true,
//    			slotMinutes :'60',
//    			firstHour : '8',
//    			slotEventOverlap:'true',
//    			weekMode : 'variable',
//    			editable: true,
//    			droppable: true, // this allows things to be dropped onto the calendar
//    			drop: function() {
//    				alert(this.id);//div 的id
//    				// is the "remove after drop" checkbox checked?
//    				if ($('#drop-remove').is(':checked')) {
//    					// if so, remove the element from the "Draggable Events" list
//    					$(this).remove();
//    				}
//    			},
//    			dayClick: function() { 
//    				alert('a day has been clicked!'); 
//    			},
//    			eventClick: function(event, jsEvent, view) {
//    				alert('an event has been clicked!'); 
//    			}
//    		});
       	};
       	$scope.saveSchedule = function(){
       		var events =  $('#calendar').fullCalendar( 'clientEvents');
       		alert(events);
       	}
 
        $scope.clickOnUser =  function(orderid,userid){  //点击用户名
        	$scope.currentUserOrderId=orderid;
        	$scope.currentUserId = userid;
                var params = {
                   orderId : orderid
                };
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