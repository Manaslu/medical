/**
 * Created by 惠波 on 14-3-10.
 */
define(function (require) {
    require('base');
    
    //禁用对话框点击背景自动关闭功能
    $.fn.modal.Constructor.DEFAULTS.backdrop = false;

    var app = angular.module('app', ['base']),
        forEach = angular.forEach,
        copy = angular.copy,
        globalFn = require('~/global'),
        controllerBase = '~/',
        prefix = /^templates\/|.html$/g,
        menu;

    //导航工厂
    app.factory('menuMap', function () {
        return menu;
    });
    
    app.factory('getMenuByParams' , function(){
    	return function(params){
    		if(menu){
    			return findMenuByPaths(makePaths(params), menu);
    		}
    	}
    });
    
    globalFn(app);

    //路由配置
    app.config(['$routeProvider' , '$controllerProvider', function ($rp, $cp) {
        var loadCache = {},//防止重复加载
            invokeQueue = app._invokeQueue,
            promise , paths , path , menuItem;
        $rp.when('/at/:ModName/:ModCtrl?/:ModFn?', {
            controllerFactory: function (routeParams, scope) {//第三步执行，在页面视图加载完成后执行，执行controller
                return function (success) {
                    promise.done(function () {
                        success(menuItem.controllerName);
                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    });
                }
            },
            templateUrl: function (routeParams) {//第二步执行，加载页面视图
            	if(!menu)return;
                paths = makePaths(routeParams);
                path = paths.join('/');
                menuItem = findMenuByPaths(paths, menu);
                if(!menuItem)return;
                promise = $.Deferred();
                if (!loadCache[path]) {
                    require.async(menuItem.controllerUrl, function (cb) {//异步加载模块
                        var start = invokeQueue.length;
                        cb && cb(app);//执行
                        invokeQueue.slice(start).forEach(function (invokeArgs) {
                            switch (invokeArgs[0]) {//invokeArgs = [provider , method , args]
                                case '$controllerProvider'://手动注入controller
                                    $cp[invokeArgs[1]].apply($cp, invokeArgs[2]);
                                    break;
                            }
                        });
                        loadCache[path] = true;
                        promise.resolve();
                    });
                } else {
                    promise.resolve();
                }
                return menuItem.template || menuItem.templateUrl;
            },
            redirectTo: function (routeParams) {//第一步执行，检测是否跳转
            	if(!menu){
            		return;
            	}
                var item = findMenuByPaths(makePaths(routeParams), menu);
                if(!item){//如果访问不存在的路由，跳转到菜单第一个
                	return menu[0].route.slice(1);
                }else if (item.redirectTo || !item.template) {//路由跳转支持
                    var childs = item.children || [],
                        params = '',
                        child;
                    if(childs.length){
                        child = item.redirectTo ? findRedirect(childs , item.redirectTo) || childs[0] : childs[0];
                        if(child.params){
                            params = '?' + child.params;
                        }
                        return item.route.slice(1) + '/' + child.action + params;
                    }
                }
            }
        }).otherwise({
            redirectTo : '/at/index'
        });
    }]);

    function findRedirect(arr , name){
        return arr.filter(function(item){
            return item.action == name;
        })[0];
    }

    function findMenusByPaths(paths){
        var result = [] , tmp;
        for (var i = 0; i < paths.length; i++) {
            var obj = paths[i];
            tmp = tmp || menu;
            for (var j = 0; j < tmp.length; j++) {
                var obj1 = tmp[j];
                if(obj1.action == obj){
                    result.push(obj1);
                    tmp = obj1.children;
                    break;
                }
            }
        }
        return result;
    }

    //启动初始化
    app.run(['$rootScope' , '$location' , '$timeout' , '$http' , '$route' , function ($rootScope , $location , $timeout , $http , $route) {
    	
    	$http.get('authMenu.shtml?menu=true').success(function(result){
    		setMenu(result);
    		$rootScope.menu = menu = result;
    		$timeout(function(){
    			$route.reload();
    		},1);
    	});
    	
        $rootScope.copy = copy;

        var path;
        $rootScope.menuMatch = function (route) {
            if (route) {
                return route.split('?')[0].replace('#', '') == path;
            }
            return false;
        };

        $rootScope.showRoute = function(item){
            //return item.route;
            if(item.name =='首页'  || item.name == '数据接收与下载'){
                return item.route;
            }
            return item.route;
        };

        !function(timeout){
            var timer = function(){
                timer._t && $timeout.cancel(timer._t);
                timer._t = $timeout(function(){
                    alert('您长时间没有操作，用户信息已失效，请重新登录！');
                    $rootScope.logout();
                } , timeout);
            };
            $(document).on('click' , function(e){
                timer();
            });
            timer();
        }(1000 * 60 * 120);

        $rootScope.checkActive = function(item , levol){
            var tmp;
            return menus && (tmp = menus[levol]) && tmp.name == item.name;
        };
        
        var paths , subMenu , menus , em;
        //监听路由变化
        $rootScope.$on('$routeChangeSuccess', function (e, route) {
            if (!route.originalPath || !menu) {//路由错误
                return;
            }

            $rootScope.hideSlider = false;

            // 设置当前路由全局变量
            path = $rootScope.path = $location.path();

            paths = makePaths(route.params);
            menus = findMenusByPaths(paths);
            subMenu = getSubMenuByRoute(menu, paths[0]);// 取出子菜单

            if ($rootScope.subMenu != subMenu) {
                $rootScope.subMenu = subMenu;
                $rootScope.menuQuery = "";
            }
            $rootScope.$emit('menuChange' , paths);

        });

        //是否隐藏左侧菜单
        $rootScope.isHideSidebar = function () {//将不需要左侧菜单的页面列入
            return  $rootScope.path == '/at/index' || $rootScope.path =='/at/doctorsmanagement' 
                ||  $rootScope.path == '/at/patientresult'  || $rootScope.path == '/at/crewschedule' 
                	|| $rootScope.hideSlider;
        };
        //切换左侧菜单的显示或隐藏
        $rootScope.toggleSidebar = function () {
            return  $rootScope.hideSlider = !$rootScope.hideSlider;
        };

        //当左侧菜单切换的时候，手动出发浏览器的resize事件，并且传递参数，表示当前的显示状态，true表示隐藏，false表示显示
        $rootScope.$watch("isHideSidebar()", function (val) {
            setTimeout(function () {
                $(window).trigger('resize', {hide: val});
            }, 0);
        });

    }]);

    //根据route读取子menu
    function getSubMenuByRoute(menu, route) {
        var result;
        for (var i = 0,ii = menu.length; i<ii; i++) {
            var obj = menu[i];
            if (obj.action == route || obj.url == route) {
                return obj;
            }
        }
    }

    //配置路由
    function setMenu(menu, parent) {
        forEach(menu, function (item) {
            var template = item.template || item.templateUrl;
            var route = (parent || "#/at") + '/' + (item.action || item.url);
            if (item.children && item.children.length) {
                setMenu(item.children, route);
            }
            if (item.params) {
                route += "?" + (item.params || "");
            }
            if (template) {
                template = template.replace(prefix, '');
                item.controllerUrl = controllerBase + template;
                item.controllerName = toController(template);
            }
            item.route = route;
        });
    }

    //转换 为 controller
    function toController(template) {
        var ctrl = '';
        template.split('/').forEach(function (item) {
            ctrl += item[0].toUpperCase() + item.slice(1);
        });
        return ctrl + 'Ctrl';
    }

    //转换route参数为paths
    function makePaths(routeParams) {
        return [routeParams.ModName, routeParams.ModCtrl, routeParams.ModFn].filter(function (item) {
            return angular.isDefined(item);
        });
    }

    //根据导航路由，查找menu实例
    function findMenuByPaths(paths, menu) {
        for (var i = 0; i < paths.length; i++) {
            var path = paths[i];//index
            for (var j = 0; j < menu.length; j++) {
                var item = menu[j];
                if (item.action === path) {
                    if (i === paths.length - 1) {
                        return item;
                    } else {
                        return findMenuByPaths(paths.slice(1), item.children);
                    }
                }
            }
        }
    }

    angular.bootstrap(document, ['app']);
});
