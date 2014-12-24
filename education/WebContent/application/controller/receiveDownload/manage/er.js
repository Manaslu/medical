define(function (require, exports, module) {
    // 数据字典管理
     function setApp(app) {
        app.controller('ReceiveDownloadManageErCtrl', ['$scope' , '$http' , function ($scope , $http) {
            $scope.erList = require('data/er.json');
		}]);
	}
    return setApp;

});