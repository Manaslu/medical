package com.idp.sysmgr.role.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.role.entity.Role;

/**
 * #############################
 * 
 * @创建日期：2014-4-21 14:25:42
 * @开发人员：hu
 * @功能描述：角色管理dao
 * @修改日志： #############################
 */

@Repository("roleDao")
public class RoleDao extends DefaultBaseDao<Role, Long> {
	public String getNamespace() {
		return Role.class.getName();
	}
	
	//查询用户配置的角色
	public List<Role> gets(Role userId) {
		return get("getRole", userId);

	}

	public List<Role> get(String key, Role userId) {
		return this.getSqlSession().selectList(sqlKey(key), userId);

	}
}
