define(function (require, exports, module) {

    //公告管理
    return function setApp(app) {
        app.controller('OmsAnnouncementListCtrl', ['$scope' ,'Announcement','Institution','$filter', function ($scope, Announcement,Institution, $filter) {
            
        	
        	var dateFormat = $filter('date');
        	
        	//校验输入框的
        	$scope.myValid = function(val){
            	if(val){
            		return true;
            	}else{
            		return false;
            	}
            };
            
        	//按条件分页查询数据
        	$scope.pager=Announcement;
        	$scope.Announcement = Announcement;
        	$scope.params = {
        			orgCd:$scope.USER_INFO.orgCd
        	};
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {
                		orgCd:$scope.USER_INFO.orgCd
                		};
            };
            
            //初始化修改/新增
        	$scope.toUpdate = function(announcement){
				$scope.announcement = angular.copy(announcement) || new Announcement;
        		if(announcement){
        			$scope.announcement.annoValidDate = dateFormat(announcement.annoValidDate , 'yyyy-MM-dd');
        		}
        	};
        	
        	//下线
            $scope.setDown = function(announcement){
            	Announcement.save({
            		annoState:4,
            		annoId:announcement.annoId
            	},$scope.refresh.bind(null , 'current',true))
            };
        	
            
        	//提交审核
            $scope.submitAudit = function(announcement){
            	Announcement.save({
            		annoState:1,
            		remards:announcement.remards,
            		annoId:announcement.annoId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
        	//修改公告
            $scope.modify = function(announcement){
            	Announcement.save({
            		annoName:announcement.annoName,
            		annoState:5,
            		orgCd:$scope.USER_INFO.orgCd,
            		annoPer:$scope.USER_INFO.id,
            		annoValidDate:announcement.annoValidDate,
            		annoDesc:announcement.annoDesc,
            		setTime:'1',
            		annoId:announcement.annoId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
            //新增公告
            $scope.create = function(announcement){
            	Announcement.put({
            		annoName:announcement.annoName,
            		orgCd:$scope.USER_INFO.orgCd,
            		annoPer:$scope.USER_INFO.id,
            		annoValidDate:announcement.annoValidDate,
            		annoDesc:announcement.annoDesc,
            		annoState:5
            	},$scope.refresh.bind(null , 'first',true));
            	
            };
            
            //跳转到删除发布下线公告对话框
        	$scope.toDelete=function(announcement){
        		$scope.announcement=announcement;
        	}
            
            //删除公告
            $scope.remove = function(announcement){
            	var params={annoId:announcement.annoId};
            	Announcement.remove({params:angular.toJson(params)},$scope.refresh.bind(null , 'current',true));
            };
        	
        }]);
    }
    
});