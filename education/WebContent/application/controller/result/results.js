define(function (require, exports, module) {
    var config = require('data/test-result.json');
    //成果展示
    function setApp(app) {
        app.controller('ResultResultsCtrl', ['$scope' , '$routeParams' , function ($scope, $routeParams) {

            /*
             TODO init 初始化
             var io = resource('getResults.do');

             io.get({id : $routeParams.id} , function(config){
                 $scope.params = angular.copy(config.params);
                 setData(angular.copy(config.datas));
             })
             */

            $scope.params = angular.copy(config.params);

            setData(angular.copy(config.datas));

            $scope.query = function () {
                var param = {};
                $scope.params.forEach(function (item) {
                    param[item.key] = item.value;
                });
                /*
                 TODO query 查询
                 io.get({data : true , id : $routeParams.id , params : angular.toJson(param)} , function(config){
                    setData(angular.copy(config.datas));
                 })
                 */
            };

            function setData(data) {
                data.forEach(function (item) {
                    var name = item.type + '_data';
                    if (CharData[name]) {
                        item.data = CharData[name](item.data);
                    }
                });
                $scope.datas = data;
            }
        }]);
    }

    /*
    数据模板解释：
     pie_data = {
         title : '',  主标题，可选，默认无
         subtitle : '', 副标题，可选，默认无
         categories : [], x轴标签，线图和柱状图时必须，默认无
         shared : true, 是否共享列提示标签可选，默认true
         valueSuffix : '°C',  单位,可选,默认无
         yAxisTitle : '', y周标题，可选，默认无
         data : [  数据，对应表图不同，对应数据格式也不同，请看demo json  ”data/test-result.json“
             ['key' , '占比value'],
             ['key' , '占比value'],
             ['key' , '占比value'],
             ['key' , '占比value']
         ]
     }
     */

    var CharData = {
        //饼图数据工厂
        pie_data: function (data) {
            return {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: data.title || ''
                },
                subtitle: {
                    text: data.subtitle || ''
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f} %</b>'//point.y是实际值
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: '占比',
                    data: data.data || []
                }]
            };
        },

        //柱状图数据工厂
        column_data: function (data) {
            return {
                chart: {
                    type: 'column'
                },
                title: {
                    text: data.title || ''
                },
                subtitle: {
                    text: data.subtitle || ''
                },
                xAxis: {
                    categories: data.categories || []
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: data.yAxisTitle || ''
                    }
                },
                tooltip: {
                    valueSuffix: data.valueSuffix || '',
                    shared : !!data.shared
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: data.data || []
            };
        },

        //线图数据工厂
        line_data: function (data) {
            return {
                title: {
                    text: data.title || '',
                    x: -20 //center
                },
                subtitle: {
                    text: data.subtitle || '',
                    x: -20
                },
                xAxis: {
                    categories: data.categories
                },
                yAxis: {
                    title: {
                        text: data.yAxisTitle || ''
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: data.valueSuffix || '',
                    shared : !!data.shared
                },
                series: data.data || []
            };
        },

        //表格数据工厂
        table_data: function (data) {
            return data;
        }
    };


    return setApp;

});