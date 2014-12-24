/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    function setApp(app){
        app.controller('IndexPage1Ctrl' , function($scope){
            $scope.name = 'index / Page1 (IndexPage1Ctrl)';
        });
    }
    return setApp;
});
