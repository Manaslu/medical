define(function (require, exports, module) {


    return function setApp(app) {

        //机构管理
        app.controller('OmsInstitutionCtrl', ['$scope' , 'Institution' ,'Common', '$q' , function ($scope, Institution,Common, $q) {

            OmsInstitutionZtreeCreate($scope, Institution);//创建ztree
            
            $scope.Institution = Institution;
            
            //所属省份,查询所有省份
        	$scope.model =Common.query({isArray:true,params:{
        		roleAll:1
        	}});
            //验证
            $scope.valid = function(data){
            	if(data && data.length){
            		return false;
            	}
            	return true;
            };
          
            $scope.getParams = function(orgCd){
            	if(orgCd){
            		return {
                        institutionList: true,
                        params: {
                            orgCd: orgCd
                        }
                    };
            	}
            };
            //校验机构编码的
            $scope.myValid = function(val){
            	if(val){
            		return /^[\da-z]+$/i.test(val);//只考虑数字与字母
            	}
            };
            //校验机构名称
            $scope.validName = function(val){
            	if(val){
            		return true
            	}else{
            		return false;
            	}
            };
            //校验邮编
        	$scope.validPost = function(val){
            	if(val){
            		return /^[0-9]+$/.test(val);//数字
            	}
            	return true;
            };
           

            var node;
            //删除
            $scope.$on('del', function (event, obj) {
                //为页面准备值
                $scope.obj = obj;
                Institution.query({
                	list: true,
                    params: {
                    	parentOrgCd: obj.orgCd
                    }
                }, function (list) {
                	if(list.length==0){
                		$scope.message="";
                	}else{
                		$scope.message="该机构有下级机构，不能删除。";
                	}
                });
                node = obj.getParentNode();
                $scope.$apply();
            });

            //修改
            $scope.$on('edit', function (event, obj, siblings) {
                //为页面准备值
                Institution.query({
                    institutionList: true,
                    params: {
                        orgCd: obj.orgCd
                    }
                }, function (list) {
                	$scope.obj = list[0];
                	$scope.obj.provCode = obj.provCode + '';;//to string
                });

                //上级的名称
                node = $scope.parent = obj.getParentNode();
                //本级的名称
                $scope.parents = obj;
                //判断是创建同级还是子级
                $scope.siblings = siblings;
                //页面用这参数判断是新增还是修改
                $scope.isEdit = true;
                node = $scope.parent = obj.getParentNode();
                $scope.isUpdate = true;
                $scope.$apply();
            });

            //新建
            $scope.$on('create', function (event, obj, siblings) {
                //创建同级的时候默认用99999，创建下级的时候用上级id
                if (obj.level == 0 && siblings) {
                    $scope.parentOrgCd =  $scope.USER_INFO.orgCd=='AA0000000000' ? '99999':obj.pId;
                } else {
                    //siblings为true时创建同级，为false时创建下级
                    $scope.parentOrgCd = siblings ? obj.pId : obj.id;
                }
                $scope.obj = {
                    parentOrgCd: $scope.parentOrgCd,
                    parentLeveCd: obj.level
                };
                //页面用这参数判断是新增还是修改
                //上级的名称
                node = $scope.parent = obj.getParentNode();
                //本级的名称
                $scope.parents = obj;
                //判断是创建同级还是子级
                $scope.siblings = siblings;
                $scope.isEdit = false;
                $scope.isCreate = true;
                //默认显示启用按钮
                $scope.obj.state = obj.level;
            	$scope.obj.provCode = obj.provCode + '';
            	$scope.obj.level = obj.level + '';
                $scope.$apply();
            });

            //保存修改删除机构树节点。
            $scope.save = function () {

                var defer = $q.defer();

                if ($scope.zTreeApi) {
                    if ($scope.isCreate) {//create
                        //新增机构
                        Institution.put($scope.obj, function () {
                            defer.resolve();
                        });
                    } else if ($scope.isUpdate) {//update
                    	//修改机构
                    	var obj = $scope.obj;
                    	
                    	Institution.save({
                    		orgName:obj.orgName,
                    		orgOname:obj.orgOname,
                    		orgId:obj.orgId,
                    		state:obj.state,
                    		postCode:obj.postCode,
                    		provCode:obj.provCode,
                    		orgCd:obj.orgCd
                    	} , function(){
                    		defer.resolve();
                    	});
                    } else {
                        //本级机构代码
                        var orgCd = $scope.obj.orgCd;

                        //按上下级关系查询所有机构树
                        Institution.query({
                            list: true,
                            params: {
                                parentOrgCd: orgCd
                            }
                        }, function (list) {
                            //根据机构代码删除机构
                            Institution.remove({
                                params: find(list, 'orgCd').concat(orgCd).join(",")//查找到本级机构及下级机构代码
                            }, function () {
                                defer.resolve();
                            });
                        });
                    }
                    
                    //新增修改删除机构后刷新机构树
                    defer.promise.then(function(){
                    	$scope.zTreeApi.reAsyncChildNodes(node, "refresh");
                    	$('#editDialog').modal('hide');
                    });

                    $scope.isCreate = $scope.isUpdate = $scope.obj = $scope.parent = null;
                }
            };//end save


        }]);

        //获取本级及下级机构的所有orgCd
        function find(list, key) {
            var ret = [];
            list.forEach(function (item) {
                //如果有值把值放到数组里
                if (item[key]) {
                    ret.push(item[key]);
                }
                //如果有子节点，把子节点追加到数组。
                if (item.children) {
                    ret = ret.concat(find(item.children, key));
                }
            });
            return ret;
        }

        //创建机构树
        function OmsInstitutionZtreeCreate(scope) {
            scope.settings = {
        		async : {
        			enable : true,
        			url : "institution.shtml",
        			autoParam : ["id"],
        			type : 'get',
        			otherParam : { 
        				orgCd : scope.USER_INFO.orgCd,
        				lists : true
        			}
        		},
                view: {
                    selectedMulti: false,
                    dblClickExpand: false,
                    addHoverDom: addHoverDom,
                    removeHoverDom: removeHoverDom
                }
            };

            var IDMark_A = "_a";
            var button = [
                      '<button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#editDialog" siblings="true" style="margin-left: 10px;" name="edit">修改</button>',
                      '<button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#removeDialog" name="del">删除</button>',
                      '<button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#editDialog" name="create" siblings="true">创建同级机构</button>',
                      '<button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#editDialog" name="create">创建子机构</button>'
                      ]
            //动态添加自定义dom按钮，创建，修改，删除
            function addHoverDom(treeId, treeNode) {
                var nodeObj = $("#" + treeNode.tId + IDMark_A);
                if (treeNode._btn) {
                    return;
                }
                var isRoot = !treeNode.getParentNode();
                
                var btnDom = $('<div class="pull-right">' + (isRoot ? button[3] : button.join('')) + '</div>');
                nodeObj.append(btnDom);
                btnDom.on("click" , 'button', function () {
                    scope.$emit(this.getAttribute('name'), treeNode, !!this.getAttribute('siblings'));
                });
                treeNode._btn = btnDom;
            }

            //移除动态创建的按钮
            function removeHoverDom(treeId, treeNode) {
                treeNode._btn && treeNode._btn.remove();
                treeNode._btn = null;
            }
        }

    }

});