define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicDoctorCrewscheduleCtrl', ['$scope','$http' ,'$filter','FullCalendar','DoctorsManagement','$timeout',function ($scope,$http,$filter,FullCalendar,DoctorsManagement,$timeout) { 
 
        	var dateFIlter = $filter('date'); 
        	
        	$scope.minusd8Hours = function(date){ 
        		var d=new Date(date); 
        		d.setHours(d.getHours()-8); 
        		return d;
        	}
        	$scope.minusd6Hours = function(date){ 
        		var d=new Date(date); 
        		d.setHours(d.getHours()-6); 
        		return d;
        	}
 
         var params = {
        		 clinicId : $scope.USER_INFO.orgCd 
         };
         DoctorsManagement.query({ //初始化医生列表
       		                    isArray:true,
       		                    params: angular.toJson(params)
       	                      },  function (list){
     		$scope.doctors =list; 
     		setTimeout(function(){//让列表可拖动
        		$('#external-events .fc-event').each(function() {

        			// store data so the calendar knows to render an event upon drop
        			$(this).data('event', {
        				title: $.trim($(this).text()), // use the element's text as the event title
        				stick: true // maintain when user navigates (see docs on the renderEvent method)
        			});

        			// make the event draggable using jQuery UI
        			$(this).draggable({
        				zIndex: 999,
        				revert: true,      // will cause the event to go back to its
        				revertDuration: 0  //  original position after the drag
        			});

        		});
     			},3000);
         });
         
   
        	
       FullCalendar.query({ //初始化排班列表
                         isArray:true,
                         params: {}
            },  function (list){
               $scope.calendars =list; 
               
       		$('#calendar').fullCalendar({
       			id: $scope.USER_INFO.orgCd ,//Function.view.calendar.options.id
    			header: {
    				left: 'prev,next',
    				center: 'title',
    				right: 'agendaWeek'
    			},
    			 
    			lang: 'zh-cn',
    			eventColor:'#333333',
    			eventLimit: true, 
    			buttonIcons: false,
    			allDaySlot : false,
    			defaultEventMinutes:480,
    			slotEventOverlap:'true',
    			firstHour:'8',
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
    				if(this.style.cssText.indexOf("red")>0){  //删除
    					 var params = {
    			            		id : event.id
    			            };
    					 FullCalendar.remove({
    			                params : angular.toJson(params)
    			            }, function(jsonData) {
    			            	$('#calendar').fullCalendar('removeEvents', event.id);
    			            });
    			            
    		            }; 
		    			if(this.style){//标记,准备删除
		    				$(this).css('border-color', 'red');
		    				  
		    			}
    				
    				
    			},
    			eventAfterRender:function( event, element, view ) { //add
    				if(!event.id && event._id.slice(0,1)=='_'){//new
    					
    					var newid = Math.floor(Math.random() * 10000000)+'';//全局唯一id
    					event._id = newid + event._id; 
    					var newevent={};
    					newevent.id = event._id;
    					newevent.title = event.title ;
    					
    					var sobj=angular.copy(event.start._d);
    					var eventsdate = dateFIlter($scope.minusd8Hours(sobj), 'yyyy-MM-ddTHH:mm:ss');
    					
    					if(!event.end){//默認全天事件
    						var eobj=angular.copy(event.start._d);
     						var eventedate = dateFIlter($scope.minusd6Hours(eobj),   'yyyy-MM-ddTHH:mm:ss');
    						
    					}else{
    						var eobj=angular.copy(event.end._d);
     						var eventedate = dateFIlter($scope.minusd8Hours(eobj),   'yyyy-MM-ddTHH:mm:ss');
    					}
						newevent.start = eventsdate;
						newevent.end   = eventedate;
 						
 
    					FullCalendar.put(newevent,function(){ 
    					});
    					
    					
    				}
    			 
    			},
    			 eventResize : function(event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view) {//update
    				 
    				var newevent={};
 					newevent.id = event._id;
 					newevent.title = event.title ;
 					if(event.allDay){//跨天
						var eventsdate = dateFIlter(event.start._d, 'yyyy-MM-dd');
						var eventedate = dateFIlter(event.end._d, 'yyyy-MM-dd');
					}else{
						var sobj=angular.copy(event.start._d);
						var eobj=angular.copy(event.end._d);
						var eventsdate = dateFIlter($scope.minusd8Hours(sobj), 'yyyy-MM-ddTHH:mm:ss');
 						var eventedate = dateFIlter($scope.minusd8Hours(eobj),   'yyyy-MM-ddTHH:mm:ss');
					}
					newevent.start = eventsdate;
					newevent.end   = eventedate;
					FullCalendar.save(newevent,function(){ 
						
					});
 
    			  },
    			  eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {//update
    				  
    				var newevent={};
   					newevent.id = event._id;
   					newevent.title = event.title ;
   					if(event.allDay){//跨天
  						var eventsdate = dateFIlter(event.start._d, 'yyyy-MM-dd');
  						var eventedate = dateFIlter(event.end._d, 'yyyy-MM-dd');
  					}else{
  						var sobj=angular.copy(event.start._d);
  						var eobj=angular.copy(event.end._d);
  						var eventsdate = dateFIlter($scope.minusd8Hours(sobj), 'yyyy-MM-ddTHH:mm:ss');
   						var eventedate = dateFIlter($scope.minusd8Hours(eobj),   'yyyy-MM-ddTHH:mm:ss');
  					}
  					newevent.start = eventsdate;
  					newevent.end   = eventedate;
  					FullCalendar.save(newevent,function(){ 
  						
  					});
    				  
    			  },
    			  eventMouseover: function(event, jsEvent, view){ //监听不止一个元素！.fc-event及初始加载两个子元素均可能
    			      var $h = $(jsEvent.target);
    			      $h.attr("_id",event.id);
    			  },
    			  
    			events:list
    		});
       		$('#calendar').fullCalendar( 'changeView', 'agendaWeek' );
            });
        }]);
    }

});