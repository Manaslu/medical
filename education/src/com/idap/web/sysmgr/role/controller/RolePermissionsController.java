package com.idap.web.sysmgr.role.controller;

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
import com.idp.sysmgr.role.entity.RolePermissions;
import com.idp.sysmgr.role.service.RolePermissionsService;

@Controller
@RequestMapping(value = "/rolePermissions")
public class RolePermissionsController extends
		BaseController<RolePermissions, Long> {

	@Resource(name = "rolePermissionsService")
	public void setBaseService(IBaseService<RolePermissions, Long> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "rolePermissionsService")
	public IBaseService<RolePermissions, String> rolePermissionsService;

	@Resource(name = "rolePermissionsService")
	public RolePermissionsService permissionService;

	@RequestMapping(params = "list=true", method = RequestMethod.GET)
	@ResponseBody
	public List<RolePermissions> findList(@RequestParam("params") String values) {
		List<RolePermissions> menus = rolePermissionsService.findList(JsonUtils
				.toMap(values));
		return menus;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> create(RolePermissions entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			// 先删除以前设置的权限，然后重新设置一遍
			Map<String, Object> rolePermissions = new HashMap<String, Object>();
			rolePermissions.put("roleId", entity.getRoleId());
			this.rolePermissionsService.delete(rolePermissions);

			if (!StringUtils.isEmpty(entity.getMenuIds())) {
				String[] menuId = entity.getMenuIds().split(",");
				// 循环给角色赋权限
				List<Object[]> roles = new ArrayList<Object[]>();
				for (String id : menuId) {
					Object[] role = new Object[3];
					int index = 0;
					role[index++] = entity.getRoleId();
					role[index++] = id;
					role[index++] = "1";
					roles.add(role);
				}
				//批量插入角色分配的菜单
				permissionService.batchUpdate(roles);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
}
