package com.idap.web.sysmgr.role.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.menu.dao.MenuTreeDao;
import com.idp.sysmgr.role.entity.Role;
import com.idp.sysmgr.role.entity.RolePermissions;
import com.idp.sysmgr.role.service.IRoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController<Role, Long> {

	@Resource(name = "roleService")
	public void setBaseService(IBaseService<Role, Long> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "menuTreeDao")
	private MenuTreeDao menuTreeDao;

	@Resource(name = "rolePermissionsService")
	public IBaseService<RolePermissions, String> rolePermissionsService;

	@Resource(name = "roleService")
	private IRoleService roleService;

	// 输入角色名称的时候查询数据库，防止重复
	@RequestMapping(params = "isArrays=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<Role> findByLists(@RequestParam("params") String values) {
		return this.getBaseService().findList(JsonUtils.toMap(values));
	}

	@RequestMapping(params = "list=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<Role> findByList(@RequestParam("params") String values) {
		// 符合用户角色配置的角色
		Pager<Role> pager = new Pager<Role>();
		pager.setCurrent(0);
		pager.setLimit(1000);
		List<Role> roleList = this.findByPager(pager, values).getData();
		return roleList;
	}

}
