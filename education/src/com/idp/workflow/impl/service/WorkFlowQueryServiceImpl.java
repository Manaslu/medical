package com.idp.workflow.impl.service;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.IWorkFlowQueryService;
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
public class WorkFlowQueryServiceImpl implements IWorkFlowQueryService {

	public WorkFlowQueryServiceImpl() {
		super();
	}

	public WorkFlowQueryServiceImpl(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException {
		this(tagretSource.getAdapterSerivce(ITaskQueryService.class),
				tagretSource.getAdapterSerivce(IProDefineQueryService.class),
				tagretSource.getAdapterSerivce(IProInstanceQueryService.class),
				tagretSource.getAdapterSerivce(IDentityQueryService.class),
				tagretSource.getAdapterSerivce(IHistoricTaskQueryService.class));
	}

	public WorkFlowQueryServiceImpl(ITaskQueryService taskQueryService,
			IProDefineQueryService proDefineQueryService,
			IProInstanceQueryService proInstanceQueryService,
			IDentityQueryService dentityQueryService,
			IHistoricTaskQueryService historicTaskQueryService) {
		this.setTaskQueryService(taskQueryService);
		this.setProDefineQueryService(proDefineQueryService);
		this.setProInstanceQueryService(proInstanceQueryService);
		this.setDentityQueryService(dentityQueryService);
		this.setHistoricTaskQueryService(historicTaskQueryService);
	}

	private ITaskQueryService taskQueryService;

	public void setTaskQueryService(ITaskQueryService taskQueryService) {
		this.taskQueryService = taskQueryService;
	}

	@Override
	public ITaskQueryService getTaskQueryService() {
		return taskQueryService;
	}

	private IProDefineQueryService proDefineQueryService;

	public void setProDefineQueryService(
			IProDefineQueryService proDefineQueryService) {
		this.proDefineQueryService = proDefineQueryService;
	}

	@Override
	public IProDefineQueryService getProDefineQueryService() {
		return proDefineQueryService;
	}

	private IProInstanceQueryService proInstanceQueryService;

	public void setProInstanceQueryService(
			IProInstanceQueryService proInstanceQueryService) {
		this.proInstanceQueryService = proInstanceQueryService;
	}

	@Override
	public IProInstanceQueryService getProInstanceQueryService() {
		return proInstanceQueryService;
	}

	private IDentityQueryService dentityQueryService;

	public void setDentityQueryService(IDentityQueryService dentityQueryService) {
		this.dentityQueryService = dentityQueryService;
	}

	@Override
	public IDentityQueryService getDentityQueryService() {
		return dentityQueryService;
	}

	private IHistoricTaskQueryService historicTaskQueryService;

	@Override
	public IHistoricTaskQueryService getHistoricTaskQueryService() {
		return historicTaskQueryService;
	}

	public void setHistoricTaskQueryService(
			IHistoricTaskQueryService historicTaskQueryService) {
		this.historicTaskQueryService = historicTaskQueryService;
	}
}
