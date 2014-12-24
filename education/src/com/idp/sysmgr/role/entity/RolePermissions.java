package com.idp.sysmgr.role.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-12 15:21:42
 * @开发人员：huhanjian
 * @功能描述：角色权限
 * @修改日志： ###################
 */
public class RolePermissions implements Serializable {

	private static final long serialVersionUID = 4736511926578194637L;

	private Long roleId;					//角色编号
	private Long menuId;					//菜单ID
	private Date createTime=new Date();		//创建日期
	private Date updateTime=new Date();		//更新日期
	private String state;					//有效状态 ，1：启用，2禁用
	private String menuIds;					//菜单ID
	
	
	
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
