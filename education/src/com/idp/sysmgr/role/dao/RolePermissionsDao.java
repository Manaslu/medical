package com.idp.sysmgr.role.dao;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.role.entity.RolePermissions;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-12 15:21:42
 * @开发人员：huhanjiang
 * @功能描述：角色权限DAO
 * @修改日志： ###################
 */

@Repository("rolePermissionsDao")
public class RolePermissionsDao extends DefaultBaseDao<RolePermissions, Long> {
	public String getNamespace() {
		return RolePermissions.class.getName();
	}
}
