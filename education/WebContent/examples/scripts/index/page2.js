/**
 * Created by huibo on 14-3-25.
 */
define(function (require, exports, module) {
    function setApp(app){
        app.controller('IndexPage2Ctrl' , function($scope){
            $scope.name = 'index / Page2 (IndexPage2Ctrl)';
        });
    }
    return setApp;
});
