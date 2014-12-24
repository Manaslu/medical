package com.idp.sysmgr.role.service;

import java.util.List;

import com.idp.sysmgr.role.entity.Role;



/**
* ###################################################
* @创建日期：2014-4-21 14:35:42
* @开发人员：hu
* @功能描述：角色管理service
* @修改日志：
* ###################################################
*/
public interface IRoleService {
	//查询用户配置的角色
	List<Role> getRole(Role entity);
}
