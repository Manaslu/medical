package com.idp.workflow.itf.service;

import com.idp.workflow.itf.service.identity.IDentityManageService;
import com.idp.workflow.itf.service.prodef.IProDefineMangeService;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.itf.service.task.ITaskManageService;

/**
 * 工作流管理服务
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowManageService {

	/**
	 * 任务管理服务
	 * 
	 * @return
	 */
	ITaskManageService getTaskManageService();

	/**
	 * 工作流定义管理服务
	 * 
	 * @return
	 */
	IProDefineMangeService getProDefineMangeService();

	/**
	 * 工作流实例管理服务
	 * 
	 * @return
	 */
	IProInstanceMangeService getProInstanceMangeService();

	/**
	 * 身份认证管理管理服务
	 * 
	 * @return
	 */
	IDentityManageService getDentityManageService();

	/**
	 * 历史任务管理服务
	 * 
	 * @return
	 */
	IHistoricTaskManageService getHistoricTaskManageService();
}
