define(function (require, exports, module) {

	//订阅通知--用户订阅
    return function setApp(app) {
        app.controller('ResultSubscriptionsUserCtrl',['$scope' ,'Subscribe','Empower','ThemeRep','User', '$filter' , function ($scope ,Subscribe ,Empower,ThemeRep,User,$filter) {
            
        	//按条件分页查询用户订阅信息数据
        	$scope.pager=Subscribe;
        	$scope.Subscribe = Subscribe;
        	
        	$scope.params = {userId:$scope.USER_INFO.id,subscribeStats:1};
        	
        	//初始查询主题名称
        	ThemeRep.query({isArray:true,params:{}},function (list){
        		$scope.themeNameList =list; 
            });
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {userId:$scope.USER_INFO.id,subscribeStats:1};
            };
               
            //初始化
       		$scope.toUpdate = function(empower){
       			//$scope.empower = {userId:$scope.USER_INFO.id,subscribeStats:1};//初始化查询参数
       			$scope.empower.useremail ="退订邮件";
       			$scope.empower = angular.copy(empower) || new Empower;
       		};
       		
       	    //订阅前查询用户Email是否为空
       		$scope.toQuery = function(empower){
       			if(empower){
           			User.query({isArrays:true,params:{id:$scope.USER_INFO.id}},function (list){
           				if(list.length){
           					empower.useremail = list[0].email;
           				}
                    });
       			}
       			$scope.empower = empower;
       		};
       		
       	    //邮件退订初始化
       		$scope.toExit = function(empower){
       			empower.useremail = "邮件退订";
       			$scope.empower = empower;
       		};
       		
       	    //为用户添加email
            $scope.modify = function(user){
       			User.save({
            		email:user.email,
            		id:$scope.USER_INFO.id
            	},$scope.refresh.bind(null ,'current',true))
            };
       		
       	    //订阅/退订修改状态  插入日志
            $scope.empowerStats = function(empower){
            	var stats=empower.subscribeType;
            	if(stats==1){
            		stats=2;
            	}else{
            		stats=1;
            	}
            	Empower.save({
            		useremail:empower.email,
            		uid:$scope.USER_INFO.id,
            		subscribeType:stats,
            		Id:empower.empowerId,
            		userName:$scope.USER_INFO.userName,
            		reportName:empower.repName,
            		subscribeName:empower.subscribeName,
            		subscribeMethod:empower.subscribeMethod
            	},$scope.refresh.bind(null , 'current',true))
            };
            
        }]);
    }

});

