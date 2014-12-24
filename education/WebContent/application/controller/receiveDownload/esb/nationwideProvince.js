define(function (require, exports, module) {

    //全国至省中心数据核对
    return function setApp(app) {
        app.controller('ReceiveDownloadEsbNationwideProvinceCtrl', ['$scope' ,'DataCheck','Common', function ($scope,DataCheck,Common) {
        	 $scope.pager = DataCheck;
        	 $scope.provList=Common.query({isArray:true,params:"{}"});
             //$scope.provList=[{id:'福建',name:'福建'},{id:'山东',name:'山东'},{id:'江西',name:'江西'}];
         	 $scope.busiList=Common.query({code:true});
         		 //[{name:"营业系统"},{name:"投递系统"},{name:"农资分销系统"},{name:"客管系统"},{name:"集邮系统"},{name:"网运系统"},{name:"报刊系统"},{name:"邮资封片卡系统"},{name:"贺卡兑奖平台"},{name:"短信平台"},{name:"电商平台(机票)"}];
         	 $scope.params = {
      			   orderBy:'BUSINESS_DATE',
      			   order:'DESC',
      			   logProvinceNo:$scope.USER_INFO.provCode
      	     };
         	 
        }]);
    }

});