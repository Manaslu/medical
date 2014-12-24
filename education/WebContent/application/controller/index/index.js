define(function (require, exports, module) {
    //首页
    return function setApp(app) {
        app.controller('IndexIndexCtrl', ['$scope' , function ($scope) {

            //全国报刊2012年-2014年总体发展情况
            $scope.all_chart1 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: [2012,2013,2014]
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
                series: [
                    {
                        type: 'line',//柱状数据
                        name: '订阅量',
                        data: [1.47,1.52,1.74],
                        yAxis : 0
                    },
                    {
                        type: 'line',//柱状数据
                        name: '流转额',
                        data: [176.12,183.47,200.18],
                        yAxis : 1
                    }
                ]
            };


            //全国报刊2014年分省发展情况
            $scope.all_chart2 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: ['江苏','陕西','广东','上海','山西','湖北','辽宁']
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '2014年订阅量',
                        data: [154360.1342,132769.218661,116420.540846,113095.475732,101382.223181,81822.959463,93326.981217],
                        yAxis : 0,
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.y:.2f}</b><br/>'
                        }
                    },
                    {
                        type: 'line',//柱状数据
                        name: '2013年订阅量增幅',
                        data: [1.75 ,7.33,6.60,4.13,0.76,0.89,-2.15],
                        yAxis : 1,
                        tooltip: {
                            valueSuffix: '%'
                        }
                    },
                    {
                        type: 'line',//柱状数据
                        name: '2014年订阅量增幅',
                        data: [6.45,3.94,8.19,3.64,8.84,12.36,9.43],
                        yAxis : 1,
                        tooltip: {
                            valueSuffix: '%'
                        }
                    }
                ]
            };

            //重点省份贡献度分析
            $scope.all_chart3 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: ['山东省','北京市','重庆市']
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '2012',
                        data: [11.6,8.3,2.5],
                        yAxis : 0
                    },
                    {
                        type: 'column',//柱状数据
                        name: '2013',
                        data: [12.4,8.3,2.7],
                        yAxis : 0
                    },
                    {
                        type: 'column',//柱状数据
                        name: '2014',
                        data: [13.4,7.7,3.6],
                        yAxis : 0
                    },
                    {
                        type: 'line',//柱状数据
                        name: '2014年增幅',
                        data: [0.08,-0.09,0.32],
                        yAxis : 1,
                        tooltip: {
                            valueSuffix: '%'
                        }
                    }
                ]
            };

            //重点省人均流转额贡献度
            $scope.all_chart4 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: ['山东2012','北京2012','重庆2012','山东2013','北京2013','重庆2013','山东2014','北京2014','重庆2014']
                }],
                yAxis: [{ // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }, { // Primary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '人口数量(万人)',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }],
                tooltip: {
                    shared: true
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '人均流转额',
                        data: [1961.9,587.86,884.6,2018.6,9637,2919,2069,9684,2945],
                        yAxis : 0
                    },
                    {
                        type: 'line',//柱状数据
                        name: '净增流转额',
                        data: [42.4,12.1,8.8,41.3,12.8,9.3,36.6,13.8,12.2],
                        yAxis : 1,
                        tooltip: {
                            valueSuffix: '%'
                        }
                    }
                ]
            };

            //2014年重点省TOP10贡献度
            $scope.all_chart5 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: ['山东省','北京市','重庆市']
                }],
                yAxis : { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#4572A7'
                        }
                    }
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '流转额',
                        data: [4.6,2.4,1.1],
                        yAxis : 0
                    }
                ]
            };

            //重点行业订户整体分析
            $scope.all_chart6 = {
                chart: {
                    type: 'bar'
                },
                title: {
                    text: ''
                },
                xAxis : [{
                    categories: ['金融业','政府机关','教育机构']
                }],

                tooltip: {
                    formatter: function(){
                        return '<b>'+ this.series.name +'</b><br/>'+ this.point.category +
                            ': '+ Highcharts.numberFormat(this.point.y, 0) + "%";
                    }
                },
                yAxis: {
                    title: {
                        text: null
                    },
                    labels: {
                        formatter: function(){
                            return this.value + '%';
                        }
                    }
                },
                series: [{
                    name: '2014年流转额增幅',
                    data: [-3.25,4.76,8.79]
                },{
                    name: '2013年流转额增幅',
                    data: [17.55,17.04,38.6]
                },{
                    name: '2014年订户数增幅',
                    data: [5.81,5.92,9.26]
                },{
                    name: '2013年订户数增幅',
                    data: [11.44,12.15,0.46]
                }]
            };


            //不同档次小区流转额均值
            $scope.sh_chart1 = {
                title: {
                    text: ''
                },
                xAxis: {
                    categories: ['普通', '中低', '中', '中高', '高']
                },
                yAxis: {title: {text: ""}},
                tooltip: {
                    formatter: function () {
                        return this.x + ': ' + this.y;
                    }
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '流转额均值',
                        data: [17275, 24545, 25470, 32179, 46595]
                    }
                ]
            };

            //不同档次小区订阅覆盖率
            $scope.sh_chart2 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: ['普通', '中低', '中', '中高', '高']
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value}个',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '数量',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '小区数',
                        data: [2797, 465, 503, 725, 159],
                        yAxis : 0,
                        tooltip: {
                            valueSuffix: '  个'
                        }
                    },
                    {
                        type: 'column',//柱状数据
                        name: '订阅小区',
                        data: [2645, 445, 473, 708, 158],
                        yAxis : 0,
                        tooltip: {
                            valueSuffix: '  个'
                        }
                    },
                    {
                        type: 'line',//柱状数据
                        name: '覆盖率',
                        data: [95, 96, 94, 98, 99],
                        yAxis : 1,
                        tooltip: {
                            valueSuffix: '%'
                        }
                    }
                ]
            };

            //不同规模商务楼覆盖率
            $scope.sh_chart3 = {
                title: {
                    text: ''
                },
                xAxis: [{
                    categories: ['<10', '10(含)~20', '20(含)~50', '50(含)~']
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
                series: [
                    {
                        type: 'column',//柱状数据
                        name: '商务楼数量',
                        data: [1926, 167, 77, 37],
                        yAxis : 0
                    },
                    {
                        type: 'line',//柱状数据
                        name: '覆盖率',
                        data: [41.7, 77.8, 83.1, 75.7],
                        yAxis : 1,
                        tooltip: {
                            valueSuffix: '%'
                        }
                    }
                ]
            };

            //订户(地址ID的关联情况)
            $scope.sh_chart4 = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {text: ''},
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                        }
                    }
                },
                series: [
                    {
                        type: 'pie',
                        name: '订户',
                        data: [
                            ['关联上小区的订户', 3694],
                            ['关联上机构的订户', 12845],
                            ['关联商务楼的订户', 1203],
                            ['未关联上的订户', 29360]
                        ]
                    }
                ]
            };


        }]);
    }

});