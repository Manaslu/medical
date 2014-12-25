/**
 * Created by baihuibo on 2014/4/19.
 */
(function (seajs) {
    seajs.config({//项目配置
        base : './application/',
        paths: {//路径别名，将路径取别名
            '~': 'controller/'
        },
        alias: {//当模块路径很长时，可以使用 alias 配置来简化
            directive: 'base/directive',//公用指令
            service: 'base/service',//接口
            filter: 'base/filter',//过滤器
            base: 'base/base',//基类
            "seajs-debug": "./seajs/seajs-debug/1.1.1/seajs-debug"
        },
        data : {
            //测试报表地址
            //resultPath : 'http://172.17.181.132:8080/'
        	//10.3.24.71 weblogic  此处不能加斜杠
            resultPath : 'http://localhost:8080'
        	//10.3.24.71 tomcat
            //resultPath : 'http://10.3.24.71:7002/'
        },
        debug: false
    });
})(seajs);
