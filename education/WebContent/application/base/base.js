define(function (require) {
    var app, httpConfig;
    require('directive');
    require('service');
    require('filter');
    require('./integrationDirective');
    require('./uploader');
    app = angular.module('base', ['ngRoute', 'ngResource', 'ngCookies' , 'ngSanitize', 'directive', 'service', 'filter']);
    
    app.factory('HttpDeferBoxInterceptor', function ($rootScope) {
        var log = {
            req: 0,
            res: 0,
            err: 0,
            errs: []
        };

        function tryFinish(response, error) {
            if (log.req == log.res + log.err) {
                $rootScope.deferApi && $rootScope.deferApi.close();
                var errs = log.errs.map(function(err , i){
                	var msg;
                	switch(err.status){
                		case 500:
                			msg = '服务器发生错误';
                			break;
                		case 404:
                			msg = '访问的资源不存在';
                			break;
                		case 400:
                			msg = '请求错误';
                		case 0:
                			if(err.data === null){
                				msg = '请求超时';
                			}else{
                				msg = '请求错误';
                			}
                	}
                	if(seajs.data.debug){
                		return '    ' + (i + 1) + ':' + msg + ' [ ' + err.config.url + ' ];';
                	}else{
                		return '    ' + (i + 1) + ':' + msg;
                	}
                });
                
                if(errs.length && seajs.data.debug){
                	alert('请求异常：\n' + errs.join('\n'));
                }else{
                	if(errs.length>0){
                		console.log('请求异常：\n' + errs.join('\n'));
                	}
                    
                }
                log.errs = [];//clear errs
            }
            error && log.errs.push(response);
        }

        return {
            request: function (config) {
            	if(config.params && config.params._box === false)return config;
            	
                log.req += 1;
                $rootScope.deferApi && $rootScope.deferApi.open();//open defer box
                config.timeout = 30000;//all ajax timeout rconfig
                return config;
            },
            response: function (response) {
            	if(response && response.config.params && response.config.params._box === false)return response;
            	
                log.res += 1;
                setTimeout(function () {
                    tryFinish(response);//test
                }, 1);
                return response;
            },
            responseError: function (response) {
            	if(response && response.config && response.config.params && response.config._box === false)return response;
            	
                log.err += 1;
                setTimeout(function () {
                    tryFinish(response, true);
                }, 1);
                return response;
            }
        }
    });

    app.factory('noCacheInterceptor' , function(){
    	return {
    		request: function (config) {
    			console.log('config' , config);
            	if(/get/i.test(config.method)){//当get请求时
            		config.params = config.params || {};
            		config.params._t = Date.now();//，不缓存	
            		config.cache = false;
            	}
                return config;
            }
    	}
    });
    
    app.factory('UserInterceptor' , function($rootScope){
    	return {
    		response: function (response) {
    			if(response.data.success == 'false'){
    				if(response.data.errorCode == 1){//user
    					alert(response.data.message);
    					$rootScope.logout();
    				}else{
    					window.console && console.warn && console.warn(response.data.message);
    				}
    			}
                return response;
            }
    	}
    });
    
    app.factory('LogInterceptor' , function($rootScope , getMenuByParams){
    	var menu;
    	$rootScope.$on('$routeChangeSuccess', function (e, route) {
    		if(route && route.originalPath && route.params){
    			menu = getMenuByParams(route.params);
    		}else{
    			menu = null;
    		}
    	});
    	return {
    		request: function (config) {
    			config.headers = config.headers || {};
    			config.headers['menu-id'] = menu ? menu.menuId : '';
                return config;
            }
    	}
    });

    httpConfig = function ($httpProvider) {
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
        $httpProvider.interceptors.push('HttpDeferBoxInterceptor');//对话框拦截
        $httpProvider.interceptors.push('noCacheInterceptor');//不缓存
        $httpProvider.interceptors.push('UserInterceptor');//登录拦截
        var isLogin = false;
        angular.forEach(seajs.data.fetchedList , function(val , key){
        	if(key.indexOf('login.js') > -1){
        		isLogin = true;
        	}
        });
        !isLogin && $httpProvider.interceptors.push('LogInterceptor');//增加菜单名称至request header是
        
    };
    app.config(['$httpProvider', httpConfig]);
});
