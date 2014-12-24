package com.idap.web.sysmgr.user.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.utils.StringUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.user.entity.UserRoleRela;
import com.idp.sysmgr.user.service.IUserService;

@Controller
@RequestMapping(value = "/userRoleRela")
public class UserRoleRelaController extends BaseController<UserRoleRela, Long> {

	@Resource(name = "userRoleRelaService")
	public void setBaseService(IBaseService<UserRoleRela, Long> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name="userRoleRelaService")
	public IBaseService<UserRoleRela,String> userRoleRelaService;
	
	@Resource(name = "userService")
	public IUserService permissionService;
	
	
	@RequestMapping(params = "list=true",method = RequestMethod.GET)
	@ResponseBody
	public List<UserRoleRela> findList(@RequestParam("params") String values){
		List<UserRoleRela> roles=userRoleRelaService.findList(JsonUtils.toMap(values));
		return roles;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> create(UserRoleRela entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			//先删除以前设置的角色权限，然后重新设置一遍
			Map<String, Object> userRoleRela = new HashMap<String, Object>();
			userRoleRela.put("userId", entity.getUserId());
			this.userRoleRelaService.delete(userRoleRela);
			
			String roleIds= entity.getRoleIds();
			if(!StringUtils.isEmpty(roleIds)){
				String[] roleId=roleIds.split(",");
				//循环给用户赋角色权限
				List<Object[]> users = new ArrayList<Object[]>();
				for(String id : roleId){
					Object[] user = new Object[3];
					int index = 0;
					user[index++] = entity.getUserId();
					user[index++] = id;
					user[index++] = "1";
					users.add(user);
				}
				//批量插入用户分配的角色
				permissionService.batchUpdate(users);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
}
