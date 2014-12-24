define(function (require, exports, module) {

    return function setApp(app) {
        app.controller('ResultCockpit2Ctrl', ['$scope' , 'ExportTables', function ($scope, ExportTables) {

            function arraymin(arr) {
                var vals = arr.map(function(item){
                	return item.value
                });
                return Math.min.apply(Math , vals)
            }

            function arraymax(arr) {
            	var vals = arr.map(function(item){
                	return item.value
                });
                return Math.max.apply(Math , vals)
            }

            ExportTables.query({isArray : true, params : "{}"}, function (data) {
                var minvalue = arraymin(data);
                var maxvalue = arraymax(data);

                $scope.option = {
                    title: {
                        text: '国内小包业务量',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    dataRange: {
                        min: minvalue,
                        max: maxvalue,
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    series: [
                        {
                            name: '中国',
                            type: 'map',
                            mapType: 'china',
                            itemStyle: {
                                normal: {label: {show: false}},
                                emphasis: {label: {show: false}}
                            },
                            data: data  //true [ {name : xxx , value : value} , {} ]
                        }
                    ]
                };
            });


           

        
 
        }]);
    };

});

