/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    function setApp(app){
        app.controller('RolePage1Ctrl' , function($scope){
            $scope.name = 'Role / Page1 (RolePage1Ctrl)';
        });
    }
    return setApp;
});
