package com.idp.workflow.itf.service.identity;

import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 身份权限
 * 
 * @author panfei
 * 
 */
public interface IDentityChecker {

	/**
	 * 设置任务的待办理人
	 * 
	 * @param userId
	 *            用户ID
	 * @throws WfBusinessException
	 */
	void setAssigneedUserId(String... userId) throws WfBusinessException;
}
