define(function (require, exports, module) {

    //公告审核
	return function setApp(app) {
        app.controller('OmsAnnouncementEapCtrl', ['$scope' ,'Announcement','$filter', function ($scope, Announcement, $filter) {
                
        	var dateFormat = $filter('date');
        	//按条件分页查询数据
        	$scope.pager=Announcement;
        	$scope.Announcement = Announcement;
        	$scope.params={
        		annoState:'1',
        		orgCd:$scope.USER_INFO.orgCd
        	}
        	
        	//重置按钮
            $scope.reset = function(params){
                $scope.params = {
                		annoState:'1',
                		orgCd:$scope.USER_INFO.orgCd};
            };
        	
        	$scope.toPassAndReject = function(announcement){
        		$scope.announcement = angular.copy(announcement) || new Announcement;
        		if(announcement){
        			$scope.announcement.annoValidDate = dateFormat(announcement.annoValidDate , 'yyyy-MM-dd');
        		}
        	};
        	//审核通过
            $scope.pass = function(announcement){
            	Announcement.save({
            		annoState:2,
            		setTime:'1',
            		apprComment:announcement.apprComment,
            		annoId:announcement.annoId
            	},$scope.refresh.bind(null , 'current',true))
            };
            
            //驳回
            $scope.reject = function(announcement){
            	Announcement.save({
            		annoState:3,
            		apprComment:announcement.apprComment,
            		annoId:announcement.annoId
            	},$scope.refresh.bind(null , 'current',true))
            };
        	
        }]);
    }

});