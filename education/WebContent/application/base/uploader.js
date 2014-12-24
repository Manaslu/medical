//Created by xiaobai on 2014/6/30.
define(function(require){
    //@type {Angular.module}
    var app = require('./directive');

    //调试工具
    app.provider('Logger' , [function(){
        this.$get = [function () {

            /*
             log 使用频率最高的一条语句：向控制台输出一条消息。支持 C 语言 printf 式的格式化输出。当然，也可以不使用格式化输出来达到同样的目的：
             info向控制台输出一条信息，该信息包含一个表示“信息”的图标，和指向该行代码位置的超链接。
             warn 同 info。区别是图标与样式不同。
             error 同 info。区别是图标与样式不同。error 实际上和 throw new Error() 产生的效果相同，使用该语句时会向浏览器抛出一个 js 异常。
             debug 向控制台输出一条信息，它包括一个指向该行代码位置的超链接。
             assert 断言，测试一条表达式是否为真，不为真时将抛出异常（断言失败）。
             group 输出消息的同时打开一个嵌套块，用以缩进输出的内容。调用 console.groupEnd() 用以结束这个块的输出。
             groupEnd 分组结束
             groupCollapsed 同 console.group(); 区别在于嵌套块默认是收起的。
             dir 输出一个对象的全部属性（输出结果类似于 DOM 面板中的样式）。
             trace 输出 Javascript 执行时的堆栈追踪。
             time 计时器，当调用 console.timeEnd(name);并传递相同的 name 为参数时，计时停止，并输出执行两条语句之间代码所消耗的时间（毫秒）。
             timeEnd 计时器结束
             count 输出该行代码被执行的次数，参数 title 将在输出时作为输出结果的前缀使用。
             clear 清空控制台
             timeStamp 时间戳
             table 输出一个listMap为表格形式
             profile 与 profileEnd() 结合使用，用来做性能测试，与 console 面板上 profile 按钮的功能完全相同。
             profileEnd 结束性能测试
             */

            var sole = {};

            var fns = "log,info,debug,warn,error,assert,group,groupEnd,groupCollapsed" +
                "dir,trace,time,timeEnd,count,clear,timeStamp,table,profile,profileEnd";
            fns.split(',').forEach(function(key){
                sole[key] = consoleFn(key);
            });

            return sole;

        }];

        var console = window.console || {};

        function consoleFn(name) {
            // 如果不是调试模式，则什么都不做
            if (seajs.data.debug === false)return angular.noop;
            var logFn = console[name] || console.log || angular.noop;
            var hasApply = false;
            try {
                // 在ie8浏览器中访问logFn的apply属性会报错,这是一个bug
                hasApply = !!logFn.apply;
            } catch (e) {
            }

            if (hasApply) {
                return function () {
                    var args = [];
                    angular.forEach(arguments, function (arg) {
                        args.push(formatError(arg));
                    });
                    return logFn.apply(console, args);
                };
            }

            // 如果可以支持apply的情况，我们使用上面的方法，如果不能的话，比如在ie8等，我们将使用下面的方法
            // 但是我们只会处理前3个参数
            return function (arg1, arg2, arg3) {
                logFn(arg1, arg2, arg3);
            };

        }

        function formatError(arg) {
            if (arg instanceof Error) {
                if (arg.stack) {
                    arg = (arg.message && arg.stack.indexOf(arg.message) === -1)
                        ? 'Error: ' + arg.message + '\n' + arg.stack
                        : arg.stack;
                } else if (arg.sourceURL) {
                    arg = arg.message + '\n' + arg.sourceURL + ':' + arg.line;
                }
            }
            return arg;
        }
    }]);

    app.factory('ResolveUrl' , function(){
       return function(path){
           return seajs.resolve(path + '#' , seajs.data.base);
       };
    });
    //表单提交自动上传文件
    app.directive('autoFileUpload', ['$timeout' , '$parse' , 'Logger', function ($timeout, $parse , Logger) {
        return {
            controller: function () {

                var apis = [];

                //添加回调
                this.add = function (api) {
                    apis.push(api);
                };

                //移除回调
                this.remove = function (api) {
                    var index = apis.indexOf(api);
                    if (index > -1) {
                        apis.splice(index, 1);
                    }
                };

                //添加回调事件
                this.success = function (success) {

                    //由_.after包裹的函数，必须执行apis.length次后才会真正触发
                    var render = _.after(apis.length, function () {
                        success();//当所有队列完成，触发回调
                    });

                    //监听完成事件
                    _.invoke(apis, 'once', 'uploadFinished', render);//每个文件上传器上传完成后都会执行一次

                    _.invoke(apis, 'upload');//开始运行
                };

                //重置
                this.reset = function(){
                    Logger.log('formCtrl:调用重置');
                    _.invoke(apis , 'reset');
                };

            },
            link: function (scope, form, attr, ctrl) {
                if (form.is('form') && attr.ngSubmit) {
                    form.off('submit');//移除掉原先ng对submit事件的注册
                }

                var fn = $parse(attr.ngSubmit);

                form.on('submit', function (event) {
                    ctrl.success(function () {//当表单回调列表完成后，触发
                        fn(scope, {
                            $event: event
                        });
                    });
                });

                //重置事件
                form.on('reset' , function(){
                    ctrl.reset();
                    scope.$apply();
                });
            }
        }
    }]);

//文件上传器
    /* *
     * @examples 样例代码
     *   <div
     *       uploader="api"
     *       upload-before-send="fn($file,$data)"
     *       upload-success="fn($file,$data)"
     *       upload-error="fn($error)"
     *       finished="fn()"
     *       file-queued="fn($file)"
     *       file-queued-error="fn($file,$type,$ext)"
     *       file-single-size-limit="1024*1024*10"
     *       extensions="jpg,exe,rar"
     *       data="{key:'val'}"
     *       server="post-server.action"
     *       ng-model="responseList"
     *       required
     *       ngRequired="isRequired"
     *       files="fileList"
     *       multiple
     *       thumb
     *   ></div>
     *
     *   @deps 依赖
     *       ^?autoFileUpload   是否随表单自动提交，并且可以支持校验，ngModel绑定等操作，在父标签form上加上此指令即可激活
     *       ^?ngModel  是否开启响应绑定，ngModel绑定到文件上传后的响应
     *
     *   @AttrDesc 属性作用描述
     *
     *       multiple
     *          多文件上传开启
     *
     *       uploader
     *           必须，但是值为可选，如果有值model，将绑定一个控制接口给model
     *
     *       thumb
     *           可选，是否显示缩略图配置，无参数
     *
     *       files
     *           可选，是否绑定选中文件列表，参数为绑定model files="files"
     *
     *       server
     *           可选，上传处理服务器，默认空 server="post.do"
     *
     *       ng-model
     *           可选，如果有，将可以处理表单校验，响应数据绑定操作 ng-model="responseList"
     *
     *       required
     *           可选，如果有，此文件上传必须有文件存在
     *
     *       ng-required
     *           可选，根据条件确定此上传器是否必选 ng-required="isRequired"
     *
     *       data
     *           可选，服务器发送数据类似json数据或者一个model变量都可以 data="{'id':123,name:'xxx'}"
     *          或者 写成变量类型 data="postData"
     *          $scope.postData = {id : 1 , age : 2};
     *
     *       extensions
     *           可选，文件类型限制，文件扩展名不家点，用英文逗号隔开即可 extensions="jpg,png,txt,exe"
     *
     *       upload-before-send
     *           可选。每一个文件上传发送前执行，可以用来修改此文件上传附带数据 uploader-before-send="fn($file,$data)";
     *           $scope.fn = function(file , data){
     *                //file = 上传的文件
     *                //data = 上送的数据
     *                data.id = 1;//可以修改data的属性，将会在上传的时候一并发送到服务器
     *           }
     *
     *       upload-success
     *           可选，每一个文件上传完成后都会触发 uploader-success="fn($file , $data)"
     *           $scope.fn = function(file , responseData){
     *               //file = 上传的文件
     *               //responseData = 服务器的返回数据
     *           }
     *
     *       upload-error
     *           可选，当任何一个文件上传出错时会触发 upload-error="fn($file)"
     *          $scope.fn = function(file){
     *              //file = 上传的文件
     *          }
     *
     *       finished
     *           可选，当上传队列全部上传完成后触发 finished="fn()" 无参数传递，只是一个通知作用
     *           $scope.finished = function(){
     *               Logger.log('文件已经全部上传完成');
     *           }
     *
     *       file-queued
     *           可选，文件加入队列成功时触发 file-queued="fn($file)"
     *           $scope.fn = function(file){
     *               //file = 当前加入队列的文件
     *           }
     *
     *       file-queued-error
     *           可选，文件加入失败时触发 file-queued-error="fn($file,$type,$ext)"
     *           $scope.fn = function(file , type , ext){
     *               //file = 发生错误的文件
     *               //type = 'extensions' or 'fileSingleSizeLimit' or 'emptyFile'
     *               //ext 当type = 'extensions' 文件现在类型错误时会传递次参数
     *           }
     *
     *       file-single-size-limit
     *           可选，单文件大小限制,可以是一个直接量也可以是一个表达式
     *               file-single-size-limit="1024 * 1024 * 10"  10MB
     *               file-single-size-limit="sizeLimit"  变量
     *               sizeLimit = 100;
     *
     *   @warn 警告
     *       当上传组件出现在一个弹出对话框里面的时候，因为初始化的时候，对话框是隐藏的，所以上传组件
     *       无法读取到位置信息，无法正确生成点击区域所以需要在触发对话框的按钮上添加下面的属性，
     *       来让上传组件可以重新调整位置信息
     *       upload-refresh
     *       <button type="button" upload-refresh  data-toggle="modal" data-target="#xxxBox"> open modal</button> 做属性
     *       传值为true，(或者任意值，不要为空即可)即可对上传插件做reset操作，清空已选择的文件等
     *       <button type="button" upload-refresh="true"> open modal</button> 做属性
     *
     * */
    app.directive('uploader', ['$q' , '$parse' , 'Logger' , '$timeout' , 'ResolveUrl', function ($q , $parse ,Logger , $timeout , ResolveUrl) {
        var errImg = ResolveUrl('../base/web-uploader/dist/thumb-err.jpg');
        return {
            scope: {
                uploader: '=',
                uploadBeforeSend: '&',  //每个文件上传前触发
                uploadSuccess: '&',  //每个文件上传完成后触发
                uploadError: '&', //当上传文件出错误时触发
                finished: '&', //当所有文件上传完成触发
                fileQueued: '&', //当文件加入队列触发
                fileQueuedError: '&', //文件加入队列校验错误抛出
                model:'=ngModel',//和外部关联model  ng-model="demand.fileResponseList"
                files:'='//和外部关联files
            },
            require: ['^?autoFileUpload' , '?ngModel'],
            link: function (scope, el, attr, ctrls) {
                var autoForm = ctrls[0],  //自动提交控制器
                    modelCtrl = ctrls[1]; //model控制器
                //是否多文件上传
                var isMultiple = scope.multiple = angular.isDefined(attr.multiple),
                    isThumb = scope.thumb = angular.isDefined(attr.thumb),//可选值 image ,默认file，选image时有预览图
                    fileList = scope.fileList = [], //文件列表
                    files = [], //文件列表
                    responseList = [], //响应列表
                    duplicate = {}; //文件重复列表

                scope.successFile = 0;

                var api = {
                    once: function (name, callback) {
                        uploader.once(name , callback);
                    },

                    upload : function () {
                        Logger.log('开始上传');
                        uploader.upload();
                    },

                    reset : function(){
                        Logger.info('uploader:触发重置');
                        fileList.length = 0; // 清空文件列表
                        files.length = 0; // 外部文件列表
                        responseList.length = 0; // 服务响应列表
                        scope.successFile = 0;
                        duplicate = {}; // 重复文件
                        uploader.reset();
                        uploader.trigger('change');
                        setTimeout(function(){
                            if(!scope.$$phase){
                                scope.$apply();
                            }
                        },1);
                    },

                    destroy : function(){
                        uploader.reset();
                        uploader.destroy();
                        fileList = files = responseList = duplicate = null;
                        uploader = isMultiple = modelCtrl = null;
                    }
                };

                //实例控制器
                var uploader = WebUploader.create({
                    swf : ResolveUrl('../base/web-uploader/dist/Uploader.swf'),
                    server : attr.server,
                    thumb:{
                        allowMagnify:false //生成缩览图时不许缩放
                    },
                    duplicate : true //默认重复选择文件是可以通过的，我们需要手动设置去重功能
                });

                //文件限制类型
                if (attr.extensions) {
                    var extMap = {};
                    var exts = attr.extensions.toLowerCase().split(/,/g);
                    exts.forEach(function (type) {
                        extMap[type.trim()] = 1;
                    });
                    uploader.on('beforeFileQueued', function (file) {
                        if (!extMap[file.ext.toLowerCase()]) {

                            if(attr.fileQueuedError){
                                scope.fileQueuedError({$file : file , $type : 'extensions' , $ext : exts});
                            }else{
                                Logger.warn('文件类型错误，不支持上传此文件类型！\n  支持的文件类型：' + exts.join(','));
                            }

                            return false;
                        }
                    });
                }

                //单文件上传大小限制，单位字节(b)
                if(attr.fileSingleSizeLimit){
                    var singleSizeLimit = +scope.$eval(attr.fileSingleSizeLimit);
                    var size = WebUploader.formatSize(singleSizeLimit);
                    uploader.on('beforeFileQueued', function (file) {
                        if (file.size > singleSizeLimit) {

                            if(attr.fileQueuedError){
                                scope.fileQueuedError({$file : file , $type : 'fileSingleSizeLimit'});
                            }else{
                                Logger.warn('fileSingleSizeLimit:文件大小超出限制，最大支持上传 : ' , size);
                            }

                            return false;
                        }
                    });
                }

                //beforeFileQueued  ｛file｝ 文件加入队列之前,返回false不加入队列了
                uploader.on('beforeFileQueued' , function(file){
                    !isMultiple && api.reset();//单文件上传的时候，我们没有单文件选项，只好清空掉以前选好的文件
                    
                    if(!file.size){//不允许上传一个空文件
                        scope.fileQueuedError({$file : file , $type : 'emptyFile'});
                        return false;
                    }
                    
                    var exp = getFileExp(file);

                    if(duplicate[exp]){
                        Logger.warn('beforeFileQueued:duplicate:文件重复，不加入队列' , file.name);
                        return false;
                    }

                    Logger.log('beforeFileQueued' , file.name);

                    duplicate[exp] = 1;

                    return true;
                });

                //文件exp
                function getFileExp(file){
                    return file.name + file.size + file.ext + (+file.lastModifiedDate);
                }

                //fileQueued {file} 当文件被加入队列以后触发
                uploader.on('fileQueued' , function(file){
                    Logger.log('fileQueued:文件加入队列' , file.name);

                    fileList.push(file);

                    files.push({
                        name: file.name,
                        size: file.size,
                        ext: file.ext,
                        lastModifiedDate: file.lastModifiedDate
                    });

                    if(isThumb){
                        uploader.makeThumb( file, function( error, src ) {
                            if(error){
                                file._error = '无法生成预览';
                                file._errImg = errImg;
                                Logger.warn('makeThumb:生成缩略图失败', file.name);
                            }else{
                                file._src = src;
                            }
                            scope.$apply();
                        }, 150, 150);
                    }
                    
                    scope.files = files;//update files bind

                    scope.fileQueued({$file : file});

                    scope.$apply();
                });

                // 通知文件变化
                uploader.on('filesQueued' , function(){
                    uploader.trigger('change');
                });

                //uploadFinished 当文件上传结束时触发
                uploader.on('uploadFinished' , function(){
                    Logger.log('uploadFinished:文件上传结束');
                    scope.finished({$files : fileList});
                    scope.$apply();
                });

                //uploadStart {file} 某个文件开始上传前触发
                uploader.on('uploadStart' , function(file){
                    Logger.log('uploadStart:文件上传开始' , file.name);
                    file._upload = true;
                    scope.$apply();
                });

                //uploadBeforeSend {object , data} 在文件上传请求发送前触发
                uploader.on('uploadBeforeSend' , function(file , data , headers){
                    Logger.log('headers' , angular.toJson(headers));
                    if(attr.data){
                        angular.extend(data , scope.$eval(attr.data) || {});
                    }
                    scope.uploadBeforeSend({$file: file, $data: data , $headers : headers});
                });

                //uploadProgress {file , percentage} 上传过程中触发，上传进度
                uploader.on('uploadProgress' , function(file , percentage){
                    Logger.log('uploadProgress:上传进度' , file.name , percentage);
                    file._progress = percentage * 100;
                    scope.$apply();
                });

                //uploadError {file , reason} 当文件上传出错时触发
                uploader.on('uploadError' , function(file , err_code){
                    Logger.warn('uploadError:文件上传出错' , file.name , ' code : ' , err_code);
                    file._error = true;
                    scope.uploadError({$file : file , $code : err_code});
                    scope.$apply();
                });

                //uploadSuccess {file} 当文件上传成功时触发
                uploader.on('uploadSuccess' , function(file , response){
                    Logger.log('uploadSuccess:件上传成功' , file.name , response);
                    file._success = true;
                    responseList.push(response);
                    scope.uploadSuccess({$file : file , $data : response});
                    scope.successFile += 1;
                    scope.$apply();
                });

                //uploadComplete {file} 当文件上传成功时触发
                uploader.on('uploadComplete' , function(file){
                    Logger.log('uploadComplete:件上传成功' , file.name);
                    file._upload = null;
                    scope.$apply();
                });

                //文件取消上传
                scope.cancel = function(file , index){
                    Logger.info('removeFile:取消上传' , file.name);
                    uploader.removeFile(file , true);
                    if(isMultiple){
                        fileList.splice(index , 1);
                        files.splice(index , 1);
                        delete duplicate[getFileExp(file)];
                    }else{
                        api.reset()
                    }
                    uploader.trigger('change');
                };

                //稍微延时，错开js线程，然后开始捕捉按钮,否则ng在运行时无法得到按钮
                $timeout(function(){
                    uploader.addButton({
                        id : el.find('.select-file')[0]
                    })
                });

                if(attr.uploader){//如果可以接收api的话
                    scope.uploader = api;
                }

                if(attr.files){//提供给外面的files接口
                    scope.files = files;
                }

                if(attr.ngModel){//提供给外面的model接口
                    uploader.on('uploadFinished' , function(){
                    	scope.model = responseList;//update responseList bind
                        scope.$apply();
                    });
                    scope.model = responseList;
                }

                if(autoForm){//提供给自动上传表单接口
                    autoForm.add(api);
                    el.on('$destroy' , function(){
                        autoForm.remove(api);
                    });
                }

                if(attr.ngModel && modelCtrl){//校验控制接口
                    if(angular.isDefined(attr.required) || angular.isDefined(attr.ngRequired)){
                        var isRegister = false;
                        attr.$observe('required' , function(isRequired){
                            if(isRequired){
                                if(!isRegister){
                                    isRegister = true;//注册一次
                                    uploader.on('change' , validRequired);
                                    validRequired();
                                }
                            }else if(!isRequired){
                                isRegister = false;
                                uploader.off('change' , validRequired);
                            }
                        });
                    }
                }

                function validRequired(){
                    setTimeout(function(){
                        modelCtrl.$setValidity('required' , !!fileList.length);
                        if(!scope.$$phase){
                            scope.$apply();
                        }
                    } , 1);
                }

                //修复弹出框里面的组件无法对齐焦点，无法点击
                $(document).on('click' , '.upload-refresh,[upload-refresh]' , refresh);

                el.on('$destroy' , destroy);

                function refresh(){
                    if($(this).attr('upload-refresh')){
                        api.reset();
                    }
                    setTimeout(function(){
                        uploader.refresh();
                    } , 300);
                }

                //销毁实例
                function destroy(){
                    api.destroy();
                    api = null;
                    $(document).off('click' , '.upload-refresh,[upload-refresh]' , refresh);
                }
            },
            replace: true,
            templateUrl: ResolveUrl('../templates/_directive/uploader.html')
        }
    }]);
});
