/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    var app = angular.module('app', ['ngRoute']),
        menu = require('../data/menu.json'),
        modules = require('./modules'),
        forEach = angular.forEach,
        copy = angular.copy,
        prefix = /^templates\/|.html$/g;

    forEach(modules, function (setApp, i) {//初始化controller
        if (setApp && angular.isFunction(setApp)) {
            setApp(app);
        } else {
            console.log('控制器加载失败。。。');
        }
    });

    //路由配置
    app.config(['$routeProvider', function ($routeProvider) {
        forEach(flatMenu(copy(menu)), function (item) {//模块路由
            var path = (item.template || item.templateUrl).replace(prefix, '').split('/');
            //如果模板路径为 templates/route/route.html
            //则处理结果为 RouteCtrl
            if (path[0] === path[1]) {
                path.shift();//remove first
            }

            $routeProvider.when(item.route, {
                controller: getCtrl(path),//这里，controller和模板路径绑定，俩者直接关联
                templateUrl: item.template || item.templateUrl
            });
        });

        //默认路由跳转
        $routeProvider.otherwise({
            redirectTo: '/index'
        });
    }]);

    app.run(['$rootScope' , '$route' , function ($rootScope, $route) {
        $rootScope.menu = copy(menu);
        setRoute($rootScope.menu);
        $rootScope.$on('$routeChangeSuccess', function (e, route) {//监听路由变化，设置导航
            if (!route.originalPath) {//路由错误
                return;
            }
            console.log('current route : ', route)
        });
    }]);
    angular.bootstrap(document, ['app']);

    //处理模板路径转换为controller
    function getCtrl(path) {
        var ctrl = '';
        forEach(path, function (item) {
            ctrl += item[0].toUpperCase() + item.slice(1);
        });
        return ctrl + 'Ctrl';
    }

    //扁平数组，将多维导航变成一维导航
    function flatMenu(menu, parentPath) {
        var ret = [];
        forEach(menu, function (item) {
            ret.push(item);
            item.route = (parentPath || "") + '/' + (item.action || item.url);
            if (item.children && item.children.length) {
                ret = ret.concat(flatMenu(item.children, item.route));
            }
        });
        return ret;
    }

    //配置路由
    function setRoute(menu, parent) {
        forEach(menu, function (item) {
            item.route = (parent || "#") + "/" + item.action;
            if (item.children && item.children.length) {
                setRoute(item.children, item.route);
            }
        });
    }


});
