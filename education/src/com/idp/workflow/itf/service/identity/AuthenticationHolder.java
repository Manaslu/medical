package com.idp.workflow.itf.service.identity;

import com.idp.workflow.vo.identity.AuthentiUsers;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;

/**
 * 保存当前认证身份信息
 * 
 * @author panfei
 * 
 */
public class AuthenticationHolder {

	private static ThreadLocal<AuthentiUsers> authenticatedUserIdThreadLocal = new ThreadLocal<AuthentiUsers>();

	public static AuthentiUsers getCurrentAuthentiUsers() {
		AuthentiUsers retobj = authenticatedUserIdThreadLocal.get();
		if (retobj == null) {
			AuthentiUsers autusers = new AuthentiUsers();
			autusers.setPermissionType(CheckType.None);
			setCurrentAuthentiUsers(autusers);
			return autusers;
		}
		return retobj;
	}

	public static void setCurrentAuthentiUsers(AuthentiUsers autusers) {
		authenticatedUserIdThreadLocal.set(autusers);
	}

	/**
	 * 设置认证用户信息
	 * 
	 * @param flowMandataryId
	 *            被委托人
	 * @param flowMandatorId
	 *            委托人
	 * @param permissionType
	 *            权限检查认证类型
	 */
	public static void setCurrentAuthentiUsers(String flowMandataryId,
			String flowMandatorId, CheckType permissionType) {
		AuthentiUsers autusers = new AuthentiUsers();
		autusers.setTaskAssignerId(flowMandataryId);
		autusers.setTaskOwnerId(flowMandatorId);
		autusers.setPermissionType(permissionType);
		authenticatedUserIdThreadLocal.set(autusers);
	}
}
