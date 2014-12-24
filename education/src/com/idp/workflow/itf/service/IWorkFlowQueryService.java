package com.idp.workflow.itf.service;

import com.idp.workflow.itf.service.identity.IDentityQueryService;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;
import com.idp.workflow.itf.service.task.ITaskQueryService;

/**
 * 工作流查询服务
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowQueryService {

	/**
	 * 工作流任务查询服务
	 * 
	 * @return
	 */
	ITaskQueryService getTaskQueryService();

	/**
	 * 工作流定义查询服务
	 * 
	 * @return
	 */
	IProDefineQueryService getProDefineQueryService();

	/**
	 * 工作流实例查询服务
	 * 
	 * @return
	 */
	IProInstanceQueryService getProInstanceQueryService();

	/**
	 * 身份认证查询服务
	 * 
	 * @return
	 */
	IDentityQueryService getDentityQueryService();

	/**
	 * 历史任务查询服务
	 * 
	 * @return
	 */
	IHistoricTaskQueryService getHistoricTaskQueryService();
}
