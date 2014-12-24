/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    function setApp(app){
        app.controller('RolePage2Ctrl' , function($scope){
            $scope.name = 'Role / Page2 (RolePage2Ctrl)';
        });
    }
    return setApp;
});
