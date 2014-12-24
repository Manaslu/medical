package com.idp.sysmgr.role.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * /** ########################
 * 
 * @创建日期：2014-4-21 14:21:42
 * @开发人员：hu
 * @功能描述：角色管理
 * @修改日志： ###################
 */

public class Role implements Serializable {

	private static final long serialVersionUID = 4736511926578194634L;

	private Long roleId;		//角色id 
	private String inRoleId;	//内置角色id
	private String roleName;	//角色名称
	private String roleDefault;	//角色默认名称
	private String roleDesc;	//角色描述
	private Date createTime=new Date();	//创建日期
	private Date updateTime=new Date();	//更新日期
	private String state;		//角色状态，1：代表启用，2：代表禁用
	private String orgCd;			//机构代码
	private String roleType; 	//角色类型 1:代表系统权限，2：代表内置权限 
	private Long userId;  		//用户id
	private String userRoleId;  //查询符合条件的角色
	
	
	public String getUserRoleId() {
		return userRoleId;
	}


	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	/** default constructor */
	public Role() {
	}

	
	public String getInRoleId() {
		return inRoleId;
	}


	public void setInRoleId(String inRoleId) {
		this.inRoleId = inRoleId;
	}


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDefault() {
		return roleDefault;
	}

	public void setRoleDefault(String roleDefault) {
		this.roleDefault = roleDefault;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	
}
