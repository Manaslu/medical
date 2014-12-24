/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    function setApp(app){
        app.controller('RoleCtrl' , function($scope){
            $scope.name = 'Role / role (RoleCtrl)';
        });
    }
    return setApp;
});
