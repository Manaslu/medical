define(function (require, exports, module) {

    //结果发布
    return function setApp(app) {
        app.controller('DataAnalyseResultsPublishingResultsCtrl', ['$scope' , 'Menu' , 'menuMap' , function ($scope , Menu , menuMap) {
            var api;
            $scope.settings = {
                view : {
                    autoCancelSelected : false,
                    selectedMulti : false
                },
                callback: {
                    beforeClick : function(_ , obj){
                        return obj.name === "分析结果展示";
                    },
                    onClick: function(e , _ , obj){
                        $scope.$apply(function(){
                            $scope.obj = obj;
                            $scope.subMenu = {parentId : obj.id};
                        });
                    }
                }
            };

            var data = Menu.query({
                findMenu : true
            });

            //成果展示

            //成果展示>分析结果展示.split('>')

            //menu = [
            //  {name : "成果展示" , id=1 , children : [
            //      {name : "分析结果展示" , id:xxx , children : [
            //
            // ]}
            // ]}
            // ];

            menuMap = findMenu(menuMap , '成果展示');
            menuMap = findMenu(menuMap.children , '分析成果展示');

            function findMenu(menu , name){
                for (var i = 0; i < menu.length; i++) {
                    var obj = menu[i];
                    if(obj.name == name){
                        return obj;
                    }
                }
            }

            $scope.put = function(){
                $scope.subMenu.putMenu = true;
                Menu.put($scope.subMenu , function(menu){
                    /**
                     * menu = {
                     *      parentId,
                     *      id,
                     *      name,
                     *      action,
                     *      template,
                     *      params
                     * }
                     */
                    menu.controllerName = 'ResultResultsShowMainCtrl';
                    menu.controllerUrl = '~/result/resultsShow/main';
                    menu.route = '/at/result/resultsShow/' + menu.action;
                    menuMap.push(menu);
                    api.refresh();
                    $scope.subMenu = null;
                });
            };

            //ztree控制api
            $scope.ready = function(ztreeApi){
                api = ztreeApi;
                api.expandAll(true);
            }
        }]);
    }

});