/**
 * Created by 惠波 on 14-3-10.
 */
define(function (require) {
    var app = angular.module('app', []);
    var menu = require('data/menu.json');
    app.config(function ($httpProvider) {
        var defaults, headers, key, value;
        defaults = $httpProvider.defaults;
        headers = defaults.headers;
        defaults.transformRequest = function (data) {
            if (data === void 0) {
                return data;
            } else {
                return $.param(angular.fromJson(angular.toJson(data)));
            }
        };
        value = 'application/x-www-form-urlencoded; charset=UTF-8';
        key = 'Content-Type';
        headers.post[key] = value;
        headers.put[key] = value;
        headers.patch[key] = value;
    });
    app.controller('Controller' , function($scope , $http){
    	var map = {
    		menu : {
    			method : 'post',
    			url : 'menu.shtml',
    			data : {
    				initMenu : true,
    				menus : angular.toJson(menu)
    			}
    		}
    	}
    	console.log('map',map);
    	$scope.init = function(name){
    		$http(map[name]).success(function(result){
    			if(result.success == 'true'){
    				alert(name + ':初始化成功');
    			}else{
    				alert(name + ':初始化失敗\n' + result.message);
    			}
    		});
    	}
    });
    
    angular.bootstrap(document, ['app']);
});