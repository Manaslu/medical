define(function(require, exports, module) {

	// 异常信息
	return function setApp(app) {
		app.controller('ReceiveDownloadEsbAbnormalCtrl', [ '$scope',
				'PortExcp', 'Common', 'Upload',
				
				function($scope, PortExcp, Common, Upload) {
					$scope.pager = PortExcp;
					$scope.busiList = Common.query({
						code : true
					});
					var defaultParams = {
						logProvince : [ $scope.USER_INFO.provCode ],
						orderBy : 'EXCP_TIME',
						order : 'DESC'
					};
					if ($scope.USER_INFO.provCode === '99') {
						Common.query({
							isArray : true,
							params : "{}"
						}, function(results) {
							$scope.provList = results;
						});
					}
					$scope.params = defaultParams;
					
					$scope.desc = function(item){
						$scope.descObj = item;
					}
					
					//
					$scope.toUpdate=function(item){
						$scope.excp=item;
						$scope.portExcp=new PortExcp();
						$scope.portExcp.userName=$scope.USER_INFO.userName;
						$scope.portExcp.excpId=item.excpId;
					}
					
					$scope.updateExcp=function(){
						PortExcp.save($scope.portExcp,$scope.refresh.bind(null , 'current'));
					};
				} ]);
	}

});