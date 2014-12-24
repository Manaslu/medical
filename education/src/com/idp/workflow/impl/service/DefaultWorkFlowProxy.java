package com.idp.workflow.impl.service;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.IWorkFlowEngineService;
import com.idp.workflow.itf.service.WorkFlowProxy;
import com.idp.workflow.itf.service.identity.IDentityManageService;
import com.idp.workflow.itf.service.identity.IDentityQueryService;
import com.idp.workflow.itf.service.prodef.IProDefineMangeService;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;
import com.idp.workflow.itf.service.task.ITaskManageService;
import com.idp.workflow.itf.service.task.ITaskQueryService;

/**
 * 默认工作流代理类
 * 
 * @author panfei
 * 
 */
public class DefaultWorkFlowProxy extends WorkFlowProxy {

	public DefaultWorkFlowProxy(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException {
		super(tagretSource);
	}

	@Override
	public void disPose() {
	}

	@Override
	public void initServices(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException {
		// 创建工作流引擎服务
		this.setWorkFlowEngineService(tagretSource
				.getAdapterSerivce(IWorkFlowEngineService.class));
		// 创建工作流管理服务
		this.setWorkFlowManageService(new WorkFlowManageServiceImpl(
				tagretSource));
		// 创建工作流查询服务
		this.setWorkFlowQueryService(new WorkFlowQueryServiceImpl(tagretSource));

	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IProDefineMangeService getProDefineMangeService() {
		return this.getWorkFlowManageService().getProDefineMangeService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IProInstanceMangeService getProInstanceMangeService() {
		return this.getWorkFlowManageService().getProInstanceMangeService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public ITaskManageService getTaskManageService() {
		return this.getWorkFlowManageService().getTaskManageService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IDentityManageService getDentityManageService() {
		return this.getWorkFlowManageService().getDentityManageService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IProDefineQueryService getProDefineQueryService() {
		return this.getWorkFlowQueryService().getProDefineQueryService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public ITaskQueryService getTaskQueryService() {
		return this.getWorkFlowQueryService().getTaskQueryService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IProInstanceQueryService getProInstanceQueryService() {
		return this.getWorkFlowQueryService().getProInstanceQueryService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IDentityQueryService getDentityQueryService() {
		return this.getWorkFlowQueryService().getDentityQueryService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IHistoricTaskManageService getHistoricTaskManageService() {
		return this.getWorkFlowManageService().getHistoricTaskManageService();
	}

	/**
	 * 可以支持spring动态工厂注入
	 * 
	 * @return
	 */
	public IHistoricTaskQueryService getHistoricTaskQueryService() {
		return this.getWorkFlowQueryService().getHistoricTaskQueryService();
	}
}
