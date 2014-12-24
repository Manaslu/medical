define(function (require, exports, module) {

    //菜单管理
    return function setApp(app) {
        app.controller('OmsMenuCtrl', ['$scope' , 'menuMap' , function ($scope , menuMap) {
            $scope.settings = {
                edit : {
                    enable : true,
                    showRemoveBtn : false,
                    showRenameBtn : false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view : {
                    autoCancelSelected : false,
                    selectedMulti : false
                },
                callback: {
                    onClick: function(e , _ , obj){
                        $scope.$apply(function(){
                            $scope.obj = obj;
                            $scope.parent = obj.getParentNode();
                        });
                    },
                    beforeDrag: beforeDrag,
                    beforeDrop: beforeDrop
                }
            };
            function beforeDrag(treeId, treeNodes) {
                for (var i=0,l=treeNodes.length; i<l; i++) {
                    if (treeNodes[i].drag === false) {
                        return false;
                    }
                }

                return true;
            }
            function beforeDrop(treeId, treeNodes, targetNode, moveType) {
                if(targetNode && targetNode.level >= 2){//层级最多2
                    return false;
                }
                return targetNode ? targetNode.drop !== false : true;
            }
            $scope.data = angular.copy(menuMap);

            $scope.edit = function(type){
                switch(type){
                    case "sibling" :
                    case "child" :
                        $scope.editObj = {};
                        break;
                    case "edit" :
                        $scope.editObj = $scope.obj;
                        break;
                }
                $scope.create = true;
                $scope.type = type;
            };

            $scope.remove = function(){
                $scope.obj = $scope.parent = null;
            };

            $scope.save = function(){
                $scope.obj = $scope.parent = null;
                $scope.create = null;
            }
        }]);
    }

});