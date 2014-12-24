package com.idp.sysmgr.user.entity;

import java.io.Serializable;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-13 17:21:42
 * @开发人员：huhanjiang
 * @功能描述：用户角色关系实体类
 * @修改日志： ###################
 */

public class UserRoleRela implements Serializable {

	private static final long serialVersionUID = 4736511926578194637L;
	
	private Long userId;		//用户ID
	private Long roleId;		//角色编号
	private Long isMain;		//Is_Main
	private String roleIds;		//角色编号
	private String roleName;	//角色名称
	private String roleType; 	//角色类型 1:代表系统权限，2：代表内置权限 
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getIsMain() {
		return isMain;
	}
	public void setIsMain(Long isMain) {
		this.isMain = isMain;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

}
