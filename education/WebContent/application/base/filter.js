define(function (require) {
    var app, iconMap;
    iconMap = require('data/icon.json');
    app = angular.module('filter', []);
    app.filter('icons', function () {
        return function (action) {
            return iconMap[action];
        };
    });
    
    
    //规则模板数据排重-去重复
    app.filter('uniqueColumns' , function() {
		return function(list , key , uniqueList){
			if(!list || !uniqueList || !key){
				return list;
			}
			
			return list.filter(function(item){
				return uniqueList.indexOf(item[key]) == -1;
			});
		}
	});

    //还原空格和换行
    app.filter('makeSpace' , function(){
        return function(str){
            return (str || "").replace(/\s/g , '&nbsp;');
        }
    });

    //取消横线  yyyy-mm-dd to yyyymmdd
    app.filter('dateReplace' , function(){
        return function(date){
            console.log('date' , date);
            return date.replace(/-/g , '');
        };
    });

    app.filter('orderByKey' , function(){
        return function(arr){
            var ret = [];
            var roles = arr.filter(function(item){
                return item._role;
            });
            var merges = arr.filter(function(item){
                return item._merge;
            });
            var other = arr.filter(function(item){
                return !item._merge && !item._role;
            });
            return ret.concat(roles , merges , other);
        };
    });
    
    //数据集内容查看时日期型数据格式化
    app.filter('viewDataSet' , function($filter){
        var dateFilter = $filter('date');
       return function(val , column){
           if(column && column.dataType.toUpperCase() == 'DATE'){
               return dateFilter(val , 'yyyy-MM-dd');
           }
           return val;
       }
    });
    
    //处理string
    app.filter('overflow' , function(){
        return function(str , size , ex){
            if(str){
                 if(str.length > size){
                     return str.slice(0,size) + ex;
                 }
                 return str;
            }
            return "";
        }
    });
    
    //---------------------------------------开始---------------------------------------
    //当字段为日期时去除合并规则 规则下拉列表过滤
    app.filter('dateNoMerge' , function(){
        return function(list , item){
            if(item.dataType == 'DATE'){
                return list.filter(function(item){
                    return item.scriptId != 33;//合并规则
                });
            }
            return list;
        };
    });
    
    //数据定义去除地址列内的指定列
    app.filter('columnsRmItem' , function(){
        return function(items , itemName){
            var list=[];
            if(!items||!itemName){
                return items;
            }
           for(var i=0;i<items.length;i++){
               if(items[i].columnName!=itemName){
                   list.push(items[i]);
               }
           }
            return list;
        };
    });
    //---------------------------------------结束---------------------------------------
    //简单过滤器工厂
    function filterFactory(name, data) {
        data = data || {};
        app.filter(name, function () {
            return function (key) {
                return data[key];
            };
        });
    }

    //专题流程管理-->状态 {{ exports | DM_appro_state }}
    filterFactory('DM_appro_state', {
        1: '待申请',
        2: '业务领导待审批',
        3: '待接收',
        4: '技术领导待审批',
        5: '待技术员确认',
        6: '成果待确认',
        7: '已结束',
        8: '已终止',
        	
        9: '领导待审批',
        a: '省联系人待接收',
        b: '集团联系人待接收',
        c: '领导待审批',
        d: '待技术员确认',
        e: '成果待确认',
        
        f: '领导待审批',
        g: '待技术员确认',
        h: '成果待确认',
        i: '成果已确认'
        	
    });

    //专题流程管理-->需求类型 {{ exports | DM_requ_type }}
    filterFactory('DM_requ_type', {
        1: '数据提取',
        2: '专题分析',
        3: '统计报表'
    });
    
    //专题流程管理-->流程类型 {{ exports | DM_flow_type }}
    filterFactory('DM_flow_type', {
        'inside': '专题、报表取数、统计',
        'outside': '跨机构取数',
        'tech': '技术需求'
    });

    //专题流程管理-->安全级别 {{ exports | DM_security_lev }}
    filterFactory('DM_security_lev', {
        1: '公开',
        2: '绝密'
    });

    //专题流程管理-->紧急程度 {{ exports | DM_emergency_lev }}
    filterFactory('DM_emergency_lev', {
        1: '一般',
        2: '紧急'
    });
    
    //专题流程管理-->数据来源  {{ exports | DM_data_sources }}
    filterFactory('DM_data_sources', {
        1: '自由业务系统',
        2: '其他业务系统',
        3: '外部数据'
    });
    
    //专题流程管理-->数据收取方式  {{ exports | DM_data_extr_type }}
    filterFactory('DM_data_extr_type', {
        1: '全量',
        2: '增量',
        3: '抽样'
    });
    
    //专题流程管理-->是否补录 {{ exports | DM_app_record }}
    filterFactory('DM_app_record', {
        0: '否',
        1: '是'
    });
    
    //专题流程管理-->任务编码 {{ exports | DM_task_code }}
    filterFactory('DM_task_code', {
		'busBegin': '业务需求申请',
		'busCheck': '业务领导审批',
		'techContact': '技术联系人操作',
		'techCheck': '技术领导审批分配',
		'techConfirm': '技术员需求确认',
		'fruitConfirm': '成果确认',
		'techBegin': '技术员发起申请',
		'proTechBegin': '省技术员发起申请',
		'proTechCheck': '省内领导审批',
		'proContact': '省内专门联系人操作',
		'groupContact': '集团技术联系人接收',
		'groupCheck': '集团领导审批分配',
		'groupConfirm': '需求确认'
    });
    
  //专题流程管理-->确认状态 {{ exports | DM_fruit_opr_id }}
    filterFactory('DM_fruit_opr_id', {
		0: '待确认',
		1: '已确认',
		2: '被驳回'
    });
    
  //专题流程管理-->发布状态 {{ exports | DM_public_state }}
    filterFactory('DM_public_state', {
		0: '未发布',
		1: '已发布'
    });
    
    //数据接收与下载-->异常信息监控的处理状态 {{ exports | ESB_abnormal }}
    filterFactory('ESB_abnormal', {
        1: '未处理',
        2: '已处理 '
    });
    filterFactory('DP_merge', {
        'init': '待执行',
        'running': '执行中 ',
        'finished':'执行成功',
        'failure':'执行失败'
    });
    filterFactory('DP_clean', {
        'init': '待清洗',
        'running': '清洗中 ',
        'finished':'清洗成功',
        'failure':'清洗失败'
    });
    filterFactory('DP_unique', {
        'init': '待排重',
        'running': '排重中 ',
        'finished':'排重成功',
        'failure':'排重失败'
    });
    filterFactory('DP_post', {
        'init': '待匹配',
        'running': '匹配中 ',
        'finished':'匹配成功',
        'failure':'匹配失败'
    });
    filterFactory('DP_rebuild', {
        'init': '待重构',
        'running': '重构中 ',
        'finished':'重构成功',
        'failure':'重构失败'
    });
    filterFactory('DP_merge', {
        'init': '待整合',
        'running': '整合中 ',
        'finished':'整合成功',
        'failure':'整合失败'
    });
    
    filterFactory('DP_assess', {
        '': '待评估',
        'init': '待评估',
        'running': '评估中 ',
        'finished':'评估成功',
        'failure':'评估失败'
    });
    
  //订阅通知^订阅管理-->订阅类型 {{ exports | RSM_subscribeStats }}
    filterFactory('RSM_subscribeMethod', {
        1: '邮件',
        2: '短信'
    });
    
    //订阅通知^订阅管理-->推送方式 {{ exports | RSM_subscribeStats }}
    filterFactory('RSM_pushMethod', {
        1: '手工 ',
        2: '系统周期'
    });

    //订阅通知^订阅管理-->发布状态 {{ exports | RSM_subscribeStats }}
    filterFactory('RSM_subscribeStats', {
        0: '未发布',
        1: '已发布 ',
        2: '已下线 '
    });

    //订阅通知^订阅管理-->订阅状态 {{ exports | RSU_subscribeType }}
    filterFactory('RSU_subscribeType', {
        1: '未订阅 ',
        2: '已订阅'
    });
    
    //订阅通知^订阅管理-->订阅记录状态 {{ exports | RSR_subscribeState }}
    filterFactory('RSR_subscribeState', {
        1: '退订 ',
        2: '订阅'
    });

    //数据分析-->同步周期 {{ exports | DSL_syncsycle }}
    filterFactory('DSL_syncsycle', {
        3: '年 ',
        1: '季度',
        17:'月',
        16:'周',
        15:'一次性',
        14:'日'
        
    });
    
    //数据分析-->同步周期 {{ exports | DSL_anaStatus}}
    filterFactory('DSL_anaStatus', {
    	 	32:	'已完成',
    	 	33:	'已失败',
    	 	30:	'待提交',
    	 	31:	'待执行',
    	 	44:	'分析中'

        
    });

    //数据分析-->审核状态{{ exports | DSL_apprstatus }}
    filterFactory('DSL_apprstatus', {
        43: '未提交 ',
        45: '已删除',
        22: '待审核',
        23: '已驳回',
        24: '已通过'
    });
    //数据分析-->审核状态{{ exports | DSL_cleanMethod }}
    filterFactory('DSL_cleanMethod', {
        1: '手动 ',
        2: '自动'
        
    });
    
  //订阅通知^订阅管理-->订阅日志推送状态 {{ exports | RSL_pushStats }}
    filterFactory('RSL_pushStats', {
    	0: '失败',
        1: '成功 '
    });
    //数据分析-->分析流程固化 {{ exports | DS_status }}
    filterFactory('DRL_status', {
    	1: '待审批',
        2: '审批中 ',
        3: '待发布 ',
        4: '已发布 '
    }); 
});
