define(function(require, exports, module) {

    // 数据评估
    return function setApp(app) {
        app.controller('DataIntegrationAssessmentCtrl', [
                '$scope',
                '$timeout',
                'DataSet',
                'DataSetAssess',
                'DataDefinition',
                'SystemCode',
                function($scope, $timeout, DataSet, DataSetAssess, DataDefinition,SystemCode) {
                    $scope.paramsQuery = {
                        'dataSourceType':'sysj',
                        'createUser' : $scope.USER_INFO.id,
                        'orderBy' : "CREATE_DATE",
                        'order' : 'desc'
                    };
                    $scope.params = angular.copy($scope.paramsQuery);

                    $scope.resetQuery = function() {
                        $scope.params = angular.copy($scope.paramsQuery);
                    };
                    $scope.pager = DataSet;
                    $scope.pageView = 0;
                    $scope.toPage = function(index) {
                        $scope.pageView = index;
                    };
                    $scope.defs = DataDefinition.query({
                        'isArray' : true,
                        'params' : angular.toJson({
                            "businessType" : "1",
                            "orderBy" : "CREATE_DATE",
                            "order" : "desc",
                            "createUser" : $scope.USER_INFO.id
                        })
                    });
                    
                    $scope.codes = SystemCode.query({
                        'isArray' : true,
                        'params' : "{}"
                    });
                    // 查看图表
                    $scope.detail = function(item) {
                        $scope.data = item;
                        var chartData = DataSetAssess.post({
                            isChart : 1,
                            dataSetId : item.dataSetId,
                            dataDefId : item.dataDefId,
                            dataSourceType:$scope.params.dataSourceType
                        }, function(jsonData) {
                            $scope.data.dataTotalCount=jsonData.dataTotalCount;
                            $scope.chart1List = jsonData.chart1List;// chart1 chart2 共用同一个数据集合
                            $scope.chart3List = jsonData.chart3List;
                            $scope.data.columnSize = jsonData.colomnSize;
                            $scope.data.notBlankCount = jsonData.notBlankCount;
                            var titleList = [];
                            var series1 = [ {
                                name : '空值量',
                                data : []
                            }, {
                                name : '唯一值个数',
                                data : []
                            }, {
                                name : '空字符数',
                                data : []
                            }, {
                                name : '零值数',
                                data : []
                            }

                            ];
                            for ( var i in jsonData.chart1List) {
                                var col = jsonData.chart1List[i];
                                titleList.push(col.colShowName);
                                series1[0].data.push(col.nullCount);
                                series1[1].data.push(col.uniqueCount);
                                series1[2].data.push(col.noValueCount);
                                series1[3].data.push(col.zeroCount);
                            }
                            $scope.chart3List[0].completeSituation = '完整';
                            $scope.chart3List[1].completeSituation = '基本完整';
                            $scope.chart3List[2].completeSituation = '可接受完整';
                            $scope.chart3List[3].completeSituation = '部分缺失';
                            $scope.chart3List[4].completeSituation = '基本缺失'
                            $scope.chart3List[5].completeSituation = '完全缺失';
                            var series0data = [];

                            for ( var i in jsonData.chart3List) {
                                var col = jsonData.chart3List[i];
                                series0data.push([ col.completeSituation, parseFloat(col.filterRatio) ]);
                            }

                            chartsConfig1.xAxis.categories = titleList;
                            chartsConfig1.series = series1;
                            chartsConfig3.series[0].data = series0data;
                            $scope.toPage(1);
                            $scope.chartsConfig1 = angular.copy(chartsConfig1);
                            $scope.chartsConfig3 = angular.copy(chartsConfig3);
                        });

                    };
                    // 执行评估
                    $scope.excute = function(item) {
                        DataSetAssess.post({
                            status : item.evaluateState,
                            execute : 1,
                            dataSetId : item.dataSetId
                        }, function() {
                            $scope.refresh('current', true);
                        });
                    };

                    $scope.open = function(index) {
                        switch (index) {
                        case 1:
                            $timeout(function() {
                                $scope.chartsConfig1 = chartsConfig1;
                            }, 1);
                            break;
                        case 2:
                            $timeout(function() {
                                $scope.chartsConfig2 = chartsConfig2;
                            }, 1);
                            break;
                        case 3:
                            $timeout(function() {
                                $scope.chartsConfig3 = chartsConfig3;
                            }, 1);
                            break;
                        }
                    };

                    // 轮廓报告详情页面
                    var chartsConfig1 = {
                        chart : {
                            type : 'column'
                        },
                        title : {
                            text : null
                        },
                        xAxis : {
                            categories : [ '姓名', '性别', '年龄', '手机号', '单位名称' ]
                        },
                        tooltip : {
                            headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
                            pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
                                    + '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
                            footerFormat : '</table>',
                            shared : true,
                            useHTML : true
                        },
                        plotOptions : {
                            column : {
                                pointPadding : 0.2,
                                borderWidth : 0
                            }
                        },
                        series : [ {
                            name : '空值量',
                            data : [ 49.9, 71.5, 106.4, 129.2, 144.0 ]

                        }, {
                            name : '唯一值个数',
                            data : [ 83.6, 78.8, 98.5, 93.4, 106.0 ]

                        }, {
                            name : '属性填充率',
                            data : [ 48.9, 38.8, 39.3, 41.4, 47.0 ]

                        } ]
                    };

                    // 记录完整性
                    var chartsConfig2 = {
                        chart : {
                            type : 'column'
                        },
                        title : {
                            text : "记录完整性"
                        },
                        xAxis : {
                            categories : [ '姓名', '性别', '年龄', '手机号', '单位名称' ]
                        },
                        tooltip : {
                            headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
                            pointFormat : '<tr><td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
                            footerFormat : '</table>',
                            shared : true,
                            useHTML : true
                        },
                        plotOptions : {
                            column : {
                                pointPadding : 0.2,
                                borderWidth : 0
                            }
                        },
                        legend : {
                            enabled : false
                        },
                        series : [ {
                            data : [ 49.9, 71.5, 106.4, 129.2, 144.0 ]
                        } ]
                    };

                    // 字段完整性
                    var chartsConfig3 = {
                        chart : {
                            plotBackgroundColor : null,
                            plotBorderWidth : null,
                            plotShadow : false
                        },
                        title : {
                            text : '字段完整性'
                        },
                        tooltip : {
                            pointFormat : '{series.name}: <b>{point.percentage:.2f}%</b>'
                        },
                        plotOptions : {
                            pie : {
                                allowPointSelect : true,
                                cursor : 'pointer',
                                dataLabels : {
                                    enabled : true,
                                    color : '#000000',
                                    connectorColor : '#000000',
                                    format : '<b>{point.name}</b>: {point.percentage:.2f} %'
                                }
                            }
                        },
                        series : [ {
                            type : 'pie',
                            name : '字段完整性',
                            data : [ [ '姓名', 45.0 ], [ '年龄', 26.8 ], {
                                name : '手机号',
                                y : 12.8,
                                sliced : true,
                                selected : true
                            }, [ '性别', 8.5 ], [ '单位名称', 6.2 ] ]
                        } ]
                    };

                    $scope.data = {
                        name : "数据A",
                        length : 3458,
                        attr : 5,
                        notNull : 17650,
                        list : [ {
                            name : "姓名",
                            length : "3458",
                            c : "",
                            notNull : "0",
                            pr : "100%"
                        }, {
                            name : "年龄",
                            length : "3458",
                            c : "",
                            notNull : "0",
                            pr : "100%"
                        }, {
                            name : "手机号",
                            length : "3458",
                            c : "",
                            notNull : "0",
                            pr : "100%"
                        }, {
                            name : "性别",
                            length : "3458",
                            c : "",
                            notNull : "0",
                            pr : "100%"
                        }, {
                            name : "单位名称",
                            length : "3458",
                            c : "",
                            notNull : "0",
                            pr : "100%"
                        } ]
                    };
                } ]);
    }

});