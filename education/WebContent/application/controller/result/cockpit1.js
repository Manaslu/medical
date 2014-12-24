define(function (require, exports, module) {
    var json = {'北京市': 11, '天津市': 12, '河北省': 13, '山西省': 14,
        '内蒙古': 15, '辽宁省': 21, '吉林省': 22, '黑龙江': 23,
        '上海市': 31, '江苏省': 32, '浙江省': 33, '安徽省': 34,
        '福建省': 35, '江西省': 36, '山东省': 37, '河南省': 41,
        '湖北省': 42, '湖南省': 43, '广东省': 44, '广西省': 45,
        '海南省': 46, '重庆市': 50, '四川省': 51, '贵州省': 52,
        '云南省': 53, '西藏': 54, '陕西省': 61, '甘肃省': 62,
        '青海省': 63, '宁夏': 64, '新疆': 65, '全国中心': 99,
        '香港': 81, '澳门': 82, '台湾': 83, '南海诸岛': 84
    }

    return function setApp(app) {
        app.controller('ResultCockpit1Ctrl', ['$scope' , 'TableColumns', function ($scope, TableColumns) {

            function arraymin(arr) {
                var min = arr[0].value;
                for (var i = 1; i < arr.length; i++) {
                    if (arr[i].value < min) {
                        min = arr[i].value;
                    }
                }
                return min;
            }

            function arraymax(arr) {
                var max = arr[0].value;
                for (var i = 1; i < arr.length; i++) {
                    if (arr[i].value > max) {
                        max = arr[i].value;
                    }
                }
                return max;
            }

            TableColumns.query({isArray: true, params: {year: '2014'}}, function (data) {
                var minvalue = arraymin(data);
                var maxvalue = arraymax(data);

                $scope.option = {
                    title: {
                        text: '导航&流转额',
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
                            selectedMode: 'single',
                            itemStyle: {
                                normal: {label: {show: false}},
                                emphasis: {label: {show: false}}
                            },
                            data: data
                        }
                    ]
                };
            });


            function getOrgCd(key) {
                var attr , name;
                if (angular.isObject(key)) {
                    for (attr in key) {
                        if (key[attr]) {
                            key = attr;
                            break;
                        }
                    }
                }

                if (!key) {
                    return 99;//默认全国中心
                }

                for (name in json) {
                    if (name == key) {
                        return json[name];
                    }
                    if (name.indexOf(key) > -1) {
                        return json[name];
                    }
                }

                return 99;
            }

            $scope.mapReady = function (api, config) {
                api.on(config.EVENT.MAP_SELECTED, function (param) {
                    $scope.org = getOrgCd(param.selected);
                    $scope.$apply();
                });
            };

            $scope.org = getOrgCd('全国中心');
        }]);
    };

});

