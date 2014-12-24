//指令集
define(function (require , exports , module) {
    var app = angular.module('directive', []);
    
    /**
     * 静态数据分页器
     * 
     * @examples
     *   html:
     *   <ul ng-controller="testCtrl">
     *       <li ng-repeat="item in limitArr">{{item}}</li>
     *   </ul>
     *   <div data-paging="list" data-limit="10" data-current="current" data-data="limitArr"></div>
     *
     *   script:
     *   function testCtrl($scope){
     *      $scope.list = 'abcdefghijklmnopqrstuvwxyz'.split('');//['a','b','c'...]
     *   }
     *
     * @params
     *   paging : 源数据 Array
     *   limit : 限制显示数据大小 number
     *   data : 数据碎片 Array
     *   current : 当前第几页 Model
     */
    app.directive('paging', function () {
        return {
            scope: {
                paging: '=',//源数据
                limit: '=',//每页多少条
                data: '=',//当前页数的数据
                current: '='//当前第几页
            },
            link: function (scope, el, attr) {
                scope.$watchCollection('paging', function (val1, val2) {
                    run();//监听数据源，发生变动时，重新启动分页器
                });

                var arr, limit, page;

                function run() {
                    limit = parseInt(scope.limit || 0, 10);
                    arr = scope.paging || [];
                    page = scope.pageList = function (num) {
                        var ret = [];
                        for (var i = 0; i < num; i++) ret.push(i);
                        return ret;
                    }(Math.ceil(limit ? arr.length / limit : 1));//如果有限制碎片大小，则按照碎片大小显示，否则显示全部
                    scope.goToPage(0);
                }

                scope.goToPage = function (i) {//to page data
                    if (!arr.length) {
                        scope.data = [];
                        return;
                    }
                    var start = limit ? i * limit : 0;
                    if (i < 0 || i >= page.length) {
                        return;
                    }
                    scope.current = i;//current
                    scope.data = arr.slice(start, start + (limit || arr.length));//current data
                };
                run();
            },
            template: '<div class="dataTables_paginate paging_bootstrap">\
                           <ul class="pagination pagination-sm">\
                               <li class="prev" ng-class="{disabled:current == 0}"><a href="javascript:" ng-click="goToPage(current-1)">上一页</a></li>\
                               <li ng-class="{active:$index == current}" ng-repeat="item in pageList"><a href="javascript:" ng-click="goToPage($index)">{{$index+1}}</a></li>\
                               <li class="next" ng-class="{disabled:current == pageList.length-1}"><a href="javascript:" ng-click="goToPage(current+1)">下一页</a></li>\
                           </ul>\
                        </div>',
            //replace: true,
            restrict: 'A'
        }
    });

    /**
     * 远程数据分页器
     *
     * @examples
     *   html:
     *   <ul ng-controller="testCtrl">
     *       <li ng-repeat="item in limitArr">{{item}}</li>
     *   </ul>
     *
     *   <div
     *      data-remote-paging="Resource"
     *      data-limit="limit"
     *      data-current="current"
     *      data-total="total"
     *      data-data="limitArr"
     *      data-params="params"
     *      data-size="size"
     *      data-refresh="resfresh"
     *      ></div>
     *
     *   script:
     *   function testCtrl($scope , UserList){
     *      $scope.Resource = UserList;//这里需要资源接口
     *   }
     *
     * @params
     *   paging : 源数据 Resource
     *   limit : 限制显示数据大小 number
     *   current : 限制显示数据大小 Model
     *   total : 限制显示数据大小 Model
     *   size : 限制显示分页器个数
     *   params : 查询条件
     *   refresh : 查询控制器
     *   data : 数据碎片 Array
1     */
    app.directive('remotePaging', function () {
        return {
            scope: {
                paging: "=remotePaging",//源数据
                limit: '=',//每页多少条
                data: '=',//当前页数的数据
                current: '=',//当前第几页
                refresh: '=',//刷新
                size: '=',//出现多少个分页
                params: '=',//查询条件
                total: '='//总共有多少条
            },
            link: function (scope, el, attr) {

                var limit = +sessionStorage.limit || 10, size = 8;
                if (attr.size) {
                    size = scope.size || size;
                    scope.$watch('size', function (newVal, oldVal) {
                        if (newVal != oldVal) {
                            size = scope.size;
                            scope.refresh('first', true);
                        }
                    });
                    scope.size = size;
                }
                if (attr.limit) {
                    limit = scope.limit || limit;
                    scope.$watch('limit', function (newVal, oldVal) {
                        if (newVal != oldVal) {
                            limit = scope.limit;
                            sessionStorage.limit = limit
                            scope.refresh('first', true);
                        }
                    });
                    scope.limit = limit;
                }

                scope.limits = [10 , 15, 20, 30, 50, 100];

                scope.current = 0;
                
                var startFix , endFix , makeMap , list = [];

                function initPageList(totalPage) {//初始化分页器
                    list = scope.pageList = [];

                    startFix = endFix = Math.floor(size / 2);
                    var test = startFix * 2 + 1;
                    if (test > size) startFix -= 1;
                    if (test < size) endFix += 1;

                    var _sf = 0 ,
                        _ef = 0 ,
                        start = scope.current - startFix,
                        end = endFix + scope.current + 1;
                    if (start < 0) {
                        _sf = 0 - start;
                        start = 0;
                    }

                    if (end >= totalPage) {
                        _ef = end - totalPage;
                        end = totalPage;
                    }

                    start = start - _ef;
                    end = end + _sf;
                    start = start < 0 ? 0 : start;
                    end = end > totalPage ? totalPage : end;

                    for (; start < end; start++) {
                        list.push(start);
                    }
                }

                var params , isLoading;
                var isAllReload = angular.isDefined(attr.reloadAll);//是否总是重新加载

                //读取对应页面的数据
                scope.goToPage = function (page , reload) {//to page data
                    if (page < 0 || (scope.totalPage && page >= scope.totalPage) || (page == scope.current && !reload)) {
                        return;
                    }

                    if(reload){//是否重新加载参数
                        params = angular.copy(scope.$eval('params') || {});
                    }

                    var config = {
                        paging : true,
                        current : page,
                        limit : limit,
                        reload : isAllReload || !!reload,
                        total : scope.total,
                        params : angular.toJson(params)
                    };
                    isLoading = true;
                    scope.paging.get(config, function (result) {
                        /**
                         * result : {
                         *      total : 999,//数据
                         *      totalPage : 10,//碎片数量
                         *      current : 1,//当前碎片
                         *      limit : 15,//限制碎片大小
                         *      data : []//数据碎片
                         * }
                         */
                        scope.current = result.current;
                        scope.data = result.data || [];
                        scope.total = result.total;
                        scope.totalPage = result.totalPage;
                        scope.data = $.map(scope.data , function(item){
                            return new scope.paging(item);
                        });
                        initPageList(result.totalPage);
                        isLoading = false;
                    } , function(){
                        isLoading = false;
                    });
                };

                //刷新数据
                scope.refresh = function(page , reload){
                    if(!scope.paging || isLoading) return;//不要重复加载,跳出方法
                    switch(page){
                        case "first" :
                            scope.goToPage(0 , reload);
                            break;
                        case "last" :
                            scope.goToPage(scope.totalPage - 1 , reload);
                            break;
                        default:
                            scope.goToPage(scope.current , reload);
                    }
                };

                //监听是否有resource资源准备就绪
                scope.$watch('paging' , function(){
                    scope.paging && scope.goToPage(0,true);
                });

                //读取显示范围
                scope.getDataRange = function(current , limit , total , to){
                    if (to) {
                        return current * limit + limit > total ? total : current * limit + limit;//结束
                    } else {
                        return current * limit + 1;//开始
                    }
                }
            },

            templateUrl : 'templates/_directive/remote-paging.html',
            //replace: true,
            restrict: 'A'
        }
    });

    /**
     * 日期组件
     * @examples
     *  html:
     *  <div
     *      data-datetimepicker="binding-value"
     *      data-format="yyyy-MM-dd"
     *      range=""
     *      start=""
     *      end=""
     *  ></div>
     *  
     *  <div datetimepicker range start="params.start" end="params.end"></div>
     *  <div datetimepicker="params.date"></div>
     *
     * @params
     *  datetimepicker 绑定数据Model,range时可选
     *  format 可选的格式化日期（默认：yyyy-MM-dd）
     *  range   是否选择范围，可选
     *  start  在range时生效，开始时间
     *  end    在range时生效，结束时间
     */
    app.directive('datetimepicker' , function($timeout , $filter){
        return {
            require : '?ngModel',
            scope : {
                dates: '=datetimepicker',//任何时候生效
                start : '=', //range 时生效
                end : '=' //range 时生效
            },
            link : function(scope , el , attr , ctrl){
                var isRange = scope.isRange = angular.isDefined(attr.range),
                    isModel = !!attr.datetimepicker,
                    isStart = isRange && !!attr.start,
                    isEnd = isRange && !!attr.end,
                    now = new Date,
                    viewMode = angular.isDefined(attr.viewMode),
                    startDate = angular.isDefined(attr.startDate),
                    endDate = angular.isDefined(attr.endDate),
                    format = attr.format || 'yyyy-mm-dd',
                    lan = 'zh-CN',
                    required = angular.isDefined(attr.required) || scope.$eval(attr.ngRequired || ''),
                    $el , inputs , picker;

                function getNow(date){
                	if(date === 'today'){//限制今天
                		date = new Date;
                	}
                    if(date && typeof date === 'string'){
                        return $.fn.datepicker.DPGlobal.parseDate(date.slice(0,10) , getFormat(format) , lan)
                    }
                    return now;
                }
                function getFormat(fm){
                    var match = (fm || format).match(/yy+|mm|dd/ig);
                    if(match){
                        return match.join('-');
                    }
                    return "";
                }
                function parseDate(date){
                    return $.fn.datepicker.DPGlobal.formatDate(getNow(date) , getFormat(format) , lan);
                }

                setTimeout(function(){
                    $el = el.find(isRange ? '.input-daterange' : 'input').datepicker({
                        format : format,
                        autoclose: true,//自动关闭
                        language: lan,//中文
                        clearBtn : true,//打开清除按钮
                        startDate : startDate ? getNow(attr.startDate) : void 0,
                        endDate : endDate ? getNow(attr.endDate) : void 0,
                        minViewMode : viewMode ? attr.viewMode : 0,//0 = days = 最小显示到日 ， 1 = months = 最小显示到月 ， 2 = years = 最小显示到年
                        todayHighlight: true//今天高亮
                    });
                    inputs = isRange ? el.find('input') : $el;
                    picker = $el.data('datepicker');
                    isRange && (picker = picker.pickers);

                    if(ctrl && required){
                        $el.on('changeDate' , function(){
                            var val = vals();
                            ctrl.$setValidity('required' , isRange ? !!val[0] && !!val[1] : !!val[0]);
                        });
                    }

                    $el.on('changeDate' , function(){
                        if(isRange){
                            var range = picker[0].range;

                            if(range[0] > range[1]){//fix #结束日期必须大于的开始日期
                                picker[0].element.val(picker[1].element.val());
                                picker[0].update();
                            }
                        }
                    	$timeout(function(){
                    		var val = vals();
                            isModel && (scope.dates = val.join(','));
                            isStart && (scope.start = val[0]);
                            isEnd && (scope.end = val[1]);
                    	},1);
                    });
                    if(isRange){
                        isStart && scope.$watch('start' , function(newVal , oldVal){
                            if(newVal == oldVal)return;
                            inputs.eq(0).val(newVal ? parseDate(newVal) : '');
                            picker[0].update()
                        });
                        isEnd && scope.$watch('end' , function(newVal , oldVal){
                            if(newVal == oldVal)return;
                            inputs.eq(1).val(newVal ? parseDate(newVal) : '');
                            picker[1].update()
                        });
                        if(scope.start){
                            inputs.eq(0).val(parseDate(scope.start));
                            picker[0].update();    
                        }
                        if(scope.end){
                            inputs.eq(1).val(parseDate(scope.end));
                            picker[1].update();    
                        }
                    }else{
                        scope.$watch('dates' , function(newVal , oldVal){
                            if(newVal == oldVal)return;
                            inputs.val(newVal ? parseDate(newVal) : '');
                            picker.update();
                        });
                        if(scope.dates){
                            inputs.val(parseDate(scope.dates));
                            picker.update();    
                        }
                    }
                } , 1);
                
                function vals(){
                    return inputs.map(function(){
                        return this.value
                    }).toArray()
                }

                scope.$on('$destroy' , function(){
                    if(isRange){
                        picker[0].remove();
                        picker[1].remove();
                    }else{
                        picker.remove();
                    }
                    $el = inputs = picker = null;
                });
            },
            replace : true,
            template : '\
                <div class="my-datepicker">\
                    <div class="input-daterange input-group" ng-if="isRange">\
                        <input type="text" readonly class="input-sm form-control date-left" />\
                        <span class="input-group-addon">至</span>\
                        <input type="text" readonly class="input-sm form-control date-right" />\
                    </div>\
                    <div class="input-date" ng-if="!isRange">\
                        <input type="text" readonly class="input-sm form-control"/>\
                    </div>\
                </div>'
        }
    });

    /*
        增强select插件
        @examples
            <select
                select2
                ng-model=""
                multiple
                ng-options=""
                data-live-search="true"
                data-selected-text-format="count>3"
                data-size="3"
            ></select>

        @demo see (/examples/select2.html)

        @api see (http://silviomoreto.github.io/bootstrap-select/)

        @params
            ng-model=""                 绑定数据结果
            multiple                    开启多选，默认不开启
            ng-options=""               绑定select选项
            live-search="true"          开启动态搜索
            selected-text-format=""     选中结果格式化
            size=""                     下拉列表最大显示选项数
     */
    app.directive('select2' , function(){
        var opts = $.fn.selectpicker.defaults;
        opts.noneSelectedText = '请选择';
        opts.noneResultsText = '无匹配结果';
        opts.countSelectedText = '已选中{0}个';
        return {
            require : '?ngModel',
            link : function(scope , el , attr , ctrl){
                var container = el.closest('.modal').length ? false : 'body';
                var init;
                if(el.is(':visible')){
                    setTimeout(function(){
                        el.selectpicker({
                            container : container
                        });
                    },1);
                    init = true;
                }
                if(!init){
                	var modal = el.closest('.modal');
                	if(modal.length){//如果是在对话框里面的select2,在对话框显示的时候初始化它
                		modal.one('show.bs.modal' , function(){
                			el.selectpicker({
                                container : container
                            });
                            init = true;
                		});
                	}else{
                		//否则,在数据变化的时候判断是否已经显示,来决定初始化自己
                		var unwatch = scope.$watch(function(){
                            if(el.is(':visible')){
                                el.selectpicker({
                                    container : container
                                });
                                init = true;
                                unwatch();
                            }
                        });
                	}
                }

                if(ctrl){
                    var options = (attr.ngOptions || '').match(/in\s+(\w+)/);
                    options && options[1] && scope.$watchCollection(options[1] , function(){
                        init && refresh();
                    });
                    scope.$watch(attr.ngModel , function(){
                        init && refresh();
                    });
                }

                function refresh(){
                    setTimeout(function(){
                        el.selectpicker('refresh');
                    } ,200);
                }

                scope.$on('$destroy' , function(){
                    el.selectpicker('destroy');
                });
            }
        }
    });

    app.directive('phone' , function(){
        var IS_PHONE = /^1[3|4|5|8][0-9]\d{8}$/;
        return {
            require : '?ngModel' ,
            link : function(scope , el , attr , ctrl){
                if(ctrl){
                    function valid(value){
                        if(ctrl.$isEmpty(value) || IS_PHONE.test(value)){
                            ctrl.$setValidity('phone' , true);
                            return value;
                        }else{
                            ctrl.$setValidity('phone', false);
                        }
                    }
                    ctrl.$formatters.unshift(valid) ;
                    ctrl.$parsers.unshift(valid);
                }
            }
        }
    });

    /**
     * 树插件
     * @examples
     *  html:
     *  <ul class="ztree" data-ztree="treeData" data-setting="setting"></ul>
     *
     *  script:
     *  function TestCtrl($scope){
     *      $scope.treeData = [//树数据
     *          {
     *              name : "parent" ,
     *              children : [
     *                  {name : "children" , children : []},
     *              ]
     *          }
     *      ];
     *      $scope.setting = {};//数插件配置（可选）
     *  }
     */
    app.directive('ztree', [function () {
        return {
            scope: {
                ztree: '=',
                setting: '=',
                ready: '&',
                data: '='
            },
            link: function (scope, el, attr) {
                scope.$watchCollection('data', function (data) {
                    scope.ztree = $.fn.zTree.init(el, scope.setting || {}, data);
                    scope.ready({ztree: scope.ztree, api: scope.ztree});
                });
                scope.$on('$destroy', function () {
                    scope.ztree && scope.ztree.destroy();
                });
            }
        }
    }]);

    //表单校验提示框
    app.directive('validTip', function ($timeout) {
        return {
            require: 'ngModel',
            link: function (scope, el, attr, ctrl) {
                var parent = el.closest('.form-group');

                var content = attr.validTip || "";

                if(!content){//is empty
                    return;
                }

                if(content.indexOf('{') != -1 && content.indexOf('}') != -1){
                    content = scope.$eval(attr.validTip);
                }

                var getContent = angular.isString(content) ? function(){
                    return content;
                } : function(errors){
                    var ret = [],
                        locals = errors || content,
                        key;
                    for (key in locals) {
                        locals[key] && ret.push(content[key]);
                    }
                    return ret.join('；');
                };

                el.popover({
                    content: getContent(),
                    placement: attr.validPos || 'right',
                    trigger: 'manual'
                });

                var popover = el.data('bs.popover');

                var focus = false;
                el.focus(function () {
                    if (ctrl.$invalid && !ctrl.$isEmpty(ctrl.$viewValue)) {
                        el.popover('show');
                    }
                    focus = !0;
                }).blur(function(){
                    el.popover('hide');
                    focus = !1;
                });
                
                setTimeout(function(){
                	if(ctrl.remote){
                    	ctrl.remote(function(pass){
                    		$timeout(setState, 100);
                    	});
                    }
                },10);
                
                function setState(){
                    if (ctrl.$invalid && focus) {
                        popover.options.content = getContent(ctrl.$error);
                        el.popover('show');
                        parent.addClass('has-error');
                    } else {
                        el.popover('hide');
                        parent.removeClass('has-error');
                    }
                }
                
                ctrl.$parsers.push(function (value) {
                	$timeout(setState, 100);
                    return value;
                });
                
                scope.$watch(attr.ngModel , function(newVal , oldVal){
                	if(newVal != oldVal){
                		setTimeout(function(){
                		    setState();
                		} , 100);
                	}
                });

                scope.$on('$destroy', function () {
                    el.popover('destroy');
                    el = parent = popover = null;
                });
            }
        }
    });

    /**
     * 自定滚动条
     * @examples
     *  html:
     *  <div data-sliderbar> .... </div>
     * */
    app.directive('sliderbar', function () {
        return {
            link : function(scope , el , attr){
                el.on('mousewheel' , function(event){
                    event.preventDefault();
                    var top = (el.scrollTop() + ((event.deltaY * 30) * -1));
                    el.scrollTop(top);
                });
            },
            transclude : true,
            template : '<div class="scroll-body" ng-transclude></div>'
        }
    });

    //导航点击，reload
    app.directive('navList', ['$rootScope' , function ($rootScope) {
        return function (scope, el, attr) {
            var fn = function(){
                if(isReady){
                    location.href = location.href + '?reload=' + Math.random();
                    isReady = false;
                }
            } , isReady = true , time;
            //点击菜单，刷新页面，如果是点击同一个页面，则延迟后刷新
            el.on('click', 'a', function () {
                if (this.hash == '#' + $rootScope.path) {
                    clearTimeout(time);
                    time = setTimeout(function(){
                        fn();
                        isReady = true;
                    } , 200);
                }
            });

            //2级导航事件
            el.on('click' , '.topMenu > li > a' , function(){
                var me = $(this),
                    ul = me.next();//拿到这个ul
                me.parent().siblings().children('ul').hide();//隐藏其它的ul
                ul.slideDown(600);//将他自己显示
            });

            $rootScope.$on('menuChange' , function(e , paths){
                setTimeout(function(){
                    el.find('.topMenu>li>ul').hide();//先隐藏全部的ul
                    var def_link = el.find('a[href*="' + paths.join('/') + '"]');//根据路由查找链接
                    if(def_link.length){//如果有找到
                        def_link.parents('ul').show();//将它的父亲ul显示
                    }else{
                        el.find('.topMenu>li:first-child>ul').show();//否则显示第一个ul
                    }
                } , 1);
            });
        }
    }]);
	
	/**
     * 批量绑定checkbox的值
     * @examples
     *  <form name="test">
     *      <input type="text" value="实际值" value-list="test.list"/>
     *      <input type="text" ng-value="变量" value-list="test.list"/>
     *
     *      test.list = {{test.list}}//值
     *  </form>
     */
	app.directive('valueList' , function(){
        return {
            scope : {
                datas : '=valueList'
            },
            link : function(scope , el , attr){
                if(attr.valueList){
                	
                	scope.$watchCollection('datas' , function(list){
                		list = list || [];
                		el[0].checked = indexOf(list , el.val()) > -1;
                	});
                	
                    scope.datas = scope.datas || [];
                    
                    el[0].checked = !!attr.checked;
                    
                	attr.ngChecked && attr.$observe('checked' , function(){
            			el.trigger('change');
                    });
                    
                    el.on('change' , function(){
                        var val = el.val() , index;
                        index = indexOf(scope.datas , val);
                        if(this.checked){
                            if(index == -1){
                                scope.datas.push(val);
                            }
                        }else{
                            if(index > -1){
                                scope.datas.splice(index , 1);
                            }
                        }
                    });
                    
                    el.on('click' , function(){
                    	setTimeout(function(){
                    		scope.$apply();
                    	},1);
                    });
                    
                    function indexOf(arr , val){
                        for(var i=0,l=arr.length; i<l; i++){
                            var obj = arr[i];
                            if(obj + '' == val + ''){
                                return i;
                            }
                        }
                        return -1;
                    }
                    
                }
            }
        }
    });

    /**
     * form.Input.equals
     * 表单校验，字段相等校验
     * @examples
     *  <form name="test">
     *      <input type="text" ng-model="t1" equals="t2"/>
     *      <input type="text" ng-model="t2" equals="t1"/>
     *
     *      {{test.t1.$valid}}//校验通过
     *      {{test.t1.$error.equals}}//错误位置
     *      {{test.t2.$valid}}
     *      {{test.t2.$error.equals}}
     *  </form>
     */
    app.directive('equals', function () {
        return {
            restrict: 'A',
            require: "?ngModel",
            link: function (scope, el, attr, ctrl) {
                if (!ctrl) return;
                var validator = function () {
                    var equals = scope.$eval(attr.equals),
                        model = scope.$eval(attr.ngModel),
                        valid = angular.equals(equals, model);
                    ctrl.$setValidity('equals', valid);
                    return valid ? model : undefined;
                };

                scope.$watch(attr.equals + ' + ' + attr.ngModel, validator);
            }
        }
    });
    
    //静态校验
    /**
     * @examples
     * html 
     * 		<input type="text" ng-model="xx" name="test" my-valid="{valid:'validFnName' , name : 'validName'}"/>
     * 		form.test.$error.validName 用它来判断校验是否通过
     * js
     * 	$scope.validFnName = function(data){
     * 		//这里如果符合规则，就返回true，不符合就返回false表示校验未通过
     * 	}
     */
    app.directive('myValid' , function(){
        return {
            restrict : 'A',
            require : '?ngModel',
            link : function(scope , el , attr , ctrl){
                if(!ctrl)return;
                
                var validData = function (newVal){
                    var config = scope.$eval(attr.myValid);//current config
                    var valid = scope[config.valid];//校验规则
                    if(config.reset){
                        for(var i in config.reset){
                            ctrl.$setValidity(config.reset[i], true);
                        }
                    }
                    ctrl.$setValidity(config.name, valid(newVal));
                }
                
                scope.$watch(attr.ngModel, validData);
            }
        }
    });
    
    
    //远程校验
    /**
     * @examples
     * 	html
     * 		<input type="text" name="test" ng-model="group.test" 
     * 			remote-valid="{server : 'Institution' , valid : 'valid' , params : 'getParams' , name : 'orgCd' , fn : 'query'}"
     * 		/>
     * 	js
     * 		$scope.Institution = Resource;//需要一个resource来调用服务
     * 		$scope.valid = function(responseData){//需要一个方法来检测是否通过校验
     * 			if(responseData)return true;
     * 			return false;
     * 		}
     * 		$scope.getParams = function(value){//需要返回一个发送给服务器的数据
     * 			return {
     * 				list : true,
     * 				params : {val : value || ''}
     * 			}
     * 		}
     * 		
     * 	@config
     * 		server	{Resource}	指定的访问服务器的server
     * 		valid 	{Function}	指定的校验方法
     * 		params	{Function}	指定的发送服务器的数据
     * 		name	{String}	校验程序名称
     * 		fn		{String}	发送服务器的方法(query,get,put,remove)
     *
     *
     *  js :
     * 	function TestCtrl($scope , UserResource){
     *      $scope.UserResource = UserResource;
     *
     *      $scope.valid = function(response){//response = {valid : true}
     *          if(response.valid){
     *              return true;
     *          }
     *          return false;
     *      };
     *
     *      $scope.getParams = function(value){
     *          return {
     *              valid : true,
     *              userName : value
     *          }
     *      };
     * 	}
     *
     * 	html :
     *
     * 	<form name="valid">
     * 	    <input type="text" name="userName" ng-model="userName"
     * 	        remote-valid="{server : 'UserResource' , valid : 'valid' , params : 'getParams' , name : 'name' , fn : 'get'}"
     * 	        />
     * 	</form>
     *
     */
    app.directive('remoteValid' , function(){
    	return {
    		restrict : 'A',
    		require : '?ngModel',
    		link : function(scope , el , attr , ctrl){
    			if(!ctrl)return;
    			
    			var config = scope.$eval(attr.remoteValid);
    			var params = scope[config.params];//参数
    			var valid = scope[config.valid];//校验规则
    			var server = scope[config.server];//发送请求
    			
    			var list = [];
    			ctrl.remote = function(fn){
    				fn && list.push(fn);
    			};
    			
    			if(!valid){
    				console.log('remote-valid valid:['+ config.valid + '] 不存在');
    			}
    			if(!server){
    				console.log('remote-valid server:['+ config.server + '] 不存在');
    			}
    			
    			function validData(newVal){
    				
    				var post = params(newVal);
                    if(post){
                    	post._box = false;
                    	server[config.fn || 'get'](post , function(data){
                    		var pass = valid(data);
                        	ctrl.$setValidity(config.name, pass);
                    		list.forEach(function(fn){
                    			fn(pass);
                    		});
                        } , function(){
                        	ctrl.$setValidity(config.name, false);
                        });	
                    }
                    return newVal;
    			}
    			
    			ctrl.$parsers.push(validData);
    			
    			scope.$watch(attr.ngModel , function(newVal , oldVal){
    				if(!newVal || newVal != oldVal){
    					ctrl.$setValidity(config.name, true);
    				}
    			});
    			
    		}
    	}
    });

    //子导航
    app.directive('dropdownMenu', [function () {
        return {
            restrict: 'C',
            link: function (scope, el) {
                setTimeout(function () {
                    el.children('li').each(function () {
                        var $li = $(this) , $ul = $li.children('ul');
                        if ($ul.length) {
                            $li.addClass('sub-dropdown');
                            $ul.addClass('sub-menu');
                        }
                        $li.on('mouseenter mouseleave', function () {
                            $li.toggleClass('active');
                            $ul.toggleClass('sub-open');
                        });
                    });
                }, 1);
            }
        }
    }]);

    /**
     * 小提示
     * @examples
     *  html:
     *  <any data-tip="t" title="title text"></any>
     *
     * @params
     * tip 显示方向( t:top , l:left , r:right , b:bottom )
     * title 提示内容
     */
    app.directive('tip', function () {
        var shared = {
            style: {classes: 'ui-tooltip-shadow ui-tooltip-tipsy'},
            //show: {delay: 100},
            hide: {delay: 0}
        }, position = {
            b: {
                position: {my: 'top center', at: 'bottom center'}
            },
            t: {
                position: {my: 'bottom center', at: 'top center'}
            },
            l: {
                position: {my: 'right center', at: 'left center'}
            },
            r: {
                position: {my: 'left center', at: 'right center'}
            }
        }, $win = $(window);
        return function (scope, el, attr) {
            var pos = position[attr.tip];
            if (pos) {
                pos.position.viewport = $win;
                el.qtip($.extend({}, shared, pos));
                el.on('$destroy', function () {
                    el.qtip('destroy');
                });
            }
        }
    });

    app.directive('loadFrame' , function(){
        return function(scope , el , attr){
            attr.$observe('loadFrame' , function(src){
                if(!src){//空地址就无法请求了
                    src = 'about:blank'

                }else if(src.indexOf("year:''") > -1){//年还没有编译完
                    console.log('bad link' , src);
                    src = 'about:blank';

                }else if(src.indexOf('year=&') > -1 || src.indexOf("month=''") > -1){
                    //年或月份都没有编译完的时候不许通过，否则会报500错误
                    console.log('bad link' , src);
                    src = 'about:blank';

                }else if(src.indexOf('{{') > -1){//还未编译完？
                    console.log('bad link' , src);
                    src = 'about:blank';
                }
                el[0].src = src;
            });
        }
    });


    /**
     * checkbox list bind
     * @examples
     * html:
     *  <div data-check-list data-model="selected" data-data="list" data-name="class" data-key="id"></div>
     *  {{selected}}
     *
     * js:
     *  scope.list = ['a','b','c','d'];
     *
     * @params
     *  model 结果绑定
     *  data {Array}数据源
     *  name 唯一name
     *  data-name="keys1"    元素name
     *  data-key="id"              元素value 
     * */
    app.directive('checkList', function () {
        return {
            scope: {
                model: '=',
                name: '=',
                data: '='
            },//id
            link: function (scope, el, attr) {//[ {id,name,age,xx,xxs} , {} , {} ]
                var key = attr.key || 'name';
                
                scope.name = attr.name || 'name';
                
                scope.pid = 'a' + Math.random();
                
                scope.$watch('model', function (model) {
                    match();
                });
                
                function match() {
                    angular.forEach(scope.data || [], function (item) {
                        item.checked = $.inArray(item[key], scope.model || []) > -1;
                    });
                }

                scope.change = function (item) {
                    var model = scope.model || [];
                    if (item.checked) {
                        model.push(item[key]);
                    } else {
                        model.splice($.inArray(item[key], model), 1);
                    }
                    scope.model = model;
                };
            },
            template: '<label class="checkbox" ng-repeat="item in data">\
                            <input type="checkbox" ng-model="item.checked" ng-change="change(item)" name="{{pid}}"> {{item[name]}}\
                        </label>'
        };
    });

    /**
     * highcharts图表
     * @examples
     *  html:
     *  <any data-charts="config"></any>
     *
     * $scope.config = {};
     */
    app.directive('charts', ['$timeout', function ($timeout) {
        return {
            scope: true,
            link: function (scope, el, attr) {
                scope.$watch(attr.charts, function (value) {
                    init(value);
                });

                function init(config) {
                    el.highcharts(config);
                }
            }
        };
    }]);

    /**
     * 地图
     * @examples
     *  html:
     *  <any data-map="myConfig" data-ready="readyFn($api , $config)"></any>
     *
     * $scope.myConfig = {};
     */
    app.directive('map', function factory() {
        var option = {
            tooltip : {
                trigger: 'item',
                formatter: '{b}'
            },
            series : [
                {
                    name: '中国',
                    type: 'map',
                    mapType: 'china',
                    selectedMode : 'single',
                    itemStyle:{
                        normal:{label:{show:true}},
                        emphasis:{label:{show:true}}
                    },
                    data:[]
                }
            ]
        };
        return {
            scope : {
               api : '=map',
               options : '=',
               ready : '&'
            },
            link :function (scope, element, attrs) {
                var isApi = !!attrs.map;

                function resize(e , data){
                    myChart.resize && myChart.resize();
                }

                if(echarts){
                    var myChart = echarts.init(element[0]);
                    attrs.options && scope.$watch('options' , function(opt){
                        if(typeof opt === 'string' && opt == 'option'){
                            myChart.setOption(option , true);
                        }else{
                            opt && myChart.setOption(opt , true);
                        }
                    });

                    scope.$on('$destroy', function (event, data) {
                        $(window).off("resize", resize);
                        myChart && myChart.dispose();
                    });

                    if(isApi){
                        scope.api = myChart;
                    }
                    scope.ready({$api : myChart , $config : echarts.config});

                    $(window).on("resize", resize);
                }
            }
        };
    });
    
    app.directive('popover' , function(){
        var doc = $(document);
        return function(scope , el){
            var obj = el.popover().data('bs.popover'),
                tip , arrow;

            el.on('show.bs.popover' , function(){
                setTimeout(function(){
                    tip = obj.$tip[0];
                    arrow = obj.$arrow[0];
                    doc.on('click' , docHide);
                } , 1)
            });
            function docHide(e){
                var target = e.target;
                if($.contains(tip , target)){
                    return;
                }else if(target == el[0]){
                    doc.off('click' , docHide);
                    return;
                }
                el.popover('toggle');
                doc.off('click' , docHide);
            }
            el.on('$destroy' , function(){
               el.popover('destroy');
            });
        }
    });

    //loading defer box
    app.directive('deferBox', function ($timeout) {
        return {
            scope: {
                deferBox: '='
            },
            link: function (scope, el, attr) {
                var wins = ['50%' , '70%' , '85%' , '90%' , '95%' , '98%'];
                var progress = el.find('.progress-bar');

                scope.show = false;

                function anim() {
                    progress.animate({
                        width: wins[anim._index]
                    }, 100);
                    anim._timer = setTimeout(function () {
                        anim._index += 1;
                        wins[anim._index] && anim();
                    }, 200);
                }

                anim._index = 0;

                scope.deferBox = {
                    _open: false,
                    open: function () {
                        if(scope.deferBox._open)return;
                        scope.show = true;
                        progress.css('width', '0%');
                        progress.animate({
                            width: '0%'
                        }, 1);
                        anim();
                        scope.deferBox._open = true;
                    },
                    close: function () {
                        $timeout(function () {
                            !scope.deferBox._open && (scope.show = false);
                        }, 600);
                        anim._index = 0;
                        clearTimeout(anim._timer);
                        progress.animate({
                            width: '100%'
                        }, 1);
                        scope.deferBox._open = false;
                    }
                };
            },
            template: '<div class="defer_box_bg" ng-show="show"></div>\
                <div class="defer_box" ng-show="show">\
                    <div class="progress progress-striped active">\
                        <div class="progress-bar progress-bar-info"></div>\
                    </div>\
                </div>'
        }
    });

    //贴心小秘书
    app.directive('helpTips' , function(){
       return function(scope , el , attr){
           var head = el.find('.head'),
               body = el.find('.body');
           head.on('click' , function(){
               body.toggleClass('ng-hide');
           });
       }
    });
    
    app.directive('modal' , function(){
    	return {
    		restrict: 'C',
    		link : function(scope , el , attr){
    			function destroy(){
    				setTimeout(function(){
    					$('.modal-backdrop').remove();
    				} , 1);
    			}
                scope.$on('$destroy' , destroy);
        	}
    	};
    });
    
    app.directive('readonly' , function(){
    	return {
    		restrict: 'A',
    		link : function(scope , el , attr){
    			function focus(){
    				el.blur();
    			}
    			attr.$observe('readonly' , function(){
    				if(el.prop('readonly')){
    					el.on('focus',focus);    					
    				}else{
    					el.off('focus',focus);
    				}
    			});
				
    		}
    	}
    });

    //数据预览
    /**
     * @examples
     * <div view-data-set="method"></div>
     *
     * @params
     * viewDataSet {String method} 访问方法名,通过访问此方法，可以打开对话框
     *
     * method(Map config)
     * config.columns   {Map columns}           列头查询参数
     * config.query     {Boolean query}         是否显示查询区域，默认false
     * config.params    {Map params}            查询参数
     */
    app.directive('viewDataSet' , function(DataSetView){
        var id = 0;
        return {
            restrict: 'A',
            scope: {
                open: '=viewDataSet'
            },
            link: function (scope, el, attr) {
                scope.id = 'viewDataSet' + id++;
                var init;
                scope.open = function (config) {
                    if (!config) {
                        return;
                    }
                    
                    scope.columns = DataSetView.query(config.columns);
                    scope.query = config.query;
                    scope.params = config.params;
                    scope.paging = DataSetView;
                    
                    if(init){
                        setTimeout(function(){
                            scope.refresh('first' , true);
                        },1);
                    }

                    init = true;
                    $('#' + scope.id).modal('show');
                };
            },
            replace : true,
            templateUrl: 'templates/dataIntegration/_directive/viewDataSet.html'
        }
    });
    
    
  //选择数据
    /**
     * @examples
     * <div selecter-data-set="fnName"></div>
     *
     * @params
     * viewDataSet {String method} 访问方法名,通过访问此方法，可以打开对话框
     *
     * method(callback [, customData] [,args..])
     * 
     * callback(item)  回调函数
     * 
     * <button ng-click="select()">选择数据</button>
     * 
     * $scope.select = function(){
     *      $scope.fnName(function(item){
     *          if(item.name == 'xxx')
     *              return false;
     *          $scope.dbName = item.name;
     *      });
     * };
     * 
     */
    app.directive('selecterDataSet' , function(DataSet , DataDefinition , $rootScope){
        var id = 0;
        return {
            restrict: 'A',
            scope: {
                open: '=selecterDataSet'
            },
            link: function (scope, el, attr) {
                scope.id = 'selecterDataSet' + id++;
                
                scope.defs = DataDefinition.query({
                    isArray : true,
                    params : angular.toJson({
                        "businessType" : "1",
                        "orderBy" : "CREATE_DATE",
                        "order" : "desc",
                        "createUser" : $rootScope.USER_INFO.id
                    })
                });
                
                var defaultParams = {
                        "createUser" : $rootScope.USER_INFO.id,
                        "orderBy" : "create_Date",
                        "order" : "desc"
                };
                
                scope.params =angular.copy(defaultParams) ;
                
                var init;
                var callback,args;
                scope.open = function (call , customData) {
                    callback = call;
                    args = [].slice.call(arguments , 2);
                    
                    if(customData){
                        scope.params = angular.extend({} , defaultParams , customData);
                    }else{
                        scope.params = angular.copy(defaultParams) ;
                    }
                    
                    scope.pager = DataSet;
                    
                    if(init){
                        setTimeout(function(){
                            scope.refresh('first' , true);
                        },1);
                    }

                    init = true;
                    $('#' + scope.id).modal('show');
                };
                
                scope.select = function(item){
                    args.unshift(item);
                    if(callback){
                        var close = callback.apply(null , args);
                        if(close !== false){
                            $('#' + scope.id).modal('hide');
                        }
                    }
                }
                
            },
            replace : true,
            templateUrl: 'templates/_directive/selecterDataSet.html'
        }
    });
    
    //对话框
    app.factory('dialog' , function($rootScope){
        return function(msg , title){
            $rootScope.dialogMsg = msg;
            $rootScope.dialogTitle = title;
            $('#dialogMsg').modal('show');
        }
    });
    
    //强制输入的是数字
    app.directive('number' , function($parse){
    	return {
    		require : '?ngModel',
    		link : function(scope , el , attr , ctrl){
    			if(ctrl){
    				var get = $parse(attr.ngModel),
    					set = get.assign;
    				var num = /(\d*\.?\d*)/;
    				scope.$watch(attr.ngModel , function(newVal , oldVal){
    					if(newVal != oldVal){
    						var match = num.exec(newVal + '');
        					set(scope , match ? match[1] : '');
    					}
    				});
    			}
    		}
    	}
    });

    return app;

});