package com.idp.workflow.vo.identity;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 默认角色VO
 * 
 * @author panfei
 * 
 */
public class RoleVO extends SuperVO implements IRoleVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1196693442843419875L;

	public RoleVO(String roleID, String roleName) {
		this(roleID, roleID, roleName);
	}

	/**
	 * 角色ID必输 角色编码和角色名称 非必输
	 * 
	 * @param roleID
	 * @param roleCode
	 * @param roleName
	 */
	public RoleVO(String roleID, String roleCode, String roleName) {
		this.roleID = roleID;
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

	private String roleID;

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	@Override
	public String getRoleID() {

		return roleID;
	}

	private String roleCode;

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Override
	public String getRoleCode() {

		return roleCode;
	}

	private String roleName;

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getRoleName() {

		return roleName;
	}

}
