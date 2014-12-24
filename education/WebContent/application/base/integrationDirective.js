//Created by xiaobai on 2014/6/30.
define(function(require){
    //@type {Angular.module}
   var app = require('./directive');

    app.directive('integrationView' , function(){
        return {
            scope : {
                options : '=integrationView'
            },
            link : function(scope , el , attr){
                var Render = _.template($(attr.viewMode == 1 ? '#integration-table1' : '#integration-table2').html(), null, {
                    variable: 'data'
                });

                scope.options.getName = function(column , has){
                    if(has){
                        if(column._rule){
                            column = column._rule.b;
                        }else{
                            column = column._merge;
                        }
                    }
                    return column._dbName + ' ' + column.columnDesc;
                };

                scope.options.getVal = function(column , mergeColumn , index){
                    var meDb = column._meDb[index] || {},//字段A
                        pDb = column._pDb[index] || {};//字段B

                    if(column._rule){//如果是整合规则,默认返回自己数据集的值
                        return meDb[column.columnName];
                    }

                    var ret = [];

                    //如果是整合字段，
                    // 则选中A字段
                    // 或者选中B字段，
                    // 或者都没有选中的情况下，必须的，将数据集A的字段做收集
                    if(column._chk || (!column._chk && mergeColumn && !mergeColumn._chk)){
                        putVal(meDb[column.columnName]);
                    }

                    //如果有选中B字段，则将B字段加入集合
                    if(mergeColumn && mergeColumn._chk){
                        putVal(pDb[mergeColumn.columnName]);
                    }

                    //字段没有任何选中，默认选中字段A
                    if(!ret.length){
                        putVal(meDb[column.columnName]);
                    }

                    //如果是number类型，则相加
                    if(column && mergeColumn && column.dataType == 'number' && mergeColumn.dataType == 'number'){
                        return ret.length <= 1 ? ret[0] || '' : ret[0] + ret[1];
                    }

                    return ret.join(',');

                    function putVal(val){
                        val && ret.push(val);
                    }
                };

                scope.$watch('options.change' , function(){
                    buildData();//优先整合依据，然后整合，然后dbA字段，然后dbB字段
                    //生成头部,当viewMode = 1的时候
                    el[0].innerHTML = Render(scope.options);//生成尾部
                });

                //合并字段
                el.on('change',  'select' , function(){//选择和并列
                    var select = $(this),
                        index = select.data('index'),
                        self = getColumn(index),
                        merge = self.mergeKeys[select.val()];

                    self._merge = merge;
                    merge._merge = self;

                    scope.options.change = !scope.options.change;

                    scope.$apply();
                });

                //字段值优先
                el.on('change',  ':checkbox[name=value]' , function(){
                    var checkbox = $(this),
                        index = checkbox.data('index');

                    getColumn(index)._chk = checkbox.prop('checked');
                    scope.options.change = !scope.options.change;

                    scope.$apply();
                });

                //数据有效性
                el.on('change',  ':checkbox[name=availability]' , function(){
                    var checkbox = $(this),
                        index = checkbox.data('index');

                    getColumn(index)._availability = checkbox.prop('checked');
                    scope.options.change = !scope.options.change;

                    scope.$apply();
                });

                //取消合并
                el.on('click' , 'a[name=cancel]' , function(){
                    var a = $(this),
                        index = a.data('index');
                    var column = getColumn(index);

                    column._chk = column._merge._chk = null;//取消选中
                    column._merge._mgCls = column._mgCls = '';//样式取消
                    column._merge = column._merge._merge = null;//取消关联

                    scope.options.change = !scope.options.change;

                    scope.$apply();
                });

                //得到当前操作列
                function getColumn(index){
                    return scope.options.columns[index];
                }

                //格式化列
                function buildData(){
                    var columns = [],
                        columns1 = scope.options.dbAColumns,
                        columns2 = scope.options.dbBColumns;
                    clsHashMap = {};

                    columns1.forEach(function(column){
                        //如果是整合依据
                        if(column._rule){
                            //记录处理
                            columns.push(column._rule.a , column._rule.b);
                        }
                        if(column._merge){
                            if(!column._chk && !column._merge._chk){//如果都没有呗选中
                                column._chk = true;
                            }
                        }
                    });

                    columns1.forEach(function(column){
                        //如果是合并
                        if(column._merge){
                            //记录处理
                            column._merge._merge = column;
                            columns.push(column , column._merge);
                        }
                    });

                    columns1.forEach(function(column){
                        //other
                        if(!column._merge && !column._rule){
                            //记录处理
                            columns.push(column);
                        }
                    });

                    columns2.forEach(function(column){
                        //other
                        if(!column._merge && !column._rule){
                            //记录处理
                            columns.push(column);
                        }
                    });

                    scope.options.columns = columns;

                    var prev;
                    columns.forEach(function(column){
                        column.mgCls = '';
                        column._prev = null;
                        if (prev && column._rule && (column._rule.a == prev || column._rule.b == prev)) {//如果是整合依据
                            prev._colspan += 1;
                            column._prev = prev;
                        }else if(prev && prev._merge == column){//下一个合并
                            prev._colspan += 1;
                            column._mgCls = 'success';
                            column._prev = prev;
                        } else if (column._merge) {//如果是合并
                            column._colspan = 1;
                            column._mgCls = 'success';
                            prev = column;
                        } else {//第一次，合并 or 依据 统一处理
                            column._colspan = 1;
                            prev = column;
                        }
                        column._class = (column._ruCls || column._mgCls || column._cls) + " " + (!column._availability ? 'no-availability' : '');
                        if(!column._rule && !column._merge){
                            column.mergeKeys = getMergeKey(column , column._key);
                        }else{
                            column.mergeKeys = null;
                        }
                    });
                }

                function getMergeKey(self , key){
                    return key.filter(function(item){
                        //筛选，类型必须一致
                        //过滤，已经属于整合依据的不许再次合并
                        //过滤，已经合并的也不许再次合并
                        //过滤，不合并的不许出现
                        return item.dataType == self.dataType && !item._rule && !item._merge && item._availability;
                    });
                }

            }
        }
    });
});
