/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    function setApp(app){
        app.controller('IndexCtrl' , function($scope){
            $scope.name = 'index / index (IndexCtrl)';
        });
    }
    return setApp;
});
