package com.idp.workflow.itf.service;

/**
 * 工作流代理类接口
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowProxy {

	/**
	 * 获取工作流引擎服务
	 * 
	 * @return
	 */
	IWorkFlowEngineService getWorkFlowEngineService();

	/**
	 * 获取工作流管理服务
	 * 
	 * @return
	 */
	IWorkFlowManageService getWorkFlowManageService();

	/**
	 * 获取工作流查询服务
	 * 
	 * @return
	 */
	IWorkFlowQueryService getWorkFlowQueryService();

	/**
	 * 释放卸载一些资源，不建议直接调用，由容器管理
	 */
	@Deprecated
	void disPose();
}
