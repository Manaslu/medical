package com.idp.workflow.impl.service;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.IWorkFlowManageService;
import com.idp.workflow.itf.service.identity.IDentityManageService;
import com.idp.workflow.itf.service.prodef.IProDefineMangeService;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.itf.service.task.ITaskManageService;

/**
 * 工作流管理服务实现
 * 
 * @author panfei
 * 
 */
public class WorkFlowManageServiceImpl implements IWorkFlowManageService {

	public WorkFlowManageServiceImpl() {
		super();
	}

	public WorkFlowManageServiceImpl(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException {
		this(tagretSource.getAdapterSerivce(ITaskManageService.class),
				tagretSource.getAdapterSerivce(IProDefineMangeService.class),
				tagretSource.getAdapterSerivce(IProInstanceMangeService.class),
				tagretSource.getAdapterSerivce(IDentityManageService.class),
				tagretSource
						.getAdapterSerivce(IHistoricTaskManageService.class));
	}

	public WorkFlowManageServiceImpl(ITaskManageService taskManageService,
			IProDefineMangeService proDefineMangeService,
			IProInstanceMangeService proInstanceMangeService,
			IDentityManageService dentityManageService,
			IHistoricTaskManageService historicTaskManageService) {
		this.setTaskManageService(taskManageService);
		this.setProDefineMangeService(proDefineMangeService);
		this.setProInstanceMangeService(proInstanceMangeService);
		this.setDentityManageService(dentityManageService);
		this.setHistoricTaskManageService(historicTaskManageService);
	}

	private ITaskManageService taskManageService;

	public void setTaskManageService(ITaskManageService taskManageService) {
		this.taskManageService = taskManageService;
	}

	@Override
	public ITaskManageService getTaskManageService() {
		return taskManageService;
	}

	private IProDefineMangeService proDefineMangeService;

	public void setProDefineMangeService(
			IProDefineMangeService proDefineMangeService) {
		this.proDefineMangeService = proDefineMangeService;
	}

	@Override
	public IProDefineMangeService getProDefineMangeService() {
		return proDefineMangeService;
	}

	private IProInstanceMangeService proInstanceMangeService;

	public void setProInstanceMangeService(
			IProInstanceMangeService proInstanceMangeService) {
		this.proInstanceMangeService = proInstanceMangeService;
	}

	@Override
	public IProInstanceMangeService getProInstanceMangeService() {
		return proInstanceMangeService;
	}

	private IDentityManageService dentityManageService;

	public void setDentityManageService(
			IDentityManageService dentityManageService) {
		this.dentityManageService = dentityManageService;
	}

	@Override
	public IDentityManageService getDentityManageService() {
		return dentityManageService;
	}

	private IHistoricTaskManageService historicTaskManageService;

	@Override
	public IHistoricTaskManageService getHistoricTaskManageService() {
		return historicTaskManageService;
	}

	public void setHistoricTaskManageService(
			IHistoricTaskManageService historicTaskManageService) {
		this.historicTaskManageService = historicTaskManageService;
	}
}
