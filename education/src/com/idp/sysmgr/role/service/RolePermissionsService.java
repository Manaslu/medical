package com.idp.sysmgr.role.service;

import java.util.List;

public interface RolePermissionsService {
	//批量插入角色分配的菜单
	public void batchUpdate(List<Object[]> batchArgs);
	
	public void deleteMenu(List<Object[]> batchArgs);
}
