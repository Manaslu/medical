package com.idp.workflow.vo.identity;

import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;

/**
 * 认证用户信息
 * 
 * @author panfei
 * 
 */
public class AuthentiUsers {

	/**
	 * 任务被委派者
	 */
	private String taskAssignerId;

	/**
	 * 任务实际拥有者
	 */
	private String taskOwnerId;

	/**
	 * 认证方式检查类型
	 */
	private CheckType permissionType;

	public CheckType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(CheckType permissionType) {
		this.permissionType = permissionType;
	}

	public String getTaskOwnerId() {
		return taskOwnerId;
	}

	public void setTaskOwnerId(String taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}

	public String getTaskAssignerId() {
		return taskAssignerId;
	}

	public void setTaskAssignerId(String taskAssignerId) {
		this.taskAssignerId = taskAssignerId;
	}

}
