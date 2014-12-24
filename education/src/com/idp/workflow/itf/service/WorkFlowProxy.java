package com.idp.workflow.itf.service;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 服务归集代理基类
 * 
 * @author panfei
 * 
 */
public abstract class WorkFlowProxy implements IWorkFlowProxy {

	/**
	 * 工作流元数据
	 */
	protected IWorkFlowMetaData<?> tagretSource;

	public IWorkFlowMetaData<?> getTagretSource() {
		return tagretSource;
	}

	public void setTagretSource(IWorkFlowMetaData<?> tagretSource) {
		this.tagretSource = tagretSource;
	}

	/**
	 * 支持构造函数初始化
	 * 
	 * @param tagretSource
	 *            工作流元数据
	 * @throws WfBusinessException
	 */
	public WorkFlowProxy(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException {
		super();
		this.tagretSource = tagretSource;
		try {
			this.initServices(tagretSource);
		} catch (Exception e) {
			throw new WfBusinessException(e);
		}
	}

	/**
	 * 支持set参数传入工作流元数据，进行init-method初始化
	 */
	public WorkFlowProxy() {
		super();
	}

	/**
	 * 初始化工作流配置信息
	 */
	public abstract void initServices(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException;

	/**
	 * 初始化工作流配置信息，通过inti-method
	 * 
	 * @throws WfBusinessException
	 */
	public void initServices() throws WfBusinessException {
		try {
			this.initServices(getTagretSource());
		} catch (Exception e) {
			throw new WfBusinessException(e);
		}
	}

	private IWorkFlowEngineService workFlowEngineService;

	@Override
	public IWorkFlowEngineService getWorkFlowEngineService() {
		return workFlowEngineService;
	}

	public void setWorkFlowEngineService(
			IWorkFlowEngineService iWorkFlowEngineService) {
		workFlowEngineService = iWorkFlowEngineService;
	}

	private IWorkFlowManageService workFlowManageService;

	@Override
	public IWorkFlowManageService getWorkFlowManageService() {
		return workFlowManageService;
	}

	public void setWorkFlowManageService(
			IWorkFlowManageService workFlowManageService) {
		this.workFlowManageService = workFlowManageService;
	}

	@Override
	public IWorkFlowQueryService getWorkFlowQueryService() {
		return workFlowQueryService;
	}

	public void setWorkFlowQueryService(
			IWorkFlowQueryService workFlowQueryService) {
		this.workFlowQueryService = workFlowQueryService;
	}

	private IWorkFlowQueryService workFlowQueryService;
}
