define(function (require, exports, module) {
    return function setApp(app) {
        app.controller('ClinicDoctorCrewscheduleCtrl', ['$scope','$http' ,'$filter','FullCalendar','DoctorsManagement','$timeout',function ($scope,$http,$filter,FullCalendar,DoctorsManagement,$timeout) { 
 
        	var dateFIlter = $filter('date'); 
        
        	var clock_start='09:00';
        	var clock_end='17:00';
        	Date.prototype.addDays = function(d) {  
        	    this.setDate(this.getDate() + d);  
        	};  
        	$scope.GetDateDiff =  function(startTime, endTime, diffType) {
                  //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式 
                  startTime = startTime.replace(/\-/g, "/");
                  endTime = endTime.replace(/\-/g, "/");

                  //将计算间隔类性字符转换为小写
                  diffType = diffType.toLowerCase();
                  var sTime = new Date(startTime);      //开始时间
                  var eTime = new Date(endTime);  //结束时间
                  //作为除数的数字
                  var divNum = 1;
                  switch (diffType) {
                      case "second":
                          divNum = 1000;
                          break;
                      case "minute":
                          divNum = 1000 * 60;
                          break;
                      case "hour":
                          divNum = 1000 * 3600;
                          break;
                      case "day":
                          divNum = 1000 * 3600 * 24;
                          break;
                      default:
                          break;
                  }
                  return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
              }
        	$scope.getDoctorId =  function(name) {
                 for (var i=0;i< $scope.doctors.length;i++) {
                     if (name  == $scope.doctors[i].doctorName) {
                         return $scope.doctors[i].doctorId;
                     }
                 }
             }
        	
        	$scope.minusd8Hours = function(date){ 
        		var d=new Date(date); 
        		d.setHours(d.getHours()-8); 
        		return d;
        	}
 
        	$scope.saveCalendar = function(){
        		var eventsarray = $('#calendar').fullCalendar('clientEvents');
        		var tobesaveobjects=[];

        		for(var i =0;i<eventsarray.length;i++){
    				var s_time = eventsarray[i].title.substring(0,5);
    				var e_time = eventsarray[i].title.substring(6,11);
        			if(!eventsarray[i].end){// one day
            			if(!eventsarray[i].id){// not new
            				var eventdate =dateFIlter(eventsarray[i].start._d, 'yyyy-MM-dd');
            				var neweventobj={};
            				neweventobj.id=eventsarray[i]._id;
            				neweventobj.title=eventsarray[i].title;
            				neweventobj.start=eventdate+"T"+s_time+":00";
            				neweventobj.end=eventdate+"T"+e_time+":00";
            				neweventobj.doctorId =$scope.getDoctorId(eventsarray[i].title.substring(12)); 
            				neweventobj.clinicId = $scope.USER_INFO.orgCd;
            				tobesaveobjects.push(neweventobj);
            			}

        			}else{
            			if(eventsarray[i].id){//not new,but add new
            				var s_date = dateFIlter(eventsarray[i].start._d,"yyyy-MM-dd HH:mm:ss");
            				var e_date = dateFIlter(eventsarray[i].end._d,"yyyy-MM-dd HH:mm:ss");
            				var interval =  $scope.GetDateDiff(s_date,e_date,'day') ;
            				var eventdate = dateFIlter(eventsarray[i].start._d,"yyyy-MM-dd");
            				currenteventdate = new Date(eventdate);
            				for(var j=1;j<=interval;j++){//new from the second one
            					 
            					currenteventdate.addDays(j);
            					var eventd=dateFIlter(currenteventdate,"yyyy-MM-dd");
            					currenteventdate.addDays(0-j);
            					var neweventobj={};
            					var newid = Math.floor(Math.random() * 10000000)+'';
            					neweventobj.id = newid;
                				neweventobj.title=eventsarray[i].title;
                				neweventobj.start=eventd+"T"+s_time+":00";
                				neweventobj.end=eventd+"T"+e_time+":00";
                				neweventobj.doctorId = $scope.getDoctorId(eventsarray[i].title.substring(12)); 
                				neweventobj.clinicId = $scope.USER_INFO.orgCd;
                				tobesaveobjects.push(neweventobj);
            				}
            			}else{ //totally new
            				var s_date = dateFIlter(eventsarray[i].start._d,"yyyy-MM-dd HH:mm:ss");
            				var e_date = dateFIlter(eventsarray[i].end._d,"yyyy-MM-dd HH:mm:ss");
            				var interval =  $scope.GetDateDiff(s_date,e_date,'day') ;
            				var eventdate = dateFIlter(eventsarray[i].start._d,"yyyy-MM-dd");
            				currenteventdate = new Date(eventdate);
            				for(var j=0;j<interval;j++){
            					 
            					currenteventdate.addDays(j);
            					var eventd=dateFIlter(currenteventdate,"yyyy-MM-dd");
            					currenteventdate.addDays(0-j);
            					var neweventobj={};
            					var newid = Math.floor(Math.random() * 10000000)+'';
            					neweventobj.id = newid;
                				neweventobj.title=eventsarray[i].title;
                				neweventobj.start=eventd+"T"+s_time+":00";
                				neweventobj.end=eventd+"T"+e_time+":00";
                				neweventobj.doctorId =  $scope.getDoctorId(eventsarray[i].title.substring(12));  
                				neweventobj.clinicId = $scope.USER_INFO.orgCd;
                				tobesaveobjects.push(neweventobj);

            				}
            			}
        				

        			}
        		}
        		
        		for(var t=0;t<tobesaveobjects.length;t++){
    				if(t==tobesaveobjects.length-1){
					FullCalendar.put(tobesaveobjects[t],function(){ 
						$('#calendar').fullCalendar('destroy'); 
						initialCaendar();
					});
				}else{
					FullCalendar.put(tobesaveobjects[t],function(){ });
				}
        		}
        	}
        	
        	$scope.s_time = $('#single-input_s').clockpicker({
        	    placement: 'bottom',
        	    align: 'left',
        	    autoclose: true,
        	    afterDone: function() {
        	    	clock_start = $scope.s_time.get(0).value;
        	    } 
        	});
        	
        	$scope.e_time = $('#single-input_e').clockpicker({
        	    placement: 'bottom',
        	    align: 'left',
        	    autoclose: true,
        	    afterDone: function() {
        	    	clock_end = $scope.e_time.get(0).value;
        	    } 
        	     
        	});
        var  initialCaendar=function (){
         
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
						right: 'month'
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
					eventClick: function(event, jsEvent, view) {
	    				if(this.style.cssText.indexOf("red")>0){  //删除
	    					 var params = {
	    			            		id : event._id
	    			            };
	    					 FullCalendar.remove({
	    			                params : angular.toJson(params)
	    			            }, function(jsonData) {
	    			            	$('#calendar').fullCalendar('removeEvents', event._id);
	    			            });
	    			            
	    		            }; 
			    			if(this.style){//标记,准备删除
			    				$(this).css('border-color', 'red');
			    				  
			    			}
					},
					eventAfterRender:function( event, element, view ) {
						if(!event.id && event._id.slice(0,1)=='_'){//new
							
							element.html('<a style="background-color:#333333;border-color:#333333" class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">'+clock_start + "-" +  clock_end +" "+  event.title +'</span></div><div class="fc-resizer"></div></a>');
							event.title = clock_start + "-" +  clock_end +" "+  event.title
							var newid = Math.floor(Math.random() * 10000000)+'';//全局唯一id
							event._id = newid + event._id; 
							var newevent={};
							newevent.id = event._id;
							newevent.title = event.title ;
							var sobj=angular.copy(event.start._d);
							var eventsdate = dateFIlter($scope.minusd8Hours(sobj), 'yyyy-MM-dd');
							newevent.start = eventsdate+"T"+clock_start+":00";
							newevent.end   = eventsdate+"T"+clock_end  +":00";
						}else{//回显
							element.html('<a style="background-color:#333333;border-color:#333333" class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">'+ event.title +'</span></div><div class="fc-resizer"></div></a>');
						}
					 
					} ,
					 
					  eventMouseover: function(event, jsEvent, view){ //监听不止一个元素！.fc-event及初始加载两个子元素均可能
					      var $h = $(jsEvent.target);
					      $h.attr("_id",event.id);
					  },
					  
					events:list
				});
					 
			   });
        	
        }
        initialCaendar();
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
         
   
        	

        }]);
    }

});